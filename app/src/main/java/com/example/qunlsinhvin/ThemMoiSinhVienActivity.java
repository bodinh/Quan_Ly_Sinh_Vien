package com.example.qunlsinhvin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qunlsinhvin.Database.Database;
import com.example.qunlsinhvin.Model.Lop;
import com.example.qunlsinhvin.Model.Sinhvien;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThemMoiSinhVienActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etLabelTen, etMaSinhVien, etEmail, etsdt1, etsdt2, etNgaySinh, etQueQuan, etChoOHientai;
    private AutoCompleteTextView etMaLop;
    private List<String> stringsMaLop;
    private Button btnTaoMoi, btnHuy;
    Database database;


    Sinhvien sinhvien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_moi_sinh_vien);
        getSupportActionBar().setTitle("Thêm mới sinh viên");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = new Database(ThemMoiSinhVienActivity.this, "DaoTaoDB", null, 1);
        database.Query("PRAGMA foreign_keys = ON;");

        initView();

        setAdapterForAutocompleteTVMaLop();
    }

    private void setAdapterForAutocompleteTVMaLop() {
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

    private void initView() {
        etLabelTen = (EditText) findViewById(R.id.et_tensinhvien);
        etMaSinhVien = (EditText) findViewById(R.id.et_masinhvien);
        etMaLop = (AutoCompleteTextView) findViewById(R.id.et_malop);
        String ml = (String) getIntent().getStringExtra("maLop");
        String tl = (String) getIntent().getStringExtra("tenLop");
        if (ml != null) {
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(150);
            etMaLop.setFilters(fArray);
            getSupportActionBar().setTitle(tl);
            etMaLop.setText(ml+" - "+tl);
            etMaLop.setEnabled(false);
            etMaLop.setFocusable(false);
        }
        etMaLop.setThreshold(1);

        etEmail = (EditText) findViewById(R.id.et_email);
        etsdt1 = (EditText) findViewById(R.id.et_sodienthoai1);
        etsdt2 = (EditText) findViewById(R.id.et_sodienthoai2);


        etNgaySinh = (EditText) findViewById(R.id.et_ngaysinh);
        etNgaySinh.setFocusable(false);
        etNgaySinh.setEnabled(true);
        etNgaySinh.setText("dd" + "/" + "MM" + "/" + "yyyy");


        etQueQuan = (EditText) findViewById(R.id.et_quequan);
        etChoOHientai = (EditText) findViewById(R.id.et_choohienay);


        btnHuy = (Button) findViewById(R.id.btn_huy);
        btnTaoMoi = (Button) findViewById(R.id.btn_taomoi);

        btnTaoMoi.setOnClickListener(this);
        btnHuy.setOnClickListener(this);

        etNgaySinh.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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
        switch (v.getId()) {
            case R.id.btn_huy:
                finish();
                break;
            case R.id.btn_taomoi:
                validateInput();
                break;
            case R.id.et_ngaysinh:
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(ThemMoiSinhVienActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                sinhvien = new Sinhvien();
                                calendar.set(year, month, dayOfMonth);
                                sinhvien.setNgaySinh(calendar.getTime());
                                etNgaySinh.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        }, (calendar.get(Calendar.YEAR) - 17), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 536468184000L); //cài maxDate cho dateapickerTime -  L : Long
                datePickerDialog.show();
                break;
            default:
                break;
        }
    }

    private void validateInput() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!etNgaySinh.getText().toString().equals("dd/MM/yyyy")) {
            etNgaySinh.setError(null);
        }
        if (etLabelTen.getText().toString().trim().length() == 0) {
            etLabelTen.setHint("Vui lòng nhập tên sinh viên");
            etLabelTen.setError("Vui lòng nhập tên sinh viên");
        } else if (etMaSinhVien.getText().toString().trim().length() == 0) {
            etMaSinhVien.setHint("Vui lòng nhập mã sinh viên");
            etMaSinhVien.setError("Vui lòng nhập mã sinh viên");
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
        } else if (etNgaySinh.getText().toString().equals("dd/MM/yyyy")) {
            etNgaySinh.setError("Vui lòng nhập ngày sinh");
        } else if (etQueQuan.getText().toString().trim().length() == 0) {
            etQueQuan.setHint("Vui lòng nhập quê quán");
            etQueQuan.setError("Vui lòng nhập quê quán");
        } else if (etChoOHientai.getText().toString().trim().length() == 0) {
            etChoOHientai.setHint("Vui lòng nhập chỗ ở hiện tại");
            etChoOHientai.setError("Vui lòng nhập chỗ ở hiện tại");
        } else {
            if (database.QueryGetData("select * from SinhVienTab where maSinhVien ='" + etMaSinhVien.getText().toString() + "'").getCount() > 0) {
                Toast.makeText(this, "Sinh viên đã tồn tại trong hệ thống", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(sinhvien.getNgaySinh());
                    String d = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
                    database.Query("Insert into SinhVienTab values('" + etMaSinhVien.getText().toString() + "','" + etLabelTen.getText().toString() + "','" + d + "','" + etMaLop.getText().toString() + "','" + etEmail.getText().toString() + "','" + etsdt1.getText().toString() + "','" + etsdt2.getText().toString() + "','" + etQueQuan.getText().toString() + "','" + etChoOHientai.getText().toString() + "')");
                    finish();
                } catch (SQLiteConstraintException e) {
                    new AlertDialog.Builder(ThemMoiSinhVienActivity.this)
                            .setTitle("Thông báo")
                            .setMessage("Lớp học chưa tồn tại\nBạn có muốn tạo lớp học mới ??")
                            .setIcon(R.drawable.ic_baseline_warning_24)
                            .setNegativeButton("No", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ThemMoiSinhVienActivity.this, ThemMoiLopActivity.class);
                                    intent.putExtra("maLop", etMaLop.getText().toString());
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            }
        }
    }
}