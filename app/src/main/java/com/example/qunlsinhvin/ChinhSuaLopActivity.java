package com.example.qunlsinhvin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.qunlsinhvin.Model.Lop;

import java.io.Serializable;

public class ChinhSuaLopActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTenLop,etMaLop,etKhoaHoc,etMaKhoa;
    private Button btnLuu,btnQuayLai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_lop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chỉnh sửa thông tin lớp");

        initView();

        setView();

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
                Lop l = new Lop(etMaLop.getText().toString(),etTenLop.getText().toString(),etKhoaHoc.getText().toString(),etMaKhoa.getText().toString());
                Intent intent=new Intent(ChinhSuaLopActivity.this,ThongTinLopActivity.class);
                intent.putExtra("lopresult",(Serializable) l);
                setResult(Activity.RESULT_OK,intent);
                finish();
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