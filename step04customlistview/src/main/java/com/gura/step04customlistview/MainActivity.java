package com.gura.step04customlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    //필드 정의하기
    ListView listView;
    List<CountryDto> countries;
    CountryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listView);
        countries=new ArrayList<>();
        countries.add(new CountryDto(R.drawable.korea,"대한한국","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.austria,"오스트리아","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.belgium,"벨기에","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.brazil,"브라질","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.france,"프랑스","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.germany,"독일","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.greece,"그리스","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.israel,"이스라엘","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.italy,"이탈리아","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.japan,"쪽바리","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.poland,"폴란드","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.spain,"스페인","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.usa,"미국","어쩌구 저쩌구"));

        //어댑터 객체 생성
        adapter=new CountryAdapter(this, R.layout.listview_cell, countries);
        //어댑터를 ListView 에 연결하기
        listView.setAdapter(adapter);
        //ListView 에 아이템 클릭 리스너 등록하기
        listView.setOnItemClickListener(this);
    }

    /*
        어떤 Activity로 이동할 땐 intent 객체가 필요한데 이 객체안에 정보도 함께 담아서 전달할 수 있다.
        클릭했을 때 그 Dto를 넘겨야 함.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
        //Intent 객체 생성하기
        Intent intent=new Intent(this, DetailActivity.class);
        //클릭한 아이템에 해당하는 국가 정보를 얻어온다.
        CountryDto dto=countries.get(i);
        //국가정보를 Intent 객체에 "dto" 라는 키값으로 담고 싶다!면?! Dto를 serializable type(인터페이스)을 구현시켜서 타입을 맞춘다. Extra 메서드중에는 serializable 타입을 받는게 있으니까!
        intent.putExtra("dto", dto);
        //액티비티 이동
        startActivity(intent);
    }
}
