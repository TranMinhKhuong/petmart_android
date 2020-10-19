package com.example.petmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petmart.R;
import com.example.petmart.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaispApdater  extends BaseAdapter {
    ArrayList<Loaisp> arraylistloaisp;
    int layout;
    Context context;

    public LoaispApdater(ArrayList<Loaisp> arraylistloaisp, int layout, Context context) {
        this.arraylistloaisp = arraylistloaisp;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylistloaisp.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylistloaisp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView txttenloaisp;
        ImageView imgloaisp;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHolder viewHolder = null;
//        if(view == null){
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view  = inflater.inflate(R.layout.dong_listview_loaisp, null);
//            viewHolder.txttenloaisp = (TextView) view.findViewById(R.id.textviewloaisp);
//            viewHolder.imgloaisp = (ImageView) view.findViewById(R.id.imagaeviewloaisp);
//            view.setTag(viewHolder);
//        }else{
//            viewHolder = (ViewHolder) view.getTag();
//        }
//        Loaisp loaisp = (Loaisp) getItem(i);
//        viewHolder.txttenloaisp.setText(loaisp.getTenloaisp());
//        Picasso.with(context).load(loaisp.getHinhanhloaisp())
//                .placeholder(R.drawable.noimage)
//                .error(R.drawable.error)
//                .into(viewHolder.imgloaisp);
//        return view;


        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(layout,null);
        TextView tvTenloaisp=view.findViewById(R.id.textviewloaisp);
        ImageView imgloaisp=view.findViewById(R.id.imagaeviewloaisp);
        tvTenloaisp.setText(arraylistloaisp.get(i).getTenloaisp());
        Picasso.with(context).load(arraylistloaisp.get(i).getHinhanhloaisp()).centerCrop().resize(150,150).into(imgloaisp);
        return view;
    }
}
