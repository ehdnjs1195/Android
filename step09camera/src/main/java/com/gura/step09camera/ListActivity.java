package com.gura.step09camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity
        implements Util.RequestListener, View.OnClickListener {
    public static final String IMAGELIST_URL="http://192.168.0.15:8888/spring05/android/image/list.do";;
    private ListView listView;
    private ImageAdapter adapter;
    private List<ImageDto> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView=findViewById(R.id.listView);
        list=new ArrayList<>();
        adapter=new ImageAdapter(this, R.layout.listview_cell, list);
        listView.setAdapter(adapter);


        Button takePicBtn = findViewById(R.id.takePicBtn);
        Button refreshBtn = findViewById(R.id.refreshBtn);
        takePicBtn.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Util.sendGetRequest(0, IMAGELIST_URL, null, this);
        /*
            activity의 생명주기를 보면 사진을 찍으러 갔을 때 ListActivity는 onStop 상태에 들어간다.
            업로드 후 MainActivity를 내리면 ListActivity가 onRestart를 거쳐 onStart - onResume 를 지나 액티비티가 실행된다.
            따라서 Util.sendGetRequest() 메서드를 통해 리스트를 다시 불러오도록하면 업로드 된 내용이 바로 업데이트되도록
            onStart나 onResume에서 작업을 해주면 바로 바뀌는 모습을 볼 수 있다.
         */
    }

    @Override
    public void onSuccess(int requestId, Map<String, Object> result) {
        //모델 클리어
        list.clear();
        String data=(String)result.get("data");
        try{
            JSONArray arr=new JSONArray(data);
            for(int i=0; i<arr.length(); i++){
                JSONObject obj=arr.getJSONObject(i);
                int num=obj.getInt("num");
                String writer=obj.getString("writer");
                String imagePath=obj.getString("imagePath");
                String regdate=obj.getString("regdate");
                //ImageDto 객체를 생성해서 이미지 정보를 담는다.
                ImageDto dto=new ImageDto();
                dto.setNum(num);
                dto.setImageUrl("http://192.168.0.15:8888/spring05"+imagePath);
                dto.setWriter(writer);
                dto.setRegdate(regdate);
                //ImageDto 객체를 List 에 추가
                list.add(dto);
            }
            adapter.notifyDataSetChanged();
        }catch (JSONException je){

        }

    }

    @Override
    public void onFail(int requestId, Map<String, Object> result) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.takePicBtn:
                //사진 찍는 액티비티로 이동하기
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.refreshBtn:
                //다시 목록 받아오기
                Util.sendGetRequest(0, IMAGELIST_URL, null, this);
                break;
        }
    }
}
