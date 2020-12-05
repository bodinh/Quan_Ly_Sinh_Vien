package com.example.qunlsinhvin.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qunlsinhvin.LopActivity;
import com.example.qunlsinhvin.MainActivity;
import com.example.qunlsinhvin.Model.Lop;
import com.example.qunlsinhvin.R;

import java.io.Serializable;
import java.util.List;

public class LopAdapter extends RecyclerView.Adapter<LopAdapter.ViewHolder> {
    private MainActivity context;
    private List<Lop> lopList;

    public LopAdapter(MainActivity context, List<Lop> lopList) {
        this.context = context;
        this.lopList = lopList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTenLop,tvMaLop,tvKhoaHoc,tvMaKhoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenLop = (TextView) itemView.findViewById(R.id.tv_tenlop);
            tvMaLop = (TextView) itemView.findViewById(R.id.tv_malop);
            tvKhoaHoc = (TextView) itemView.findViewById(R.id.tv_khoahoc);
            tvMaKhoa = (TextView) itemView.findViewById(R.id.tv_makhoa);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.lop_item_rv,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvTenLop.setText(lopList.get(position).getTenLop());
        holder.tvMaLop.setText(lopList.get(position).getMaLop());
        holder.tvKhoaHoc.setText(lopList.get(position).getKhoaHoc());
        holder.tvMaKhoa.setText(lopList.get(position).getMaKhoa());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Xoá")
                        .setMessage("Bạn có chắc muốn xoá")
                        .setIcon(R.drawable.ic_baseline_delete_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                context.deleteItem(position);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, LopActivity.class);
                intent.putExtra("lop",(Serializable) lopList.get(position) );
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return lopList.size();
    }
}
