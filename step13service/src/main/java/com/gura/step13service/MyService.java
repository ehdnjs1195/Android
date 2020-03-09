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
public class MyService extends Service {

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
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //서비스가 종료될 때 마무리 작업을 하느 메서드
    @Override   //액티비티가 종료되고 마무리 작업을 할 때 호출.
    public void onDestroy() {
        super.onDestroy();
    }

    //카운트값을 저장할 필드
    int count = 0;

    //핸들러(주기적으로 토스트를 띄울)
    Handler handler = new Handler() {  //(중괄호를 열고 닫아서 익명 클래스를 상속받는 개념)
        @Override
        public void handleMessage(@NonNull Message msg) {
            count ++;
            Toast.makeText(getApplicationContext(),count+" 번 호출", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(0,5000);
        }
    };


}
