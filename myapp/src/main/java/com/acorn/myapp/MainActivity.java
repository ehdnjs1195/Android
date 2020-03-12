package com.acorn.myapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
                                                            AdapterView.OnItemLongClickListener {
    ArrayAdapter<String> adapter;
    List<String> names;
    int selectedIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView=findViewById(R.id.listView);
        names=new ArrayList<>();
        names.add("김구라");
        names.add("해골");
        names.add("원숭이");
        names.add("주뎅이");
        names.add("덩어리");
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, names.get(position), Toast.LENGTH_SHORT).show();
    }


    DialogInterface.OnClickListener listener= new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    names.remove(selectedIndex);
                    adapter.notifyDataSetChanged();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        selectedIndex=position;
        new AlertDialog.Builder(this).setTitle("알림").setMessage(names.get(position)+" 를 삭제 하시겠습니까?")
                .setPositiveButton("네", listener).setNegativeButton("아니오", null).create().show();
        return false;
    }
}
