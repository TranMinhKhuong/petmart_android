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

public class QuanaoAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayquanao;

    public QuanaoAdapter(Context context, ArrayList<Sanpham> arrayquanao) {
        this.context = context;
        this.arrayquanao = arrayquanao;
    }

    @Override
    public int getCount() {
        return arrayquanao.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayquanao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txttenquanao, txtgiaquanao, txtmotaquanao;
        public ImageView imgquanao;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_quanao,null);
            viewHolder.txttenquanao = (TextView) view.findViewById(R.id.textviewtenquanao);
            viewHolder.txtgiaquanao= (TextView) view.findViewById(R.id.textviewgiaquanao);
            viewHolder.txtmotaquanao = (TextView) view.findViewById(R.id.textviewmotaquanao);
            viewHolder.imgquanao = (ImageView) view.findViewById(R.id.imageviewquanao);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttenquanao.setText(sanpham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiaquanao.setText("Giá : " +decimalFormat.format(sanpham.getGiaSanPham())+ " Đ");
        viewHolder.txtmotaquanao.setMaxLines(2);
        viewHolder.txtmotaquanao.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotaquanao.setText(sanpham.getMoTaSanPham());
        Picasso.with(context).load(sanpham.getHinhAnhSanPham())
                .centerCrop()
                .resize(150,150)
                .into(viewHolder.imgquanao);

        return view;
    }
}
