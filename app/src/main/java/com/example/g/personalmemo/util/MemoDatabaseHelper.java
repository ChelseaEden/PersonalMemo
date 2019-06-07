package com.example.g.personalmemo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDatabaseHelper extends SQLiteOpenHelper {
    private static final String db_name = "PersonalMemo.db";//数据文件的名字
    private static int NUMBER = 1;//当前数据库版本，用于升级
    private static final String table_name = "data";//表名
    private static String sql = null;//sql语句

    public MemoDatabaseHelper(Context context) {
        super(context, db_name, null, NUMBER);//数据库文件保存在当前应用所在包名:<包>/database/
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sql = "CREATE TABLE IF NOT EXISTS " + table_name + " (" +
                "id            INTEGER         PRIMARY KEY AUTOINCREMENT," +
                "title       VARCHAR(20)        NOT NULL,"+
                "info       VARCHAR(1000)        NOT NULL,"+
                "user       VARCHAR(20)        NOT NULL,"+
                "kindid       VARCHAR(5)        NOT NULL,"+
                "time       TimeStamp NOT NULL DEFAULT CURRENT_TIMESTAMP)";//创建数据库的SQL语句

        db.execSQL(sql);//执行SQL语句
    }

    /**
     * 当数据库进行升级是调用，这里通过NUMBER值来进行判断，数据库是否升级
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
