package com.example.g.personalmemo.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MemoDb  {
    private MemoDatabaseHelper mytabopen = null;
    private static SQLiteDatabase db = null;
    private static String sql = null;
    private static final String tab_name = "data";

    public MemoDb(Context context) {
        this.mytabopen = new MemoDatabaseHelper(context);//获得数据库操作实例
    }

    //添加数据
    public void save(String title,String info,String user,int kindid){
        db = mytabopen.getWritableDatabase();
        sql = "insert into "+tab_name+"(title,info,user,kindid) values(?,?,?,?)";
        db.execSQL(sql, new Object[]{title,info,user,kindid});
        db.close();            //为了提高性能sqliter数据库可以不关闭
    }

    //删除数据
    public void del(Integer id){
        db = mytabopen.getWritableDatabase();
        sql = "delete from "+tab_name+" where id = ?";
        db.execSQL(sql, new Object[]{id});
        db.close();
    }

    //更新数据
    public void update(String title,String info,int kindid,int id ){
        db = mytabopen.getWritableDatabase();
        sql = "update "+tab_name+" set title=?,info=?,kindid=?,time = datetime('now','localtime') where id=?";
        db.execSQL(sql, new Object[]{title, info, kindid, id});
        db.close();
    }
    public void checkbyname(String name){
        db = mytabopen.getWritableDatabase();
        sql = "select count(*) from "+tab_name+ " where user=? order by id" ;
        db.execSQL(sql, new Object[]{name});
        db.close();
    }

    //查询数据
    public List<Memo> find(String Username){

        db = mytabopen.getReadableDatabase();
        sql = "select * from "+tab_name+ " where user=? order by id" ;
        Cursor cur = db.rawQuery(sql, new String[]{Username});
        List<Memo> mlist = new ArrayList<Memo>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            int id = cur.getInt(cur.getColumnIndex("id"));
            String title = cur.getString(cur.getColumnIndex("title"));
            String info = cur.getString(cur.getColumnIndex("info"));
            String user = cur.getString(cur.getColumnIndex("user"));
            int kindid = cur.getInt(cur.getColumnIndex("kindid"));
            String time = cur.getString(cur.getColumnIndex("time"));
            mlist.add(new Memo(id,title,info,user,kindid,time));
        }
        cur.close();
        db.close();
        return mlist;
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
