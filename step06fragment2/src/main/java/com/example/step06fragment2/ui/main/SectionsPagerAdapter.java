package com.example.step06fragment2.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.step06fragment2.CountryDto;
import com.example.step06fragment2.R;

import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    //ViewPager 에 출력할 모델
    private List<CountryDto> countries;
    //생성자
    public SectionsPagerAdapter(List<CountryDto> countries, FragmentManager fm) {
        super(fm);
        this.countries=countries;
    }

    @Override   //ViewPager가 호출하는 메서드. 사용자가 페이지를 넘길때. 단, 한 번 받아간 것은 버리지 않는다(프레그먼트). 액티비티가 종료될 때 까지 재활용 한다. (앞으로 넘길때 인덱스당 한 번씩 호출되어  생성을 하다가 뒤로 다시 넘길때 재활용되는 방식)
    public Fragment getItem(int position) { //position은 0,1,2,3...  프레그먼트를 리턴해준다.
        //position 인덱스에 해당하는 CountryDto
        CountryDto dto=countries.get(position);
        //position 인덱스에 해당하는 프레그먼트의 참조값 얻어내서
        PlaceholderFragment fr=PlaceholderFragment.newInstance(dto);
        //리턴해주기
        return fr;
    }
    //인자로 전달되는 인덱스에 해당하는 문자열(페이지 제목, tab 제목)을 리턴해주는 메서드
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //국가의 이름을 title로 사용하도록
        String title=countries.get(position).getName();
        return title;
    }
    //전체 페이지의 갯수를 리턴해준다.
    @Override
    public int getCount() {
        // 전체 국가의 갯수 리턴
        return countries.size();
    }
}