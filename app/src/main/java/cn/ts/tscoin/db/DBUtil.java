package cn.ts.tscoin.db;

import android.text.TextUtils;

import java.util.Locale;

class DBUtil {


    static String getColumnType(String type) {
        String value = null;
        if (type.contains("String")) {
            value = " text ";
        } else if (type.contains("int") || type.contains("Integer")) {
            value = " integer ";
        } else if (type.contains("boolean") || type.contains("Boolean")) {
            value = " boolean ";
        } else if (type.contains("float") || type.contains("Float")) {
            value = " float ";
        } else if (type.contains("double") || type.contains("Double")) {
            value = " double ";
        } else if (type.contains("char")) {
            value = " varchar ";
        } else if (type.contains("long") || type.contains("Long")) {
            value = " long ";
        }
        return value;
    }

    static String getTableName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    static String capitalize(String string) {
        if (!TextUtils.isEmpty(string)) {
            return string.substring(0, 1).toUpperCase(Locale.US) + string.substring(1);
        }
        return string == null ? null : "";
    }

    //todo 更新Tag的Color数据
    public static void initTagColor(){

    }


}

