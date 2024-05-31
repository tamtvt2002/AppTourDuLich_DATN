package com.example.apptourdulich.Domains;

import com.google.type.DateTime;

import java.util.Date;

public class KhachHang {

    private String HoTen;
    private String Gioitinh;
    private String NgaySinh;
    private String SDT;
    private String password;
    private String CMND;
    private String DiaChi;
    private String Imageid;
    private String role;

    public KhachHang() {
    }

    public KhachHang(String hoTen, String gioitinh, String cMND, String sdt, String ngaySinh, String diaChi, String imageid) {
        this.HoTen = hoTen;
        this.DiaChi = diaChi;
        this.CMND = cMND;
        this.Gioitinh = gioitinh;
        this.NgaySinh = ngaySinh;
        this.SDT = sdt;
        this.Imageid = imageid;
    }

    public KhachHang(String hoTen, String gioitinh, String ngaySinh, String SDT, String CMND, String diaChi, String imageid, String role) {
        HoTen = hoTen;
        Gioitinh = gioitinh;
        NgaySinh = ngaySinh;
        this.SDT = SDT;
        this.CMND = CMND;
        DiaChi = diaChi;
        Imageid = imageid;
        this.role = role;
    }

    public KhachHang(String hoTen, String gioitinh, String ngaySinh, String SDT, String password, String CMND, String diaChi, String imageid, String role) {
        HoTen = hoTen;
        Gioitinh = gioitinh;
        NgaySinh = ngaySinh;
        this.SDT = SDT;
        this.password = password;
        this.CMND = CMND;
        DiaChi = diaChi;
        Imageid = imageid;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImageid() {
        return Imageid;
    }

    public void setImageid(String imageid) {
        Imageid = imageid;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getGioitinh() {
        return Gioitinh;
    }

    public void setGioitinh(String gioiTinh) {
        Gioitinh = gioiTinh;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }
}
