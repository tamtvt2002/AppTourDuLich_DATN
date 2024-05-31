package com.example.apptourdulich.ui.admin;

import static com.example.apptourdulich.Activity.SignLoginActivity.role;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.apptourdulich.R;
import com.example.apptourdulich.databinding.ActivityChiTietHoaDonKsactivityBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChiTietHoaDonKSActivity extends AppCompatActivity {
    ActivityChiTietHoaDonKsactivityBinding binding;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("KhachSan");
    private DatabaseReference myRef2 = database.getReference("HoaDonKhachSan");
    private HoaDonKhachSan hoaDonKhachSan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChiTietHoaDonKsactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().getExtras() != null) {
            hoaDonKhachSan = (HoaDonKhachSan) getIntent().getSerializableExtra
                    ("hoaDonKhachSan");

            binding.tvNgayDatBill1.setText(hoaDonKhachSan.getNgayDat());
            if (hoaDonKhachSan.getStatus()) {
                binding.tvTrangThaiBill1.setText("Đã xác nhận");
                binding.tvTrangThaiBill1.setTextColor(getResources().getColor(R.color.green));
            } else {
                binding.tvTrangThaiBill1.setText("Chưa xác nhận");
                binding.tvTrangThaiBill1.setTextColor(getResources().getColor(R.color.red));
            }
            binding.tvTenKSBill1.setText(hoaDonKhachSan.getTenKhachSan());
            binding.tvSoDienThoaiHis1.setText(hoaDonKhachSan.getSoDienThoai());
            binding.tvHoTenBill1.setText(hoaDonKhachSan.getTenKhachHang());
        }
        if (role.equals("admin")) {
            binding.btnConfirmTour.setVisibility(View.VISIBLE);
        }

        binding.btnConfirmTour.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có chắc chắn muốn xác nhận hóa đơn này không?");
            builder.setPositiveButton("Có", (dialog, which) -> {
                dialog.dismiss();
                myRef2.child(hoaDonKhachSan.getKeyId()).child("status").setValue(true).addOnCompleteListener(task -> {
                    binding.tvTrangThaiBill1.setText("Đã xác nhận");
                    binding.tvTrangThaiBill1.setTextColor(getResources().getColor(R.color.green));
                });
            });
            builder.setNegativeButton("Không", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.show();
        });

        binding.btnCancelTour.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có chắc chắn muốn hủy hóa đơn này không?");
            builder.setPositiveButton("Có", (dialog, which) -> {
                dialog.dismiss();
                myRef2.child(hoaDonKhachSan.getKeyId()).removeValue().addOnCompleteListener(task -> {
                    finish();
                });
            });
            builder.setNegativeButton("Không", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.show();
        });

        binding.imgBackInfoHistory.setOnClickListener(v -> finish());
    }
}