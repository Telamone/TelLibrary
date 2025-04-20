package it.telami.minecraft.commons.color;

import java.util.regex.Pattern;

/**
 * API for applying color, hex and gradient patterns. <br>
 * Its implementations' details are cited in {@link ColorEngine}.
 * @apiNote All the implementation MUST be completely stateless for
 *          guaranteeing thread safety!
 * @author Telami
 * @since 1.0.0
 */
public sealed interface Color permits ColorImpl {
    String  colorPattern      = "&";
    Pattern hexPatter         = Pattern.compile("&#[a-fA-F0-9]{6}");
    Pattern gradientPattern   = Pattern.compile("<#[a-fA-F0-9]{6}>(?!<#[a-fA-F0-9]{6}>).*<#[a-fA-F0-9]{6}>");
    Color   impl              = new ColorImpl();

    /**
     * Get {@link Color Color}'s common implementation, always updated basing
     * on your license.
     * @return the common implementation
     */
    static Color getInstance () {
        return impl;
    }

    /**
     * Return the {@link ColorEngine engine} responsible for this {@link Color Color}'s implementation.
     * @return this implementation's {@link ColorEngine engine}
     * @author Telami
     * @since 1.0.0
     */
    ColorEngine getEngine ();

    /**
     * Replace all the '&' characters with '§' from the given string.
     * @param s the given string
     * @return formatted string
     * @author Telami
     * @since 1.0.0
     */
    String color (final String s);

    /**
     * (Supported only in the <b>{@link ColorEngine#PREMIUM Premium}</b> implementation) <p>
     * Replace all the '&' characters with '§' <b><i>IN</i></b>
     * the given string.
     * @param s the given string
     */
    void unsafeColor (final String s);

    /**
     * Apply hex formatting to the given string containing the '&#xxxxxx' pattern.
     * @param s the given string
     * @return formatted string
     * @author Telami
     * @since 1.0.0
     */
    String hex (final String s);

    /**
     * Apply gradient formatting to the given string containing the '<#xxxxxx> ... <#xxxxxx>' pattern.
     * @param s the given string
     * @return formatted string
     * @author Telami
     * @since 1.0.0
     */
    String gradient (final String s);

    /**
     * Apply gradient formatting to the given string containing the '<#xxxxxx> ... <#xxxxxx>' pattern. <br>
     * This differs from {@link Color#gradient(String)} for tolerating non-hexadecimal values, converting
     * the gradient with the respective, either bad or good, format. <br>
     * Additionally, this method performs slightly better in terms of performance.
     * @param s the given string
     * @return formatted string
     * @apiNote Since this method has been designed for skipping the hexadecimal format check, it
     *          is recommended to be used only after the correctness of the string has been verified.
     * @author Telami
     * @since 1.0.0
     */
    default String tolerantGradient (final String s) {
        return gradient(s);
    }

    /**
     * Completely format the given string.
     * @param s the given string
     * @return formatted string
     * @author Telami
     * @since 1.0.0
     */
    String fullTranslation (final String s);
}
