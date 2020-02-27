package com.example.step06fragment2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.step06fragment2.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //ViewPager 에서 사용할 모델
    List<CountryDto> countries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //레이아웃 구성하기
        setContentView(R.layout.activity_main); //부모 메서드로 ViewPager를 가져온다. ViewPager: 프레그먼트를 슬라이더로 한개씩 가져오는 것.

        countries=new ArrayList<>();
        countries.add(new CountryDto(R.drawable.korea,"대한한국","위대한 대한민국😍"));
        countries.add(new CountryDto(R.drawable.austria,"오스트리아","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.belgium,"벨기에","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.brazil,"브라질","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.france,"프랑스","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.germany,"독일","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.greece,"그리스","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.israel,"이스라엘","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.italy,"이탈리아","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.japan,"쪽바리","일본놈 이므니다!"));
        countries.add(new CountryDto(R.drawable.poland,"폴란드","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.spain,"스페인","어쩌구 저쩌구"));
        countries.add(new CountryDto(R.drawable.usa,"미국","어쩌구 저쩌구"));

        //Pager 어댑터 객체 생성하기 => ViewPager에 프레그먼트를 공급해주는 어댑터
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(countries, getSupportFragmentManager());
        //ViewPager 객체의 참조값 얻어오기
        ViewPager viewPager = findViewById(R.id.view_pager);    //부모 메서드 findViewById()
        //ViewPager 에 PagerAdapter 객체 연결하기
        viewPager.setAdapter(sectionsPagerAdapter); //ViewPager에 프레그먼트를 공급하는 역할을 하는 어댑터 sectionsPagerAdapter
        //Tab 레이아웃 객체의 참조값 얻어오기
        TabLayout tabs = findViewById(R.id.tabs);
        //Tab 과 ViewPager 를 함께 연계해서 쓰도록 설정
        tabs.setupWithViewPager(viewPager);


        // 우하단에 떠있는 버튼
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}