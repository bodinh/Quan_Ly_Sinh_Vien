package com.example.qunlsinhvin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlsinhvin.Database.Database;
import com.example.qunlsinhvin.Model.Lop;
import com.example.qunlsinhvin.Model.Sinhvien;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThongTinLopActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CODE_LOP=114;

    private TextView tvTenLop,tvMaLop,tvKhoaHoc,tvMaKhoa;
    private Button btnChinhSua,btnXoa;

    Database database;
    Lop lop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_lop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thông tin lớp");
        database = new Database(ThongTinLopActivity.this,"DaoTaoDB",null,1);
        database.Query("PRAGMA foreign_keys = ON;");
        initView();

        getLop();

        setView(lop);
    }

    private void setView(Lop lop) {
        tvTenLop.setText(lop.getTenLop());
        tvMaLop.setText(lop.getMaKhoa());
        tvKhoaHoc.setText(lop.getKhoaHoc());
        tvMaKhoa.setText(lop.getMaKhoa());
    }

    private void getLop() {
        //lấy thông tin lớp dc chuyển sang từ LopActivity
        lop = (Lop) getIntent().getSerializableExtra("lop");
    }

    private void initView() {
        tvTenLop = (TextView) findViewById(R.id.tv_tenlop);
        tvMaLop = (TextView) findViewById(R.id.tv_malop);
        tvKhoaHoc = (TextView) findViewById(R.id.tv_khoahoc);
        tvMaKhoa = (TextView) findViewById(R.id.tv_makhoa);

        btnChinhSua = (Button) findViewById(R.id.btn_chinhsua);
        btnXoa= (Button) findViewById(R.id.btn_xoa);

        btnXoa.setOnClickListener(this);
        btnChinhSua.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                // TODO: 12/4/2020
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_chinhsua:
                // TODO: 12/4/2020
                Intent intent=new Intent(ThongTinLopActivity.this,ChinhSuaLopActivity.class);
                intent.putExtra("lop",(Serializable) lop);
                startActivityForResult(intent,CODE_LOP);
                break;
            case R.id.btn_xoa:
                new AlertDialog.Builder(ThongTinLopActivity.this)
                        .setTitle("Xoá")
                        .setMessage("Bạn có chắc muốn xoá")
                        .setIcon(R.drawable.ic_baseline_delete_alert)
                        .setNegativeButton("No",null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    //Xoa lop rong database
                                    database.Query("delete from LopTab where maLop = '"+lop.getMaLop()+"'");
                                    //=======================
                                    Toast.makeText(ThongTinLopActivity.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }catch (SQLiteConstraintException e){
                                   new AlertDialog.Builder(ThongTinLopActivity.this)
                                    .setIcon(R.drawable.ic_baseline_delete_alert)
                                            .setNegativeButton("No", null)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //delete sinh vien trong database
                                                    database.Query("delete from SinhVienTab where maLop='" + lop.getMaLop() + "'");
                                                    //Xoas lop rong database
                                                    database.Query("delete from LopTab where maLop = '" + lop.getMaLop() + "'");
                                                    Toast.makeText(ThongTinLopActivity.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            })
                                            .setTitle("Xoá")
                                            .setMessage("Lớp học có học sinh , "+ "\n"+"Bạn có chắc muốn xoá "+"\n"+"(Thao tác này sẽ xoá tất cả học sinh trong lớp )").show();
                                }
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CODE_LOP && resultCode == Activity.RESULT_OK){
            Lop l = (Lop) data.getSerializableExtra("lopresult");
            setView(l);

            database.Query("Update LopTab set tenLop='"+l.getTenLop()+"',khoaHoc = '"+l.getKhoaHoc()+"',maKhoa ='"+l.getMaKhoa()+"' ");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}