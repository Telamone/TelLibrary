package it.telami.minecraft.commons.color;

import java.util.regex.Pattern;

/**
 * API for applying color, hex and gradient patterns.
 * @author Dr4aKy
 * @since 1.0.0
 */
public sealed interface Color permits TelColor {
    String  colorPattern      = "&";
    Pattern hexPatter         = Pattern.compile("&#[a-fA-F0-9]{6}");
    Pattern gradientPattern   = Pattern.compile("<#[a-fA-F0-9]{6}>((?!<#[a-fA-F0-9]{6}>).)*<#[a-fA-F0-9]{6}>");

    /**
     * Replace all the '&' characters with '§' from the given string.
     * @param s the given string
     * @return formatted string
     * @author Dr4aKy
     * @since 1.0.0
     */
    String color (final String s);

    /**
     * (Supported only in the <b>Premium</b> implementation) <p>
     * Replace all the '&' characters with '§' <b><i>IN</i></b>
     * the given string.
     * @param s the given string
     */
    void unsafeColor (final String s);

    /**
     * Apply hex formatting to the given string containing the '&#xxxxxx' pattern.
     * @param s the given string
     * @return formatted string
     * @author Dr4aKy
     * @since 1.0.0
     */
    String hex (final String s);

    /**
     * Apply gradient formatting to the given string containing the '<#xxxxxx> ... <#xxxxxx>' pattern.
     * @param s the given string
     * @return formatted string
     * @author Dr4aKy
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
     * @author Dr4aKy
     * @since 1.0.0
     */
    default String tolerantGradient (final String s) {
        return gradient(s);
    }

    /**
     * Completely format the given string.
     * @param s the given string
     * @return formatted string
     * @author Dr4aKy
     * @since 1.0.0
     */
    String fullTranslation (final String s);
}
