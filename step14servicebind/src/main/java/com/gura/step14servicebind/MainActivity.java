package com.gura.step14servicebind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    //필요한 맴버필드 정의하기
    MessengerService mService;
    //서비스에 연결되었는지 여부
    boolean mServiceConnected = false;
    EditText console;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        console = (EditText) findViewById(R.id.editText);
        //서비스를 시작 시킨다.
        Intent intent = new Intent(this, MessengerService.class);   //어떤 서비스를 시작할지 클래스타입을 전달한다. 서비스는 인텐트가 필요하므로 인텐트객체를 생성하는 것이다.
        startService(intent);
    }

    //서비스 종료 버튼을 눌렀을때
    public void end(View v) {
        if (mServiceConnected) {//서비스에 바인딩 된 상태라면
            //바인딩을 해제해준다.
            unbindService(mConn);
            mServiceConnected = false;
        }
        Intent intent = new Intent(this, MessengerService.class);
        stopService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //서비스 바인딩하기( 연결하는 작업 => 데이터를 주고받기 위해서. 인텐트 객체를 생성한다)
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConn, Context.BIND_AUTO_CREATE);   //bindService() 부모로부터 받은 메서드 => 액티비티가 활성화 될 때 마다 서비스와 바인딩 하겠다! 서비스와 소통을 하기 위해
        //바인드는 비동기 작업이다.
    }

    @Override   //액티비티가 비활성화 될 때.
    protected void onStop() {
        if (mServiceConnected) {//서비스에 바인딩 된 상태라면
            //바인딩을 해제해준다.
            unbindService(mConn);   //액티비티가 종료될 때 바인딩을 해제한다.
            mServiceConnected = false;
        }
        super.onStop();
    }

    //서비스로 부터 문자열을 전달받을 메소드
    public void setMsg(final String msg) {
//        fianl String a="김구라";

        //UI 스레드에서 동작할수 있도록 한다.
        runOnUiThread(new Runnable() {  //여기도 익명의 클래스.  => class '익명의Class' implements Runnable{ }
            @Override
            public void run() {
                //콘솔에 출력하기
                console.append(msg + "\n");     //local innerClass에서 지역변수(msg)를 참조할 땐 값이  변하지 않는다는 보장이 있는 final만 참조가 가능하다.
//                console.append(a);    이해를 돕기위해.   원칙상 밖에 그냥 정의된 지역변수는 참조할 수 없다. final 이라는 상수가 된 변수는 참조가 가능하다.
            }
        });
    }

    //서비스 연결객체
    ServiceConnection mConn = new ServiceConnection() { //익명 innerClass로 만듬. => class '익명의Class' extends ServiceConnection{ } 한 것이나 마찬가지
        //서비스와 연결되었을때 호출되는 메소드
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {   //IBinder의 참조값인 service가 핵심 객체가 된다.
            //서비스의 onBind() 메소드에서 리턴해주는 IBinder 객체가 들어온다.
            MessengerService.LocalBinder
                    localBinder = (MessengerService.LocalBinder) service;
            //MessengerService 의 참조값을 맴버필드에 저장한다.
            mService = localBinder.getService();    //mService에 service 참조값을 넣기위해 이러한 복잡한 과정을 거친다. 운영체제가 생성한 Service 참조값.
            //서비스에 액티비티의 참조값을 전달한다.
            localBinder.setActivity(MainActivity.this);     // 여기서 그냥 this 를 쓰면 ServiceConnection 을 가리키는 것이므로 밖의 MainActivity를 가리키기 위해 직접 써준다.
            //서비스와 연결되었다고 표시한다.
            mServiceConnected = true;

        }

        //서비스와 연결 해제 되었을때 호출되는 메소드
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceConnected = false;
            mService = null;
        }
    };
}
