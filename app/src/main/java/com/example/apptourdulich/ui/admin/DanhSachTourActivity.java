package com.example.apptourdulich.ui.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.apptourdulich.R;
import com.example.apptourdulich.Domains.ThongTinTour;
import com.example.apptourdulich.databinding.ActivityDanhSachTourBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DanhSachTourActivity extends AppCompatActivity {

    ActivityDanhSachTourBinding binding;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Tour");
    private AdapterTourAdmin adapterTourAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDanhSachTourBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseRecyclerOptions<ThongTinTour> options = new FirebaseRecyclerOptions.Builder<ThongTinTour>()
                .setQuery(myRef, ThongTinTour.class)
                .build();

        FirebaseRecyclerAdapter<ThongTinTour, AdapterTourAdmin> adapter = new FirebaseRecyclerAdapter<ThongTinTour, AdapterTourAdmin>(options) {
            @Override
            protected void onBindViewHolder(AdapterTourAdmin holder, int position, ThongTinTour model) {
                holder.setItem(DanhSachTourActivity.this, model.getMaTour(), model.getTenTour(), model.getPhuongTien(), model.getKhachSan(), model.getDonGia(), model.getNgayKhoiHanh(), model.getNgayketThuc(), model.getMoTa(), model.getTinhTrang(), model.getKhuVuc(), model.getNoiKhoiHanh(), model.getSoNgay(), model.getImage());

                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(DanhSachTourActivity.this, AddTourActivity.class);
                    intent.putExtra("action", "edit");
                    intent.putExtra("Tour", model);
                    startActivity(intent);
                });
            }

            @Override
            public AdapterTourAdmin onCreateViewHolder(ViewGroup parent, int viewType) {
                return new AdapterTourAdmin(getLayoutInflater().inflate(R.layout.touritem, parent, false));
            }
        };

        binding.recyclerView.setAdapter(adapter);
        adapter.startListening();

        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(DanhSachTourActivity.this, AddTourActivity.class);
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
        FirebaseRecyclerOptions<ThongTinTour> options = new FirebaseRecyclerOptions.Builder<ThongTinTour>()
                .setQuery(myRef.orderByChild("tenTour").startAt(query).endAt(query + "\uf8ff"), ThongTinTour.class)
                .build();
        FirebaseRecyclerAdapter<ThongTinTour, AdapterTourAdmin> adapter = new FirebaseRecyclerAdapter<ThongTinTour, AdapterTourAdmin>(options) {
            @Override
            protected void onBindViewHolder(AdapterTourAdmin holder, int position, ThongTinTour model) {
                holder.setItem(DanhSachTourActivity.this, model.getMaTour(), model.getTenTour(), model.getPhuongTien(), model.getKhachSan(), model.getDonGia(), model.getNgayKhoiHanh(), model.getNgayketThuc(), model.getMoTa(), model.getTinhTrang(), model.getKhuVuc(), model.getNoiKhoiHanh(), model.getSoNgay(), model.getImage());

                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(DanhSachTourActivity.this, AddTourActivity.class);
                    intent.putExtra("action", "edit");
                    intent.putExtra("Tour", model);
                    startActivity(intent);
                });
            }

            @Override
            public AdapterTourAdmin onCreateViewHolder(ViewGroup parent, int viewType) {
                return new AdapterTourAdmin(getLayoutInflater().inflate(R.layout.touritem, parent, false));
            }
        };

        binding.recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}