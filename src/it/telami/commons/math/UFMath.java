package it.telami.commons.math;

/**
 * Class dedicated to unbranched and fast math functions. <br>
 * Some are written directly using the bytecode for better
 * stack manipulation.
 * @author Telami
 * @since 1.0.0
 */
//Unbranched-Fast-Math
public final class UFMath {
    private UFMath () {}

    /**
     * Calculate the absolute value of the given 32-bit signed number.
     * @param n the 32-bit signed number
     * @return the absolute value
     * @author Telami
     * @since 1.0.0
     */
    public static int abs (int n) {
        //Hidden implementation...
        return 0;
    }
    /**
     * Calculate the absolute value of the given 64-bit signed number.
     * @param n the 64-bit signed number
     * @return the absolute value
     * @author Telami
     * @since 1.0.0
     */
    public static long abs (long n) {
        //Hidden implementation...
        return 0L;
    }

    /**
     * Calculate the value that best fit in the giving interval. <br>
     * if the given 32-bit signed number is lower than min, then
     * min is returned; if it is greater than max, then max is
     * returned; otherwise the original number is returned.
     * @param n the 32-bit signed number
     * @param min the minimum value in the given interval
     * @param max the maximum value in the given interval
     * @return a 32-bit signed number equal or higher than <b>min</b>
     *         and equal or lower to <b>max</b>
     * @author Telami
     * @since 1.0.1
     */
    public static int clamp (int n, final int min, final int max) {
        //Hidden implementation...
        return 0;
    }
    /**
     * Calculate the value that best fit in the giving interval. <br>
     * if the given 64-bit signed number is lower than min, then
     * min is returned; if it is greater than max, then max is
     * returned; otherwise the original number is returned.
     * @param n the 64-bit signed number
     * @param min the minimum value in the given interval
     * @param max the maximum value in the given interval
     * @return a 64-bit signed number equal or higher than <b>min</b>
     *         and equal or lower to <b>max</b>
     * @author Telami
     * @since 1.0.1
     */
    public static long clamp (long n, final long min, final long max) {
        //Hidden implementation...
        return 0L;
    }

    /**
     * Calculate the sum of the numbers from 0 to the given 64-bit signed number.
     * @apiNote This method works perfectly given a negative number too. <br>
     *          The method make use of 64-bit signed numbers as other primitives
     *          may easily overflow into others.
     * @param n the 64-bit signed number
     * @return a 64-bit signed number that is the sum of the numbers from 0
     * @author Telami
     * @since 1.0.0
     */
    public static long sum (final long n) {
        //Hidden implementation...
        return 0L;
    }

    /**
     * Calculate the given 64-bit signed number modulo {@code 0x7fe001}
     * without the use of any branch or division. <br>
     * This is useful in cryptography since any division or branch can
     * leak information about the data.
     * @param n the 64-bit signed number
     * @return {@code n} modulo {@code 0x7fe001}
     */
    public static int modulo_7fe001 (long n) {
        //Hidden implementation...
        return 0;
    }
}
