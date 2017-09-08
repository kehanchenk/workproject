package com.wyyc.tableview;

import android.view.View;

import java.util.Stack;

/**
 */
public class Recycler {

    private Stack<View>[] views;

    /**
     */
    @SuppressWarnings("unchecked")
    public Recycler(int size) {
        views = new Stack[size];
        for (int i = 0; i < size; i++) {
            views[i] = new Stack<View>();
        }
    }

    /**
     */
    public void addRecycledView(View view, int type) {
        views[type].push(view);
    }

    /**
     */
    public View getRecycledView(int typeView) {
        try {
            return views[typeView].pop();
        } catch (java.util.EmptyStackException e) {
            return null;

        }
    }
}
