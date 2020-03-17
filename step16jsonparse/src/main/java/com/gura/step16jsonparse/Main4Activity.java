package com.gura.step16jsonparse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main4Activity extends AppCompatActivity implements Util.RequestListener, AdapterView.OnItemClickListener , AdapterView.OnItemLongClickListener , View.OnClickListener {
    private MemberAdapter adapter;
    private List<MemberDto> list;
    //EditText 의 참조값
    EditText inputName, inputAddr;

    //static final 상수 정의하기
    private static final int REQUEST_LIST = 0;
    private static final int REQUEST_DELETE = 1;
    private static final int REQUEST_INSERT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        //모델 객체 생성
        list = new ArrayList<>();
        //아답타 객체 생성
        adapter = new MemberAdapter(this, R.layout.listview_cell, list);
        //ListView
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        //스프링 웹서버에 요청하기
        String urlAddr = "http://192.168.0.34:8888/spring05/android/member/list.do";  //특정 서버의 요청경로.
        Util.sendGetRequest(REQUEST_LIST,urlAddr,null,this);

        //ListView 에 아이템 클릭 리스너 등록
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        //EditText 의 참조값을 필드에 저장하고
        inputName = findViewById(R.id.inputName);
        inputAddr = findViewById(R.id.inputAddr);
        //Button 의 참조값 얻어내서 리스너 등록하기
        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
    }

    @Override
    public void onSuccess(int requestId, Map<String, Object> result) {
        String urlAddr = "http://192.168.0.34:8888/spring05/android/member/list.do";
        // 어떤 요청에 대한 결과인지 switch 문으로 분기한다.
        switch (requestId){
            case REQUEST_LIST:
                // [{"num":1, "name":"김구라" }, {}, {} ... ]
                String data = (String)result.get("data");
                updateListView(data);
                break;
            case REQUEST_DELETE:    //삭제 요청이 성공하면 다시 목록을 불러오도록 한다.
                //스프링 웹서버에 요청하기
                Util.sendGetRequest(REQUEST_LIST,urlAddr,null,this);
                break;
            case REQUEST_INSERT:
                //스프링 웹서버에 요청하기
                Util.sendGetRequest(REQUEST_LIST,urlAddr,null,this);
                break;
        }
    }

    //ListView 를 업데이트 하는 메서드
    public void updateListView(String data){
        list.clear();
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

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
        //클릭한 셀에 해당하는 회원 정보 얻어오기
        MemberDto dto = list.get(i);
        //삭제할 회원의 번호를 필드에 저장한다.
        selectedNum = dto.getNum();
        new AlertDialog.Builder(this)
                .setMessage(dto.getName()+" 의 정보를 삭제 하시겠습니까?")
                .setPositiveButton("확인",listener)
                .setNegativeButton("취소",null)
                .create().show();
        return false;
    }

    //필드
    private int selectedNum;    //삭제할 회원의 번호(PK)
    //셀을 오래눌렀을 때 삭제 요청을 하도록 하는 리스너(인터페이스로 구현)
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String urlAddr = "http://192.168.0.34:8888/spring05/android/member/delete.do";
            //삭제할 회원의 번호를 Map 에 담고
            Map<String, String> map= new HashMap<>();
            map.put("num", Integer.toString(selectedNum));
            //Util 을 이용해서 post 방식으로 요청을 한다.
            Util.sendPostRequest(REQUEST_DELETE,urlAddr,map,Main4Activity.this);     //요청 아이디를 1로 다르게 함.(목록 가져오기는 0) , 여기서 그냥 this를 하면 listener 자체를 가르키기 때문에 정확히 써준다.

        }
    };

    //추가하기 버튼을 눌렀을 때 호출되는 메서드
    @Override
    public void onClick(View v) {
        //입력한 이름과 주소를 익어와서
        String name = inputName.getText().toString();
        String addr = inputAddr.getText().toString();
        //Map 에 담고
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("addr", addr);
        //Util 을 이용해서 Post 방식 요청과 함께 전송한다.
        String urlAddr = "http://192.168.0.34:8888/spring05/android/member/insert.do";
        Util.sendPostRequest(REQUEST_INSERT, urlAddr, map, this);
    }
}
