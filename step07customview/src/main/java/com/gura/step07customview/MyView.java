package com.gura.step07customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/*
    Custom View 만드는 방법
    1. View 클래스를 상속 받는다.
    2. Context 객체를 생성자의 인자로 전달받아서 부모 생성자에 전달하도록 한다.
    3. onDraw() 메서드를 오버라이딩 해서 View 화면을 구성한다.
 */
public class MyView extends View {//1. View 상속
    //터치 이벤트가 일어난 x, y 좌표를 저장할 필드
    private int x, y;

    //2. 생성자 정의 (화면을 View로 꽉 채우려면 이 생성자만 있어도 된다)
    public MyView(Context context) {
        super(context);
    }

    // layout xml 문서에 해당 View 를 사용하게 하려면 필요한 생성자
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    //3. onDraw() 오버라이드 (차지하고 있는 영역에 무엇을 그릴지 정의하는 메서드)
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(100);
        canvas.drawText("x:"+x+" y:"+y, 10, 110, textPaint);    //좌표는 text의 좌하단이 기준이다.


    }

    //터치 이벤트 처리 (여기서는 그냥 메서드를 오버라이드 해서 이벤트를 처리할 수 있다.)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //이벤트가 일어난 곳의 좌표를 필드에 저장
        x = (int) event.getX();     //event.getX(); => float type으로 리턴됨.
        y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        //View 갱신하기 (결과적으로 onDraw() 메서드가 다시 호출된다.)  현재 그린것을 무효화 하고 다시 그리겠다 => onDraw() 메서드를 다시 호출하겠다. 새로운 좌표가 찍힐 것이다!!
        invalidate();

        return true;
    }


}
