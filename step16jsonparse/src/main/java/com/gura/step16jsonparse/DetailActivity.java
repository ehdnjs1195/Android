package com.gura.step16jsonparse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements Util.RequestListener {
    private TextView textNum,textName,textAddr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Intent 객체에 "num" 이라는 키값으로 담긴 회원의 번호 얻어내기
        int num = getIntent().getIntExtra("num",0);
        //DetailActivity 라는 액티비티를 활성화 하기 위해 생성된 Intent 객체를 getIntent() 메서드를 통해 얻어올 수 있고, 전 액티비티에서 넘겨주었던 Extra 값을 타입에 맞게 가져올 수 있다.

        // 회원 번호를 Map 객체에 담는다.
        Map<String, String> map = new HashMap<>();
        map.put("num", Integer.toString(num));
        String urlAddr = "http://192.168.0.2:8888/spring05/android/member/detail.do";      //http://192.168.0.34:8888/spring05/android/member/detail.do?num=85 이런식으로 GET 방식으로 파라미터를 전달하고 요청함.
        //Util 을 이용해서 요청한다.     요청하면서 num 이라는 키값의 파라미터가 전달된다. 요청하는 파라미터는 모두 String 이므로 Map의 제너릭을 모두 String 으로 지정해줌.
        Util.sendGetRequest(0, urlAddr, map, this);

        //TextView 의 참조값 얻어내서 필드에 저장하기
        textNum=findViewById(R.id.num);
        textName=findViewById(R.id.name);
        textAddr=findViewById(R.id.addr);
    }

    @Override
    public void onSuccess(int requestId, Map<String, Object> result) {
        /*
            data는
            {"num":1, "name":"김구라", "addr":"노량진"}
            형식의 JSON 문자열이다.
         */
        String data = (String)result.get("data");
        try{
            JSONObject obj = new JSONObject(data);
            int num = obj.getInt("num");
            String name = obj.getString("name");
            String addr = obj.getString("addr");

            //TextView 에 출력하기
            textNum.setText(Integer.toString(num));
            textName.setText(name);
            textAddr.setText(addr);
        }catch (JSONException je){}
    }

    @Override
    public void onFail(int requestId, Map<String, Object> result) {

    }
}
