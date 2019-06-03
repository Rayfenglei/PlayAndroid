package com.example.ray.playviewandroid.bean;

public class HotWebBean {
    String icon;
    int id;
    String link;
    String name;
    int order;
    int visible;

    public String getIcon() {
        return icon;
    }

    public int getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public int getVisible() {
        return visible;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "HotWebBean{" +
                "icon='" + icon + '\'' +
                ", id=" + id +
                ", link='" + link + '\'' +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", visible=" + visible +
                '}';
    }
}
