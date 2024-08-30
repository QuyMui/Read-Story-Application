package com.example.appdoctruyen.object;

public class Truyen {
    private int ID;
    private String TenTruyen;
    private String ChiTiet;
    private String Anh;
    private int ID_TK;

    public Truyen(int ID, String tenTruyen, String chiTiet, String anh, int ID_TK) {
        this.ID = ID;
        TenTruyen = tenTruyen;
        ChiTiet = chiTiet;
        Anh = anh;
        this.ID_TK = ID_TK;
    }

    public Truyen(String tenTruyen, String chiTiet, String anh, int ID_TK) {
        TenTruyen = tenTruyen;
        ChiTiet = chiTiet;
        Anh = anh;
        this.ID_TK = ID_TK;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenTruyen() {
        return TenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        TenTruyen = tenTruyen;
    }

    public String getChiTiet() {
        return ChiTiet;
    }

    public void setChiTiet(String chiTiet) {
        ChiTiet = chiTiet;
    }

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String anh) {
        Anh = anh;
    }

    public int getID_TK() {
        return ID_TK;
    }

    public void setID_TK(int ID_TK) {
        this.ID_TK = ID_TK;
    }
}

