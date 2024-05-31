package com.example.apptourdulich.ui.admin;

import static com.example.apptourdulich.Activity.SignLoginActivity.role;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.apptourdulich.Home;
import com.example.apptourdulich.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HistoryHotel extends AppCompatActivity {
    DatabaseReference referenceTour, referenceKhachHang, referenceHoaDon, referenceKhuyenMai;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RecyclerView recyclerView;
    AdapterHoaDon adapterHoaDon;
    String SoDienThoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_hotel);
        recyclerView = findViewById(R.id.rcv_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageView imgBack = findViewById(R.id.imgBackHistory);

        Bundle bundle = this.getIntent().getExtras();
        if (role.equals("admin")) {
            FirebaseRecyclerOptions<HoaDonKhachSan> options =
                    new FirebaseRecyclerOptions.Builder<HoaDonKhachSan>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("HoaDonKhachSan"), HoaDonKhachSan.class).build();
            adapterHoaDon = new AdapterHoaDon(options);
            recyclerView.setAdapter(adapterHoaDon);
        } else {
            SoDienThoai = bundle.getString("SoDienThoai");
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(HistoryHotel.this, Home.class);
                    i.putExtra("SoDienThoai", SoDienThoai);
                    startActivity(i);
                }
            });

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String currentUserid = user.getPhoneNumber();

            FirebaseRecyclerOptions<HoaDonKhachSan> options =
                    new FirebaseRecyclerOptions.Builder<HoaDonKhachSan>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("HoaDonKhachSan").
                                    orderByChild("soDienThoai").equalTo(SoDienThoai), HoaDonKhachSan.class).build();

            adapterHoaDon = new AdapterHoaDon(options);
            recyclerView.setAdapter(adapterHoaDon);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        adapterHoaDon.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterHoaDon.stopListening();
    }
}