package com.gura.step13service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*
    [ UI 없이 백그라운드에서 동작할 수 있는 Service ]

    1. Service 추상클래스를 상속 받는다.
    2. onBind() 메서드 오버라이딩
    3. 추가로 필요한 메서드를 오버라이딩해서 작업한다.

 */
public class MyService extends Service {    //서비스가 시작된다는 것은 결국 onStartCommand() 메서드가 호출된다는 것.

    //특정 Activity 와 연결이 되었을 때 호출되는 메서드 ( 이 예제에서는 할 작업이 없다.)
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    //초기화 할 작업을 하는 메서드
    @Override   //객체가 생성되고 최초 사용될 때 호출
    public void onCreate() {
        super.onCreate();
    }

    //서비스에서 수행해야할 main 작업을 하는 메서드
    @Override   //객체가 생성되고나서 실제로 사용이 될 때 호출. =>핵심 작업
    public int onStartCommand(Intent intent, int flags, int startId) {  //특정 시점에 intent 객체를 통해 service가 시작된다. 전달되는 intent가 파라미터로 들어온다.)
        //핸들러에 메세지 보내기
        handler.sendEmptyMessage(0);
        return super.onStartCommand(intent, flags, startId);
    }

    //서비스가 종료될 때 마무리 작업을 하느 메서드
    @Override   //액티비티가 종료되고 마무리 작업을 할 때 호출.
    public void onDestroy() {
        super.onDestroy();
        //핸들러 동작 하지 않도록
        handler.removeMessages(0);
    }

    //카운트값을 저장할 필드
    int count = 0;

    //핸들러(주기적으로 토스트를 띄울)
    Handler handler = new Handler() {  //(중괄호를 열고 닫아서 익명 클래스를 상속받는 개념)
        @Override
        public void handleMessage(@NonNull Message msg) {   //핸들러 객체를 생성하면 자동으로 호출됨.
            count ++;
            Toast.makeText(getApplicationContext(),count+" 번 호출", Toast.LENGTH_SHORT).show();   //getApplicationContext() => Service의 메서드. 부모로부터 물려받은 context를 리턴함.
            handler.sendEmptyMessageDelayed(0,5000);    //5초 후에 다시 메세지를 보낸다. 즉, 메세지를 한 번 보내면 계속 5초마다 메세지를 보내게 된다. (handleMessage() 메서드 호출)
        }
    };


}
// onCreate()만들기. -> onStartCommand()시작. -> onDestroy()종료.