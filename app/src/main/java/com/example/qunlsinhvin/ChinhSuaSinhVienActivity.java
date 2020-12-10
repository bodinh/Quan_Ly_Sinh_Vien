package com.example.qunlsinhvin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qunlsinhvin.Database.Database;
import com.example.qunlsinhvin.Model.Lop;
import com.example.qunlsinhvin.Model.Sinhvien;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChinhSuaSinhVienActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etLabelTen,etMaSinhVien,etEmail,etsdt1,etsdt2,etNgaySinh,etQueQuan,etChoOHientai;
    private AutoCompleteTextView etMaLop;
    private Button btnLuu,btnQuayLai;
    Sinhvien sinhvien;
    private List<String> stringsMaLop;

    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_sinh_vien);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chỉnh sửa thông tin sinh viên");
        database = new Database(ChinhSuaSinhVienActivity.this, "DaoTaoDB", null, 1);
        database.Query("PRAGMA foreign_keys = ON;");


        initView();

        getLopSinhVien();

        setView();
    }

    private void getLopSinhVien() {
        sinhvien = (Sinhvien) getIntent().getSerializableExtra("sinhvien");
        Cursor cursor =database.QueryGetData("select * from LopTab");
        if(cursor.getCount() > 0){
            stringsMaLop = new ArrayList<>();
            cursor.moveToFirst();
            do{
                stringsMaLop.add(cursor.getString(0).toString() + " - " +cursor.getString(1).toString());
            }while (cursor.moveToNext());
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringsMaLop);
            etMaLop.setAdapter(adapter);
        }
    }

    private void setView() {
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
        etMaLop = (AutoCompleteTextView) findViewById(R.id.et_malop);
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
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        switch (v.getId()){
            case R.id.btn_Lưu:
                // TODO: 12/4/2020
                if (etLabelTen.getText().toString().trim().length() == 0) {
                    etLabelTen.setHint("Vui lòng nhập tên sinh viên");
                    etLabelTen.setError("Vui lòng nhập tên sinh viên");
                } else if (etMaLop.getText().toString().trim().length() == 0) {
                    etMaLop.setHint("Vui lòng nhập mã lớp");
                    etMaLop.setError("Vui lòng nhập mã lớp");
                }else if(etMaLop.getText().toString().trim().length() < 6){
                    etMaLop.setHint("Vui lòng nhập đủ 6 ký tự");
                    etMaLop.setError("Vui lòng nhập đủ 6 ký tự");
                } else if (etEmail.getText().toString().trim().length() == 0) {
                    etEmail.setHint("Vui lòng nhập email");
                    etEmail.setError("Vui lòng nhập email");
                } else if (!etEmail.getText().toString().trim().matches(emailPattern)) {
                    etEmail.setError("Email không hợp lệ");
                } else if (etsdt1.getText().toString().trim().length() == 0) {
                    etsdt1.setHint("Vui lòng nhập số điện thoại ");
                    etsdt1.setError("Vui lòng nhập số điện thoại");
                }else if (etQueQuan.getText().toString().trim().length() == 0) {
                    etQueQuan.setHint("Vui lòng nhập quê quán");
                    etQueQuan.setError("Vui lòng nhập quê quán");
                } else if (etChoOHientai.getText().toString().trim().length() == 0) {
                    etChoOHientai.setHint("Vui lòng nhập chỗ ở hiện tại");
                    etChoOHientai.setError("Vui lòng nhập chỗ ở hiện tại");
                }else {
                    Sinhvien s = new Sinhvien(etMaSinhVien.getText().toString(),etLabelTen.getText().toString(),sinhvien.getNgaySinh(),etMaLop.getText().toString(),etEmail.getText().toString(),etsdt1.getText().toString(),etsdt2.getText().toString(),etQueQuan.getText().toString(),etChoOHientai.getText().toString());

                    //update in data base
                    Calendar calendar=Calendar.getInstance();
                    calendar.setTime(s.getNgaySinh());
                    String d = calendar.get(Calendar.YEAR)+ "-" +(calendar.get(Calendar.MONTH)+1) + "-"+ calendar.get(Calendar.DAY_OF_MONTH);

                    database.Query("update SinhVienTab set tenSinhVien = '"+s.getTenSinhVien()+"'," +
                            "ngaySinh = '"+d+"'," +
                            "maLop = '"+s.getMaLop()+"'," +
                            "email ='"+s.getEmail()+"'," +
                            "soDienThoai1 ='"+s.getSoDienThoai1()+"'," +
                            "soDienThoai2 = '"+s.getSoDienthoai2()+"'," +
                            "queQuan ='"+s.getQueQuan()+"'," +
                            "choOHienNay='"+s.getChoOHienNay()+"' where maSinhVien='"+s.getMaSinhVien()+"'");

                    Intent intent=new Intent(ChinhSuaSinhVienActivity.this,SinhVienActivity.class);
                    intent.putExtra("sinhvienresult",(Serializable) s);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
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
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 536468184000L); //cài maxDate cho dateapickerTime - L : Long
                datePickerDialog.show();
                // TODO: 12/4/2020
                break;
            default:
                break;
        }
    }
}