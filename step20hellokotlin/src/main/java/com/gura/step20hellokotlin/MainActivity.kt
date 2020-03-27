package com.gura.step20hellokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {    // 물음표는 null 값도 허용한다는 의미
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
