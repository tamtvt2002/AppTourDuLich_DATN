package com.example.apptourdulich.Domains;

import com.example.apptourdulich.Domains.ThongTinThongBao;
import com.example.apptourdulich.InterfaceBuilder;

public class NotificationBuilder implements InterfaceBuilder {
    //khai báo các constructor và kế thừa của các interfacebuilder
    private int maThongBao;
    private String noiDung;
    private String soDienThoai;
    private String ngayThongBao;

    @Override
    public InterfaceBuilder maThongBao(int maThongBao) {
        this.maThongBao=maThongBao;

        return this;
    }

    @Override
    public InterfaceBuilder ngaythongbao(String ngaythongbao) {
        this.ngayThongBao=ngaythongbao;
        return this;
    }

    @Override
    public InterfaceBuilder noiDung(String noiDung) {
        this.noiDung=noiDung;
        return this;
    }

    @Override
    public InterfaceBuilder soDienThoai(String soDienThoai) {
        this.soDienThoai=soDienThoai;
        return this;
    }

    @Override
    public ThongTinThongBao build() {
        return new ThongTinThongBao(maThongBao,ngayThongBao,noiDung,soDienThoai);
    }
}
