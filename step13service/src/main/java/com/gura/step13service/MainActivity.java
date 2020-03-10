package com.gura.step13service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //서비스 시작 버튼을 눌렀을 때
    public void start(View v){
        //서비스를 시작시킬 Intent 객체 생성
        Intent intent=new Intent(this, MyService.class);
        //Intent 객체를 이용해서 서비스를 시작 시킨다.
        startService(intent);   //activity의 startService() 라는 메서드를 호출해서 service에 인텐트 객체를 전달해서 서비스를 시작시킨다.
    }
    //서비스 종료 버튼을 눌렀을 때
    public void end(View v){
        Intent intent=new Intent(this, MyService.class);
        //Intent 객체를 이용해서 서비스를 종료 시킨다.
        stopService(intent);
    }
}   // => 서비스를 시작, 종료시킬 때 Intent 객체가 필요하다!!
//운영체제가 Service를 생성하는 것이다. Activity가 직접 생성하는 것이 아님. (다음 예제에서 추가 공부)
