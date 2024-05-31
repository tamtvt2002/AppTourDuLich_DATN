package com.example.apptourdulich.Domains;

import java.io.Serializable;

public class ThongTinTinTuc implements Serializable {
    int maTinTuc;
    String tenTinTuc;
    String thongTin;
    String thongTinNgan;
    String image;
    public String getThongTinNgan() {
        return thongTinNgan;
    }

    public void setThongTinNgan(String thongTinNgan) {
        this.thongTinNgan = thongTinNgan;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public int getMaTinTuc() {
        return maTinTuc;
    }

    public void setMaTinTuc(int maTinTuc) {
        this.maTinTuc = maTinTuc;
    }

    public String getTenTinTuc() {
        return tenTinTuc;
    }

    public void setTenTinTuc(String tenTinTuc) {
        this.tenTinTuc = tenTinTuc;
    }

    public String getThongTin() {
        return thongTin;
    }

    public void setThongTin(String thongTin) {
        this.thongTin = thongTin;
    }
}
