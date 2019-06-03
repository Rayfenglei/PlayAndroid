package com.example.ray.playviewandroid.view.defineview;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class FlowLayout extends ViewGroup {
    /**
     * 存储每一行的剩余的空间
     */
    private List<Integer> lineSpaces = new ArrayList();
    /**
     * 存储每一行的高度
     */
    private List<Integer> lineHeights = new ArrayList<>();
    /**
     * 存储每一行的view
     */
    private List<List<View>> lineViews = new ArrayList<>();
    /**
     * 提供添加view
     */
    private List<View> children = new ArrayList<>();
    /**
     * 每一行是否平分空间
     */
    private boolean isAverageInRow = false;

    /**
     * 每一列是否垂直居中
     */
    private boolean isAverageInColumn = true;

    public FlowLayout(Context context) {
        super(context);
    }

    /**
     * 设置是否每列垂直居中
     * @param averageInColumn
     */
    public void setAverageInColumn(boolean averageInColumn) {
        if (isAverageInColumn != averageInColumn) {
            isAverageInColumn = averageInColumn;
            requestLayout();
        }
    }

    /**
     * 设置是否每一行居中
     * @param averageInRow
     */
    public void setAverageInRow(boolean averageInRow) {
        if (isAverageInRow != averageInRow) {
            isAverageInRow = averageInRow;
            requestLayout();

        }
    }

    /**
     * 动态添加view
     * @param children
     */
    public void setChildren(List<View> children) {
        if (children == null) return;
        this.children = children;
        this.removeAllViews();
        for (int i = 0; i < children.size(); i++) {
            this.addView(children.get(i));
        }
    }


    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重新方法用来获取子view的margin值
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {

        return new MarginLayoutParams(getContext(), attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("onMeasure", "onMeasure");
        //清除记录数据
        lineSpaces.clear();
        lineHeights.clear();
        lineViews.clear();
        //测量view的宽高
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        //计算children的数量
        int count = this.getChildCount();
        //统计子view总共高度
        int childrenTotalHeight = 0;
        //一行中剩余的空间
        int lineLeftSpace = 0;
        int lineRealWidth = 0;
        int lineRealHeight = 0;
        List<View> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            //不可见的View不作处理
            if(child.getVisibility()==GONE)continue;
            //对子view进行测量
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //获取子view的间距
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            //获取view占据的空间大小
            int childViewWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            int childViewHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            if (childViewWidth + lineRealWidth <= viewWidth) {//一行
                //已占用的空间
                lineRealWidth += childViewWidth;
                //剩余的空间
                lineLeftSpace = viewWidth - lineRealWidth;
                //一行的最大高度
                lineRealHeight = Math.max(lineRealHeight, childViewHeight);
                //将一行中的view加到同意个集合
                list.add(child);
            } else {//下一行
                //统计上一行的总高度
                childrenTotalHeight += lineRealHeight;
                //上一行的高度
                lineHeights.add(lineRealHeight);
                //上一行剩余的空间
                lineSpaces.add(lineLeftSpace);
                //将上一行的元素保存起来
                lineViews.add(list);
                //重置一行中已占用的空间
                lineRealWidth = childViewWidth;
                //重置一行中剩余的空间
                lineLeftSpace = viewWidth - lineRealWidth;
                //重置一行中的高度
                lineRealHeight = childViewHeight;
                //更换新的集合存储下一行的元素
                list = new ArrayList<>();
                list.add(child);

            }
            if (i == count - 1) {//最后一个元素
                childrenTotalHeight += lineRealHeight;
                //将最后一行的信息保存下来
                lineViews.add(list);
                lineHeights.add(lineRealHeight);
                lineSpaces.add(lineLeftSpace);
            }
        }
        //宽度可以不用考虑 主要考虑高度
        if (heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(viewWidth, viewHeight);
        } else {
            setMeasuredDimension(viewWidth, childrenTotalHeight);
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("onLayout", "onLayout   change: " + changed);
//        if (true) {
        //View最开始左边
        int viewLeft = 0;
        //View最开始上边
        int viewTop = 0;

        //每一个view layout的位置
        int vl = 0;
        int vt = 0;
        int vr = 0;
        int vb = 0;

        //每一行中每一个view多平分的空间
        float averageInRow = 0;
        //每一列中每一个view距离顶部的高度
        float averageInColumn = 0;

        //列数
        int columns = lineViews.size();
        for (int i = 0; i < columns; i++) {
            //该行剩余的空间
            int lineSpace = lineSpaces.get(i);
            //该行的高度
            int lineHeight = lineHeights.get(i);
            //该行的所有元素
            List<View> list = lineViews.get(i);
            //每一行的view的个数
            int rows = list.size();

            //view layout的位置
            vl = 0;
            vt = 0;
            vr = 0;
            vb = 0;
            //每一行中每一个view多平分的空间<一行只有一个不管>
            if (isAverageInRow && rows > 1) {
                averageInRow = lineSpace * 1.0f / (rows + 1);
            } else {
                averageInRow = 0;
            }

            //获取View的间距属性
            MarginLayoutParams params = null;
            for (int j = 0; j < rows; j++) {
                //对应位置的view元素
                View child = list.get(j);
                params = (MarginLayoutParams) child.getLayoutParams();
                //是否计算每一列中的元素垂直居中的时候多出的距离
                if (isAverageInColumn && rows > 1) {
                    averageInColumn = (lineHeight - child.getMeasuredHeight() - params.topMargin - params.bottomMargin) / 2;
                } else {
                    averageInColumn = 0;
                }

                //左边位置 =起始位置+view左间距+多平分的空间
                vl = (int) (viewLeft + params.leftMargin + averageInRow);
                //上面的位置 = 起始位置+view上间距+多平分的空间
                vt = (int) (viewTop + params.topMargin + averageInColumn);
                vr = vl + child.getMeasuredWidth();
                vb = vt + child.getMeasuredHeight();
                child.layout(vl, vt, vr, vb);
                viewLeft += child.getMeasuredWidth() + params.leftMargin + params.rightMargin + averageInRow;
            }
            viewLeft = 0;
            viewTop += lineHeight;
        }
//        }
    }


}