package com.gura.step12shardpref;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    //필요한 필드
    private EditText editText;
    private Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //EditText 참조값 얻어와서 필드에 저장하기
        editText=findViewById(R.id.editText);
        //Button 의 잠조값 얻어와서 리스너 등록하기
        Button saveBtn=findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
        //Switch 의 참조값을 필드에 저장하기
        sw=findViewById(R.id.switch1);
        //스위치의 체크 상태가 바뀌었음을 감시할 리스너등록
        sw.setOnCheckedChangeListener(this);


        SharedPreferences pref=getSharedPreferences("info", MODE_PRIVATE);
        // .getString( key값, default값 ) 값 읽어오기
        String myName=pref.getString("myName","empty");   // myName 이란 키로 저장되어 있는게 읽어와본다.
        if(myName.equals("empty")){
            return;
        }
        //저장된 이름을 editText 에 출력하기
        editText.setText(myName);

        //스위치 체크 여부 읽어오기
        boolean isChecked=pref.getBoolean("isChecked", false);
        //스위치의 체크 상태 반영하기
        sw.setChecked(isChecked);

        if(!isChecked){
            sw.setText("Off");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //메뉴 전개자 객체를 이용해서 res/menu/menu_main.xml 문서를 전개해서 메뉴를 구성한다.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   //메뉴 아이템을 선택했을 때 실행순서가 들어온다.
        //선택된 메뉴 아이템의 아이디값을 읽어온다.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {   //메뉴바에서 세팅을 누르면 실행순서가 여기로 들어온다.
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.finish) {
            //액티비티 종료 시키기(실제로 있는 메서드, 액티비티를 종료시킨다.)
            finish();
        } else if (id == R.id.start) {

        }
        return true;
    }

    @Override
    public void onClick(View v) {
        //입력한 문자열 읽어오기
        String inputName=editText.getText().toString();
        //Switch 체크 상태 읽어오기
        boolean isChecked=sw.isChecked();

        //SharedPreferences 를 활용해서 저장하기
        SharedPreferences pref=getSharedPreferences("info", MODE_PRIVATE);        // SharedPreference 라이브러리가 info.xml 문서를 만들고 myName 이라는 키값으로 입력한 text를 저장까지 해준다.
        //Editor 객체의 참조값을 얻어와서                                                  // 단점 : 느리다.   하지만, 저장하여 사용하기 간편하다.
        SharedPreferences.Editor editor=pref.edit();
        //myName 이라는 키값으로 문자열 저장히기
        editor.putString("myName", inputName);      //editor 를 이용해서 key,value 를 저장.
        editor.putBoolean("isChecked", isChecked);  //switch 의 상태 저장.
        editor.commit();    //실제 저장되는 시점
        //알림 띄우기
        new AlertDialog.Builder(this).setMessage("저장 했습니다.").setNeutralButton("확인", null).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            sw.setText("On");
        }else{
            sw.setText("Off");
        }
    }
    //Setting 액티비티에서 저장했던 값을 읽어내는 메서드
    public void readSettings(View v ){  //View 만 전달해주면 버튼을 onClick 을 등록해두면 사용할 수 있다.
        //디폴트로 사용하는 SharedPreferences 객체의 참조값 얻어오기
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);    //Activity가 context
        //signature 라는 키값으로 저장된 문자열 읽어오기
        String signature=pref.getString("signature","");
        // reply 라는 키값으로 저장된 문자열 읽어오기
        String reply=pref.getString("reply", "");
        // sync 라는 키값으로 저장된 boolean 값 읽어오기
        boolean sync=pref.getBoolean("sync",false);

        String info="signature: " + signature + " | reply: " + reply + " | sync: " + sync;
        Toast.makeText(this, info, Toast.LENGTH_LONG).show();
    }
}
