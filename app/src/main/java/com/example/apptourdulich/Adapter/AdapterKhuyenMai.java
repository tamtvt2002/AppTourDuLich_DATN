package com.example.apptourdulich.Adapter;

import static com.example.apptourdulich.Activity.SignLoginActivity.role;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptourdulich.Activity.DetailKhuyenMaiActivity;
import com.example.apptourdulich.R;
import com.example.apptourdulich.Domains.ThongTinKhuyenMai;
import com.example.apptourdulich.ui.admin.ThemKhuyenMaiActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterKhuyenMai extends FirebaseRecyclerAdapter<ThongTinKhuyenMai,AdapterKhuyenMai.MyViewHolder> {
    //adapterKhuyenMai là adapter để kết nối adaptee và target
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterKhuyenMai(@NonNull FirebaseRecyclerOptions<ThongTinKhuyenMai> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int i, @NonNull ThongTinKhuyenMai thongTinKhuyenMai) {
        holder.ten.setText(thongTinKhuyenMai.getTenKhuyenMai());
        holder.ngayBatDau.setText(thongTinKhuyenMai.getNgayBatDau());
        holder.ngayKetThuc.setText(thongTinKhuyenMai.getNgayKetThuc());

        holder.ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                Intent i = new Intent(appCompatActivity, DetailKhuyenMaiActivity.class);
                Bundle b = new Bundle();
                b.putString("Ten", thongTinKhuyenMai.getTenKhuyenMai());
                b.putString("MaKhuyenMai", thongTinKhuyenMai.getMaKhuyenMai());
                b.putString("ThongTin", thongTinKhuyenMai.getThongTin());
                b.putString("NgayBatDau", thongTinKhuyenMai.getNgayBatDau());
                b.putString("NgayKetThuc", thongTinKhuyenMai.getNgayKetThuc());
                b.putString("ChietKhau", String.valueOf(thongTinKhuyenMai.getChietKhau()));
                i.putExtras(b);
                appCompatActivity.startActivity(i);
            }
        });

        if(role.equals("admin")){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                    Intent i = new Intent(appCompatActivity, ThemKhuyenMaiActivity.class);
                    Bundle b = new Bundle();
                    b.putString("action", "edit");
                    b.putSerializable("KhuyenMai", thongTinKhuyenMai);
                    i.putExtras(b);
                    appCompatActivity.startActivity(i);
                    return false;
                }
            });
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.khuyenmaiitem,null);
        return new MyViewHolder(view);
    }

    //target
    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView ten,ngayBatDau,ngayKetThuc;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ten=itemView.findViewById(R.id.tvTenKhuyenMaiItem);
            ngayBatDau=itemView.findViewById(R.id.tvNgayBatDauItem);
            ngayKetThuc=itemView.findViewById(R.id.tvNgayKetThucItem);

        }
    }

}
