package com.example.apptourdulich.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.apptourdulich.Adapter.AdapterTinTuc;
import com.example.apptourdulich.Domains.ThongTinTinTuc;
import com.example.apptourdulich.databinding.ActivityDanhSachTinBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DanhSachTinActivity extends AppCompatActivity {
    ActivityDanhSachTinBinding binding;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("TinTuc");
    private AdapterTinTuc adapterTinTuc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDanhSachTinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(DanhSachTinActivity.this, ThemTinTucActivity.class);
            intent.putExtra("action", "add");
            startActivity(intent);
        });

        FirebaseRecyclerOptions<ThongTinTinTuc> thongTinTinTucFirebaseOptions=new FirebaseRecyclerOptions.Builder<ThongTinTinTuc>().setQuery(FirebaseDatabase.getInstance().getReference().child("TinTuc"),ThongTinTinTuc.class).build();

        adapterTinTuc=new AdapterTinTuc(thongTinTinTucFirebaseOptions);
        binding.rvDanhSachTin.setAdapter(adapterTinTuc);
        adapterTinTuc.startListening();
        binding.rvDanhSachTin.setLayoutManager(new LinearLayoutManager(this));

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
        FirebaseRecyclerOptions<ThongTinTinTuc> thongTinTinTucFirebaseOptions=new FirebaseRecyclerOptions.Builder<ThongTinTinTuc>().setQuery(FirebaseDatabase.getInstance().getReference().child("TinTuc").orderByChild("tenTinTuc").startAt(query).endAt(query+"\uf8ff"),ThongTinTinTuc.class).build();
        adapterTinTuc=new AdapterTinTuc(thongTinTinTucFirebaseOptions);
        binding.rvDanhSachTin.setAdapter(adapterTinTuc);
        adapterTinTuc.startListening();
    }
}