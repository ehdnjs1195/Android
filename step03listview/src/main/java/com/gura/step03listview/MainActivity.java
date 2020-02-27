package com.gura.step03listview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //모델의 참조값을 저장할 필드(다른 메서드에서 참조할 수 있도록)
    List<String> names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ListView 의 참조값 얻어오기
        ListView listView=findViewById(R.id.listView);  // ListView : 목록 뷰
        // 어댑터에 연결할 모델(Data)
        names=new ArrayList<>();
        // 모델에 sample 데이터 저장
        for(int i=0; i<100; i++) {
            names.add("김구라" + i);
        }
        // ListView 에 연결할 어댑터 객체 생성하기 (미리 만들어져 있는 어댑터. 간단히 문자열 하나만 출력하는, 리스트 셀에 더 많은 정보를 담기 위한 복잡한 어댑터는 만들기도 한다.)
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);    // 셀의 레이아웃 -> simple_list_item_1 지금은 간단히 textView만 있다.
        // ListView 에 어댑터 연결하기(모델의 데이터 타입, 레이아웃이 다양할 수 있다. 그래서 바로 listView에 출력할 수는 없고 어댑터를 통해 출력이 가능하다)
        listView.setAdapter(adapter);
        // ListView 에 아이템 클릭 리스너 등록하기
        listView.setOnItemClickListener(this);
    }

    @Override   // 각각의 셀을 클릭했는지 확인하는 객체(AdapterView.OnItemClickListener)의 메서드    // 각각의 셀을 클릭했을 때 실행순서가 들어오는 메서드.
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
        //인자로 전달되는 i 에는 클릭한 셀의 인덱스 값이 들어있다.
        new AlertDialog.Builder(this).setNeutralButton("확인", null).setMessage(names.get(i)).create().show();

        /*
            위의 코딩 한 줄을 풀어보면..
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(names.get(i));
        builder.setNeutralButton("확인", null);
        AlertDialog dialog=builder.create();
        dialog.show();

        */
    }
    //다음 예제 버튼을 눌러을 때 호출되는 메서드
    public void moveNext(View v){
        //Main2Activity 로 이동할 의도 객체 생성
        Intent intent=new Intent(this, Main2Activity.class);
        //startActivity() 메서드 호출하면서 의도 객체를 전달하면 이동된다.
        startActivity(intent);
    }
}
