package com.example.qunlsinhvin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.qunlsinhvin.Database.Database;
import com.example.qunlsinhvin.Model.Lop;

import java.io.Serializable;

public class ChinhSuaLopActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTenLop,etMaLop,etKhoaHoc,etMaKhoa;
    private Button btnLuu,btnQuayLai;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_lop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chỉnh sửa thông tin lớp");
        database = new Database(ChinhSuaLopActivity.this,"DaoTaoDB",null,1);
        database.Query("PRAGMA foreign_keys = ON;");

        initView();

        setView();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setView() {
        Lop lop =(Lop) getIntent().getSerializableExtra("lop");

        etTenLop.setText(lop.getTenLop());
        etMaLop.setText(lop.getMaLop());
        etMaLop.setEnabled(false);
        etKhoaHoc.setText(lop.getKhoaHoc());
        etMaKhoa.setText(lop.getMaKhoa());
    }

    private void initView() {
        etTenLop = (EditText) findViewById(R.id.et_tenlop);
        etMaLop = (EditText) findViewById(R.id.et_malop);
        etKhoaHoc = (EditText) findViewById(R.id.et_khoahoc);
        etMaKhoa = (EditText) findViewById(R.id.et_makhoa);

        btnLuu= (Button) findViewById(R.id.btn_luu);
        btnQuayLai= (Button) findViewById(R.id.btn_quaylai);
        btnQuayLai.setOnClickListener(this);
        btnLuu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_luu:
                // TODO: 12/5/2020
                if(etTenLop.getText().toString().trim().length() ==0){
                    etTenLop.setHint("Vui lòng nhập tên lớp");
                    etTenLop.setError("Vui lòng nhập tên lớp");
                }else if(etKhoaHoc.getText().toString().trim().length() ==0){
                    etKhoaHoc.setHint("Vui lòng nhập khoá học");
                    etKhoaHoc.setError("Vui lòng nhập khoá học");
                }else if(etMaKhoa.getText().toString().trim().length() ==0){
                    etMaKhoa.setHint("Vui lòng nhập mã khoa");
                    etMaKhoa.setError("Vui lòng nhập mã khoa");
                }else {
                    Lop l = new Lop(etMaLop.getText().toString(),etTenLop.getText().toString(),etKhoaHoc.getText().toString(),etMaKhoa.getText().toString());

                    database.Query("Update LopTab set tenLop='"+l.getTenLop()+"',khoaHoc = '"+l.getKhoaHoc()+"',maKhoa ='"+l.getMaKhoa()+"' where maLop='"+l.getMaLop()+"' ");


                    Intent intent=new Intent(ChinhSuaLopActivity.this,ThongTinLopActivity.class);
                    intent.putExtra("lopresult",(Serializable) l);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
                break;
            case R.id.btn_quaylai:
                // TODO: 12/5/2020
                finish();
                break;
            default:
                break;
        }
    }
}