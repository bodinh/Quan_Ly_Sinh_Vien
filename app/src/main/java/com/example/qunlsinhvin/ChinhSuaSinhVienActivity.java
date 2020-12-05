package com.example.qunlsinhvin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qunlsinhvin.Model.Sinhvien;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChinhSuaSinhVienActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etLabelTen,etMaSinhVien,etMaLop,etEmail,etsdt1,etsdt2,etNgaySinh,etQueQuan,etChoOHientai;
    private Button btnLuu,btnQuayLai;
    Sinhvien sinhvien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_sinh_vien);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chỉnh sửa thông tin sinh viên");

        initView();
        sinhvien = (Sinhvien) getIntent().getSerializableExtra("sinhvien");
        setView(sinhvien);
    }

    private void setView(Sinhvien sinhvien) {
        etLabelTen.setText(sinhvien.getTenSinhVien());
        etMaSinhVien.setText(sinhvien.getMaSinhVien());
        etMaLop.setText(sinhvien.getMaLop());
        etEmail.setText(sinhvien.getEmail());
        etsdt1.setText(sinhvien.getSoDienThoai1());
        etsdt2.setText(sinhvien.getSoDienthoai2());

        //ngay sinh
        Date date = sinhvien.getNgaySinh();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        etNgaySinh.setText(simpleDateFormat.format(calendar.getTime()));
        etNgaySinh.setFocusable(false);
        etNgaySinh.setClickable(true);

        etQueQuan.setText(sinhvien.getQueQuan());
        etChoOHientai.setText(sinhvien.getChoOHienNay());


        etNgaySinh.setOnClickListener(this);
    }

    private void initView() {
        etLabelTen = (EditText) findViewById(R.id.et_tensinhvien);
        etMaSinhVien = (EditText) findViewById(R.id.et_masinhvien);
        etMaSinhVien.setEnabled(false);
        etMaLop = (EditText) findViewById(R.id.et_malop);
        etEmail = (EditText) findViewById(R.id.et_email);
        etsdt1 = (EditText) findViewById(R.id.et_sodienthoai1);
        etsdt2 = (EditText) findViewById(R.id.et_sodienthoai2);
        etNgaySinh = (EditText) findViewById(R.id.et_ngaysinh);
        etQueQuan = (EditText) findViewById(R.id.et_quequan);
        etChoOHientai = (EditText) findViewById(R.id.et_choohienay);


        btnLuu = (Button) findViewById(R.id.btn_Lưu);
        btnQuayLai = (Button) findViewById(R.id.btn_quaylai);

        btnQuayLai.setOnClickListener(this);
        btnLuu.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Lưu:
                // TODO: 12/4/2020
                Sinhvien s = new Sinhvien(etMaSinhVien.getText().toString(),etLabelTen.getText().toString(),sinhvien.getNgaySinh(),etMaLop.getText().toString(),etEmail.getText().toString(),etsdt1.getText().toString(),etsdt2.getText().toString(),etQueQuan.getText().toString(),etChoOHientai.getText().toString());
                Intent intent=new Intent(ChinhSuaSinhVienActivity.this,SinhVienActivity.class);
                intent.putExtra("sinhvienresult",(Serializable) s);
                setResult(Activity.RESULT_OK,intent);
                finish();
                break;
            case R.id.btn_quaylai:
                // TODO: 12/4/2020
                finish();
                break;
            case R.id.et_ngaysinh:
                final Calendar calendar=Calendar.getInstance();
                calendar.setTime(sinhvien.getNgaySinh());
                DatePickerDialog datePickerDialog = new DatePickerDialog(ChinhSuaSinhVienActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(year,month,dayOfMonth);
                                sinhvien.setNgaySinh(calendar.getTime());
                                etNgaySinh.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                            }
                        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                // TODO: 12/4/2020
                break;
            default:
                break;
        }
    }
}