package com.example.step17sqlitedb;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    //필드
    private EditText content;
    private DBHelper helper;
    private TodoDto dto;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //필요한 UI 의 참조값 얻어오기
        TextView num = findViewById(R.id.num);
        content = findViewById(R.id.content);
        TextView regdate = findViewById(R.id.regdate);
        Button updateBtn = findViewById(R.id.updateBtn);
        Button listBtn = findViewById(R.id.listBtn);

        //TodoDto를 가져와서
        dto = (TodoDto)getIntent().getSerializableExtra("dto");
        //정보 출력하기
        num.setText("번호 : "+Integer.toString(dto.getNum()));
        content.setText(dto.getContent());
        regdate.setText("등록일 : "+dto.getRegdate());
        // DBHelper 객체 생성
        helper = new DBHelper(this, "MyDB.sqlite", null,  1);

        //버튼에 리스너 등록하기
        updateBtn.setOnClickListener(this);
        listBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.updateBtn:
                //입력한 문자열을 읽어와서
                String todo = content.getText().toString();
                //DB 에 수정 반영한다.
                SQLiteDatabase db = helper.getWritableDatabase();
                Object[] args = {todo, dto.getNum()};
                String sql = "UPDATE todo " +
                        " SET content = ?, regdate = datetime('now', 'localtime')" +
                        " WHERE num = ?";
                db.execSQL(sql, args);
                db.close();

                Toast.makeText(this, "정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                content.clearFocus();
                break;
            case R.id.listBtn:
                //액티비티를 종료 시켜서 목록 보기 Activity로 돌아간다.
                finish();
                break;
        }
    }
}
