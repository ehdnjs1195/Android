package com.gura.hello;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/*
    여러줄 주석 입니다.
 */
public class MainActivity extends AppCompatActivity {
    //MainActivity 가 활성화 될 대 최초 한 번 호출되는 메소드
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //res/layout/activity_main.xml 문서를 전개해서 화면 구성하기
        setContentView(R.layout.activity_main); //R 클래스 => res / layout 클래스 => layout / activity_main => activity_main 에 setContentView로 저장해 두면 알아서 16진수 정수로 등록 되어 있다.

    }
    //버튼을 눌렀을 때 메소드 호출되게 하려면 인자로 View 객체를 받을 준비를 하면 된다.
    public void btnClicked(View v) {
        //로그 출력하기
        Log.d("one", "버튼을 눌렀네요!");
        //토스트 띄우기
        Toast.makeText(this, "또 버튼을 눌렀습니당", Toast.LENGTH_SHORT).show();   //앞으로 context의 참조값으론 this를 입력한다. 즉, MainActivity 에 Toast를 띄우겠다는 뜻.
        //알림창 띄우기
        new AlertDialog.Builder(this).setTitle("야 이 섀캬!").setMessage("버튼 좀 그만 눌러").setNeutralButton("확인", null).create().show();
        setContentView(R.layout.test01);
    }
}
