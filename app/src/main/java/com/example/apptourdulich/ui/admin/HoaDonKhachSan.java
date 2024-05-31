package com.example.apptourdulich.ui.admin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HoaDonKhachSan implements Serializable {
    private String keyId;
    private String maKhachSan;
    private String soDienThoai;
    private String tenKhachSan;
    private String tenKhachHang;
    private String ngayDat;
    private boolean status;

    public HoaDonKhachSan() {
    }

    public HoaDonKhachSan(String keyId, String maKhachSan, String soDienThoai, String ngayDat, boolean status) {
        this.keyId = keyId;
        this.maKhachSan = maKhachSan;
        this.soDienThoai = soDienThoai;
        this.ngayDat = ngayDat;
        this.status = status;
    }



    public String getTenKhachSan() {
        return tenKhachSan;
    }

    public void setTenKhachSan(String tenKhachSan) {
        this.tenKhachSan = tenKhachSan;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }
    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getMaKhachSan() {
        return maKhachSan;
    }

    public void setMaKhachSan(String maKhachSan) {
        this.maKhachSan = maKhachSan;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
