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
import android.view.View;
import android.widget.Toast;

import com.example.apptourdulich.Domains.ThongTinTinTuc;
import com.example.apptourdulich.databinding.ActivityThemTinTucBinding;
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

public class ThemTinTucActivity extends AppCompatActivity {
    ActivityThemTinTucBinding binding;
    private boolean check = false;
    private final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("TinTuc");
    private ThongTinTinTuc thongTinTinTuc;
    private String action="add";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityThemTinTucBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        thongTinTinTuc = new ThongTinTinTuc();
        if(getIntent().getStringExtra("action")!=null){
            action = getIntent().getStringExtra("action");
        }
        if(action.equals("edit")){
            thongTinTinTuc = (ThongTinTinTuc) getIntent().getSerializableExtra("thongTinTinTuc");
            binding.editTextTenTinTuc.setText(thongTinTinTuc.getTenTinTuc());
            binding.editTextThongTin.setText(thongTinTinTuc.getThongTin());
            binding.editTextThongTinNgan.setText(thongTinTinTuc.getThongTinNgan());
            Picasso.get().load(thongTinTinTuc.getImage()).into(binding.imageView);
            binding.buttonChoose.setText("Thay đổi");
            check = true;
            binding.buttonDelete.setVisibility(View.VISIBLE);
        }

        binding.buttonChoose.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            pickImage.launch(intent);
        });

        binding.buttonSubmit.setOnClickListener(v -> {
            String tenTinTuc = binding.editTextTenTinTuc.getText().toString();
            String thongTin = binding.editTextThongTin.getText().toString();
            String thongTinNgan = binding.editTextThongTinNgan.getText().toString();
            if(tenTinTuc.isEmpty() || thongTin.isEmpty() || thongTinNgan.isEmpty()){
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            thongTinTinTuc.setTenTinTuc(tenTinTuc);
            thongTinTinTuc.setThongTin(thongTin);
            thongTinTinTuc.setThongTinNgan(thongTinNgan);
            if(action.equals("add")){
                thongTinTinTuc.setMaTinTuc((int) (Math.random() * 100000));
            }
            uploadImage(thongTinTinTuc);
        });
    }

    public void uploadImage(ThongTinTinTuc  thongTinTinTuc) {
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
            thongTinTinTuc.setImage(image);
            myRef.child(String.valueOf(thongTinTinTuc.getMaTinTuc())).setValue(thongTinTinTuc).addOnCompleteListener(task -> {
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

        binding.buttonDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xác nhận");
            builder.setMessage("Bạn có chắc chắn muốn xóa?");
            builder.setPositiveButton("Có", (dialog, which) -> {
                myRef.child(String.valueOf(thongTinTinTuc.getMaTinTuc())).removeValue().addOnCompleteListener(task -> {
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
            builder.setNegativeButton("Không", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.show();
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
}