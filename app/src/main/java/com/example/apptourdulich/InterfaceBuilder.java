package com.example.apptourdulich;

import com.example.apptourdulich.Domains.ThongTinThongBao;

public interface InterfaceBuilder {
    //parameter có các đối tượng của thông tin thông báo
    InterfaceBuilder maThongBao(int maThongBao);
    InterfaceBuilder ngaythongbao(String ngaythongbao);
    InterfaceBuilder noiDung(String noiDung);
    InterfaceBuilder soDienThoai(String soDienThoai);
    ThongTinThongBao build();
}