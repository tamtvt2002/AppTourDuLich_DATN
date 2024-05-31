package com.example.apptourdulich.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.IntentFilter;

import android.net.ConnectivityManager;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptourdulich.Home;
import com.example.apptourdulich.Domains.KhachHang;
import com.example.apptourdulich.NetworkChangeListener;
import com.example.apptourdulich.R;
import com.example.apptourdulich.Register;
import com.example.apptourdulich.ui.admin.AdminActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignLoginActivity extends AppCompatActivity {
 Button dangnhap;
 TextView  dangki,Demo;
 EditText edtSoDienThoai,edtMatKhau;
 ImageView imageView;
NetworkChangeListener networkChangeListener=new NetworkChangeListener();
  CallbackManager callbackManager = CallbackManager.Factory.create();
         private FirebaseAuth mFirebaseAuth;
         private LoginButton loginButton;

private static final  String Email="email";
    public static String role = "admin";
    public static String _phone = "";
    public static String _name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_login);
//facebook
        mFirebaseAuth=FirebaseAuth.getInstance();
//        FacebookSdk.sdkInitialize(getApplicationContext());
        dangnhap=findViewById(R.id.btndn);



        edtSoDienThoai=findViewById(R.id.edtSDT);
        edtMatKhau  = findViewById(R.id.edtPass);
        dangki=findViewById(R.id.btnDangKi);


        callbackManager=CallbackManager.Factory.create();
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Toast.makeText(SignLoginActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();
//                handleFacebookToken(loginResult.getAccessToken());
//                Intent i=new Intent(SignLoginActivity.this,Home.class);
//                startActivity(i);
//            }
//
//            @Override
//            public void onCancel() {
//                Toast.makeText(SignLoginActivity.this,"Login Cancel",Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Toast.makeText(SignLoginActivity.this,"Login error"+error.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });

        dangki=findViewById(R.id.btnDangKi);

        dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SignLoginActivity.this, Register.class);
                startActivity(i);
            }
        });
      edtSoDienThoai=findViewById(R.id.edtSDT);
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("KhachHang");
                userRef.orderByChild("sdt").equalTo(edtSoDienThoai.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() == null) {
                            //it means user already registered
                            //Add code to show your prompt
                      edtSoDienThoai.setError("Số Điện Thoại Chưa Được Đăng Kí");
                        } else {
                            String SoDienThoai = edtSoDienThoai.getText().toString().trim();
                            if (SoDienThoai.isEmpty()) {
                                edtSoDienThoai.setError("Vui Lòng Nhập Số Điện Thoại!!");
                                edtSoDienThoai.requestFocus();
                                return;
                            }

                            Log.d("TAGAPI", "onDataChange: " + snapshot.getValue());
                            Log.d("TAGAPI", "onDataChange: " + snapshot.getChildren());
                            KhachHang khachHang = new KhachHang();
                            khachHang = snapshot.getChildren().iterator().next().getValue(KhachHang.class);
                            Log.d("TAGAPI", "onDataChange: " + khachHang.getRole());
                            role = khachHang.getRole();
                            _phone = khachHang.getSDT();
                            _name = khachHang.getHoTen();
                            String pass = edtMatKhau.getText().toString().trim();
                            if (pass.isEmpty()) {
                                edtMatKhau.setError("Vui Lòng Nhập Mật Khẩu!!");
                                edtMatKhau.requestFocus();
                                return;
                            }
                            if (!pass.equals(khachHang.getPassword())) {
                                edtMatKhau.setError("Mật Khẩu Không Đúng!!");
                                edtMatKhau.requestFocus();
                                return;
                            }
                            Intent i;
                            if(role.equals("admin")){
                                i = new Intent(SignLoginActivity.this, AdminActivity.class);
                            }
                            else {
                                i = new Intent(SignLoginActivity.this, Home.class);
                                i.putExtra("SoDienThoai", edtSoDienThoai.getText().toString());
                            }
                            //login
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }




    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential=FacebookAuthProvider.getCredential(accessToken.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user= mFirebaseAuth.getCurrentUser();
                    updateUI(user);
                }else{
                    Toast.makeText(SignLoginActivity.this,"Login error",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void updateUI(FirebaseUser user) {
       return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
    }

    @Override
    protected void onStop() {

        super.onStop();
        unregisterReceiver(networkChangeListener);
    }
}