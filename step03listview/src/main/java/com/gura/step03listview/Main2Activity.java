package com.gura.step03listview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemLongClickListener{
    //필드
    List<String> names;
    //선택한 셀 인덱스를 저장할 필드
    int selectedIndex;
    //ListView 에 연결된 어댑터의 참조값을 저장할 필드   (필드에 값을 넣을때는 this.adapter 이지만 this는 생략이 가능하다)
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // ListView 의 참조값 얻어오기
        ListView listView = findViewById(R.id.listView);  // ListView : 목록 뷰
        // 어댑터에 연결할 모델(Data)
        names = new ArrayList<>();
        // 모델에 sample 데이터 저장
        for (int i = 0; i < 100; i++) {
            names.add("김구라" + i);
        }
        // ListView 에 연결할 어댑터 객체 생성하기
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        // ListView 에 어댑터 연결하기
        listView.setAdapter(adapter);
        // 셀을 오랫동안 눌렀을 때 동작할 리스너 등록하기
        listView.setOnItemLongClickListener(this);
    }
    //AlertDialog 버튼 클릭 리스너 객체 (field)
    DialogInterface.OnClickListener listener= new DialogInterface.OnClickListener(){

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:   //긍정 버튼을 눌렀을 때(which 정수가 몇일때)
                    //1. 선택한 아이템 인덱스를 모델에서 삭제한다.
                    names.remove(selectedIndex);
                    //2. ListView 를 업데이트 한다. (어댑터를 이용해서)
                    adapter.notifyDataSetChanged(); //데이터가 바뀌었을 때 업데이트를 하는 메서드.
                    break;
                case DialogInterface.BUTTON_NEGATIVE:   //부정 버튼을 눌렀을 때

                    break;
            }
        }
    };

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //선택한 인덱스(item)를 필드에 저장한다.
        selectedIndex=position;

        new AlertDialog.Builder(this).setTitle("알림").setMessage(names.get(position)+" 정보를 삭제 하시겠습니까?")
                .setPositiveButton("네", listener).setNegativeButton("아니오", null).create().show();
                            //PositiveButton을 누르면 listener 필드의 onClick() 으로 실행순서가 들어간다.
        return false;
    }
}
