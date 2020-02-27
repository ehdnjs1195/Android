package com.gura.beer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BeerView extends View {
    //뷰의 크기를 저장할 필드
    int viewWidth, viewHeight;
    //맥주병 이비지를 저장할 필드
    Bitmap bottleImg;       // 이미지를 로딩해서 원하는 크기의 비트맵으로 미리 만들어 놔야 그릴 수 있다.
    //맥주병의 크기
    int bottleWidth, bottleHeight;  // view 의 비율에 맞게
    //MyView 의 가운데 좌표
    int centerX, centerY;

    //canvas 의 회전각도
    int angle = 0;
    //action down 이 일어난곳의 좌표를 저장할 필드
    int downX, downY;
    //action down 이 일어난 시간을 저장할 필드
    long downTime;
    //맥주병의 회전 각속도
    int angleSpeed;

    //상태값을 관리할 필드(현재 회전하고 있는지 여부)
    boolean isRotating = false;

    //트릭모드인지 확인할 필드
    boolean isTrick;
    //트릭 모드 횟수
    int trickCount=0;

    //사운드 매니저를 필드에 정의
    Util.SoundManager sManager;
    //필요한 static final 상수 정의하기
    static final int SOUND_BIRDDIE=0;
    static final int SOUND_SHOOT=1;

    public BeerView(Context context) {
        super(context);
    }

    public BeerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //View 의 사이즈를 전달 받을 메소드
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        viewWidth = w;
        viewHeight = h;
        //비트맵 이미지 로딩하기(원본크기)
        bottleImg = BitmapFactory.decodeResource(getResources(), R.drawable.guinness);     //getResources() 는 View의 메서드로 불러올 수 있다.
        //크기 조절
        bottleImg = Bitmap.createScaledBitmap(bottleImg, 600, 600, false);   // 원본 이미지를 넣고, 픽셀 단위로 크기를 지정하고, 필터를 적용할 것인지 정한다.
        //로딩된 이미지의 폭과 높이
        bottleWidth = bottleImg.getWidth();   //200
        bottleHeight = bottleImg.getHeight(); //400
        //View 의 정중앙 설정
        centerX = viewWidth / 2;
        centerY = viewHeight / 2;

        //핸들러에 지연된 메세지 보내기
        handler.sendEmptyMessageDelayed(0, 10);
        //사운드 재생 준비
        //사운드 매니저의 참조값 얻어와서
        sManager=Util.SoundManager.getInstance();
        //준비 작업을 하고
        sManager.init(getContext());
        //필요한 사운드를 로딩 시킨다.
        sManager.addSound(SOUND_BIRDDIE, R.raw.dunken_tiger);
        sManager.addSound(SOUND_SHOOT, R.raw.shoot1);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        //캔바스 회전 시키기
        canvas.rotate(angle, centerX, centerY);


        canvas.drawBitmap(bottleImg, centerX - bottleWidth / 2, centerY - bottleHeight / 2, null);   //paint 객체를 넣어주어서 투명도 정도를 정할 수 있다.

        if (angleSpeed > 0) {   //회전 중인 경우
            //각속도 만큼 회전 각도를 반영한다.
            angle += angleSpeed;
            //각속도를 1씩 감소 시키기
            angleSpeed--;

            if (angleSpeed == 80 && isTrick) {     //최소 angleSpeed가 80 이상이면 12시에서만 멈추게 된다.      =>1부터 80까지 더하는 값이 360의 배수가 될 때, 그 때가 12시 방향이 되는 것이기 때문에.
                angle = 0 + 90 * trickCount;
                isTrick = false;    //한 번만 동작 하도록 트릭모드 해제
                trickCount ++;  // 트릭을 한 번 수행하고 나면 1, 그다음 2, 3,4,.. 90도씩 변하게 된다.
            }
        } else {  //회전이 끝난 경우
            if(isRotating){
                //birddie.mp3 효과음을 재생해 보기
                sManager.play(SOUND_BIRDDIE);
            }
            isRotating = false;
            angleSpeed = 0;   //보정.
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            invalidate();   //그려졌던 view를 무효화 하고 onDraw() 를 계속 호출하는 메서드.
            handler.sendEmptyMessageDelayed(0, 10);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isRotating) { //만일 회전 상태이면
            return false;   //여기서 메서드 종료하기
        }
        //이벤트가 일어난 곳의 좌표
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //액션 다운 이벤트가 일어난 곳의 좌표와 시간을 필드에 저장하기
                downX = x;
                downY = y;
                downTime = System.currentTimeMillis();    //1970년 1월 1일 부터 현재까지의 시간을 1000분의 1초 단위로 기록 해둔 것.
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                long upTime = System.currentTimeMillis();
                // action_down 이벤트와 action_up 이벤트가 일어나는데 걸린시간
                long deltaT = upTime - downTime;
                //손가락의 속도 구하기
                double fingerSpeed =
                        Math.sqrt(Math.pow((x - downX), 2)
                                + Math.pow((y - downY), 2)) / deltaT;        //sqrt 루트, pow 거듭제곱
                //맥주병의 회전 각속도 부여하기
                angleSpeed = (int) fingerSpeed * 10;
                isRotating = true;

                //만일 좌하단 영역을 터치 했을때는 트릭 모드로 바꾼다.
                if (x < 100 && y > viewHeight - 100) {
                    isTrick = true;
                }
                break;
        }
        return true;
    }
}
