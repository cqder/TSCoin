package cn.ts.tscoin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.util.List;

import cn.ts.tscoin.tool.LogTool;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static String dbName = "cn_ts_tscoin";
    private static int dbVersion = 1;

    private Class myClazz;
    private List<Class> classList;

    MySQLiteHelper(Context context, Class clazz) {
        super(context, dbName, null, dbVersion);
        myClazz = clazz;
        classList = null;
    }


    MySQLiteHelper(Context context, List<Class> allClass) {
        super(context, dbName, null, dbVersion);
        classList = allClass;
        myClazz = null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        LogTool.w("myClazz != null"+(myClazz != null)+"\nclassList != null"+(classList != null));
        if (myClazz != null) {
            createTable(db, myClazz);
        }
        if (classList != null) {
            for (Class clz : classList) {
                createTable(db, clz);
            }
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 根据制定类名创建表
     */
    private static void createTable(SQLiteDatabase db, Class mClazz) {
        db.execSQL(getCreateTableSql(mClazz));
    }

    /**
     * 得到建表语句
     *
     * @param clazz 指定类
     * @return sql语句
     */
    private static String getCreateTableSql(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        String tabName = DBUtil.getTableName(clazz);
        sb.append("create table if not exists ").append(tabName).append(" (id  INTEGER PRIMARY KEY AUTOINCREMENT, ");
        Field[] fields = clazz.getDeclaredFields();

        for (Field fd : fields) {
            String fieldName = fd.getName();
            String fieldType = fd.getType().getName();
            if (!(fieldName.equalsIgnoreCase("_id") || fieldName.equalsIgnoreCase("id"))) {
                sb.append(fieldName).append(DBUtil.getColumnType(fieldType)).append(", ");
            }
        }
        int len = sb.length();
        sb.replace(len - 2, len, ")");
        LogTool.w("得到的语句为:" + sb.toString());
        return sb.toString();
    }


}
