package com.example.step17sqlitedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/*
    DB 생성 및 업그레이드를 도와주는 도우미 클래스 만들기
    - SQLiteOpenHelper 추상 클래스를 상속 받아서 만든다.

    - 운영체제에 설치되어 있는 sqlite를 이용한다. (DB)
    - mainActivity 에서 객체를 생성하여 사용. xxx.sqlite file 이 만들어진다.
    - sqlite file => SQLite app 를 열어준다.

    - factory 는 인스톨 하자마자 어떤 데이터를 사용하고자 할 때.

 */
public class DBHelper extends SQLiteOpenHelper {
    // 생성자
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);

    }
    // DB 가 새로 초기화 될 때 호출되는 메서드
    @Override
    public void onCreate(SQLiteDatabase db) {
        //todo 라는 이름의 테이블 만들기
        String sql = "CREATE TABLE todo" +
                "(num INTEGER PRIMARY KEY AUTOINCREMENT," +     //AUTOINCREAMENT 가 sequence 의 역할을 대신한다.
                "content TEXT, regdate TEXT)";
        db.execSQL(sql);
    }
    // DB 테이블의 구조를 바꾸거나 전체 혹은 부분 업데이트를 할 때 호출되는 메서드 ( 버전정보가 바뀔 때 호출된다. version 2, 3 등으로)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //todo 테이블 삭제하고 다시 만들어 질 수 있도록
        db.execSQL("DROP TABLE IF EXISTS todo");
        onCreate(db);
    }
}
