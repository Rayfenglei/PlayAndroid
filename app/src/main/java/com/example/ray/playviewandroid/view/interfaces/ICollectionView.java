package com.example.ray.playviewandroid.view.interfaces;

public interface ICollectionView {
    void showCollectSuccess(); //收藏成功
    void showUnCollectSuccess();//取消收藏成功
    void collect(int position);//收藏操作
}
