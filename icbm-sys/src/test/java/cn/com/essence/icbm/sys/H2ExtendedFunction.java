package cn.com.essence.icbm.sys;

import java.sql.Date;
import java.util.Objects;

/**
 * @author: huangll
 * @date: 2021-2-18
 */
public class H2ExtendedFunction {
    public static int findInSet(String s, String strList) {
        String[] list = strList.split(",");
        for (int i = 0; i < list.length; i++) {
            if (Objects.equals(s, list[i])) {
                return i + 1;
            }
        }
        return 0;
    }

    public static Date unixTimestamp(Long timestamp) {
        return new Date(timestamp);
    }

    public static Long fromUnixtime(Date date) {
        return date.getTime();
    }

}
