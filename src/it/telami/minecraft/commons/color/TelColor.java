package it.telami.minecraft.commons.color;

import it.telami.annotations.Optimized;

final class TelColor implements Color {
    TelColor () {
        throw new InstantiationError();
    }

    public ColorEngine getEngine () {
        return ColorEngine.PREMIUM;
    }

    @Optimized(tested = true)
    public String fullTranslation (String s) {
        return null;
    }

    @Optimized(tested = true)
    public String color (final String s) {
        return null;
    }

    @Optimized(tested = true)
    public void unsafeColor (final String s) {}

    @Optimized(tested = true)
    public String hex (final String s) {
        return null;
    }
    @Optimized(tested = true)
    public String gradient (final String s) {
        return null;
    }
    @Optimized(tested = true)
    public String tolerantGradient (final String s) {
        return null;
    }
}
