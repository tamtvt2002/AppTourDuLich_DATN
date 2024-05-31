package com.example.apptourdulich;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.apptourdulich.Adapter.AdapterLichSu;
import com.example.apptourdulich.Domains.ThongTinHoaDon;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class History extends AppCompatActivity {

    DatabaseReference referenceTour,referenceKhachHang,referenceHoaDon,referenceKhuyenMai;
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    RecyclerView recyclerView;
    AdapterLichSu adapterLichSu;
    String SoDienThoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.rcv_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageView imgBack=findViewById(R.id.imgBackHistory);

        Bundle bundle = this.getIntent().getExtras();
        SoDienThoai = bundle.getString("SoDienThoai");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(History.this,Home.class);
                i.putExtra("SoDienThoai",SoDienThoai);
                startActivity(i);
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String currentUserid = user.getPhoneNumber();

        FirebaseRecyclerOptions<ThongTinHoaDon> options =
                new FirebaseRecyclerOptions.Builder<ThongTinHoaDon>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("HoaDon").
                                orderByChild("soDienThoai").equalTo(SoDienThoai), ThongTinHoaDon.class).build();

        adapterLichSu = new AdapterLichSu(options);
        recyclerView.setAdapter(adapterLichSu);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapterLichSu.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapterLichSu.stopListening();
    }
}