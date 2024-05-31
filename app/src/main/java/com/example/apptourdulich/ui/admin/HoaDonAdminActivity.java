package com.example.apptourdulich.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.example.apptourdulich.Adapter.AdapterLichSu;
import com.example.apptourdulich.R;
import com.example.apptourdulich.Domains.ThongTinHoaDon;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class HoaDonAdminActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterLichSu adapterLichSu;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_admin);
        recyclerView = findViewById(R.id.rcv_hoa_don_admin);
        searchView = findViewById(R.id.searchView);

        FirebaseRecyclerOptions<ThongTinHoaDon> options =
                new FirebaseRecyclerOptions.Builder<ThongTinHoaDon>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("HoaDon"), ThongTinHoaDon.class).build();

        adapterLichSu = new AdapterLichSu(options);
        recyclerView.setAdapter(adapterLichSu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterLichSu.startListening();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        FirebaseRecyclerOptions<ThongTinHoaDon> options =
                new FirebaseRecyclerOptions.Builder<ThongTinHoaDon>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("HoaDon").orderByChild("soDienThoai").startAt(query).endAt(query+"\uf8ff"), ThongTinHoaDon.class).build();
        adapterLichSu = new AdapterLichSu(options);
        recyclerView.setAdapter(adapterLichSu);
        adapterLichSu.startListening();
    }
}