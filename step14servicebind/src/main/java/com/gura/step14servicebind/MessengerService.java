package com.gura.step14servicebind;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

/*
    액티비티와 서비스가 서로 데이터를 주고 받을 수 있다.
    - Binder 객체 이용하기
    서비스는 액티비티와 상관없이 계속 돌아가고 있다.
    액티비티는 활성화가 되었다가 비활성화가 되었다가 생명주기를 가짐. => 액티비티의 참조값은 바뀐다. 서비스는 바뀌지 않는다.
    => 서로 데이터를 주고받으려면 바인딩을 해야하는데 이때 연결고리 역할을 하는것이 Binder 이다!!

 */
public class MessengerService extends Service {
    //맴버필드로 바인더 객체를 가지고 있는다.
    final IBinder mBinder=new LocalBinder();
    //MainActivity 의 참조값을 저장할 맴버필드
    MainActivity mActivity;

    //Activity 에서 연결요청이 오면 호출되는 메소드
    @Override
    public IBinder onBind(Intent intent) {
        //맴버필드의 IBinder 객체를 리턴해준다.
        return mBinder;
    }
    //연결이 해제 되었을때 호출되는 메소드
    @Override
    public boolean onUnbind(Intent intent) {
        //초기화 작업을 한다.
        mActivity=null;
        return super.onUnbind(intent);
    }

    //서비스 시작 요청이 왔을때 호출되는 메소드
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        handler.removeMessages(0);
        //핸들러에 메세지를 보내서 동작하게 만든다.
        handler.sendEmptyMessage(0);

        return super.onStartCommand(intent, flags, startId);
    }

    //Binder 클래스를 상속받아서 LocalBinder 클래스를 정의한다.
    public class LocalBinder extends Binder {
        //MessengerService 의 참조값을 리턴해주는 메소드
        public MessengerService getService(){
            return MessengerService.this;
        }
        //액티비티의 참조값을 전달받아서 맴버필드에 저장하는 메소드
        public void setActivity(MainActivity activity){
            mActivity = activity;
        }
    }
    //셈플 데이터
    String[] msgList={"너는 지금","뭐해?","자니?","밖이야?",
            "뜬금없는","문자를","돌려보지","난","어떻게",
            "해볼까란","뜻은 아니야","그냥 심심해서","그래", "아니 외로워서","그래"};
    int index=0;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(mActivity != null){
                mActivity.setMsg(msgList[index]);
            }
            index++;
            if(index==10){//없는 인덱스라면
                index=0;//다시 처음으로
            }
            handler.sendEmptyMessageDelayed(0, 5000);
        }
    };

    @Override
    public void onDestroy() {
        handler.removeMessages(0);
        super.onDestroy();
    }
}
