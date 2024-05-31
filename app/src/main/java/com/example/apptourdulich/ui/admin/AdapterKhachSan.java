package com.example.apptourdulich.ui.admin;

import static com.example.apptourdulich.Activity.SignLoginActivity.role;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptourdulich.R;
import com.example.apptourdulich.databinding.KhachsanitemBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class AdapterKhachSan extends FirebaseRecyclerAdapter<ThongTinKhachSan, AdapterKhachSan.MyViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterKhachSan(@NonNull FirebaseRecyclerOptions<ThongTinKhachSan> options) {
        super(options);
    }

    @NonNull
    @Override
    public AdapterKhachSan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.khachsanitem,parent,false);
        return new AdapterKhachSan.MyViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterKhachSan.MyViewHolder holder, int i, @NonNull ThongTinKhachSan thongTinKhachSan) {
        holder.binding.tvTenKhachSan.setText(thongTinKhachSan.getTenKhachSan());
        holder.binding.tvGia1dem.setText(thongTinKhachSan.getGiaTien1dem() + " VND / 1 đêm");
        holder.binding.tvGia1h.setText(thongTinKhachSan.getGiaTien1h() + " VND / 1 giờ");
        holder.binding.tvTrangThai.setText(thongTinKhachSan.getTinhTrang());

        Picasso.get().load(thongTinKhachSan.getHinhAnh()).into(holder.binding.imgKhachSan);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailKhachSanActivity.class);
                intent.putExtra("khachSan", thongTinKhachSan);
                v.getContext().startActivity(intent);
            }
        });

        if(role.equals("admin")){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(v.getContext(), ThemKhachSanActivity.class);
                    intent.putExtra("action", "edit");
                    intent.putExtra("khachSan", thongTinKhachSan);
                    v.getContext().startActivity(intent);
                    return false;
                }
            });
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        KhachsanitemBinding binding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = KhachsanitemBinding.bind(itemView);
        }
    }
}
