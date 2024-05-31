package com.example.apptourdulich.ui.admin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.apptourdulich.Domains.ThongTinKhuyenMai;
import com.example.apptourdulich.databinding.ActivityThemKhuyenMaiBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kongzue.dialogx.dialogs.WaitDialog;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class ThemKhuyenMaiActivity extends AppCompatActivity {
    ActivityThemKhuyenMaiBinding binding;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("KhuyenMai");
    private boolean check = false;
    private String action = "add";
    private ThongTinKhuyenMai thongTinKhuyenMai = new ThongTinKhuyenMai();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemKhuyenMaiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent().getStringExtra("action") != null){
            action = getIntent().getStringExtra("action");
            if(action.equals("edit")){
                thongTinKhuyenMai = (ThongTinKhuyenMai) getIntent().getSerializableExtra("KhuyenMai");
                binding.editTextTenKhuyenMai.setText(thongTinKhuyenMai.getTenKhuyenMai());
                binding.editTextChietKhau.setText(String.valueOf(thongTinKhuyenMai.getChietKhau()));
                binding.editTextNgayBatDau.setText(thongTinKhuyenMai.getNgayBatDau());
                binding.editTextNgayKetThuc.setText(thongTinKhuyenMai.getNgayKetThuc());
                binding.editTextThongTin.setText(thongTinKhuyenMai.getThongTin());
                Picasso.get().load(thongTinKhuyenMai.getImage()).into(binding.imageView);
                check = true;
                binding.buttonSubmit.setText("Cập nhật");
                binding.buttonDelete.setVisibility(View.VISIBLE);
            }
        }

        binding.editTextNgayBatDau.addTextChangedListener(new TextWatcher() {
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
                    binding.editTextNgayBatDau.setText(current);
                    binding.editTextNgayBatDau.setSelection(sel < current.length() ? sel : current.length());
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

        binding.buttonChoose.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            pickImage.launch(intent);
        });

        binding.buttonSubmit.setOnClickListener(v -> {
            String ten = binding.editTextTenKhuyenMai.getText().toString();
            String chietKhau = binding.editTextChietKhau.getText().toString();
            String ngayBatDau = binding.editTextNgayBatDau.getText().toString();
            String ngayKetThuc = binding.editTextNgayKetThuc.getText().toString();
            String thongTin = binding.editTextThongTin.getText().toString();

            if (ten.isEmpty() || chietKhau.isEmpty() || ngayBatDau.isEmpty() || ngayKetThuc.isEmpty() || thongTin.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            if(action.equals("add")){
                thongTinKhuyenMai.setMaKhuyenMai((int) (Math.random() * 100000) + "");
            }

            thongTinKhuyenMai.setTenKhuyenMai(ten);
            thongTinKhuyenMai.setChietKhau(Integer.parseInt(chietKhau));
            thongTinKhuyenMai.setNgayBatDau(ngayBatDau);
            thongTinKhuyenMai.setNgayKetThuc(ngayKetThuc);
            thongTinKhuyenMai.setThongTin(thongTin);
            uploadImage(thongTinKhuyenMai);
        });
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

    public void uploadImage(ThongTinKhuyenMai thongTinKhuyenMai) {
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
            thongTinKhuyenMai.setImage(image);
            myRef.child(String.valueOf(thongTinKhuyenMai.getMaKhuyenMai())).setValue(thongTinKhuyenMai).addOnCompleteListener(task -> {
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
