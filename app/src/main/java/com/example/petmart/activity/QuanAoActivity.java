package com.example.petmart.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.petmart.R;
import com.example.petmart.adapter.KhanchoangAdapter;
import com.example.petmart.adapter.QuanaoAdapter;
import com.example.petmart.model.Sanpham;
import com.example.petmart.util.CheckConnection;
import com.example.petmart.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuanAoActivity extends AppCompatActivity {
    Toolbar toolbarquanao;
    ListView lvquanao;
    QuanaoAdapter quanaoAdapter;
    ArrayList<Sanpham> mangquanao;
    int idquanao = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitdata = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ao);
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            anhXa();
            getIdloaisp();
            ActionToolBar();
            GetData(page);
            LoadMoreData();
        }else{
            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối");
            finish();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.menugiohang:
//                Intent intent = new Intent(getApplicationContext(),GiohangActivity.class);
//                startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void anhXa() {
        toolbarquanao = (Toolbar) findViewById(R.id.toolbarquanao);
        lvquanao =(ListView) findViewById(R.id.listviewquanao);
        mangquanao = new ArrayList<>();
        quanaoAdapter = new QuanaoAdapter(getApplicationContext(), mangquanao);
        lvquanao.setAdapter(quanaoAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }

    private void LoadMoreData() {

        lvquanao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham", mangquanao.get(i));
                startActivity(intent);
            }
        });
        lvquanao.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirtsItem, int VisibleItem, int TotalItem) {
                if(FirtsItem + VisibleItem == TotalItem && TotalItem != 0 && isLoading == false && limitdata == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void getIdloaisp() {
        idquanao = getIntent().getIntExtra("idloaisanpham",-1);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarquanao);
        getSupportActionBar().setTitle("Quần áo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarquanao.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.DuongDanKhanChoang+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tenquanao = "";
                int Giaquanao = 0;
                String Hinhanhquanao = "";
                String Motaquanao = "";
                int Idspquanao = 0;
                if(response != null && response.length() != 2){
                    lvquanao.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tenquanao = jsonObject.getString("tensp");
                            Giaquanao = jsonObject.getInt("giasp");
                            Hinhanhquanao = jsonObject.getString("hinhanhsp");
                            Motaquanao = jsonObject.getString("motasp");
                            Idspquanao = jsonObject.getInt("idsanpham");
                            mangquanao.add(new Sanpham(id, Tenquanao, Giaquanao, Hinhanhquanao, Motaquanao, Idspquanao));
                            quanaoAdapter.notifyDataSetChanged();
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata = true;
                    lvquanao.removeFooterView(footerview);
                    CheckConnection.showToast_Short(getApplicationContext(),"Đã hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idsanpham",String.valueOf(idquanao));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvquanao.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends  Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}