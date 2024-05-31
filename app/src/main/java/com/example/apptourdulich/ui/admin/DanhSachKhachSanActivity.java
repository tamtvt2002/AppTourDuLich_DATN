package com.example.apptourdulich.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.apptourdulich.databinding.ActivityDanhSachKhachSanBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DanhSachKhachSanActivity extends AppCompatActivity {

    ActivityDanhSachKhachSanBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("KhachSan");
    AdapterKhachSan adapterKhachSan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDanhSachKhachSanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseRecyclerOptions<ThongTinKhachSan> ThongTinKhachSanFirebaseOptions=new FirebaseRecyclerOptions.Builder<ThongTinKhachSan>().setQuery(myRef,ThongTinKhachSan.class).build();

        adapterKhachSan = new AdapterKhachSan(ThongTinKhachSanFirebaseOptions);
        binding.recyclerView.setAdapter(adapterKhachSan);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        adapterKhachSan.startListening();

        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(DanhSachKhachSanActivity.this, ThemKhachSanActivity.class);
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
        FirebaseRecyclerOptions<ThongTinKhachSan> ThongTinKhachSanFirebaseOptions=new FirebaseRecyclerOptions.Builder<ThongTinKhachSan>().setQuery(myRef.orderByChild("tenKhachSan").startAt(query).endAt(query+"\uf8ff"),ThongTinKhachSan.class).build();
        adapterKhachSan = new AdapterKhachSan(ThongTinKhachSanFirebaseOptions);
        binding.recyclerView.setAdapter(adapterKhachSan);
        adapterKhachSan.startListening();
    }
}