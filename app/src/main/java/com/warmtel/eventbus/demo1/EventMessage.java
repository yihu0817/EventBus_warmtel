package com.warmtel.eventbus.demo1;

public class EventMessage {
    private String message;

    public int getAge() {
        return age;
    }

    private int age;

    public String getMessage() {
        return message;
    }
    public EventMessage(String message,int age){
        this.message = message;
        this.age = age;
    }

}
