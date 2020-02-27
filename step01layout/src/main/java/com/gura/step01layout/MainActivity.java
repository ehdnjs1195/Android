package com.gura.step01layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {   //앱이 실행될 때 호출되는 클래스

    @Override   //부모의 메서드 재정의.
    protected void onCreate(Bundle savedInstanceState) {    //최초 활성화 될 때 한 번 호출된다.(화면구성)
        super.onCreate(savedInstanceState);
        /*
            res/layout/activity_main.xml 문서를 전개(해석) 해서 화면 구성하기.
            xml 문서에 있는 UI 객체(View객체) 가 각각 생성된다.
        */
        setContentView(R.layout.activity_main); //부모가 가지고 있는 메서드 호출. setContentView() 화면구성을 어떻게 할지. 16진수 정수값을 전달한다(R.layout.activity_main에 등록된 16진수)

    }
    public void goToHome(View v){
        setContentView(R.layout.activity_main);
    }
    public void goToGuyngUniv(View v){
        setContentView(R.layout.linear08);
    }
    public void btnClicked(View v){
        setContentView(R.layout.linear01);
    }
    public void btnClicked2(View v){
        setContentView(R.layout.linear02);
    }
    public void btnClicked3(View v){
        setContentView(R.layout.linear03);
    }
    public void btnClicked4(View v){
        setContentView(R.layout.linear04);
    }
    public void btnClicked5(View v){
        setContentView(R.layout.linear05);
    }
    public void btnClicked6(View v){
        setContentView(R.layout.linear06);
    }
    public void btnClicked7(View v){
        setContentView(R.layout.linear07);
    }
}
