package com.gura.step04customlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/*
    [ ListView 에 연결할 어댑터 클래스 정의하기 ]

    - BaseAdapter 추상 클래스를 상속 받아서 만든다.
*/
public class CountryAdapter extends BaseAdapter {
    //필요한 필드 정의하기
    private Context context;
    private int layoutRes;
    private List<CountryDto> list;
    private LayoutInflater inflater;    //레이아웃 전개자 객체

    //생성자
    public CountryAdapter(Context context, int layoutRes, List<CountryDto> list){
        this.context=context;   //레이아웃 전개자 객체를 얻어내기 위해
        this.layoutRes=layoutRes;
        this.list=list;
        /*
            [ 레이아웃 전개자 객체 ]
            xml 로 정의한 레이아웃 정보를 실제로 전개해서 View 객체로 만들어 주는 객체
            화면 설계를 바탕으로 실제 View객체를 만들어 내는것.
         */
        inflater=LayoutInflater.from(context);  //LayoutInflater 클래스에 static from() 메서드를 이용해서 레이아웃 전개자 객체를 리턴해준다.
    }


    //전체 모델의 갯수를 리턴해준다.
    @Override
    public int getCount() {
        return list.size();
    }
    // i 인덱스에 해당하는 아이템(data) 를 리턴해준다.
    @Override
    public Object getItem(int i) {
        return list.get(i); // i번째 해당되는 아이템 리턴.
    }
    // i 인덱스에 해당하는 아이템의 아이디가 있으면 리턴해준다.
    @Override
    public long getItemId(int i) {
        return i;   //지금은 아이디가 없으므로 index를 ID로 리턴해준다.
    }

    /*
        i 번째 인덱스에 맞는 view를 만들어서 리턴해주어야 한다(여기서는 국가정보 등) => 화면에 나오는 만큼 호출됨.
        예) 3개의 국가가 보이면 3번 호출된것. 그 밑으로 아직 보이지 않는 셀은 아직 생성하지 않은 상태이다.

        그 이후 화면에서 필요한 셀을 하나 더 만드는데 이때 또 호출되고, 반대로 화면에서 없어지는 셀은 재활용 되어 내용만 바뀌는 셈이된다.
        view 객체는 결국 화면에서 4개만 필요하게 되기 때문에 그 이상부터는 null이 아니다.
        계속 재활용 하면서 사용함

        결론=> 처음 화면에 보이는 것. i가 0, 1, 2 일때 3개이고 화면을 조금 내렸을 때 다음 셀, i가 4일때이다.
        그 이후로는 화면에서 없어지는 셀을 재활용하여 사용하므로 view 객체는 4개만 만들어진다.
        => 최대로 보이는 셀의 갯수만큼 생성.(화면에 보이는 최소한의 것만)
     */

    // i 인덱스에 해당하는 셀 View 를 리턴해준다.(화면에 출력할 View 객체를 받아가는 메서드 , 0번셀의 뷰, 1번셀의 뷰, 2번셀의 뷰 ...)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //처음에는 인자로 선언된 지역변수 view 에 null 이 들어있다.
        if(view == null){
            //레이아웃 전개자 객체를 이용해서 view 객체를 만든다.
            view=inflater.inflate(layoutRes, viewGroup, false);
        }                   //layuotRes => R.layout.listview_cell 즉, 정수값이 들어있다.
        //View 에서 원하는 UI 의 참조값을 얻어낸다.
        ImageView imageView=view.findViewById(R.id.imageView);  // 그냥 findViewById 는 Activity에서 찾는것임. 헷갈리지 마라.
        TextView textView=view.findViewById(R.id.textView);
        //i 번째 인덱스에 해당하는 데이터를 얻어온다.
        CountryDto dto=list.get(i);
        //View 에 데이터를 출력한다.
        imageView.setImageResource(dto.getResId());
        textView.setText(dto.getName());
        //구성된 View 객체를 리턴해준다.
        return view;
    }   //이 getView 라는 메서드는 나중에 ListView가 호출할 예정임. 우리가 직접하지 않는다.
}
