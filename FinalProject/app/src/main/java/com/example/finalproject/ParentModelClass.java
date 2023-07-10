package com.example.finalproject;

import java.util.List;

public class ParentModelClass {
    String title;
    List<ChildModelClass>childModelClassList;

    public ParentModelClass(String title, List<ChildModelClass> childModelClassList) {
        this.title = title;
        this.childModelClassList = childModelClassList;
    }

    public String getTitle() {
        return title;
    }

    public List<ChildModelClass> getChildModelClassList() {
        return childModelClassList;
    }


}
