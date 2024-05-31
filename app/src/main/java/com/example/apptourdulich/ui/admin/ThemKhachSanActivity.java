package com.example.apptourdulich.ui.admin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apptourdulich.databinding.ActivityThemKhachSanBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kongzue.dialogx.dialogs.TipDialog;
import com.kongzue.dialogx.dialogs.WaitDialog;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ThemKhachSanActivity extends AppCompatActivity {

    ActivityThemKhachSanBinding binding;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("KhachSan");
    private ThongTinKhachSan thongTinKhachSan = new ThongTinKhachSan();
    private String action = "add";
    private boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemKhachSanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().getStringExtra("action").equals("edit")) {
            action = "edit";
            thongTinKhachSan = (ThongTinKhachSan) getIntent().getSerializableExtra("khachSan");
            binding.editTextTenKhachSan.setText(thongTinKhachSan.getTenKhachSan());
            binding.editTextDiaChi.setText(thongTinKhachSan.getDiaChi());
            binding.editTextSoDienThoai.setText(thongTinKhachSan.getSoDienThoai());
            binding.editTextMoTa.setText(thongTinKhachSan.getMoTa());
            binding.editTextGiaTien1h.setText(thongTinKhachSan.getGiaTien1h());
            binding.editTextGiaTien1ngay.setText(thongTinKhachSan.getGiaTien1ngay());
            binding.editTextGiaTien1dem.setText(thongTinKhachSan.getGiaTien1dem());
            binding.editTextTinhTrang.setText(thongTinKhachSan.getTinhTrang());
            binding.buttonSubmit.setText("Cập nhật");
            Glide.with(this).load(thongTinKhachSan.getHinhAnh()).into(binding.imageView);
            check = true;
        }

        binding.buttonChoose.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            pickImage.launch(intent);
        });

        binding.buttonSubmit.setOnClickListener(v -> {
            String tenKhachSan = binding.editTextTenKhachSan.getText().toString();
            String diaChi = binding.editTextDiaChi.getText().toString();
            String soDienThoai = binding.editTextSoDienThoai.getText().toString();
            String moTa = binding.editTextMoTa.getText().toString();
            String giaTien1h = binding.editTextGiaTien1h.getText().toString();
            String giaTien1ngay = binding.editTextGiaTien1ngay.getText().toString();
            String giaTien1dem = binding.editTextGiaTien1dem.getText().toString();
            String tinhTrang = binding.editTextTinhTrang.getText().toString();
            if (tenKhachSan.isEmpty() || diaChi.isEmpty() || soDienThoai.isEmpty() || moTa.isEmpty() || giaTien1h.isEmpty() || giaTien1ngay.isEmpty() || giaTien1dem.isEmpty() || tinhTrang.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            thongTinKhachSan.setTenKhachSan(tenKhachSan);
            thongTinKhachSan.setDiaChi(diaChi);
            thongTinKhachSan.setSoDienThoai(soDienThoai);
            thongTinKhachSan.setMoTa(moTa);
            thongTinKhachSan.setGiaTien1h(giaTien1h);
            thongTinKhachSan.setGiaTien1ngay(giaTien1ngay);
            thongTinKhachSan.setGiaTien1dem(giaTien1dem);
            thongTinKhachSan.setTinhTrang(tinhTrang);
            if (action.equals("add")) {
                thongTinKhachSan.setKeyId(myRef.push().getKey());
            }
            uploadImage(thongTinKhachSan);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            finish();
        });

        binding.buttonDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xác nhận");
            builder.setMessage("Bạn có chắc chắn muốn xóa?");
            builder.setPositiveButton("Xóa", (dialog, which) -> {
                        myRef.child(thongTinKhachSan.getKeyId()).removeValue();
                        TipDialog.show(this, "Đã xóa", TipDialog.TYPE.SUCCESS);
                        finish();
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> {
                    });
            builder.create().show();

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

    public void uploadImage(ThongTinKhachSan thongTinKhachSan) {
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
            thongTinKhachSan.setHinhAnh(image);
            myRef.child(String.valueOf(thongTinKhachSan.getKeyId())).setValue(thongTinKhachSan).addOnCompleteListener(task -> {
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