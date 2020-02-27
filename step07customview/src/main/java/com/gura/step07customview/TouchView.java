package com.gura.step07customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TouchView extends View {
    //이 View 의 폭과 높이
    private int width, height;
    //글자 혹은 도형을 출력할 때 사용할 Paint 객체
    private Paint bluePaint, greenPaint;
    //이벤트의 종류를 저장할 필드
    private String eventType="";
    //이벤트가 일어난 곳의 좌표를 저장할 필드
    private int eventX, eventY;

    public TouchView(Context context) {
        super(context);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /*
        이 View가 처음 구성될 때 최초 한 번 호출되고(onDraw() 보다 먼저 호출됨) 그 이후에는 View 의 사이즈가
        바뀌었을 때 호출된다.(예를 들어 화면을 돌렸을 때)
        View 가 차지하고 있는 폭과 높이가 전달된다.
        이 값을 사용하고 싶으면 필드에 저장해 놓는다.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //크기를 필드에 저장하고
        width=w;
        height=h;
        //초기화 메서드를 호출한다.    의미: 일단 크기를 알아낸 다음에 초기화 작업을 하겠다.
        init();
    }

    //초기화 하는 메서드(custom으로 만든 메서드)
    public void init(){
        //파란색 Paint 객체 설정
        bluePaint = new Paint();
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.FILL);   //FILL  <ㅡ>  STROKE  예를들어 사각형을 그린다 했을 때 내부를 채우느냐의 차이. 외곽선만 그리는것은 stroke, 내부를 채우면서 그리는 것은 fill . 글자에도 적용이 된다.
        bluePaint.setTextSize(70);

        //녹색 Paint 객체 설정
        greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);
        greenPaint.setStyle(Paint.Style.FILL);
        greenPaint.setStrokeWidth(5);

        //핸들러가 동작하도록 빈 메세지 보내기
        handler.sendEmptyMessageDelayed(0,10);
    }

    @Override
    protected void onDraw(Canvas canvas) {  // drawText 글자, drawCircle 원, drawLine 직선
        //이벤트의 종류 출력하기
        canvas.drawText("이벤트 종류:"+eventType, 0, 100, bluePaint);
        //이벤트가 일어난 곳의 좌표 출력하기
        canvas.drawText("x:"+eventX+" y:"+eventY, 0, 200, bluePaint);
        //View 의 폭과 높이
        canvas.drawText("width:"+width+" height:"+height, 0, 300, bluePaint);
        //이벤트가 일어난 곳에 원 그리기
        canvas.drawCircle(eventX, eventY, 100, greenPaint); // 좌표는 원의 중심, 반지름
        //이벤트가 일어난 곳에 직선 그리기
        canvas.drawLine(0, eventY, 2000, eventY, greenPaint);   // 시작점의 좌표, 끝점의 좌표
        canvas.drawLine(eventX, 0, eventX, 2000, greenPaint);
    }

    //View 에 터치 이벤트가 일어나면 호출되는 메서드
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        eventX = (int)event.getX();
        eventY = (int)event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                eventType = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                eventType = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_UP:
                eventType = "ACTION_UP";
                break;
        }
//        invalidate();     handler가 있기 때문에 자동으로 계속 0.01초마다 화면에.

        return true; // false 를 리턴하면 ACTION_DOWN 이벤트만 처리된다.(최초 한 번 터치를 했을 때는 false를 리턴해주어서 사용할 수 있다)
    }

    //android.os.Handler 로 임포트
    Handler handler=new Handler(){  //익명 클래스로 Handler 를 상속받은 모양.

        @Override
        public void handleMessage(@NonNull Message msg) {
            invalidate();   //화면 갱신하기
            // 10/1000 초 이후에 핸들러에 빈 메세지 보내기 (새로운 스레드라 생각해도 좋다.)     10/1000마다 invalidate()가 호출된다. 화면이 갱신된다.
            handler.sendEmptyMessageDelayed(0,10);  //메세지를 한 번 보내면(start되면) 계속 도는 구조가 된다. 이 메서드를 통해서 10/1000초 마다 msg가 또 보내질 것이기 때문에
        }
    };  //=> 결론적으로 touch이벤트와는 다르게 가만히 있어도 화면이 계속해서 업데이트 되도록 할 수 있다.

}
