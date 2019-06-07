package com.example.g.personalmemo.util;

public class User {
    public Integer id;
    public String username;
    public String password;
    public String question;
    public String answer;

    public User(String _name,String _password,String _question,String _answer){
        username=_name;
        password=_password;
        question= _question;
        answer= _answer;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return username;
    }

    public void setName(String _name) {
        username = _name;
    }
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String _answer) {
        answer = _answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String _question) {
        question = _question;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String _password) {
        password = _password;
    }


    @Override
    public String toString() {
        return "id:"+this.getId()+"  name:"+this.getName()+"  password:"+this.getPassword();
    }
}
