package com.example.step06fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //버튼을 눌렀을 때 호출되는 메서드
    public void move(View v){
        Intent intent=new Intent(this, SubActivity.class);
        startActivity(intent);
    }
    //프레그먼트가 호출할 메서드
    public void showMessagee(int count){
        Toast.makeText(this, "현재 카운트: "+count, Toast.LENGTH_LONG).show();
    }
}
