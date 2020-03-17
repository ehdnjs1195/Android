package com.gura.step16jsonparse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main4Activity extends AppCompatActivity implements Util.RequestListener, AdapterView.OnItemClickListener {
    private MemberAdapter adapter;
    private List<MemberDto> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //모델 객체 생성
        list = new ArrayList<>();
        //아답타 객체 생성
        adapter = new MemberAdapter(this, R.layout.listview_cell, list);
        //ListView
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        //ListView 에 아이템 클릭 리스너 등록
        listView.setOnItemClickListener(this);
        //스프링 웹서버에 요청하기
        String urlAddr = "http://192.168.0.2:8888/spring05/android/member/list.do";  //특정 서버의 요청경로.
        Util.sendGetRequest(0,urlAddr,null,this);
    }

    @Override
    public void onSuccess(int requestId, Map<String, Object> result) {
        // [{"num":1, "name":"김구라" }, {}, {} ... ]
        String data = (String)result.get("data");
        try {
            JSONArray arr = new JSONArray(data);
            for(int i=0; i<arr.length();i++){
                JSONObject tmp=arr.getJSONObject(i);
                int num=tmp.getInt("NUM");
                String name=tmp.getString("NAME");
//                String addr=tmp.getString("ADDR");    => addr 이 없는데 넣어주면 Exception 이 발생한다.
                //회원정보를 MemberDto 객체를 생성해서 담고
                MemberDto dto = new MemberDto(num, name, null);
                //모델에 추가.
                list.add(dto);
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onFail(int requestId, Map<String, Object> result) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //long id에 클릭한 셀의 id값이 들어온다. => position을 사용해도 되고 id 를 사용해도 된다.
        //1. 클릭한 셀의 회원번호를 읽어와서
        int num = list.get(position).getNum();
        //2. Intent 객체에 담고
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("num", num);
        //3. DetailActivity 로 이동하기
        startActivity(intent);
    }
}
