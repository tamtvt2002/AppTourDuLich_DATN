package com.example.apptourdulich.Domains;

public class ThongTinTour implements java.io.Serializable{
    private int maTour;
    private String tenTour;
    private String phuongTien;
    private String khachSan;
    private int donGia;
    private String ngayKhoiHanh;
    private String ngayketThuc;
    private String moTa;
    private String tinhTrang;
    private String khuVuc;
    private String noiKhoiHanh;
    private String soNgay;
    private String image;
    public ThongTinTour(){}

    public ThongTinTour(int maTour, String tenTour, String phuongTien, String khachSan, int donGia, String ngayKhoiHanh, String ngayketThuc, String moTa, String tinhTrang, String khuVuc, String noiKhoiHanh, String soNgay, String image) {
        this.maTour = maTour;
        this.tenTour = tenTour;
        this.phuongTien = phuongTien;
        this.khachSan = khachSan;
        this.donGia = donGia;
        this.ngayKhoiHanh = ngayKhoiHanh;
        this.ngayketThuc = ngayketThuc;
        this.moTa = moTa;
        this.tinhTrang = tinhTrang;
        this.khuVuc = khuVuc;
        this.noiKhoiHanh = noiKhoiHanh;
        this.soNgay = soNgay;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) { this.image = image; }

    public String getSoNgay() {
        return soNgay;
    }

    public void setSoNgay(String soNgay) {
        this.soNgay = soNgay;
    }

    public String getTenTour() {
        return tenTour;
    }

    public void setTenTour(String tenTour) {
        this.tenTour = tenTour;
    }

    public String getPhuongTien() {
        return phuongTien;
    }

    public void setPhuongTien(String phuongTien) {
        this.phuongTien = phuongTien;
    }

    public String getKhachSan() {
        return khachSan;
    }

    public void setKhachSan(String khachSan) {
        this.khachSan = khachSan;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getNgayKhoiHanh() {
        return ngayKhoiHanh;
    }

    public void setNgayKhoiHanh(String ngayKhoiHanh) {
        this.ngayKhoiHanh = ngayKhoiHanh;
    }

    public String getNgayketThuc() {
        return ngayketThuc;
    }

    public void setNgayketThuc(String ngayketThuc) {
        this.ngayketThuc = ngayketThuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getKhuVuc() {
        return khuVuc;
    }

    public void setKhuVuc(String khuVuc) {
        this.khuVuc = khuVuc;
    }

    public String getNoiKhoiHanh() {
        return noiKhoiHanh;
    }

    public void setNoiKhoiHanh(String noiKhoiHanh) {
        this.noiKhoiHanh = noiKhoiHanh;
    }

    public int getMaTour() {
        return maTour;
    }

    public void setMaTour(int maTour) {
        this.maTour = maTour;
    }
}
