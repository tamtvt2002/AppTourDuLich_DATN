package com.example.apptourdulich;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.apptourdulich.Adapter.AdapterThongBao;
import com.example.apptourdulich.Domains.ThongTinThongBao;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ThongBao extends AppCompatActivity {
RecyclerView rcvThongBao;
AdapterThongBao adapterThongBao;
ThongTinThongBao thongTinThongBao;
ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);
        back=findViewById(R.id.imgThoatThongBao);
        rcvThongBao=findViewById(R.id.rvThongBao);
        Bundle b= getIntent().getExtras();
        String sdt=b.getString("SoDienThoai");

        rcvThongBao.setLayoutManager(new LinearLayoutManager(ThongBao.this));
        FirebaseRecyclerOptions<ThongTinThongBao> thongTinTinTucFirebaseOptions=new FirebaseRecyclerOptions.Builder<ThongTinThongBao>().setQuery(FirebaseDatabase.getInstance().getReference().child("ThongBao").orderByChild("soDienThoai").equalTo(sdt),ThongTinThongBao.class).build();

        adapterThongBao=new AdapterThongBao(thongTinTinTucFirebaseOptions);
        rcvThongBao.setAdapter(adapterThongBao);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterThongBao.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterThongBao.stopListening();
    }
}