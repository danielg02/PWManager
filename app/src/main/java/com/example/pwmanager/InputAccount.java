package com.example.pwmanager;

public class InputAccount {
    private String name, pass;

    public InputAccount(String name, String pass){
        this.name = name;
        this.pass = pass;
    }

    public String getName(){
        return this.name;
    }
    public String getPass(){
        return this.pass;
    }
}
