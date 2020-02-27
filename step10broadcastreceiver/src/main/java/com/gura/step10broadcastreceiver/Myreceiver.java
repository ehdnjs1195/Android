package com.gura.step10broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/*
    [ BroadcastReceiver 를 만드는 방법 ]

    - BroadcastReceiver 추상 클래스를 상속받아서 만든다.
    - onReceiver() 메서드를 오버라이딩해서 방송이 수신되었을 때 원하는 작업을 한다.
    - AndroidManifest.xml 에 등록을 해야한다.
 */
public class Myreceiver  extends BroadcastReceiver {

    //특정 방송이 수신되면 호출되는 메서드
    @Override
    public void onReceive(Context context, Intent intent) { //과연 이 메서드는 UI스레드인가? UI는 오직 UI스레드에서만 변경할 수 있다. => TextView가 바뀌는 것으로 보아 UI스레드가 맞다!
        //Context 를 MainActivity type 으로 casting    => 운영체제에서 context에 MainActivity를 담는다.
        MainActivity activity=(MainActivity)context;

        //비행기 모드가 ON 되었는지 정보는 intent에 담겨온다.
        boolean isOn=intent.getBooleanExtra("state", false);    //state라는 키값으로 넘어온다. 넘어온 것이 없으면 default 값으로 false로 정해준다.
        if(isOn){   //On
            Toast.makeText(context, "비행기 모드 ON", Toast.LENGTH_LONG).show();
            activity.updateUI("메롱메롱 ON");
        }else{  //Off
            Toast.makeText(context, "비행기 모드 OFF", Toast.LENGTH_LONG).show();
            activity.updateUI("메롱메롱 OFF");
        }
    }
}
