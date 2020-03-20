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
    private TextView num, regdate;
    private DBHelper helper;
    private TodoDto dto;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //객체 필드에 저장.
        num = findViewById(R.id.num);
        content = findViewById(R.id.content);
        regdate = findViewById(R.id.regdate);
        //TodoDto를 가져와서
        dto = (TodoDto)getIntent().getSerializableExtra("dto");
        //Text 입력하기
        num.setText("번호 : "+Integer.toString(dto.getNum()));
        content.setText(dto.getContent());
        regdate.setText("입력 날짜 : "+dto.getRegdate());
        // DBHelper 객체 생성
        helper = new DBHelper(this, "MyDB.sqlite", null,  1);
        
        Button updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //1. 입력한 문자열을 읽어와서
        String todo = content.getText().toString();
        //2. todo 테이블에 저장한다.
        SQLiteDatabase db = helper.getWritableDatabase();
        Object[] args = {todo, dto.getNum()}; //바인딩 할 args , 순서대로 들어감.
        String sql = "UPDATE todo " +
                " SET content = ?, regdate = datetime('now', 'localtime')" +
                " WHERE num = ?";
        db.execSQL(sql, args);
        db.close();

        Toast.makeText(this, "정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
        content.clearFocus();
    }
}
