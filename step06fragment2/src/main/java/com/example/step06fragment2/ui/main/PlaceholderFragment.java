package com.example.step06fragment2.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.step06fragment2.CountryDto;
import com.example.step06fragment2.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment { //팩토리 스타일: 프레그먼트를 만들어서 보내는 방식.
    //Fragment 객체를 생성해서 리턴해주는 static 메서드
    public static PlaceholderFragment newInstance(CountryDto dto) {  // index 번째의 fragment를 가져가는 메서드
        PlaceholderFragment fr=new PlaceholderFragment();
        //Fragment 에 전달할 Bundle 객체
        Bundle bundle=new Bundle();
        bundle.putSerializable("dto", dto);  //번들에 key:value 를 담아서
        //Fragment 에 인자 전달하기
        fr.setArguments(bundle);    //onCreate() 메서드에 번들이 전달된다.
        return fr;    //ViewPager 에서 사용한다.
    }

    //이미지 리소스 아이디를 담을 필드
    private CountryDto dto;

    //1. 프레그먼트가 최초 사용될 때 호출되는 메서드(프레그먼트 객체 마다 딱 한 번)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //인자로 Bundle 객체가 전달된다.(위의 PlaceholderFragment 에서 setArguments()에서 전달된 Bundle)
        dto=(CountryDto)getArguments().getSerializable("dto");
    }
    //2. 프레그먼트가 활성화 될 때마다 호출되는 메서드(onCreate 후에 계속 호출되는 메서드)
    @Override
    public View onCreateView(       //프레그먼트의 화면을 만들어서 리턴해준다. UI를 담당. layout 이 필요하다.
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // res/layout/fragment_main.xml 문서를 전개해서 View 객체를 만든다.
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        // 이미지 뷰의 참조값 얻어와서( 위에 inflater로 만든 view에 imageView가 있으므로 view 에서 가져온다)
        ImageView imageView=view.findViewById(R.id.imageView);
        // 이미지 출력하기
        imageView.setImageResource(dto.getResId());

        TextView textView=view.findViewById(R.id.textView);
        textView.setText(dto.getContent());
        // View 객체 리턴해주기
        return view;
    }
}
/*
    프레그먼트도 재활용이 된다.
    페이지 개숫만큼 다 만든다.
    하지만 앞으로 막 넘기다 뒤로 다시 넘길때 재활용하여 사용하는 것이다.
*/

