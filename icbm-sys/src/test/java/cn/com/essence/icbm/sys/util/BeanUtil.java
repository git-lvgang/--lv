package cn.com.essence.icbm.sys.util;

import nl.jqno.equalsverifier.EqualsVerifier;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: huangll
 * Date: 2021-2-3
 * Description:
 */
public class BeanUtil {

    public static void coverGetMethod(Object obj) {
        List<String> list = new ArrayList<>();
        int num = 0;
        try {
            Class<?> cls = obj.getClass();
            Method[] methods = cls.getDeclaredMethods();
            for (Class<?> c = cls; c !=  Object.class; c = c.getSuperclass()) {
                try {
                    c.getDeclaredField("dynamicField");
                    list.add("dynamicField");
                } catch (NoSuchFieldException e) {
                }
                try {
                    c.getDeclaredField("pageSize");
                    list.add("pageSize");
                } catch (NoSuchFieldException e) {
                }
                try {
                    c.getDeclaredField("pageNo");
                    list.add("pageNo");
                } catch (NoSuchFieldException e) {
                }
            }
            if (list.size() > 0) {
                String s[] = new String[list.size()];
                for (int i = 0; i < list.size(); i ++) {
                    s[i] = list.get(i);
                }

                EqualsVerifier.simple().forClass(cls).withIgnoredFields(s).verify();
            } else {
                EqualsVerifier.simple().forClass(cls).verify();
            }
            for (Method m : methods) {
                if (m.getName().startsWith("get")) {
                    m.invoke(obj, null);
                } else if (m.getName().startsWith("is")) {
                    m.invoke(obj, null);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
