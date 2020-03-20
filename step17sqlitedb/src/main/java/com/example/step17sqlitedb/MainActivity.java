package com.example.step17sqlitedb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //필드
    private DBHelper helper;
    private EditText inputText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    //ListView 에 출력하기 위해 아답타에 연결할 문자열 목록
    private List<String> stringList;
    //DB 에 있는 실제 DATA 를 가지고 있는 TodoDto 목록
    private List<TodoDto> todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // version 을 바꾸게 되면 DBHelper 객체의 onUpgrade() 메서드가 호출된다.
        helper = new DBHelper(this, "MyDB.sqlite", null,  1);
        //version 이 1인 것은. 최초 호출될 때 MyDB.sqlite라는 DB가 한 번 생성되고 그 다음 호출될 때는 더 생성하지 않는다.
        // but, 만약에 어떤 조건하에 버전이 바뀌게 한다면.. version을 2로 올리면 helper에서 onUpgrade() 가 실행된다.

        //UI 의 참조값 얻어오기
        inputText = findViewById(R.id.inputText);
        listView = findViewById(R.id.listView);
        Button saveBtn = findViewById(R.id.saveBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);
        Button detailBtn = findViewById(R.id.detailBtn);
        //버튼 리스너 등록
        saveBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        detailBtn.setOnClickListener(this);
        //모델 객체 생성
        stringList = new ArrayList<>();
        //아답타 객체 생성
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, stringList);
        //ListView 에 아답타 연결하기
        listView.setAdapter(adapter);

        todoList = new ArrayList<>();
        
        showList();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.saveBtn:  //저장 버튼을 눌렀을 때
                //1. 입력한 문자열을 읽어와서
                String inputMsg = inputText.getText().toString();
                //2. todo 테이블에 저장한다.
                SQLiteDatabase db = helper.getWritableDatabase();
                Object[] args = {inputMsg}; //바인딩 할 args , 순서대로 들어감.
                String sql = "INSERT INTO todo (content, regdate)" +
                        " VALUES(?, datetime('now','localtime'))";
                db.execSQL(sql, args);
                db.close();
                showList();
                break;
            case R.id.deleteBtn:    //삭제 버튼을 툴렀을 때
                int index = listView.getCheckedItemPosition();
                if(index == -1) {   //선택된 셀이 없을 때
                    Toast.makeText(this, "삭제할 셀을 선택하세요.", Toast.LENGTH_SHORT).show();
                    return; //메서드 끝내기
                }
                SQLiteDatabase db2 = helper.getWritableDatabase();
                String sql2 = "DELETE FROM todo" +
                        " WHERE num = ? " ;
                //삭제할 셀의 primary key
                int num = todoList.get(index).getNum();
                Object[] args2 = {num};
                db2.execSQL(sql2, args2);
                db2.close();
                //선택된 cell 취소하기
                listView.clearChoices();
                //목록 다시 출력하기
                showList();
                break;
            case R.id.detailBtn:
                Intent intent = new Intent(this, DetailActivity.class);
                int index2 = listView.getCheckedItemPosition();
                TodoDto dto = todoList.get(index2);
                intent.putExtra("dto", dto);
                startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        showList();
    }

    //ListView 에 할일 목록을 출력하는 메서드
    public void showList(){
        //모델 초기화
        stringList.clear();
        todoList.clear();
        
        SQLiteDatabase db = helper.getReadableDatabase();
        //실행할 SELECT 문
        String sql = "SELECT num, content, regdate" +
                " FROM todo" +
                " ORDER BY num DESC";
        //SELECT 문 수행하고 결과를 Cursor type 으로 받아오기
        Cursor result = db.rawQuery(sql, null);
        //반복문 돌면서 Cursor 객체에서 정보 읽어오기
        while(result.moveToNext()){
            // 0, 1, 2 번째 칼럼 읽어오기
            int num = result.getInt(0);
            String content = result.getString(1);
            String regdate = result.getString(2);
            // 읽어온 문자열 모델에 추가하기
            stringList.add(content);
            // TodoDto 객체를 생성해서 번호, 내용, 등록날짜를 넣어주고
            TodoDto dto = new TodoDto(num, content, regdate);
            todoList.add(dto);
        }
        //모델의 data 가 바뀌었다고 아답타에 알려서 ListView 업데이트 하기
        adapter.notifyDataSetChanged();
    }
}
