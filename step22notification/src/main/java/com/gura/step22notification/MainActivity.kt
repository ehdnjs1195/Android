package com.gura.step22notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //현재 알림의 아이디
    var currentId=0
    //채널의 아이디를 겹치지 않게 유일하게 구성하기
    val CHANNEL_ID="com.gura.step22notification.ALERT_CHANNEL"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //버튼에 리스너 등록하기
        notiBtn.setOnClickListener(this)
        /*
        notiBtn2.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {

            }
        })
        override 할 메소드가 하나인 경우에 아래와 같이 간략하게 code 를 작성할 수 있다
        */

        //오버라이드 할 메소드가 하나밖에 없다면 아래와 같이 쓸 수 있음
        notiBtn2.setOnClickListener({
            //입력한 문자열 읽어오기
            var msg=inputMsg.text.toString()
            makeManucalCancelNoti(msg)
        })
    }

    fun makeManucalCancelNoti(msg:String){
        //이 앱의 알림 채널 만들기
        createNotificationChannel()
        //알림을 클릭했을 때 실행할 Activity 정보를 가지고 있는 Intent 객체
        val intent = Intent(this, DetailActivity::class.java).apply {//apply 는 기능을 사용할 것이 있으면 {} 안에서 모두 참조하고 담을 수 있는 기능
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("msg", msg)
        }
        //Intent 객체를 인텐트 전달자 객체에 담는다
        val pendingIntent: PendingIntent =
                PendingIntent.getActivity(this, 0, intent, 0)

        //NotificationCompat.Builder 객체 생성
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                .setContentTitle("오빠 나야~")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false) //여기를 true로 하면 알림을 선택하면 알림이 사라진다
        //아이디값 1 증가 시키기
        currentId++

        with(NotificationManagerCompat.from(this)) {
            //with 안하고 . 찍어도 사용 가능
            notify(currentId, builder.build()) // 알림의 id 값
        }
    }

    override fun onClick(v: View?) { //눌러진 버튼의 참조값이 View type 으로 전달된다(부모타입)
        when(v?.id){ // nullnable View 타입이기때문에 참조값을 사용할 때 ? 를 붙여줘야 한다, null 이면 무시되고 exception이 발생하지 않는다(null 이 아니면 참조됨)
            R.id.notiBtn -> {
                //입력한 문자열을 읽어와서
                var msg=inputMsg.text.toString() //아이디만 쓰고 getText() 대신에 text만 쓰면 됨
                //알림에 띄운다.
                makeAutoCancelNoti(msg)
            }

        }
    }

    //인자로 전달되는 문자열을 알림에 띄우는 함수
    fun makeAutoCancelNoti(msg:String){
        //이 앱의 알림 채널 만들기
        createNotificationChannel()
        //알림을 클릭했을 때 실행할 Activity 정보를 가지고 있는 Intent 객체
        val intent = Intent(this, DetailActivity::class.java).apply {//apply 는 기능을 사용할 것이 있으면 {} 안에서 모두 참조하고 담을 수 있는 기능
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("msg", msg)
        }
        //Intent 객체를 인텐트 전달자 객체에 담는다
        val pendingIntent: PendingIntent =
                PendingIntent.getActivity(this, 0, intent, 0)

        //NotificationCompat.Builder 객체 생성
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                .setContentTitle("오빠 나야~")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true) //여기를 true로 하면 알림을 선택하면 알림이 사라진다
        //아이디값 1 증가 시키기
        currentId++

        with(NotificationManagerCompat.from(this)) {
            //with 안하고 . 찍어도 사용 가능
            notify(currentId, builder.build()) // 알림의 id 값
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "kim"
            val descriptionText = "test!"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

//View는 View 타입, View?는 nullnable view 타입
/*
    [ in java ]

    Intent intent=new Intent()
    intent.setFlag(xxx)
    intent.putExtra(xxx,xxx)

    [ in Kotlin ]

    var intent=Intent().apply{
        flag=xxx
        putExtra(xxx,xxx)
    }
 */