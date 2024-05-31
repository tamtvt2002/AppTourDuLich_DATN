package com.example.apptourdulich.ui.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.apptourdulich.R;
import com.example.apptourdulich.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnDanhSachHoaDon.setOnClickListener(v -> {
            startActivity(new Intent(this, HoaDonAdminActivity.class));
        });
        binding.btnDanhSachKhuyenMai.setOnClickListener(v -> {
            startActivity(new Intent(this, DanhSachKhuyenMaiActivity.class));
        });
        binding.btnDanhSachTour.setOnClickListener(v -> {
            startActivity(new Intent(this, DanhSachTourActivity.class));
        });
        binding.btnDanhSachTin.setOnClickListener(v -> {
            startActivity(new Intent(this, DanhSachTinActivity.class));
        });
        binding.btnDanhSachKhachSan.setOnClickListener(v -> {
            startActivity(new Intent(this, DanhSachKhachSanActivity.class));
        });
        binding.btnDanhSachHoaDonKhachSan.setOnClickListener(v -> {
            startActivity(new Intent(this, HistoryHotel.class));
        });
    }
}