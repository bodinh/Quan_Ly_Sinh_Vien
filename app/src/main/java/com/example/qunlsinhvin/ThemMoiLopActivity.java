package com.example.qunlsinhvin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qunlsinhvin.Database.Database;

public class ThemMoiLopActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etTenLop,etMaLop,etKhoaHoc,etMaKhoa;
    private Button btnTaoMoi,btnHuy;


    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_moi_lop);
        getSupportActionBar().setTitle("Thêm mới lớp");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = new Database(ThemMoiLopActivity.this,"DaoTaoDB",null,1);

        initView();
    }

    private void initView() {
        etTenLop = (EditText) findViewById(R.id.et_tenlop);
        etMaLop = (EditText) findViewById(R.id.et_malop);
        etKhoaHoc = (EditText) findViewById(R.id.et_khoahoc);
        etMaKhoa = (EditText) findViewById(R.id.et_makhoa);

        btnHuy= (Button) findViewById(R.id.btn_huy);
        btnTaoMoi= (Button) findViewById(R.id.btn_taomoi);
        btnTaoMoi.setOnClickListener(this);
        btnHuy.setOnClickListener(this);

        String maLop = (String) getIntent().getStringExtra("maLop");
        if(maLop != null){
            etMaLop.setText(maLop);
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_huy:
                finish();
                break;
            case R.id.btn_taomoi:
                try {
                    if(etTenLop.getText().toString().trim().length() ==0){
                        etTenLop.setHint("Vui lòng nhập tên lớp");
                        etTenLop.setError("Vui lòng nhập tên lớp");
                    }else if(etMaLop.getText().toString().trim().length() ==0){
                        etMaLop.setHint("Vui lòng nhập mã lớp");
                        etMaLop.setError("Vui lòng nhập mã lớp");
                    }else if(etKhoaHoc.getText().toString().trim().length() ==0){
                        etKhoaHoc.setHint("Vui lòng nhập khoá học");
                        etKhoaHoc.setError("Vui lòng nhập khoá học");
                    }else if(etMaKhoa.getText().toString().trim().length() ==0){
                        etMaKhoa.setHint("Vui lòng nhập mã khoa");
                        etMaKhoa.setError("Vui lòng nhập mã khoa");
                    }
                    else {
                        database.Query("Insert into LopTab values('"+etMaLop.getText().toString()+"','"+etTenLop.getText().toString()+"','"+etKhoaHoc.getText().toString()+"','"+etMaKhoa.getText().toString()+"')");
                        Toast.makeText(this, "Thêm lớp thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }catch (SQLiteConstraintException e){

                }
                break;
            default:
                break;
        }
    }
}