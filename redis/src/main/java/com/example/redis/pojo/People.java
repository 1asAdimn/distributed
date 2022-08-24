package com.example.redis.pojo;

import java.io.Serializable;

public class People implements Serializable {
    public Integer id;
    public int age;
    public String name;

    public People(int id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public People() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
