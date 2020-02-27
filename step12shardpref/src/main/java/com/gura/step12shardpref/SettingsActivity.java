package com.gura.step12shardpref;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //res/layout/settings_activity.xml 문서를 전개해서 화면 구성하기
        setContentView(R.layout.settings_activity);
        /*
            id 가 settings 인 레이아웃(FrameLayout) 에
            SettingsFragment 로 화면 구성하게 하기
         */
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();      //프레임 레이아웃에(settings) SettingsFragment() 를 출력해달라.  프레임레이아웃에 프레그먼트를 표시할 수 있다. 그 프레그먼트를 상황에 따라 바꿀 수 있다.

        // 좌상단 up 버튼 동작을 가능하도록 한다.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);    //SharedPreference의 참조값 얻어오기.
    }

    //SharedPreferences 에 저장된 값이 바뀌면 호출되는 메서드
    @Override
    public void onSharedPreferenceChanged(SharedPreferences pref, String key) {
        // key: 바뀐 설정의 key 값이 전달된다.
        if(key.equals("signature")) {
            String signature = pref.getString(key, "");
            Toast.makeText(this, signature, Toast.LENGTH_LONG).show();
        }
    }


    //FrameLayout 을 채울 프레그먼트
    public static class SettingsFragment extends PreferenceFragmentCompat {
        //프레그먼트가 처음 활성화 될 때 호출되는 메서드
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            // res/xml/root_preferences.xml 문서를 전개해서 프레그먼트 UI 구성하기
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    //액티비티가 활성화 되기 바로 직전에 호출되는 메서드(Activity 생명주기를 보고 이해할 것)
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
        //SharedPreferences 가 바뀌었는지 감시할 리스너 등록하기
        pref.registerOnSharedPreferenceChangeListener(this);
    }
    //액티비티가 비활성화 직후 바로 호출되는 메서드
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
        //SharedPreferences 가 바뀌었는지 감시할 리스너 등록 해제   ex) 새로운 Activity 를 열었을 때 원래 보던 Activity의 리스너를 해제하는 것이다. 이상한 동작을 방지하기 위해.
        pref.unregisterOnSharedPreferenceChangeListener(this);
    }
}