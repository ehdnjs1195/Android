package com.gura.step04customlistview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        /* // DetailActivity 가 활성화 되는데 사용되었던 Intent 객체의 참조값 얻어오기
        Intent intent=getIntent();  //부모의 메서드임.
        // "dto" 라는 키값으로 담긴 Serializable type 객체의 참조값 얻어오기
        Serializable serial=intent.getSerializableExtra("dto");
        // 원래 type 으로 casting!
        CountryDto dto=(CountryDto)serial;*/

        //위의 3줄을 1줄의 코드로 바꾸면..↓
        CountryDto dto=(CountryDto)getIntent().getSerializableExtra("dto");

        //전개된 레이아웃에서 UI 참조값 얻어오기
        ImageView imageView=findViewById(R.id.imageView);
        TextView textView=findViewById(R.id.textView);
        Button confirmBtn=findViewById(R.id.confirmBtn);
        TextView contentView=findViewById(R.id.contentView);

        //이미지와 내용 출력하기
        imageView.setImageResource(dto.getResId());
        textView.setText(dto.getName());
        contentView.setText(dto.getContent());

        //버튼에 리스너 등록하기
        confirmBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 디테일 액티비티 종료 시키기
        finish();

        // 액티비티의 수명 주기 알기 https://developer.android.com/guide/components/activities/activity-lifecycle
        // 한글 ver       https://kairo96.gitbooks.io/android/content/ch2.4.1.html
    }
}
