package com.example.apptourdulich.ui.admin;

import java.io.Serializable;

public class ThongTinKhachSan implements Serializable {
    private String keyId;
    private String tenKhachSan;
    private String diaChi;
    private String soDienThoai;
    private String moTa;
    private String hinhAnh;
    private String giaTien1h;
    private String giaTien1ngay;
    private String giaTien1dem;
    private String tinhTrang;

    public ThongTinKhachSan() {
    }

    public ThongTinKhachSan(String keyId, String tenKhachSan, String diaChi, String soDienThoai, String moTa, String hinhAnh, String giaTien1h, String giaTien1ngay, String giaTien1dem) {
        this.keyId = keyId;
        this.tenKhachSan = tenKhachSan;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.giaTien1h = giaTien1h;
        this.giaTien1ngay = giaTien1ngay;
        this.giaTien1dem = giaTien1dem;
    }

    public ThongTinKhachSan(String keyId, String tenKhachSan, String diaChi, String soDienThoai, String moTa, String hinhAnh, String giaTien1h, String giaTien1ngay, String giaTien1dem, String tinhTrang) {
        this.keyId = keyId;
        this.tenKhachSan = tenKhachSan;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.giaTien1h = giaTien1h;
        this.giaTien1ngay = giaTien1ngay;
        this.giaTien1dem = giaTien1dem;
        this.tinhTrang = tinhTrang;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getTenKhachSan() {
        return tenKhachSan;
    }

    public void setTenKhachSan(String tenKhachSan) {
        this.tenKhachSan = tenKhachSan;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getGiaTien1h() {
        return giaTien1h;
    }

    public void setGiaTien1h(String giaTien1h) {
        this.giaTien1h = giaTien1h;
    }

    public String getGiaTien1ngay() {
        return giaTien1ngay;
    }

    public void setGiaTien1ngay(String giaTien1ngay) {
        this.giaTien1ngay = giaTien1ngay;
    }

    public String getGiaTien1dem() {
        return giaTien1dem;
    }

    public void setGiaTien1dem(String giaTien1dem) {
        this.giaTien1dem = giaTien1dem;
    }
}
