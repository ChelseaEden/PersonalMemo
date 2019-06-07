package com.example.g.personalmemo.util;

public class Memo {
    public Integer id;
    private String title;
    private int kindid;
    private String info;
    private String user;
    private String time;
    public Memo(int id,String title,String info,String user,int kindid,String time){
        this.id = id;
        this.user = user;
        this.title = title;
        this.kindid = kindid;
        this.info = info;
        this.time = time;
    }
    public String getTime(){
        return time;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public String getUser(){return user;}
    public int getKindId() {
        return kindid;
    }
    public String getInfo() {
        return info;
    }
    public void setKindId(int i){
        this.kindid = kindid;
    }

}