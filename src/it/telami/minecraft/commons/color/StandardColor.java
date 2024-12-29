package it.telami.minecraft.commons.color;

import it.telami.annotations.Optimized;

final class StandardColor implements Color {
    StandardColor () {
        throw new InstantiationError();
    }

    public ColorEngine getEngine () {
        return ColorEngine.STANDARD;
    }

    @Optimized(tested = true)
    public String fullTranslation (final String s) {
        return null;
    }

    @Optimized(tested = true)
    public String color (final String s) {
        return null;
    }

    @Optimized(tested = true)
    public void unsafeColor (final String s) {
        throw new UnsupportedOperationException("This method is implemented only in the Premium version.");
    }

    @Optimized(tested = true)
    public String hex (final String s) {
        return null;
    }
    @Optimized(tested = true)
    public String gradient (final String s) {
        return null;
    }
}