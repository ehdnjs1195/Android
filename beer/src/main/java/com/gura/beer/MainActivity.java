package com.gura.beer;

import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //BeerView 객체 생성
        BeerView view=new BeerView(this);
        setContentView(view);

    }
}
