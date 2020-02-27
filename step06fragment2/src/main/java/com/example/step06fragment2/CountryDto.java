package com.example.step06fragment2;

import java.io.Serializable;

/*
        Intent 객체에 putExtra() 해서 담을 수 있도록
        Serializable 인터페이스를 구현 시킨다.
 */
public class CountryDto implements Serializable {
    //필드
    private int resId;  //정수값으로 저장된 이미지를 담을 필드
    private String name;    //나라 이름
    private String content; //나라 설명
    //생성자
    public CountryDto(){}

    public CountryDto(int resId, String name, String content) {
        this.resId = resId;
        this.name = name;
        this.content = content;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
