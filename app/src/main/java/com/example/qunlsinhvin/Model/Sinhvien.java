package com.example.qunlsinhvin.Model;

import java.io.Serializable;
import java.util.Date;

public class Sinhvien implements Serializable {
    private String maSinhVien;
    private String tenSinhVien;
    private Date ngaySinh;
    private String maLop;
    private String email;
    private String soDienThoai1;
    private String soDienthoai2;
    private String queQuan;
    private String choOHienNay;

    public Sinhvien(){

    }
    public Sinhvien(String maSinhVien, String tenSinhVien, Date ngaySinh, String maLop, String email, String soDienThoai1, String soDienthoai2, String queQuan, String choOHienNay) {
        this.maSinhVien = maSinhVien;
        this.tenSinhVien = tenSinhVien;
        this.ngaySinh = ngaySinh;
        this.maLop = maLop;
        this.email = email;
        this.soDienThoai1 = soDienThoai1;
        this.soDienthoai2 = soDienthoai2;
        this.queQuan = queQuan;
        this.choOHienNay = choOHienNay;
    }

    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getTenSinhVien() {
        return tenSinhVien;
    }

    public void setTenSinhVien(String tenSinhVien) {
        this.tenSinhVien = tenSinhVien;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai1() {
        return soDienThoai1;
    }

    public void setSoDienThoai1(String soDienThoai1) {
        this.soDienThoai1 = soDienThoai1;
    }

    public String getSoDienthoai2() {
        return soDienthoai2;
    }

    public void setSoDienthoai2(String soDienthoai2) {
        this.soDienthoai2 = soDienthoai2;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getChoOHienNay() {
        return choOHienNay;
    }

    public void setChoOHienNay(String choOHienNay) {
        this.choOHienNay = choOHienNay;
    }
}