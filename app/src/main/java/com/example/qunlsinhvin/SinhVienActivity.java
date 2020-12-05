package com.example.qunlsinhvin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.qunlsinhvin.Database.Database;
import com.example.qunlsinhvin.Model.Sinhvien;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SinhVienActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvLabelTen,tvMaSinhVien,tvMaLop,tvEmail,tvsdt1,tvsdt2,tvNgaySinh,tvQueQuan,tvChoOHientai;
    private Button btnChinhSua,btnXoa;

    Sinhvien sinhvien;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinh_vien);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thông tin sinh viên");
        database = new Database(SinhVienActivity.this,"DaoTaoDB",null,1);
        initView();

        sinhvien = (Sinhvien) getIntent().getSerializableExtra("sinhvien");
        setView(sinhvien);
    }

    private void setView(Sinhvien sinhvien) {
        tvLabelTen.setText(sinhvien.getTenSinhVien());
        tvMaSinhVien.setText(sinhvien.getMaSinhVien());
        tvMaLop.setText(sinhvien.getMaLop());
        tvEmail.setText(sinhvien.getEmail());
        tvsdt1.setText(sinhvien.getSoDienThoai1());
        tvsdt2.setText(sinhvien.getSoDienthoai2());

        //ngay sinh
        Date date = sinhvien.getNgaySinh();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tvNgaySinh.setText(simpleDateFormat.format(calendar.getTime()));
        tvQueQuan.setText(sinhvien.getQueQuan());
        tvChoOHientai.setText(sinhvien.getChoOHienNay());
    }

    private void initView() {
        tvLabelTen = (TextView) findViewById(R.id.tv_tensinhvien);
        tvMaSinhVien = (TextView) findViewById(R.id.tv_masinhvien);
        tvMaLop = (TextView) findViewById(R.id.tv_malop);
        tvEmail = (TextView) findViewById(R.id.tv_email);
        tvsdt1 = (TextView) findViewById(R.id.tv_sodienthoai1);
        tvsdt2 = (TextView) findViewById(R.id.tv_sodienthoai2);
        tvNgaySinh = (TextView) findViewById(R.id.tv_nagysinh);
        tvQueQuan = (TextView) findViewById(R.id.tv_quequan);
        tvChoOHientai = (TextView) findViewById(R.id.tv_choohienay);

        btnChinhSua = (Button) findViewById(R.id.btn_chinhsua);
        btnXoa = (Button) findViewById(R.id.btn_xoa);

        btnChinhSua.setOnClickListener(this);
        btnXoa.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                // TODO: 12/4/2020
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private static final int CODE_EDIT=112;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_chinhsua:
                // TODO: 12/4/2020
                Intent intent=new Intent(SinhVienActivity.this,ChinhSuaSinhVienActivity.class);
                intent.putExtra("sinhvien",(Serializable) sinhvien);
                startActivityForResult(intent,CODE_EDIT);
                break;
            case R.id.btn_xoa:
                // TODO: 12/4/2020
                new AlertDialog.Builder(SinhVienActivity.this)
                        .setTitle("Xoá")
                        .setMessage("Bạn có chắc muốn xoá")
                        .setIcon(R.drawable.ic_baseline_delete_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database.Query("delete from SinhVienTab where maSinhVien = '"+ sinhvien.getMaSinhVien() +"'");
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==CODE_EDIT && resultCode == Activity.RESULT_OK){
            Sinhvien s =(Sinhvien) data.getSerializableExtra("sinhvienresult");
            sinhvien =s;
            //update in data base
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(sinhvien.getNgaySinh());
            String d = calendar.get(Calendar.YEAR)+ "-" +(calendar.get(Calendar.MONTH)+1) + "-"+ calendar.get(Calendar.DAY_OF_MONTH);

            database.Query("update SinhVienTab set tenSinhVien = '"+sinhvien.getTenSinhVien()+"'," +
                    "ngaySinh = '"+d+"'," +
                    "maLop = '"+sinhvien.getMaLop()+"'," +
                    "email ='"+sinhvien.getEmail()+"'," +
                    "soDienThoai1 ='"+sinhvien.getSoDienThoai1()+"'," +
                    "soDienThoai2 = '"+sinhvien.getSoDienthoai2()+"'," +
                    "queQuan ='"+sinhvien.getQueQuan()+"'," +
                    "choOHienNay='"+sinhvien.getChoOHienNay()+"' where maSinhVien='"+sinhvien.getMaSinhVien()+"'");
            setView(s);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}