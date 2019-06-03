package com.example.ray.playviewandroid.bean;


public class HistoryBean {
    private int id;
    private String key;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "HistoryBean{" +
                "id=" + id +
                ", key='" + key + '\'' +
                '}';
    }
}
