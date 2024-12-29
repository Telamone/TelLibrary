package it.telami.minecraft.commons.color;

/**
 * Responsible for the {@link Color}'s implementation.
 * @see ColorEngine#COMMUNITY
 * @see ColorEngine#STANDARD
 * @see ColorEngine#PREMIUM
 * @author Telami
 * @since 1.0.0
 */
public enum ColorEngine {
    /**
     * Uses community formatting techniques. <br>
     * You can optimize this yourself by pushing an update in the TelLibrary's GitHub
     * (<a href="https://github.com/Telamone/TelLibrary">TelLibrary</a>). <br>
     * The documentation of {@link ColorEngine#STANDARD} and {@link ColorEngine#PREMIUM}
     * will be updated every time a new version of this implementation gets approved
     * and tested!
     */
    COMMUNITY,
    /**
     * Functions properly only when a valid license is active. <br>
     * It's slightly faster in hex and faster in gradients formatting than the {@link ColorEngine#COMMUNITY} version.
     */
    STANDARD,
    /**
     * Functions properly only when a valid license is active. <br>
     * It's MUCH faster in hex and gradients formatting than the {@link ColorEngine#STANDARD} version.
     */
    PREMIUM
}
