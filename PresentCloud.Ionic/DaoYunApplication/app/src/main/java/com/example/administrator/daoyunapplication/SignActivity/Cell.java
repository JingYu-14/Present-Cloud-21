package com.example.administrator.daoyunapplication.SignActivity;

/**
 * Created by Administrator on 2020/4/18 0018.
 */

public class Cell {
    private float centerX;
    private float centerY;
    private boolean selected;

    public Cell(float x, float y) {
        centerX = x;
        centerY = y;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
