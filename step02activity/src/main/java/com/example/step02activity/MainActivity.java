package com.example.step02activity;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    // View.OnClickListener 인터페이스 type 을 필드에 가지고 있기
    View.OnClickListener listener=new View.OnClickListener(){   //OnClickListener 타입을 필드에 넣어둘 수 있다. 단, 인터페이스 타입이므로 어나니머스 클래스로 오버라이드 해주어야 한다.
        @Override
        public void onClick(View v) { //(세 번째 버튼)
            //1. EditText 에 입력한 문자열을 읽어온다.
            EditText inputMsg=findViewById(R.id.inputMsg);
            String msg=inputMsg.getText().toString();
            //2. Toast 메세지에 문자열을 띄우기
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();       // context의 참조값이 필요한데 여기서의 this는 listener의 참조값(View.OnClickListener)이기 때문에 그냥은 사용할 수가 없다.
        }                                                                                    // 따라서 context의 참조값을 가져오기 위해 밖에서 싸고있는 클래스명을 지칭해주어야 함(MainActivity는 AppCompatActivity를 상속받고 있고, AppCompatActivity가 Context를 상속받고 있기 때문에 가능).
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //전송2 버튼의 참조값 얻어오기  (두 번째 버튼    => 가장 많이 쓰이는 형태!)
        Button sendBtn=findViewById(R.id.sendBtn);
        //버튼에 클릭 리스너 등록하기
        sendBtn.setOnClickListener(this);

        //전송3 버튼의 참조값 얻어오기
        Button sendBtn2=findViewById(R.id.sendBtn2);
        sendBtn2.setOnClickListener(listener);  // 버튼을 누르면 실행순서는 필드의 익명클래스 안의 onClick() 메서드로 간다.
    }
    //전송 버튼을 클릭하면 호출되는 함수(첫 번째 버튼)
    public void sendClicked(View v){    //View type을 반드시 전달받아야 버튼과 연결이 될 수 있다.
        //1. EditText 에 입력한 문자열을 읽어온다.
        EditText inputMsg=findViewById(R.id.inputMsg);      // findViewById(R.id.inputMsg) =>  id="@+id/inputMsg" 받을 때는 원래 타입으로(EditText)
        String msg=inputMsg.getText().toString();           // Text를 가져와서 변수에 담기.
        //2. Toast 메세지에 문자열을 띄우기
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    //리스너를 등록한 버튼을 클릭하면 호출되는 메서드
    @Override
    public void onClick(View v) {
        //1. EditText 에 입력한 문자열을 읽어온다.
        EditText inputMsg=findViewById(R.id.inputMsg);
        String msg=inputMsg.getText().toString();
        //2. Toast 메세지에 문자열을 띄우기
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //다음 예제로 이동하기 버튼을 눌렀을 때 호출되는 메서드
    public void move(View v){
        //액티비니를 이동하기 위한 Intent(의도) 객체를 생성한다.
        Intent intent=new Intent(this, Example2Activity.class);
        //startActivity() 메서드를 호출하면서 Intent 객체를 전달한다.
        startActivity(intent);      //setContentView로 이동하는 것은 레이아웃만 바꾸는 것이고 Intent 객체를 이용해서 startActivity() 메서드로 이동하는 것은 새로운 액티비티를 쌓는 것이다. => 뒤로가기를 누르면 쌓여있던 액티비티가 하나씩 빠지는 셈.
    }
}

/*
*   버튼의 사용방법 세가지
*  1. 버튼에서 직접 onClick 메서드 등록하기
*  2. 클릭 리스너 등록하기       => 가장 많이 사용함.
*  3. 필드에 참조값 넣어서 사용하기.
*/