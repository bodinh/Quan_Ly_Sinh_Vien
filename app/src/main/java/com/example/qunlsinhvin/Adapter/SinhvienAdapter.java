package com.example.qunlsinhvin.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qunlsinhvin.MainActivity;
import com.example.qunlsinhvin.Model.Lop;
import com.example.qunlsinhvin.Model.Sinhvien;
import com.example.qunlsinhvin.R;
import com.example.qunlsinhvin.SinhVienActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.List;

public class SinhvienAdapter extends RecyclerView.Adapter<SinhvienAdapter.ViewHolder> {
    private MainActivity context;
    private List<Sinhvien> sinhvienList;

    public SinhvienAdapter(MainActivity context, List<Sinhvien> sinhvienList) {
        this.context = context;
        this.sinhvienList = sinhvienList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener  {
        private TextView tvTenSinhVien, tvMaSinhVien, tvEmail,tvSoDienThoai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSinhVien = (TextView) itemView.findViewById(R.id.tv_tensinhvien);
            tvMaSinhVien = (TextView) itemView.findViewById(R.id.tv_masinhvien);
            tvEmail = (TextView) itemView.findViewById(R.id.tv_email);
            tvSoDienThoai = (TextView) itemView.findViewById(R.id.tv_sodienthoai);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Tuỳ chọn");
            menu.add(this.getAdapterPosition(), 1222, 0, "Chỉnh sửa");//groupId, itemId, order, title
            menu.add(this.getAdapterPosition(), 1223, 0, "Xoá");
        }

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sinh_vien_item_rv, null);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tvTenSinhVien.setText(sinhvienList.get(position).getTenSinhVien());
        holder.tvMaSinhVien.setText(sinhvienList.get(position).getMaSinhVien());
        holder.tvEmail.setText(sinhvienList.get(position).getEmail());
        holder.tvSoDienThoai.setText(sinhvienList.get(position).getSoDienThoai1());


//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                new AlertDialog.Builder(context)
//                        .setTitle("Xoá")
//                        .setMessage("Bạn có chắc muốn xoá")
//                        .setIcon(R.drawable.ic_baseline_delete_alert)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                context.deleteItem(position);
//                            }
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
//                return false;
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SinhVienActivity.class);
                intent.putExtra("sinhvien",(Serializable) sinhvienList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sinhvienList.size();
    }


}
