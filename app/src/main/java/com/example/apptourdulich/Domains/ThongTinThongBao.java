package com.example.apptourdulich.Domains;

public class ThongTinThongBao {

    private int maThongBao;
    private String noiDung;
    private String soDienThoai;
    private String ngayThongBao;

    public ThongTinThongBao() {

    }
    public ThongTinThongBao(int maThongBao, String ngayThongBao, String noiDung, String soDienThoai) {
        this.maThongBao=maThongBao;
        this.ngayThongBao=ngayThongBao;
        this.soDienThoai=soDienThoai;
        this.noiDung=noiDung;
    }

    public int getMaThongBao() {
        return maThongBao;
    }

    public void setMaThongBao(int maThongBao) {
        this.maThongBao = maThongBao;
    }


    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getNgayThongBao() {
        return ngayThongBao;
    }

    public void setNgayThongBao(String ngayThongBao) {
        this.ngayThongBao = ngayThongBao;
    }

}
