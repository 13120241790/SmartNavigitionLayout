package com.julive.library.navigation.model;

public class TabModel {

    private int index = -1; //待优化点 index 去重

    private Object imageSelected;

    private Object imageNormal;

    private String text;

    private int textColorSelected;

    private int textColorNormal;

    public int getIndex() {
        return index;
    }

    public TabModel(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index can not negative!");
        }
        this.index = index;
    }

    private TabModel() {
    }

    public Object getImageSelected() {
        return imageSelected;
    }

    public TabModel setImageSelected(Object imageSelected) {
        this.imageSelected = imageSelected;
        return this;
    }

    public Object getImageNormal() {
        return imageNormal;
    }

    public TabModel setImageNormal(Object imageNormal) {
        this.imageNormal = imageNormal;
        return this;
    }

    public String getText() {
        return text;
    }

    public TabModel setText(String text) {
        this.text = text;
        return this;
    }

    public int getTextColorSelected() {
        return textColorSelected;
    }

    public TabModel setTextColorSelected(int textColorSelected) {
        this.textColorSelected = textColorSelected;
        return this;
    }

    public int getTextColorNormal() {
        return textColorNormal;
    }

    public TabModel setTextColorNormal(int textColorNormal) {
        this.textColorNormal = textColorNormal;
        return this;
    }
}
