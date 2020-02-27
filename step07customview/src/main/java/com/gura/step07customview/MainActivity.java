package com.gura.step07customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(new TouchView(this));    //Layout으로 화면을 채우는 것이 아니라 만든 View 로 화면을 채우겠다.
    }
}
