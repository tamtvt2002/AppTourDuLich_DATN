package com.example.apptourdulich.ui.admin;

import static com.example.apptourdulich.Activity.SignLoginActivity._name;
import static com.example.apptourdulich.Activity.SignLoginActivity._phone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.example.apptourdulich.databinding.ActivityDetailKhachSanBinding;
import com.example.apptourdulich.databinding.DialogbookBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kongzue.dialogx.dialogs.TipDialog;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class DetailKhachSanActivity extends AppCompatActivity {

    ActivityDetailKhachSanBinding binding;
    private ThongTinKhachSan thongTinKhachSan;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("HoaDonKhachSan");
    String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailKhachSanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().getSerializableExtra("khachSan") != null) {
            thongTinKhachSan = (ThongTinKhachSan) getIntent().getSerializableExtra
                    ("khachSan");
            binding.tvTenKhachSan.setText(thongTinKhachSan.getTenKhachSan());
            binding.tvSoDienThoai.setText(thongTinKhachSan.getSoDienThoai());
            binding.tvDiaChi.setText(thongTinKhachSan.getDiaChi());
            NumberFormat fmDonGia = new DecimalFormat("#,###");
            binding.tvGia1h.setText(fmDonGia.format(Integer.parseInt(thongTinKhachSan.getGiaTien1h())) + " VND");
            binding.tvGia1Ngay.setText(fmDonGia.format(Integer.parseInt(thongTinKhachSan.getGiaTien1ngay())) + " VND");
            binding.tvGia1Dem.setText(fmDonGia.format(Integer.parseInt(thongTinKhachSan.getGiaTien1dem())) + " VND");
            binding.tvDiaChi.setText(thongTinKhachSan.getDiaChi());
            binding.tvMoTa.setText(thongTinKhachSan.getMoTa());
            binding.tvTinhTrang.setText(thongTinKhachSan.getTinhTrang());
            Picasso.get().load(thongTinKhachSan.getHinhAnh()).into(binding.imgHinhNen);
        }

        binding.imageBackDetailTour.setOnClickListener(v -> finish());

        binding.btnDatTour.setOnClickListener(v -> {
            //dialog chonj ngayf
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            DialogbookBinding dialogbookBinding = DialogbookBinding.inflate(getLayoutInflater());
            bottomSheetDialog.setContentView(dialogbookBinding.getRoot());

            dialogbookBinding.tvNgayDen.setOnClickListener(a -> {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                date = selectedDate;
                                dialogbookBinding.tvNgayDen.setText(selectedDate);
                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            });
            dialogbookBinding.btnXacNhan.setOnClickListener(v1 -> {
                if (date.equals("")) {
                    dialogbookBinding.tvNgayDen.setText("Chọn ngày");
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn muốn đặt phòng này không?");
                builder.setPositiveButton("Có", (dialog, which) -> {
                    dialog.dismiss();
                    HoaDonKhachSan hoaDonKhachSan = new HoaDonKhachSan();
                    hoaDonKhachSan.setKeyId(myRef.push().getKey());
                    hoaDonKhachSan.setMaKhachSan(thongTinKhachSan.getKeyId());
                    hoaDonKhachSan.setSoDienThoai(_phone);
                    hoaDonKhachSan.setNgayDat(date);
                    hoaDonKhachSan.setStatus(false);
                    hoaDonKhachSan.setTenKhachSan(thongTinKhachSan.getTenKhachSan());
                    hoaDonKhachSan.setTenKhachHang(_name);
                    myRef.child(hoaDonKhachSan.getKeyId()).setValue(hoaDonKhachSan);
                    TipDialog.show(this, "Đặt phòng thành công", TipDialog.TYPE.SUCCESS);
                });
                builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
                builder.show();
            });
            dialogbookBinding.btnHuy.setOnClickListener(v1 -> bottomSheetDialog.dismiss());

            bottomSheetDialog.show();
        });



        binding.imageBackDetailTour.setOnClickListener(v -> finish());


    }

    public void showDatePickerDialog(View view) {

    }
}