package com.example.apptourdulich;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.apptourdulich.Domains.ThongTinTour;
import com.example.apptourdulich.ui.admin.AdapterKhachSan;
import com.example.apptourdulich.ui.admin.ThongTinKhachSan;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;


public class Search extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterTour adapterTour;
    Toolbar myToolbar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference, fvrtref, fvrt_listRef;
    Boolean fvrtChecker = false;
    String SoDienThoai;
    ThongTinTour thongTinTour;
    EditText searchView;
    Button searchButton;
    RadioGroup radioGroup;
    RadioButton rdoTour, rdoKhachSan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle bundle = getIntent().getExtras();
        SoDienThoai = bundle.getString("SoDienThoai");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String currentUserid = user.getPhoneNumber();///
        String currentUserid = SoDienThoai;
        thongTinTour = new ThongTinTour();
        fvrtref = database.getReference("Likes");
        fvrt_listRef = database.getReference("LikeList").child(currentUserid);


        radioGroup = findViewById(R.id.radioGroup);
        rdoTour = findViewById(R.id.rbTour);
        rdoKhachSan = findViewById(R.id.rbHotel);

        searchView = findViewById(R.id.search);
        searchButton = findViewById(R.id.btnSearch);

        recyclerView = findViewById(R.id.recycleviewSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reference = database.getReference("Tour");

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchView.getText().toString();
                if(rdoTour.isChecked()) {
                    firebaseSearch(searchText);
                }else{
                    firebaseSearch2(searchText);
                }
            }
        });

    }

    private void firebaseSearch(String searchText) {
        FirebaseRecyclerOptions<ThongTinTour> options =
                new FirebaseRecyclerOptions.Builder<ThongTinTour>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tour").orderByChild("tenTour").startAt(searchText).endAt(searchText + "\uf8ff"), ThongTinTour.class).build();
        FirebaseRecyclerAdapter<ThongTinTour, AdapterTour> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ThongTinTour, AdapterTour>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdapterTour holder, int i, @NonNull ThongTinTour thongTinTour) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                        String currentUserid = user.getPhoneNumber();///
                        String currentUserid = SoDienThoai;
                        final String postkey = getRef(i).getKey();

                        holder.setItem(Search.this, thongTinTour.getMaTour(), thongTinTour.getTenTour(), thongTinTour.getPhuongTien(), thongTinTour.getKhachSan(), thongTinTour.getDonGia(), thongTinTour.getNgayKhoiHanh(), thongTinTour.getNgayketThuc(), thongTinTour.getMoTa(), thongTinTour.getTinhTrang(), thongTinTour.getKhuVuc(), thongTinTour.getNoiKhoiHanh(), thongTinTour.getSoNgay(), thongTinTour.getImage());

                        //Glide.with(holder.imageItemTour.getContext()).load(thongTinTour.getImage().into(holder.imageItemTour));
                        //todo: Load Image
                        String image = thongTinTour.getImage();
                        Task<Uri> storageReference = FirebaseStorage.getInstance().getReference().child("Images/" + image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL for 'users/me/profile.png'
                                System.out.println(uri);
                                Glide.with(holder.imageItemTour.getContext()).load(uri).into(holder.imageItemTour);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                        Picasso.get().load(thongTinTour.getImage()).into(holder.imageItemTour);

                        int idT = getItem(i).getMaTour();
                        String name = getItem(i).getTenTour();
                        String pt = getItem(i).getPhuongTien();
                        String ks = getItem(i).getKhachSan();
                        int dg = getItem(i).getDonGia();
                        String nkh = getItem(i).getNgayKhoiHanh();
                        final String nkt = getItem(i).getNgayketThuc();
                        String mt = getItem(i).getMoTa();
                        String tt = getItem(i).getTinhTrang();
                        String kv = getItem(i).getKhuVuc();
                        String imgg = getItem(i).getImage();
                        String sng = getItem(i).getSoNgay();
                        String noikh = getItem(i).getNoiKhoiHanh();

                        //todo: Save
                        holder.favoriteChecker(postkey);
                        holder.imgBtnfav.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                fvrtChecker = true;
                                fvrtref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (fvrtChecker.equals(true)) {
                                            if (snapshot.child(postkey).hasChild(currentUserid)) {
                                                fvrtref.child(postkey).child(currentUserid).removeValue();
                                                delete(idT);
                                                fvrtChecker = false;
                                            } else {
                                                fvrtref.child(postkey).child(currentUserid).setValue(true);
                                                thongTinTour.setMaTour(idT);
                                                thongTinTour.setTenTour(name);
                                                thongTinTour.setNgayKhoiHanh(nkh);
                                                thongTinTour.setPhuongTien(pt);
                                                thongTinTour.setKhachSan(ks);
                                                thongTinTour.setNgayketThuc(nkt);
                                                thongTinTour.setDonGia(dg);
                                                thongTinTour.setMoTa(mt);
                                                thongTinTour.setTinhTrang(tt);
                                                thongTinTour.setKhuVuc(kv);
                                                thongTinTour.setImage(imgg);
                                                thongTinTour.setSoNgay(sng);
                                                thongTinTour.setNoiKhoiHanh(noikh);

                                                String id = fvrt_listRef.push().getKey();
                                                fvrt_listRef.child(id).setValue(thongTinTour);
                                                fvrtChecker = false;

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        });
                        // todo: infor
                        holder.imageItemTour.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                                Intent i = new Intent(appCompatActivity, InfoTour.class);
                                Bundle b = new Bundle();
                                b.putInt("IDTour", thongTinTour.getMaTour());
                                b.putString("SoDienThoai", SoDienThoai);
                                i.putExtras(b);
                                startActivity(i);
                            }
                        });


                    }


                    @NonNull
                    @Override
                    public AdapterTour onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view1 = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.touritem, parent, false);
                        return new AdapterTour(view1, SoDienThoai);
                    }


                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    private void firebaseSearch2(String newText) {
//        FirebaseRecyclerOptions<ThongTinKhachSan> ThongTinKhachSanFirebaseOptions=new FirebaseRecyclerOptions.Builder<ThongTinKhachSan>().setQuery(FirebaseDatabase.getInstance().getReference("KhachSan"),ThongTinKhachSan.class).build();
//        AdapterKhachSan adapterKhachSan = new AdapterKhachSan(ThongTinKhachSanFirebaseOptions);
//        recyclerView1.setAdapter(adapterKhachSan);
//        recyclerView1.setHasFixedSize(true);
//        adapterKhachSan.startListening();

        FirebaseRecyclerOptions<ThongTinKhachSan> options =
                new FirebaseRecyclerOptions.Builder<ThongTinKhachSan>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("KhachSan").orderByChild("tenKhachSan").startAt(newText).endAt(newText + "\uf8ff"), ThongTinKhachSan.class).build();
        AdapterKhachSan adapterKhachSan = new AdapterKhachSan(options);
        adapterKhachSan.startListening();
        recyclerView.setAdapter(adapterKhachSan);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.search_button);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FirebaseRecyclerOptions<ThongTinTour> options =
                        new FirebaseRecyclerOptions.Builder<ThongTinTour>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("Tour").orderByChild("tenTour").startAt(newText).endAt(newText + "\uf8ff"), ThongTinTour.class).build();
                FirebaseRecyclerAdapter<ThongTinTour, AdapterTour> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<ThongTinTour, AdapterTour>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull AdapterTour holder, int i, @NonNull ThongTinTour thongTinTour) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String currentUserid = user.getPhoneNumber();///
                                final String postkey = getRef(i).getKey();

                                holder.setItem(Search.this, thongTinTour.getMaTour(), thongTinTour.getTenTour(), thongTinTour.getPhuongTien(), thongTinTour.getKhachSan(), thongTinTour.getDonGia(), thongTinTour.getNgayKhoiHanh(), thongTinTour.getNgayketThuc(), thongTinTour.getMoTa(), thongTinTour.getTinhTrang(), thongTinTour.getKhuVuc(), thongTinTour.getNoiKhoiHanh(), thongTinTour.getSoNgay(), thongTinTour.getImage());

                                //Glide.with(holder.imageItemTour.getContext()).load(thongTinTour.getImage().into(holder.imageItemTour));
                                //todo: c
                                String image = thongTinTour.getImage();
                                Task<Uri> storageReference = FirebaseStorage.getInstance().getReference().child("Images/" + image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        System.out.println(uri);
                                        Glide.with(holder.imageItemTour.getContext()).load(uri).into(holder.imageItemTour);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                    }
                                });

                                int idT = getItem(i).getMaTour();
                                String name = getItem(i).getTenTour();
                                String pt = getItem(i).getPhuongTien();
                                String ks = getItem(i).getKhachSan();
                                int dg = getItem(i).getDonGia();
                                String nkh = getItem(i).getNgayKhoiHanh();
                                final String nkt = getItem(i).getNgayketThuc();
                                String mt = getItem(i).getMoTa();
                                String tt = getItem(i).getTinhTrang();
                                String kv = getItem(i).getKhuVuc();
                                String imgg = getItem(i).getImage();
                                String sng = getItem(i).getSoNgay();
                                String noikh = getItem(i).getNoiKhoiHanh();

                                //todo:c
                                holder.favoriteChecker(postkey);
                                holder.imgBtnfav.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        fvrtChecker = true;
                                        fvrtref.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (fvrtChecker.equals(true)) {
                                                    if (snapshot.child(postkey).hasChild(currentUserid)) {
                                                        fvrtref.child(postkey).child(currentUserid).removeValue();
                                                        delete(idT);
                                                        fvrtChecker = false;
                                                    } else {
                                                        fvrtref.child(postkey).child(currentUserid).setValue(true);
                                                        thongTinTour.setMaTour(idT);
                                                        thongTinTour.setTenTour(name);
                                                        thongTinTour.setNgayKhoiHanh(nkh);
                                                        thongTinTour.setPhuongTien(pt);
                                                        thongTinTour.setKhachSan(ks);
                                                        thongTinTour.setNgayketThuc(nkt);
                                                        thongTinTour.setDonGia(dg);
                                                        thongTinTour.setMoTa(mt);
                                                        thongTinTour.setTinhTrang(tt);
                                                        thongTinTour.setKhuVuc(kv);
                                                        thongTinTour.setImage(imgg);
                                                        thongTinTour.setSoNgay(sng);
                                                        thongTinTour.setNoiKhoiHanh(noikh);

                                                        String id = fvrt_listRef.push().getKey();
                                                        fvrt_listRef.child(id).setValue(thongTinTour);
                                                        fvrtChecker = false;

                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                });
                                // todo: c
                                holder.imageItemTour.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                                        Intent i = new Intent(appCompatActivity, InfoTour.class);
                                        Bundle b = new Bundle();
                                        b.putInt("IDTour", thongTinTour.getMaTour());
                                        b.putString("SoDienThoai", SoDienThoai);
                                        i.putExtras(b);
                                        startActivity(i);
                                    }
                                });


                            }


                            @NonNull
                            @Override
                            public AdapterTour onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view1 = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.touritem, parent, false);
                                return new AdapterTour(view1, SoDienThoai);
                            }


                        };
                firebaseRecyclerAdapter.startListening();
                recyclerView.setAdapter(firebaseRecyclerAdapter);


                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }


    void delete(int idT) {
        Query query = fvrt_listRef.orderByChild("maTour").equalTo(idT);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dataSnapshot.getRef().removeValue();
                    Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}