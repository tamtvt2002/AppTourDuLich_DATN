package com.example.apptourdulich;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apptourdulich.Domains.KhachHang;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText HoTen, NgaySinh, SDT, CMND, DiaChi, password, confirmPassword;
    private RadioButton GioiTinhNam, GioiTinhNu;
    Button DangKi;
    DatabaseReference Ref;
    StorageReference mStorageRef;
    KhachHang khachHang;
    long maxid;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    Uri uri;
    StorageReference storageRef;
    String gt;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFirebaseAuth=FirebaseAuth.getInstance();
        HoTen = findViewById(R.id.edHoTenDK);
        GioiTinhNam = findViewById(R.id.btnGioiTinhNam);
        GioiTinhNu = findViewById(R.id.btnGioiTinhNam);
        NgaySinh = findViewById(R.id.edNgaySinhDK);
        SDT = findViewById(R.id.edSDTDK);
        CMND = findViewById(R.id.edCMNDDK);
        DiaChi = findViewById(R.id.edDiaChiDK);
        DangKi = findViewById(R.id.btnSubmitDangKi);
        password = findViewById(R.id.edPassDK);
        confirmPassword = findViewById(R.id.edConfirmPassDK);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.man_user);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference("images");
        mStorageRef = FirebaseStorage.getInstance().getReference("profiles");
        Ref = database.getReference().child("KhachHang");
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxid = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        NgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatepickerDialog();
            }
        });
        khachHang = new KhachHang();
        DangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = HoTen.getText().toString().trim();
                gt = "Nữ";

                String NS = NgaySinh.getText().toString().trim();
                String dt = SDT.getText().toString().trim();
                String cm = CMND.getText().toString().trim();
                String dc = DiaChi.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String confirmPass = confirmPassword.getText().toString().trim();
                if (GioiTinhNam.isChecked()) {
                    gt = "Nam";
                } else if (GioiTinhNam.isChecked() == false && GioiTinhNu.isChecked() == false) {
                    Toast.makeText(getApplicationContext(),
                            "Vui Lòng Chọn Giới Tính !!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (ten.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Vui Lòng Nhập Họ Tên !!", Toast.LENGTH_LONG).show();
                    return;
                } else if (dt.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Vui Lòng Nhập Số Điện Thoại !!", Toast.LENGTH_LONG).show();
                    return;
                } else if (cm.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Vui Lòng Nhập CMND !!", Toast.LENGTH_LONG).show();
                    return;
                } else if (NS.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Vui Lòng Chọn Ngày Sinh !!", Toast.LENGTH_LONG).show();
                    return;
                } else if (dc.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Vui Lòng Nhập Địa Chỉ !!", Toast.LENGTH_LONG).show();
                    return;
                } else if (pass.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Vui Lòng Nhập Mật Khẩu !!", Toast.LENGTH_LONG).show();
                    return;
                } else if (confirmPass.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Vui Lòng Nhập Xác Nhận Mật Khẩu !!", Toast.LENGTH_LONG).show();
                    return;
                } else if (!pass.equals(confirmPass)) {
                    Toast.makeText(getApplicationContext(),
                            "Mật Khẩu Không Trùng Khớp !!", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("KhachHang");
                    String finalGt = gt;
                    userRef.orderByChild("sdt").equalTo(dt).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() == null) {
                                khachHang.setHoTen(ten);
                                khachHang.setGioitinh(gt);
                                khachHang.setNgaySinh(NS);
                                khachHang.setSDT(dt);
                                khachHang.setImageid("man_user.png");
                                khachHang.setCMND(cm);
                                khachHang.setDiaChi(dc);
                                khachHang.setRole("user");
                                khachHang.setPassword(pass);
                                Ref.push().setValue(khachHang);

                                Toast.makeText(Register.this, "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                                PhoneAuthOptions options;
                                options = PhoneAuthOptions.newBuilder(mFirebaseAuth)
                                        .setPhoneNumber("+84" + dt)       // Phone number to verify
                                        .setTimeout(60l, TimeUnit.SECONDS) // Timeout and unit
                                        .setActivity(Register.this)                 // Activity (for callback binding)
                                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                            @Override
                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                                                DangKi .setVisibility(View.VISIBLE);


                                            }

                                            @Override
                                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                                DangKi.setVisibility(View.VISIBLE);
                                            }

                                            @Override
                                            public void onCodeSent(@NonNull String s,
                                                                   @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                DangKi.setVisibility(View.VISIBLE);
                                                Intent i = new Intent(Register.this, Confirm_otp.class);
                                                i.putExtra("SoDienThoai", dt);
                                                i.putExtra("codeotp", s);
                                                startActivity(i);
                                            }
                                        })          // OnVerificationStateChangedCallbacks
                                        .build();
                                PhoneAuthProvider.verifyPhoneNumber(options);

                                //it means user already registered
                                //Add code to show your prompt

                            } else {
                                Toast.makeText(getApplicationContext(), "Phone Number Unregistered", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    public void showDatepickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, this,

                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        NgaySinh.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
    }

    @Override
    protected void onStop() {

        super.onStop();
        unregisterReceiver(networkChangeListener);
    }

}