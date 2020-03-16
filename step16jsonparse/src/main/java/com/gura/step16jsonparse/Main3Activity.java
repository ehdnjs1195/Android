package com.gura.step16jsonparse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener , Util.RequestListener {
    ListView result;
    List<String> member;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Button getBtn = findViewById(R.id.getBtn);
        result = findViewById(R.id.result);
        getBtn.setOnClickListener(this);

        member=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, member);
        result.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        String urlAddr = "http://192.168.0.34:8888/spring05/android/getmember.do";  //특정 서버의 요청경로.
        Util.sendGetRequest(0,urlAddr,null,this);

    }

    @Override
    public void onSuccess(int requestId, Map<String, Object> result) {
        // [{"num":1, "name":"김구라", "isMan":true}, {}, {} ... ]
        String data = (String)result.get("data");
        try {
            JSONArray arr = new JSONArray(data);
            for(int i=0; i<arr.length();i++){
                JSONObject tmp=arr.getJSONObject(i);
                int num=tmp.getInt("NUM");
                String name=tmp.getString("NAME");
                String addr=tmp.getString("ADDR");
                String info = "번호: " + num + " | 이름: " + name + " | 주소: " + addr;
                member.add(info);
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onFail(int requestId, Map<String, Object> result) {

    }
}
