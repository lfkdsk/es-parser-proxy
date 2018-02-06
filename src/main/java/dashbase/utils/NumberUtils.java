package dashbase.utils;


/**
 * Parser Number.
 *
 * @author liufengkai
 *         Created by liufengkai on 2017/7/24.
 */
public final class NumberUtils {

    private NumberUtils() {
        throw new IllegalStateException("No instances!");
    }

    public static Number parseLong(String s) {
        return parseNumber(Long.parseLong(s));
    }

    public static Number parseDouble(String s) {
        return parseNumber(Double.parseDouble(s));
    }

    /**
     * parser number: int / long
     *
     * @param l long number
     */
    public static Number parseNumber(long l) {
        int i = (int) l;
        if (i == l) {
            return i;
        }
        return l;
    }

    /**
     * parser number: float / double
     *
     * @param d double number
     */
    public static Number parseNumber(double d) {
        long f = (long) d;
        if (f == d) {
            return parseNumber(f);
        }
        return d;
    }
}
