package com.gura.drawpanel;

import java.io.Serializable;

public class Point implements Serializable {    //객체를 파일에 저장, 읽어들이기 하기 위해 serializable을 구현하도록 한다. Object(Input, Output)Stream 을 쓰기 위해
    public int x, y;
    public boolean isStartPoint;    //선의 시작점인지 여부
    //추가적으로 기능을 만들기 위해선 여기에 필드를 선언하고 만들면 된다. 예) 색상, 굵기 조절, 등등.
}
