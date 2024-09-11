package com.example.appdoctruyen.object;

public class DanhMuc {

    private int id; // ID of the category
    private String tenDanhMuc; // Name of the category

    // Constructor
    public DanhMuc(int id, String tenDanhMuc) {
        this.id = id;
        this.tenDanhMuc = tenDanhMuc;
    }

    // Getter for ID
    public int getId() {
        return id;
    }

    // Getter for category name
    public String getTenDanhMuc() {
        return tenDanhMuc;
    }
}
