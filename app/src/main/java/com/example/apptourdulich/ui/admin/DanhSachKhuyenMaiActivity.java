package com.example.apptourdulich.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.apptourdulich.Adapter.AdapterKhuyenMai;
import com.example.apptourdulich.Domains.ThongTinKhuyenMai;
import com.example.apptourdulich.databinding.ActivityDanhSachKhuyenMaiBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class DanhSachKhuyenMaiActivity extends AppCompatActivity {
    ActivityDanhSachKhuyenMaiBinding binding;
    private AdapterKhuyenMai adapterKhuyenMai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDanhSachKhuyenMaiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        FirebaseRecyclerOptions<ThongTinKhuyenMai> thongTinKhuyenMaiFirebaseRecyclerOptionsFirebaseOptions=new FirebaseRecyclerOptions.Builder<ThongTinKhuyenMai>().setQuery(FirebaseDatabase.getInstance().getReference().child("KhuyenMai"),ThongTinKhuyenMai.class).build();

        adapterKhuyenMai=new AdapterKhuyenMai(thongTinKhuyenMaiFirebaseRecyclerOptionsFirebaseOptions);
        binding.recyclerView.setAdapter(adapterKhuyenMai);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        adapterKhuyenMai.startListening();

        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(DanhSachKhuyenMaiActivity.this, ThemKhuyenMaiActivity.class);
            intent.putExtra("action", "add");
            startActivity(intent);
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);
                return false;
            }
        });
    }

    private void processSearch(String query) {
        FirebaseRecyclerOptions<ThongTinKhuyenMai> thongTinKhuyenMaiFirebaseRecyclerOptionsFirebaseOptions=new FirebaseRecyclerOptions.Builder<ThongTinKhuyenMai>().setQuery(FirebaseDatabase.getInstance().getReference().child("KhuyenMai").orderByChild("tenKhuyenMai").startAt(query).endAt(query+"\uf8ff"),ThongTinKhuyenMai.class).build();
        adapterKhuyenMai=new AdapterKhuyenMai(thongTinKhuyenMaiFirebaseRecyclerOptionsFirebaseOptions);
        adapterKhuyenMai.startListening();
        binding.recyclerView.setAdapter(adapterKhuyenMai);
    }
}