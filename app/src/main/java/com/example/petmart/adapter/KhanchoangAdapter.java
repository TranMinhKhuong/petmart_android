package com.example.petmart.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petmart.R;
import com.example.petmart.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class KhanchoangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraykhanchoang;

    public KhanchoangAdapter(Context context, ArrayList<Sanpham> arraykhanchoang) {
        this.context = context;
        this.arraykhanchoang = arraykhanchoang;
    }

    @Override
    public int getCount() {
        return arraykhanchoang.size();
    }

    @Override
    public Object getItem(int i) {
        return arraykhanchoang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txttenkhanchoang, txtgiakhanchoang, txtmotakhanchoang;
        public ImageView imgkhanchoang;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_khanchoang,null);
            viewHolder.txttenkhanchoang = (TextView) view.findViewById(R.id.textviewkhanchoang);
            viewHolder.txtgiakhanchoang = (TextView) view.findViewById(R.id.textviewgiakhanchoang);
            viewHolder.txtmotakhanchoang = (TextView) view.findViewById(R.id.textviewmotakhanchoang);
            viewHolder.imgkhanchoang = (ImageView) view.findViewById(R.id.imageviewkhanchoang);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttenkhanchoang.setText(sanpham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiakhanchoang.setText("Giá : " +decimalFormat.format(sanpham.getGiaSanPham())+ " Đ");
        viewHolder.txtmotakhanchoang.setMaxLines(2);
        viewHolder.txtmotakhanchoang.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotakhanchoang.setText(sanpham.getMoTaSanPham());
//        Picasso.with(context).load(sanpham.getHinhAnhSanPham())
//                .placeholder(R.drawable.noimage)
//                .error(R.drawable.error)
//                .into(viewHolder.imgkhanchoang);
        Picasso.with(context).load(sanpham.getHinhAnhSanPham())
                .centerCrop()
                .resize(150,150)
                .into(viewHolder.imgkhanchoang);
        return view;
    }
}
