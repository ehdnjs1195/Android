package com.gura.step11contentresolver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private Context context;
    private int layoutRes;
    private List<ContactDto> list;
    private LayoutInflater inflater;

    //생성자
    public ContactAdapter(Context context, int layoutRes, List<ContactDto> list){
        //필드에 필요한 값과 참조값을 넣어준다.
        this.context=context;
        this.layoutRes=layoutRes;
        this.list=list;
        //레이아웃 전개자 객체
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        //i번째 모델 아이디
        return list.get(position).getId();
    }

    @Override   //getView() 는 ListView 가 호출한다. ListView 에 목록을 출력하기 위해 Adapter로 부터 cellView를 가져오는데
    public View getView(int i, View view, ViewGroup viewGroup) {    //getView() 는 cell view 를 만들어서 리턴해준다.
        if(view==null){ //만일 view 가 null 이면
            //레이아웃을 전개해서 cell View 객체를 만든다.     =>필요한 만큼 만들어지면 view에 참조값이 전달 되는것이다.
            view=inflater.inflate(layoutRes, viewGroup,false);
        }
        //cell View 에 ContactDto 에 있는 정보를 출력하고
        ContactDto dto=list.get(i);

        TextView text_id=view.findViewById(R.id.text_id);
        TextView text_phone=view.findViewById(R.id.text_phone);
        TextView text_name=view.findViewById(R.id.text_name);

        text_id.setText(Integer.toString(dto.getId()));
        text_phone.setText(dto.getPhone());
        text_name.setText(dto.getName());

        //cell View 를 리턴해준다.
        return view;
    }
}
