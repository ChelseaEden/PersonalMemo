package com.example.g.personalmemo.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class Dbservice {
    private MyDatabaseHelper mytabopen = null;
    private static SQLiteDatabase db = null;
    private static String sql = null;
    private static final String tab_name = "user";

    public Dbservice(Context context) {
        this.mytabopen = new MyDatabaseHelper(context);//获得数据库操作实例
    }

    //添加数据
    public void save(User user){
        db = mytabopen.getWritableDatabase();
        sql = "insert into "+tab_name+"(username,password,question,answer) values(?,?,?,?)";
        db.execSQL(sql, new Object[]{user.getName(),user.getPassword(),user.getQuestion(),user.getAnswer()});
        db.close();            //为了提高性能sqliter数据库可以不关闭
    }

    public int checkbyname(String name){
        db = mytabopen.getWritableDatabase();
        sql = "select count(*) from "+tab_name+ " where username=? order by id" ;
        Cursor cur = db.rawQuery(sql, new String[]{name});
        cur.moveToFirst();
        int i = cur.getInt(0);
        cur.close();
        return i;

    }

    //删除数据
    public void del(Integer id){
        db = mytabopen.getWritableDatabase();
        sql = "delete from "+tab_name+" where id = ?";
        db.execSQL(sql, new Object[]{id});
        db.close();
    }

    //更新数据
    public void update(User user){
        db = mytabopen.getWritableDatabase();
        sql = "update "+tab_name+" set username=?,password=? where id=?";
        db.execSQL(sql, new Object[]{user.getName(), user.getPassword(), user.getId()});
        db.close();
    }

    //查询数据
    public User find(String Username){

        db = mytabopen.getReadableDatabase();
        sql = "select * from "+tab_name+ " where username=?";
        Cursor cur = db.rawQuery(sql, new String[]{Username});
        if(cur.moveToFirst()){
            String name = cur.getString(cur.getColumnIndex("username"));
            String password = cur.getString(cur.getColumnIndex("password"));
            String question = cur.getString(cur.getColumnIndex("question"));
            String answer = cur.getString(cur.getColumnIndex("answer"));
            return new User(name,password,question,answer);
        }
        cur.close();
        db.close();
        return null;
    }

    //获取数据总数目
    public long gettab(){
        db = mytabopen.getReadableDatabase();
        sql = "select count(*) from "+tab_name;
        Cursor cur = db.rawQuery(sql, null);
        cur.moveToFirst();
        long result = cur.getLong(0);
        return result;
    }
}
