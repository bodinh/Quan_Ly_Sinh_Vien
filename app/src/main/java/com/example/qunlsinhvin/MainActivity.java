package com.example.qunlsinhvin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlsinhvin.Adapter.LopAdapter;
import com.example.qunlsinhvin.Adapter.SinhvienAdapter;
import com.example.qunlsinhvin.Database.Database;
import com.example.qunlsinhvin.Model.Lop;
import com.example.qunlsinhvin.Model.Sinhvien;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //database
    Database database;

    //View
    private TextView tvLabel;
    private RecyclerView recyclerView;
    private SinhvienAdapter sinhvienAdapter;
    private LopAdapter lopAdapter;
    private List<Lop> lopList;
    private List<Sinhvien> sinhvienList;


    //navigation
    private TextView tvUsername;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDatabase();

        navigation();

        initView();
    }

    private void createDatabase() {
        database = new Database(MainActivity.this, "DaoTaoDB", null, 1);
        database.Query("PRAGMA foreign_keys = ON;");
        //database.Query("drop table SinhVienTab");

        database.Query("create table if not exists LopTab (maLop NVARCHAR(6) primary key," +
                " tenLop NVARCHAR(150)," +
                " khoaHoc NVARCHAR(15) ," +
                " maKhoa NVARCHAR(2))");
        database.Query("create table if not exists SinhVienTab (maSinhVien NVARCHAR(9) primary key," +
                " tenSinhVien NVARCHAR(150) ," +
                " ngaySinh DATE ," +
                " maLop NVARCHAR(6)," +
                " email NVARCHAR(256)," +
                "soDienThoai1 NVARCHAR(15)," +
                "soDienThoai2 NVARCHAR(15)," +
                "queQuan NVARCHAR(250)," +
                " choOHienNay NVARCHAR(250)," +
                " foreign key (maLop) references LopTab(maLop)  )");

        try {

            database.Query("Insert into LopTab values('111111','Công nghệ phần mềm 16','16','01')");
            database.Query("Insert into LopTab values('222222','Công nghệ thông tin 16','15','02')");

            database.Query("Insert into SinhVienTab values('17150012','Phạm Văn Đình','1999-07-10','111111','phamvandinhmta@gmail.com','0963118434','0963118434','tổ 1 thị trấn xuân trường,xuân trường,nam định','Bắc từ liêm,hà nội')");
            database.Query("Insert into SinhVienTab values('17150013','Ngô Quang Vũ','1997-07-10','111111','ngoquangvu@gmail.com','0963118111','0963118222','tổ 1 thị trấn xuân trường,xuân trường,nam định','Bắc từ liêm,hà nội')");
            database.Query("Insert into SinhVienTab values('17150014','Phạm Văn A','1999-07-10','111111','phamvandinhmta@gmail.com','0963118434','0963118434','tổ 1 thị trấn xuân trường,xuân trường,nam định','Bắc từ liêm,hà nội')");
            database.Query("Insert into SinhVienTab values('17150015','Phạm Văn B','1999-07-10','111111','phamvandinhmta@gmail.com','0963118434','0963118434','tổ 1 thị trấn xuân trường,xuân trường,nam định','Bắc từ liêm,hà nội')");
            database.Query("Insert into SinhVienTab values('17150016','Phạm Văn C','1999-07-10','111111','phamvandinhmta@gmail.com','0963118434','0963118434','tổ 1 thị trấn xuân trường,xuân trường,nam định','Bắc từ liêm,hà nội')");
            database.Query("Insert into SinhVienTab values('17150017','Phạm Văn D','1999-07-10','111111','phamvandinhmta@gmail.com','0963118434','0963118434','tổ 1 thị trấn xuân trường,xuân trường,nam định','Bắc từ liêm,hà nội')");


            database.Query("Insert into SinhVienTab values('15150012','Nguyễn Văn Đình','1999-07-10','222222','phamvandinhmta@gmail.com','0963118434','0963118434','tổ 1 thị trấn xuân trường,xuân trường,nam định','Bắc từ liêm,hà nội')");
            database.Query("Insert into SinhVienTab values('15150013','Nguyễn Quang Vũ','1997-07-10','222222','ngoquangvu@gmail.com','0963118111','0963118222','tổ 1 thị trấn xuân trường,xuân trường,nam định','Bắc từ liêm,hà nội')");
            database.Query("Insert into SinhVienTab values('15150014','Nguyễn Văn A','1999-07-10','222222','phamvandinhmta@gmail.com','0963118434','0963118434','tổ 1 thị trấn xuân trường,xuân trường,nam định','Bắc từ liêm,hà nội')");
            database.Query("Insert into SinhVienTab values('15150015','Nguyễn Văn B','1999-07-10','222222','phamvandinhmta@gmail.com','0963118434','0963118434','tổ 1 thị trấn xuân trường,xuân trường,nam định','Bắc từ liêm,hà nội')");
            database.Query("Insert into SinhVienTab values('15150016','Nguyễn Văn C','1999-07-10','222222','phamvandinhmta@gmail.com','0963118434','0963118434','tổ 1 thị trấn xuân trường,xuân trường,nam định','Bắc từ liêm,hà nội')");
            database.Query("Insert into SinhVienTab values('15150017','Nguyễn Văn D','1999-07-10','222222','phamvandinhmta@gmail.com','0963118434','0963118434','tổ 1 thị trấn xuân trường,xuân trường,nam định','Bắc từ liêm,hà nội')");
        } catch (SQLiteConstraintException e) {

        }
    }

    private void initView() {
        tvLabel = (TextView) findViewById(R.id.tv_label);
        tvLabel.setText("Danh sách lớp học");

        recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        //recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        lopList = new ArrayList<>();
        sinhvienList = new ArrayList<>();


        readListLop();

        //swipe in recycleView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    //swipe in recycleView
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.bgDeleteItem))
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.bgDeleteItem))
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
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    new AlertDialog.Builder(MainActivity.this)
                            //xử lý khi người dùng click bên ngoài Dialog
                            .setCancelable(true)
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    if (lopList != null) {
                                        lopAdapter.notifyDataSetChanged();
                                    } else {
                                        sinhvienAdapter.notifyDataSetChanged();
                                    }
                                }
                            })
                            //===========================================
                            .setTitle("Xoá")
                            .setMessage("Bạn có chắc muốn xoá")
                            .setIcon(R.drawable.ic_baseline_delete_alert)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteItem(position);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (lopList != null) {
                                        lopAdapter.notifyDataSetChanged();
                                    } else {
                                        sinhvienAdapter.notifyDataSetChanged();
                                    }
                                }
                            })
                            .show();
                    // TODO: 12/3/2020
                    break;
                default:
                    break;
            }
        }
    };
    //===================END swipe in recycleView==================================

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()){
            case R.id.themlop:
                // TODO: 12/5/2020
                Intent intent=new Intent(MainActivity.this,ThemMoiLopActivity.class);
                startActivity(intent);
                break;
            case R.id.themsinhvien:
                Intent intents=new Intent(MainActivity.this,ThemMoiSinhVienActivity.class);
                startActivity(intents);
                // TODO: 12/5/2020
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);

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
                if (lopList != null) {
                    List<Lop> searchResult = new ArrayList<>();
                    for (Lop l : lopList
                    ) {
                        if (l.getTenLop().toLowerCase().contains(newText.toLowerCase()) || l.getMaLop().toLowerCase().contains(newText.toLowerCase())) {
                            searchResult.add(l);
                        }
                    }
                    lopAdapter = new LopAdapter(MainActivity.this, searchResult);
                    recyclerView.setAdapter(lopAdapter);
                } else {
                    List<Sinhvien> sinhvienResult = new ArrayList<>();
                    for (Sinhvien s : sinhvienList
                    ) {
                        if (s.getTenSinhVien().toLowerCase().contains(newText.toLowerCase()) || s.getMaSinhVien().toLowerCase().contains(newText.toLowerCase())) {
                            sinhvienResult.add(s);
                        }
                    }
                    sinhvienAdapter = new SinhvienAdapter(MainActivity.this, sinhvienResult);
                    recyclerView.setAdapter(sinhvienAdapter);
                }
                return true;
            }
        });
        ///=====================

        return super.onCreateOptionsMenu(menu);
    }



    private void navigation() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawLayout);

        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        //getView header Navigation
        View headerView = navigationView.getHeaderView(0);
        tvUsername = (TextView) headerView.findViewById(R.id.tv_username);
        tvUsername.setText(getIntent().getStringExtra("username").toString());
        //=========================

        //set Checked item when open app
        navigationView.getMenu().getItem(0).setCheckable(true).setChecked(true);
        //navigationView.setCheckedItem(R.id.dslop);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //set back ground item when checked
        menuItem.setCheckable(true);
        menuItem.setChecked(true);
        //================================
        switch (menuItem.getItemId()) {
            case R.id.dslop:
                // TODO: 12/2/2020
                tvLabel.setText("Danh sách lớp học");
                readListLop();
                drawerLayout.closeDrawers();
                break;
            case R.id.dssinhvien:
                // TODO: 12/2/2020
                tvLabel.setText("Danh sách sinh viên");
                readListSinhVien();
                drawerLayout.closeDrawers();
                break;
            case R.id.dangxuat:
                // TODO: 12/4/2020
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
        return false;
    }

    public void readListLop() {
        sinhvienList = null;
        lopList = new ArrayList<>();
        Cursor cursor = database.QueryGetData("select * from LopTab");
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                lopList.add(new Lop(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        lopAdapter = new LopAdapter(MainActivity.this, lopList);
        recyclerView.setAdapter(lopAdapter);
    }

    public void readListSinhVien() {
        lopList = null;
        sinhvienList = new ArrayList<>();
        Cursor cursor = database.QueryGetData("select * from SinhVienTab");
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = simpleDateFormat.parse(cursor.getString(2));
                    sinhvienList.add(new Sinhvien(cursor.getString(0), cursor.getString(1), date, cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        sinhvienAdapter = new SinhvienAdapter(MainActivity.this, sinhvienList);
        recyclerView.setAdapter(sinhvienAdapter);
    }

    public void deleteItem(final int position) {
        if (lopList != null) {
            final Lop lop = lopList.get(position);
            try {
                //Xoas lop rong database
                database.Query("delete from LopTab where maLop = '" + lop.getMaLop() + "'");
                //=======================
                lopList.remove(position);
                lopAdapter.notifyItemRemoved(position);
                Snackbar.make(recyclerView, "Đã xoá Mã lớp : " + lop.getMaLop(), Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                lopList.add(position, lop);
                                lopAdapter.notifyItemInserted(position);
                            }
                        }).show();
            } catch (SQLiteConstraintException e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.ic_baseline_delete_alert)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lopAdapter.notifyDataSetChanged();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //delete sinh vien trong database
                                database.Query("delete from SinhVienTab where maLop='" + lop.getMaLop() + "'");
                                //Xoas lop rong database
                                database.Query("delete from LopTab where maLop = '" + lop.getMaLop() + "'");
                                lopList.remove(lop);
                                lopAdapter.notifyItemRemoved(position);
                            }
                        })
                        .setCancelable(true)
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                lopAdapter.notifyDataSetChanged();
                            }
                        })
                        .setTitle("Xoá")
                        .setMessage("Lớp học có học sinh , " + "\n" + "Bạn có chắc muốn xoá " + "\n" + "(Thao tác này sẽ xoá tất cả học sinh trong lớp )").show();

            }
        } else {
            final Sinhvien sinhvien = sinhvienList.get(position);
            //delete sinh vien trong database
            database.Query("delete from SinhVienTab where maSinhVien='" + sinhvien.getMaSinhVien() + "'");
            //===============================
            sinhvienList.remove(position);
            sinhvienAdapter.notifyItemRemoved(position);
            Snackbar.make(recyclerView, "Đã xoá Mã sinh viên : " + sinhvien.getMaSinhVien(), Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sinhvienList.add(position, sinhvien);
                            //restore sinh vien trong database
                            Date date = sinhvien.getNgaySinh();
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            String d = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
                            database.Query("Insert into SinhVienTab values('" + sinhvien.getMaSinhVien() + "','" + sinhvien.getTenSinhVien() + "','" + d + "','" + sinhvien.getMaLop() + "','" + sinhvien.getEmail() + "','" + sinhvien.getSoDienThoai1() + "','" + sinhvien.getSoDienthoai2() + "','" + sinhvien.getQueQuan() + "','" + sinhvien.getChoOHienNay() + "')");
                            sinhvienAdapter.notifyItemInserted(position);
                        }
                    })
                    .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                                Toast.makeText(MainActivity.this, "Xoá thành công", Toast.LENGTH_LONG).show();
                            }

                        }

                    }).show();
        }

    }

    @Override
    protected void onResume() {
        if (sinhvienList != null) {
            sinhvienList.clear();
            readListSinhVien();
        }
        if (lopList != null) {
            lopList.clear();
            readListLop();
        }
        super.onResume();
    }
}