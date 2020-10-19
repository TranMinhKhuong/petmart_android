package com.example.petmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmart.R;
import com.example.petmart.activity.ChiTietSanPhamActivity;
import com.example.petmart.model.Sanpham;
import com.example.petmart.util.CheckConnection;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arraysanpham;


    public SanphamAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }


    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;

    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Sanpham sanpham = arraysanpham.get(position);
        holder.txttensanpham.setText(sanpham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Giá : " +decimalFormat.format(sanpham.getGiaSanPham())+ " Đ");
//        Picasso.with(context).load(sanpham.getHinhAnhSanPham())
//                .networkPolicy(NetworkPolicy.NO_CACHE)
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
//                .placeholder(R.drawable.noimage)
//                .error(R.drawable.error)
//                .into(holder.imghinhsanpham);
        Picasso.with(context).load(sanpham.getHinhAnhSanPham())
                .centerCrop()
                .resize(150,150)
                .into(holder.imghinhsanpham);

//        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view=layoutInflater.inflate(layout,null);
//        TextView tvTenloaisp=view.findViewById(R.id.textviewloaisp);
//        ImageView imgloaisp=view.findViewById(R.id.imagaeviewloaisp);
//        tvTenloaisp.setText(arraylistloaisp.get(i).getTenloaisp());
//        Picasso.with(context).load(arraylistloaisp.get(i).getHinhanhloaisp()).centerCrop().resize(150,150).into(imgloaisp);
//        return view;
    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhsanpham;
        public TextView txttensanpham, txtgiasanpham;


        public ItemHolder(View itemView) {
            super(itemView);
            imghinhsanpham = (ImageView) itemView.findViewById(R.id.imageviewsanpham);
            txtgiasanpham = (TextView) itemView.findViewById(R.id.textviewgiasanpham);
            txttensanpham = (TextView) itemView.findViewById(R.id.textviewtensanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("thongtinsanpham",arraysanpham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.showToast_Short(context, arraysanpham.get(getPosition()).getTenSanPham());
                    context.startActivity(intent);
                }
            });
        }
    }
}
