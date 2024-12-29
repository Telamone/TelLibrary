package it.telami.minecraft.commons.color;

import java.util.regex.Matcher;

final class CommunityColor implements Color {
    CommunityColor () {}

    public ColorEngine getEngine () {
        return ColorEngine.COMMUNITY;
    }

    public String color (final String s) {
        return s.replaceAll(colorPattern, "§");
    }

    public void unsafeColor (final String s) {
        throw new UnsupportedOperationException("This method is implemented only in the Premium version.");
    }

    //https://www.spigotmc.org/threads/i-need-help-using-hex-code-in-plugin.514190/
    public String hex (final String s) {
        final StringBuilder buffer = new StringBuilder(s.length());
        final Matcher matcher = hexPatter.matcher(s);
        while (matcher.find()) {
            final String group = matcher.group();
            matcher.appendReplacement(buffer, "§x§"
                    + group.charAt(2) + '§' + group.charAt(3) + '§'
                    + group.charAt(4) + '§' + group.charAt(5) + '§'
                    + group.charAt(6) + '§' + group.charAt(7));
        }
        return matcher.appendTail(buffer).toString();
    }

    public String gradient (final String s) {
        final StringBuilder buffer = new StringBuilder(s.length());
        final Matcher matcher = gradientPattern.matcher(s);
        while (matcher.find()) matcher.appendReplacement(
                buffer, addGradient(
                        s.substring(matcher.start() + 9, matcher.end() - 9),
                        s.substring(matcher.start() + 2, matcher.start() + 8),
                        s.substring(matcher.end() - 7, matcher.end() - 1)));
        return matcher.appendTail(buffer).toString();
    }
    //https://www.spigotmc.org/threads/help-with-gradient-function.452196/
    private String addGradient (final String str,
                                final String hex1,
                                final String hex2) {
        final int r1 = Integer.parseInt(hex1.substring(0, 2), 16);
        final int g1 = Integer.parseInt(hex1.substring(2, 4), 16);
        final int b1 = Integer.parseInt(hex1.substring(4, 6), 16);
        final int r2 = Integer.parseInt(hex2.substring(0, 2), 16);
        final int g2 = Integer.parseInt(hex2.substring(2, 4), 16);
        final int b2 = Integer.parseInt(hex2.substring(4, 6), 16);
        final char[] chars = str.toCharArray();
        final int rs = r1 - r2;
        final int gs = g1 - g2;
        final int bs = b1 - b2;
        final int distanceR, distanceG, distanceB;
        if (rs < 0) distanceR = (rs * -1) / (chars.length - 2);
        else distanceR = rs / (chars.length - 2);
        if (gs < 0) distanceG = (gs * -1) / (chars.length - 2);
        else distanceG = gs / (chars.length - 2);
        if (bs < 0) distanceB = (bs * -1) / (chars.length - 2);
        else distanceB = bs / (chars.length - 2);
        final StringBuilder sb = new StringBuilder();
        sb.append("&#")
                .append(String.format("%02X", r1))
                .append(String.format("%02X", g1))
                .append(String.format("%02X", b1))
                .append(chars[0]);
        for (int i = 1; i < chars.length - 1; i++) {
            int r, g, b;
            if (rs < 0) r = r1 + (distanceR * i);
            else r = r1 - (distanceR * i);
            if (gs < 0) g = g1 + (distanceG * i);
            else g = g1 - (distanceG * i);
            if (bs < 0) b = b1 + (distanceB * i);
            else b = b1 - (distanceB * i);
            if (rs == 0) r = r1;
            if (gs == 0) g = g1;
            if (bs == 0) b = b1;
            sb.append("&#")
                    .append(String.format("%02X", r))
                    .append(String.format("%02X", g))
                    .append(String.format("%02X", b))
                    .append(chars[i]);
        }
        sb.append("&#")
                .append(String.format("%02X", r2))
                .append(String.format("%02X", g2))
                .append(String.format("%02X", b2))
                .append(chars[chars.length - 1]);
        return hex(sb.toString());
    }

    public String fullTranslation (final String s) {
        return color(hex(gradient(s)));
    }
}
