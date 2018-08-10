package cn.com.pfinfo.demo.util;

import java.util.Collection;

/**
 * @author panfei
 * @since 1.0
 * @version 1.0
 */
public class NullUtil {


    public static boolean isEmpty(Object obj) {
        return (obj == null);
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isEmpty(Object[] objs) {
        return ((objs == null) || (objs.length == 0));
    }

    public static boolean isNotEmpty(Object[] objs) {
        return !isEmpty(objs);
    }


    public static boolean isEmpty(Collection<?> objs) {
        return ((objs == null) || (objs.size() <= 0));
    }
    public static boolean isNotEmpty(Collection<?> objs) {
        return !isEmpty(objs);
    }


    public static boolean isEmpty(byte[] objs) {
        return ((objs == null) || (objs.length == 0));
    }

    public static boolean isNotEmpty(byte[] objs) {
        return !isEmpty(objs);
    }


    public static boolean isEmpty(String str) {
        return ((str == null) || (str.trim().length() == 0));
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(Long l) {
        return ((l == null) || (l.longValue() == 0L));
    }

    public static boolean isNotEmpty(Long l) {
        return !isEmpty(l);
    }

}