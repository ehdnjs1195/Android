package com.gura.drawpanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends View {
    Paint redPaint;
    //모든 점의 좌표를 젖아할 배열
    List<Point> pointList = new ArrayList<>();


    public DrawPanel(Context context) {
        super(context);
    }

    public DrawPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {  //draw color: 색깔을 칠할 때, bitmap: 이미지를 넣을 때, text: 글씨를 쓸 때, line: 선을 그릴 때

        int startX = 0;
        int startY = 0;
        //반복문 돌면서 모든 점을 이어준다.
        for (Point tmp : pointList) {
            if (tmp.isStartPoint) {
                startX = tmp.x;
                startY = tmp.y;
                continue;   //실행순서가 다시 for 문으로 돌아가도록(아래로 내려가지 않고).   isStartPoint 가 false 일 때 실행순서가 내려간다.
            }
            canvas.drawLine(startX, startY, tmp.x, tmp.y, redPaint);
            startX = tmp.x;
            startY = tmp.y;
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //이벤트가 일어난 곳의 좌표
        int eventX = (int) event.getX();
        int eventY = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:   //선의 시작점
                //이벤트가 일어난 곳의 좌표를 Point 객체를 생성해서 담고
                Point p = new Point();
                p.x = eventX;
                p.y = eventY;
                p.isStartPoint = true;
                //배열에 저장한다.
                pointList.add(p);
                break;
            case MotionEvent.ACTION_MOVE:   //선의 끝점
                //이을점 정보를 Point 객체에 담고
                Point p2 = new Point();
                p2.x = eventX;
                p2.y = eventY;
                //배열에 저장하고
                pointList.add(p2);
                //화면을 다시 그린다.
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        //모든 이벤트를 다 받을수 있도록 true 를 리턴해준다.
        return true;
    }

    //List<Point> 를 리턴해주는 메서드
    public List<Point> getPointList(){
        return  this.pointList;
    }
    //Activity 에서 List<Point> 를 전달 받아서 화면에 다시 복구 시키는 메서드
    public void setPointList(List<Point> pointList){
        this.pointList=pointList;
        //다시 그리도록 한다.
        invalidate();
    }
}
