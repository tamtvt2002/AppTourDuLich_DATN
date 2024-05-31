package com.example.apptourdulich.ui.admin;

import static android.app.PendingIntent.getActivity;

import static kotlin.random.RandomKt.Random;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.apptourdulich.R;
import com.example.apptourdulich.Domains.ThongTinTour;
import com.example.apptourdulich.databinding.ActivityAddTourBinding;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kongzue.dialogx.dialogs.TipDialog;
import com.kongzue.dialogx.dialogs.WaitDialog;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class AddTourActivity extends AppCompatActivity {

    ActivityAddTourBinding binding;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("Tour");
    private String action = "";
    private ThongTinTour thongTinTour = new ThongTinTour();
    private boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTourBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        action = getIntent().getStringExtra("action");
        assert action != null;
        if (action.equals("edit")) {
            binding.buttonSubmit.setText("Cập nhật");
            thongTinTour = (ThongTinTour) getIntent().getSerializableExtra("Tour");
            binding.editTextTenTour.setText(thongTinTour.getTenTour());
            binding.editTextPhuongTien.setText(thongTinTour.getPhuongTien());
            binding.editTextKhachSan.setText(thongTinTour.getKhachSan());
            binding.editTextDonGia.setText(String.valueOf(thongTinTour.getDonGia()));
            binding.editTextNgayKhoiHanh.setText(thongTinTour.getNgayKhoiHanh());
            binding.editTextNgayKetThuc.setText(thongTinTour.getNgayketThuc());
            binding.editTextMoTa.setText(thongTinTour.getMoTa());
            binding.editTextTinhTrang.setText(thongTinTour.getTinhTrang());
            if (thongTinTour.getKhuVuc().equals("Miền Bắc")) {
                binding.radioGroupKhuVuc.check(R.id.radioButtonMienBac);
            } else if (thongTinTour.getKhuVuc().equals("Miền Trung")) {
                binding.radioGroupKhuVuc.check(R.id.radioButtonMienTrung);
            } else if (thongTinTour.getKhuVuc().equals("Miền Nam")) {
                binding.radioGroupKhuVuc.check(R.id.radioButtonMienNam);
            }
            binding.editTextNoiKhoiHanh.setText(thongTinTour.getNoiKhoiHanh());
            binding.editTextSoNgay.setText(thongTinTour.getSoNgay());
            Picasso.get().load(thongTinTour.getImage()).into(binding.imageView);
            check = true;
            binding.buttonDelete.setVisibility(View.VISIBLE);
        } else {
            binding.buttonSubmit.setText("Thêm");
        }

        binding.buttonChoose.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            pickImage.launch(intent);
        });

        binding.buttonDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xác nhận");
            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
            builder.setPositiveButton("Có", (dialog, which) -> {
                WaitDialog.show(this, "Đang xóa");
                myRef.child(String.valueOf(thongTinTour.getMaTour())).removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        WaitDialog.dismiss();
                        TipDialog.show(this, "Xóa Thành công", TipDialog.TYPE.SUCCESS);
                        finish();
                    } else {
                        WaitDialog.dismiss();
                        Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
            });
            builder.setNegativeButton("Không", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.show();
        });

        binding.editTextNgayKhoiHanh.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private final String ddmmyyyy = "DDMMYYYY";
            private final Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d]", "");
                    String cleanC = current.replaceAll("[^\\d]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        // with leap years - otherwise, date e.g. 29/02/2012
                        // would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    binding.editTextNgayKhoiHanh.setText(current);
                    binding.editTextNgayKhoiHanh.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.editTextNgayKetThuc.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private final String ddmmyyyy = "DDMMYYYY";
            private final Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d]", "");
                    String cleanC = current.replaceAll("[^\\d]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        // with leap years - otherwise, date e.g. 29/02/2012
                        // would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    binding.editTextNgayKetThuc.setText(current);
                    binding.editTextNgayKetThuc.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.buttonSubmit.setOnClickListener(v -> {
            String tenTour = binding.editTextTenTour.getText().toString();
            String phuongTien = binding.editTextPhuongTien.getText().toString();
            String khachSan = binding.editTextKhachSan.getText().toString();
            int donGia = Integer.parseInt(binding.editTextDonGia.getText().toString());
            String ngayKhoiHanh = binding.editTextNgayKhoiHanh.getText().toString();
            String ngayKetThuc = binding.editTextNgayKetThuc.getText().toString();
            String moTa = binding.editTextMoTa.getText().toString();
            String tinhTrang = binding.editTextTinhTrang.getText().toString();
            String khuVuc = "";
            if (binding.radioGroupKhuVuc.getCheckedRadioButtonId() == R.id.radioButtonMienBac) {
                khuVuc = "Miền Bắc";
            } else if (binding.radioGroupKhuVuc.getCheckedRadioButtonId() == R.id.radioButtonMienTrung) {
                khuVuc = "Miền Trung";
            } else if (binding.radioGroupKhuVuc.getCheckedRadioButtonId() == R.id.radioButtonMienNam) {
                khuVuc = "Miền Nam";
            }
            String noiKhoiHanh = binding.editTextNoiKhoiHanh.getText().toString();
            String soNgay = binding.editTextSoNgay.getText().toString();
            if (!check) {
                Toast.makeText(this, "Chưa chọn ảnh", Toast.LENGTH_SHORT).show();
                return;
            }
            String image = "";
            int maTour = 0;
            if(action.equals("edit")){
                image = thongTinTour.getImage();
                maTour = thongTinTour.getMaTour();
            }else{
                maTour = (int) (Math.random() * 100000);
            }
            if (tenTour.isEmpty()) {
                binding.textInputLayoutTenTour.setError("Không được để trống");
                return;
            }
            if (phuongTien.isEmpty()) {
                binding.textInputLayoutPhuongTien.setError("Không được để trống");
                return;
            }
            if (khachSan.isEmpty()) {
                binding.textInputLayoutKhachSan.setError("Không được để trống");
                return;
            }
            if (donGia == 0) {
                binding.textInputLayoutDonGia.setError("Không được để trống");
                return;
            }
            if (ngayKhoiHanh.isEmpty()) {
                binding.textInputLayoutNgayKhoiHanh.setError("Không được để trống");
                return;
            }
            //check ngày có đúng định dạng không
            if (ngayKhoiHanh.length() != 10) {
                binding.textInputLayoutNgayKhoiHanh.setError("Ngày không đúng định dạng");
                return;
            }
            if (checkIsNumber(ngayKhoiHanh.substring(0, 2)) && checkIsNumber(ngayKhoiHanh.substring(3, 5)) && !checkIsNumber(ngayKhoiHanh.substring(6, 10))) {
                binding.textInputLayoutNgayKhoiHanh.setError("Ngày không đúng định dạng");
                return;
            }
            if (ngayKhoiHanh.charAt(0) > '3' || ngayKhoiHanh.charAt(3) > '1' || ngayKhoiHanh.charAt(3) == '1' && ngayKhoiHanh.charAt(4) > '2' || ngayKhoiHanh.charAt(6) > '2' || ngayKhoiHanh.charAt(6) == '2' && ngayKhoiHanh.charAt(7) > '2' || ngayKhoiHanh.charAt(9) > '6' || ngayKhoiHanh.charAt(9) == '6' && ngayKhoiHanh.charAt(10) > '9') {
                binding.textInputLayoutNgayKhoiHanh.setError("Ngày không đúng định dạng");
                return;
            }

            //check ngày kết thúc co đúng định dạng không
            if (ngayKetThuc.isEmpty()) {
                binding.textInputLayoutNgayKetThuc.setError("Không được để trống");
                return;
            }
            if (ngayKetThuc.length() != 10) {
                binding.textInputLayoutNgayKetThuc.setError("Ngày không đúng định dạng");
                return;
            }
            if (checkIsNumber(ngayKetThuc.substring(0, 2)) && checkIsNumber(ngayKetThuc.substring(3, 5)) && !checkIsNumber(ngayKetThuc.substring(6, 10))) {
                binding.textInputLayoutNgayKetThuc.setError("Ngày không đúng định dạng");
                return;
            }
            if (ngayKetThuc.charAt(0) > '3' || ngayKetThuc.charAt(3) > '1' || ngayKetThuc.charAt(3) == '1' && ngayKetThuc.charAt(4) > '2' || ngayKetThuc.charAt(6) > '2' || ngayKetThuc.charAt(6) == '2' && ngayKetThuc.charAt(7) > '2' || ngayKetThuc.charAt(9) > '6' || ngayKetThuc.charAt(9) == '6' && ngayKetThuc.charAt(10) > '9') {
                binding.textInputLayoutNgayKetThuc.setError("Ngày không đúng định dạng");
                return;
            }
            //check ngày kết thúc > ngày khởi hành
            if (ngayKetThuc.compareTo(ngayKhoiHanh) < 0) {
                binding.textInputLayoutNgayKetThuc.setError("Ngày kết thúc phải lớn hơn ngày khởi hành");
                return;
            }
            if (moTa.isEmpty()) {
                binding.textInputLayoutMoTa.setError("Không được để trống");
                return;
            }
            if (tinhTrang.isEmpty()) {
                binding.textInputLayoutTinhTrang.setError("Không được để trống");
                return;
            }
            if (noiKhoiHanh.isEmpty()) {
                binding.textInputLayoutNoiKhoiHanh.setError("Không được để trống");
                return;
            }
            if (soNgay.isEmpty()) {
                binding.textInputLayoutSoNgay.setError("Không được để trống");
                return;
            }

            ThongTinTour thongTinTour = new ThongTinTour(maTour, tenTour, phuongTien, khachSan, donGia, ngayKhoiHanh, ngayKetThuc, moTa, tinhTrang, khuVuc, noiKhoiHanh, soNgay, image);
            uploadImage(thongTinTour);

        });

        binding.buttonCancel.setOnClickListener(v -> {
            finish();
        });
    }


    private boolean checkIsNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            binding.imageView.setImageBitmap(bitmap);
                            check = true;
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    public void uploadImage(ThongTinTour thongTinTour) {
        if (!check) {
            Toast.makeText(this, "Chưa chọn ảnh", Toast.LENGTH_SHORT).show();
            return;
        }
        WaitDialog.show(this, "Đang tải ảnh");
        String filepath = "images/" + System.currentTimeMillis();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filepath);
        binding.imageView.setDrawingCacheEnabled(true);
        binding.imageView.buildDrawingCache();
        Bitmap bitmap = binding.imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = storageReference.putBytes(data);
        uploadTask.addOnFailureListener(e -> {
            Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
        }).addOnSuccessListener(taskSnapshot -> {
            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while (!uriTask.isSuccessful()) ;
            Uri downloadUri = uriTask.getResult();
            String image = downloadUri.toString();
            thongTinTour.setImage(image);
            myRef.child(String.valueOf(thongTinTour.getMaTour())).setValue(thongTinTour).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    WaitDialog.dismiss();
                    Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    WaitDialog.dismiss();
                    Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

}