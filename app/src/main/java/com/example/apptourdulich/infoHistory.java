package com.example.apptourdulich;

import static com.example.apptourdulich.Activity.SignLoginActivity.role;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptourdulich.Domains.KhachHang;
import com.example.apptourdulich.Domains.NotificationBuilder;
import com.example.apptourdulich.Domains.ThongTinKhuyenMai;
import com.example.apptourdulich.Domains.ThongTinThongBao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kongzue.dialogx.dialogs.TipDialog;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class infoHistory extends AppCompatActivity {
    TextView tvTenTourBill,tvNgayKhoiHanhHis,tvNoiKhoiHanhHis,tvSoNgayHis,tvHoTenBill,
            tvSoDienThoaiHis,tvDiaChiHis,tvSoLuongNguoiLonBill,tvSoLuongTreEmBill,tvTongTienBill,
            tvChietKhauBill,tvSoTienThanhToanBill,tvngayThanhtoan,tvGiatreem,tvGianguoilon;
    DatabaseReference databaseReferenceTour;
    DatabaseReference databaseReferenceKhachHang;
    DatabaseReference databaseReferenceKhuyenMai,reference;
    Button btnHuy,btnConfirm;
    DatabaseReference refThongBao;
    ImageView imgBack;
    long maxid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_history);

        tvTenTourBill=findViewById(R.id.tvTenTourBill1);
        tvNgayKhoiHanhHis=findViewById(R.id.tvNgayKhoiHanhHis1);
        tvNoiKhoiHanhHis=findViewById(R.id.tvNoiKhoiHanhHis1);
        tvSoNgayHis=findViewById(R.id.tvSoNgayHis1);

        tvHoTenBill=findViewById(R.id.tvHoTenBill1);
        tvSoDienThoaiHis=findViewById(R.id.tvSoDienThoaiHis1);
        tvDiaChiHis=findViewById(R.id.tvDiaChiHis1);

        tvSoLuongNguoiLonBill=findViewById(R.id.tvSoLuongNguoiLonBill1);
        tvSoLuongTreEmBill=findViewById(R.id.tvSoLuongTreEmBill1);

        tvGianguoilon=findViewById(R.id.tvGiaNguoiLonBill1);
        tvGiatreem=findViewById(R.id.tvGiaTreEmBill1);

        tvTongTienBill=findViewById(R.id.tvTongTienBill1);
        tvChietKhauBill=findViewById(R.id.tvChietKhauBill1);
        tvSoTienThanhToanBill=findViewById(R.id.tvSoTienThanhToanBill1);
        tvngayThanhtoan=findViewById(R.id.tvngayThanhtoan);

        btnHuy=findViewById(R.id.btnCancelTour);
        btnConfirm=findViewById(R.id.btnConfirmTour);
        imgBack=findViewById(R.id.imgBackInfoHistory);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        Bundle b = this.getIntent().getExtras();
        String SoDienThoai = b.getString("soDienThoai");

        String id=b.getString("maTour");
        String idSale=b.getString("maKhuyenMai");
        int SoLuongNguoiLon=b.getInt("soLuongNguoiLon");
        int SoLuongTreEm=b.getInt("soLuongTreEm");

        String ngayThanhtoan=b.getString("ngayThanhToan");
        String tt=b.getString(String.valueOf("tongTien"));
        String idHoaDon=b.getString("maHoaDon");

        tvSoLuongNguoiLonBill.setText(String.valueOf(SoLuongNguoiLon));
        tvSoLuongTreEmBill.setText(String.valueOf(SoLuongTreEm));
        tvngayThanhtoan.setText(ngayThanhtoan);
        NumberFormat fmDonGia = new DecimalFormat("#,###");
        double DonGia = Double.parseDouble(String.valueOf(tt));
        String SoTienThanhToan = fmDonGia.format(DonGia);
        tvSoTienThanhToanBill.setText(String.valueOf(SoTienThanhToan));
        tvSoDienThoaiHis.setText(SoDienThoai);

        databaseReferenceTour= FirebaseDatabase.getInstance().getReference("Tour");
        databaseReferenceTour.child(String.valueOf(id)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists())
                    {
                        DataSnapshot dataSnapshot = task.getResult();
                        String Ten = String.valueOf(dataSnapshot.child("tenTour").getValue());
                        String ngayKhoiHanh = String.valueOf(dataSnapshot.child("ngayKhoiHanh").getValue());
                        String noiKhoiHanh = String.valueOf(dataSnapshot.child("noiKhoiHanh").getValue());
                        String soNgay = String.valueOf(dataSnapshot.child("soNgay").getValue());
                        String donGia = String.valueOf(dataSnapshot.child("donGia").getValue());

                        tvTenTourBill.setText(Ten);
                        tvNgayKhoiHanhHis.setText(ngayKhoiHanh);
                        tvNoiKhoiHanhHis.setText(noiKhoiHanh);
                        tvSoNgayHis.setText(soNgay);

                        int DonGiaTinh=Integer.parseInt(donGia);
                        int GiaNguoiLon=SoLuongNguoiLon*DonGiaTinh;

                        int DonGiaTreEm=DonGiaTinh/2;
                        int GiaTreEm=SoLuongTreEm*DonGiaTreEm;
                        NumberFormat DonGia = new DecimalFormat("#,###");
                        double DonGia1 = Double.parseDouble(String.valueOf(GiaNguoiLon));
                        String GiaNL = DonGia.format(DonGia1);
                        tvGianguoilon.setText(GiaNL);

                        double DonGia2 = Double.parseDouble(String.valueOf(GiaTreEm));
                        String GiaTE = DonGia.format(DonGia2);
                        tvGiatreem.setText(GiaTE);

                        int Tong=GiaNguoiLon+GiaTreEm;
                        double DonGia3 = Double.parseDouble(String.valueOf(Tong));
                        String TongTien = DonGia.format(DonGia3);
                        tvTongTienBill.setText(TongTien);

                    }
                }
            }
        });

        databaseReferenceKhachHang= FirebaseDatabase.getInstance().getReference("KhachHang");
        databaseReferenceKhachHang.orderByChild("sdt").equalTo(SoDienThoai).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    KhachHang kh = child.getValue(KhachHang.class);
                    tvHoTenBill.setText(kh.getHoTen());
                    tvDiaChiHis.setText(kh.getDiaChi());//cais thoong bao dau
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReferenceKhuyenMai= FirebaseDatabase.getInstance().getReference("KhuyenMai");
        databaseReferenceKhuyenMai.orderByChild("maKhuyenMai").equalTo(idSale).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    ThongTinKhuyenMai sale = child.getValue(ThongTinKhuyenMai.class);
                    tvChietKhauBill.setText(String.valueOf(sale.getChietKhau()));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ThongTinThongBao thongBao=new ThongTinThongBao();
        refThongBao = FirebaseDatabase.getInstance().getReference().child("ThongBao");
        refThongBao.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxid = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c=Calendar.getInstance();
                SimpleDateFormat _newformat=new SimpleDateFormat("dd/MM/yyyy");
                try{
                    Date datetime = _newformat.parse(ngayThanhtoan);//ngày thanh toán
                    String currentime = _newformat.format(c.getTime());
                    Date _cvcurrent = _newformat.parse(currentime);//ngày hiện tại

                    Long diff = _cvcurrent.getTime() - datetime.getTime();
                    if (diff < 4)
                    {


                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String currentDateandTime = sdf.format(new Date());
                        String Thongtin="Quý khách muốn hủy chuyến đi "+tvTenTourBill.getText().toString().trim()+" vào ngày " +tvNgayKhoiHanhHis.getText().toString().trim();
                        AlertDialog alertDialog = new AlertDialog.Builder( infoHistory.this).setTitle("Thông Báo").
                                setMessage(Thongtin).setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                thongBao.setMaThongBao((int)maxid+1);
//                                thongBao.setNgayThongBao(currentDateandTime);
//                                thongBao.setNoiDung(Thongtin);
//                                thongBao.setSoDienThoai(tvSoDienThoaiHis.getText().toString().trim());
                                ThongTinThongBao thongBao= new NotificationBuilder().maThongBao((int)maxid+1).soDienThoai(tvSoDienThoaiHis.getText().toString().trim())
                                        .noiDung(Thongtin).ngaythongbao(currentDateandTime).build();
                                refThongBao.child(String.valueOf(maxid+1)).setValue(thongBao);
                                deleteBill(idHoaDon);
                                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                                Intent intent=new Intent(infoHistory.this,History.class);
                                Bundle b = new Bundle();
                                b.putString("SoDienThoai",SoDienThoai);
                                intent.putExtras(b);
                                appCompatActivity.startActivity(intent);

                            }
                        }).setNegativeButton(
                                "Đóng", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {


                                    }
                                }
                        ).show();


                    }
                    else
                    {
                        ShowToast("Không thể hủy tour, Vui lòng liên hệ để được hỗ trợ");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
        });

        if(role.equals("admin")){
            btnConfirm.setVisibility(View.VISIBLE);
            btnConfirm.setOnClickListener(v -> {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(infoHistory.this);
                builder.setTitle("Xác nhận tour");
                builder.setMessage("Xác nhận tour này?");
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference=FirebaseDatabase.getInstance().getReference("HoaDon").child(idHoaDon);
                        reference.child("status").setValue(true);
                        TipDialog.show(infoHistory.this, "Xác nhận thành công", TipDialog.TYPE.SUCCESS);
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TipDialog.show(infoHistory.this, "Hủy xác nhận", TipDialog.TYPE.WARNING);
                    }
                });
                builder.show();
            });
        }

    }

    private void ShowToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    private void deleteBill(String idHoaDon){
        reference=FirebaseDatabase.getInstance().getReference("HoaDon").child(idHoaDon);
        Task<Void> mTask=reference.removeValue();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                ShowToast("Đã Hủy Tour Thành Công ");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ShowToast("Không Thể Hủy Tour Được");
            }
        });
    }


    }