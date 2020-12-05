package com.example.qunlsinhvin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.GetChars;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlsinhvin.Adapter.LopAdapter;
import com.example.qunlsinhvin.Adapter.SinhvienAdapter;
import com.example.qunlsinhvin.Adapter.SinhvienInClassAdapter;
import com.example.qunlsinhvin.Database.Database;
import com.example.qunlsinhvin.Model.Lop;
import com.example.qunlsinhvin.Model.Sinhvien;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class LopActivity extends AppCompatActivity {
    private TextView tvTenLop;
    private RecyclerView recyclerView;
    private SinhvienInClassAdapter sinhvienAdapter;
    private List<Sinhvien> sinhvienList;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lop);
        database = new Database(LopActivity.this,"DaoTaoDB",null,1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách sinh viên");

        initView();

        viewDSSinhVien();

    }

    //swipe in recycleView
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            new RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(LopActivity.this,R.color.bgDeleteItem))
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(LopActivity.this,R.color.bgDeleteItem))
                    .create()
                    .decorate();
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:
                    // TODO: 12/4/2020
                    new AlertDialog.Builder(LopActivity.this)
                            .setTitle("Xoá")
                            .setIcon(R.drawable.ic_baseline_delete_alert)
                            .setMessage("Bạn có chắc muốn xoá")
                            .setCancelable(true)
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    sinhvienAdapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sinhvienAdapter.notifyDataSetChanged();
                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteSV(position);
                                }
                            }).show();
                    break;
                default:
                    break;
            }
        }
    };
    //===================

    private Lop lop;

    private void viewDSSinhVien() {
        getLop();

        getListSinhVien();
    }

    private void getListSinhVien() {
        Cursor cursor = database.QueryGetData("select * from SinhVienTab where maLop ='"+lop.getMaLop()+"'");
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = simpleDateFormat.parse(cursor.getString(2));
                    sinhvienList.add(new Sinhvien(cursor.getString(0),cursor.getString(1),date,cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());
        }

        sinhvienAdapter = new SinhvienInClassAdapter(LopActivity.this, sinhvienList);
        recyclerView.setAdapter(sinhvienAdapter);
    }

    private void getLop() {
        //Lấy đối tượng lớp được truyền sang từ MainActivity
        lop =(Lop) getIntent().getSerializableExtra("lop");
        tvTenLop.setText(lop.getTenLop());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                // TODO: 12/4/2020
                break;
            case R.id.chitietlop:
                // TODO: 12/4/2020
                Intent intent=new Intent(LopActivity.this,ThongTinLopActivity.class);
                intent.putExtra("lop",(Serializable) lop);
                startActivity(intent);
                break;
            case R.id.themmoisinhvien:
                Intent intent1=new Intent(LopActivity.this,ThemMoiSinhVienActivity.class);
                intent1.putExtra("maLop",lop.getMaLop());
                intent1.putExtra("tenLop",lop.getTenLop());
                startActivity(intent1);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        tvTenLop = (TextView) findViewById(R.id.tv_label_lop);
        recyclerView = (RecyclerView) findViewById(R.id.rv_list_lop_sinhvien);
        recyclerView.setLayoutManager(new LinearLayoutManager(LopActivity.this));
        sinhvienList = new ArrayList<>();

        //swipe in recycleView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void deleteSV(final int position){
        final Sinhvien sinhvien=sinhvienList.get(position);
        //delete sinh vien trong database
        database.Query("delete from SinhVienTab where maSinhVien='"+ sinhvien.getMaSinhVien() +"'");
        //===============================
        sinhvienList.remove(position);
        sinhvienAdapter.notifyItemRemoved(position);
        Snackbar.make(recyclerView,"Đã xoá Mã sinh viên : " + sinhvien.getMaSinhVien(),Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sinhvienList.add(position,sinhvien);
                        //restore sinh vien trong database
                        Date date = sinhvien.getNgaySinh();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        String d = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) +1 )+ "-"+ calendar.get(Calendar.DAY_OF_MONTH);
                        database.Query("Insert into SinhVienTab values('"+sinhvien.getMaSinhVien()+"','"+sinhvien.getTenSinhVien()+"','"+d+"','"+sinhvien.getMaLop()+"','"+sinhvien.getEmail()+"','"+sinhvien.getSoDienThoai1()+"','"+sinhvien.getSoDienthoai2()+"','"+sinhvien.getQueQuan()+"','"+sinhvien.getChoOHienNay()+"')");
                        sinhvienAdapter.notifyItemInserted(position);
                    }
                })
                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        if(event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT){
                            Toast.makeText(LopActivity.this, "Xoá thành công", Toast.LENGTH_LONG).show();
                        }

                    }

                }).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_info_class,menu);

        //SearchView ===========
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Search by name or id ...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                    List<Sinhvien> sinhvienResult = new ArrayList<>();
                    for (Sinhvien s:sinhvienList
                    ) {
                        if(s.getTenSinhVien().toLowerCase().contains(newText.toLowerCase()) || s.getMaSinhVien().toLowerCase().contains(newText.toLowerCase())){
                            sinhvienResult.add(s);
                        }
                    }
                    sinhvienAdapter = new SinhvienInClassAdapter(LopActivity.this,sinhvienResult);
                    recyclerView.setAdapter(sinhvienAdapter);

                return true;
            }
        });
        ///=====================

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onResume() {
        sinhvienList.clear();
        getListSinhVien();
        super.onResume();
    }
}