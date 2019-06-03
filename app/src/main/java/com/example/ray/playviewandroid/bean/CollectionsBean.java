package com.example.ray.playviewandroid.bean;

import java.util.List;


public class CollectionsBean {
            /**
            * curPage : 1
            * datas : []
            * offset : 0
            * over : true
            * pageCount : 1
            * size : 20
            * total : 3
            */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<CollectionBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CollectionBean> getDatas() {
        return datas;
    }

    public void setDatas(List<CollectionBean> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "CollectionsBean{" +
                "curPage=" + curPage +
                ", offset=" + offset +
                ", over=" + over +
                ", pageCount=" + pageCount +
                ", size=" + size +
                ", total=" + total +
                ", datas=" + datas +
                '}';
    }
}
