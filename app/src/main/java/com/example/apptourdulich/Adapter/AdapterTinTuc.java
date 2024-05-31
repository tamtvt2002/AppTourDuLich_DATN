package com.example.apptourdulich.Adapter;

import static com.example.apptourdulich.Activity.SignLoginActivity.role;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptourdulich.Activity.DetailTinTucActivity;
import com.example.apptourdulich.R;
import com.example.apptourdulich.Domains.ThongTinTinTuc;
import com.example.apptourdulich.ui.admin.ThemTinTucActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class AdapterTinTuc extends FirebaseRecyclerAdapter<ThongTinTinTuc,AdapterTinTuc.MyViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterTinTuc(@NonNull FirebaseRecyclerOptions<ThongTinTinTuc> options) {
        super(options);
    }

    @NonNull
    @Override
    public AdapterTinTuc.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tintucitem,parent,false);
        return new AdapterTinTuc.MyViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int i, @NonNull ThongTinTinTuc thongTinTinTuc) {
        holder.ten.setText(thongTinTinTuc.getTenTinTuc());
        holder.thongtinngan.setText(thongTinTinTuc.getThongTinNgan());


//        String image=thongTinTinTuc.getImage();
//        Task<Uri> storageReference = FirebaseStorage.getInstance().getReference().child("Images/"+image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                // Got the download URL for 'users/me/profile.png'
//                System.out.println(uri);
//                Glide.with(holder.imageView.getContext()).load(uri).into(holder.imageView);
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });
        Picasso.get().load(thongTinTinTuc.getImage()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity=(AppCompatActivity) v.getContext();
                Intent i=new Intent(appCompatActivity, DetailTinTucActivity.class);
                Bundle b= new Bundle();
                b.putString("Ten",thongTinTinTuc.getTenTinTuc());
                b.putString("ThongTin",thongTinTinTuc.getThongTin());
                b.putString("Image",thongTinTinTuc.getImage());
                i.putExtras(b);
                appCompatActivity.startActivity(i);
               // appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fm_Container,new DetailTinTucFragment(thongTinTinTuc.getTenTinTuc(),thongTinTinTuc.getThongTin(),thongTinTinTuc.getImage())).addToBackStack(null).commit();
            }
        });
        holder.ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity=(AppCompatActivity) v.getContext();
                Intent i=new Intent(appCompatActivity,DetailTinTucActivity.class);
                Bundle b= new Bundle();
                b.putString("Ten",thongTinTinTuc.getTenTinTuc());
                b.putString("ThongTin",thongTinTinTuc.getThongTin());
                b.putString("Image",thongTinTinTuc.getImage());
                i.putExtras(b);
                appCompatActivity.startActivity(i);
                // appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fm_Container,new DetailTinTucFragment(thongTinTinTuc.getTenTinTuc(),thongTinTinTuc.getThongTin(),thongTinTinTuc.getImage())).addToBackStack(null).commit();
            }
        });
        holder.thongtinngan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity=(AppCompatActivity) v.getContext();
                Intent i=new Intent(appCompatActivity,DetailTinTucActivity.class);
                Bundle b= new Bundle();
                b.putString("Ten",thongTinTinTuc.getTenTinTuc());
                b.putString("ThongTin",thongTinTinTuc.getThongTin());
                b.putString("Image",thongTinTinTuc.getImage());
                i.putExtras(b);
                appCompatActivity.startActivity(i);
                // appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fm_Container,new DetailTinTucFragment(thongTinTinTuc.getTenTinTuc(),thongTinTinTuc.getThongTin(),thongTinTinTuc.getImage())).addToBackStack(null).commit();
            }
        });
        if(role.equals("admin")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity appCompatActivity=(AppCompatActivity) v.getContext();
                    Intent i=new Intent(appCompatActivity, ThemTinTucActivity.class);
                    i.putExtra("action","edit");
                    i.putExtra("thongTinTinTuc",thongTinTinTuc);
                    appCompatActivity.startActivity(i);
                }
            });
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        TextView ten,thongtinngan;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imgitemtintuc);
            ten=itemView.findViewById(R.id.txtTieuDe);
            thongtinngan=itemView.findViewById(R.id.txtThongTinNgan);



        }
    }

}
