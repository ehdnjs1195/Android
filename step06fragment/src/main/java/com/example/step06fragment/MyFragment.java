package com.example.step06fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/*
    [ Fragment 만드는 방법 ]
    1. Fragment 클래스를 상속받는다.
    2. onCreateView() 메서드를 오버라이드 한다.
*/
public class MyFragment extends Fragment implements View.OnTouchListener { //일부 기능,용도 등의 코드를 담아두는 조각. 다른 Activity에서도 계속해서 활용하여 사용할 수 있도록.
    //터치 횟수를 관리할 필드
    private int touchCount;
    //TextView 의 참조값
    private TextView textView;
    private MainActivity activity;


    @Nullable
    @Override //Activity의 onCreate() 와 같이 화면을 구성하는 메서드(onCreateView() 도 onCreate()처럼 최초에 한 번 호출된다.)
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //인자로 전달되는 레이아웃 전개자 객체를 이용해서 view 객체를 만들어서
        View view = inflater.inflate(R.layout.fragment_my, container);
        //TextView 의 참조값 얻어오기(위에 view에서 이미 있으므로 view 에서 가져온다)
        textView = view.findViewById(R.id.textView);
        //TextView 에 터치 리스너 등록하기
        textView.setOnTouchListener(this);

        //해당 프레그먼트를 관리하는 액티비티의 참조값(MainActivity로 캐스팅 해야만 showMessage 메서드를 사용할 수 있다.
        //하지만 이렇게 하면 이 프레그먼트는 MainActivity에 의존관계가 생긴다. 그렇게 되면 SubActivity에서 사용할 수가 없다. (오류가 생김) 재사용 불가)
        activity=(MainActivity)getActivity();



        //리턴해 주어야 한다.
        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //1. 터치 카운트를 1 증가 시킨다.
        touchCount++;
        //2. TextView 에 출력하기
        textView.setText(Integer.toString(touchCount));
        //3. 액티비티의 메서드 호출하면서 카운트 전달하기
        if(touchCount % 10 == 0){
            activity.showMessagee(touchCount);
        }
        return false;
    }
}
