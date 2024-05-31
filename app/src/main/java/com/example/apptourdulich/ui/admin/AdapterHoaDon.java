package com.example.apptourdulich.ui.admin;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptourdulich.R;
import com.example.apptourdulich.databinding.ItemHistoryKhachsanBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterHoaDon extends FirebaseRecyclerAdapter<HoaDonKhachSan, AdapterHoaDon.MyViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterHoaDon(@NonNull FirebaseRecyclerOptions<HoaDonKhachSan> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull AdapterHoaDon.MyViewHolder holder, int position, @NonNull HoaDonKhachSan model) {

        String id= model.getKeyId();

        holder.binding.tvNameKSHis.setText(model.getTenKhachSan());
        holder.binding.tvDayHis.setText(model.getNgayDat());

        if(model.getStatus()){
            holder.binding.tvStatusHis.setText("Đã Xác Nhận");
            holder.binding.tvStatusHis.setTextColor("#00FF00".hashCode());
        }
        else {
            holder.binding.tvStatusHis.setText("Chưa Được Xác Nhận");
            holder.binding.tvStatusHis.setTextColor("#FF0000".hashCode());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                Intent i = new Intent(appCompatActivity, ChiTietHoaDonKSActivity.class);
                i.putExtra("hoaDonKhachSan",model);
                appCompatActivity.startActivity(i);
            }
        });

    }

    @NonNull
    @Override
    public AdapterHoaDon.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_khachsan,null);
        return new AdapterHoaDon.MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ItemHistoryKhachsanBinding binding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemHistoryKhachsanBinding.bind(itemView);

        }
    }
}
