package com.gura.step11contentresolver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    //필요한 필드 정의하기
    private EditText editText;
    private ListView listView;
    private ContactAdapter adapter;
    public List<ContactDto> list=new ArrayList<>();
    //클릭한 셀의 인덱스 값을 저장할 필드
    private int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.editText);
        listView=findViewById(R.id.listView);
        adapter=new ContactAdapter(this, R.layout.listview_cell, list);
        listView.setAdapter(adapter);

        //버튼의 참조값 얻어와서 리스너 등록하기
        Button findBtn=findViewById(R.id.findBtn);
        findBtn.setOnClickListener(this);

        //ListView 에 아이템 클릭 리스너 등록하기
        listView.setOnItemClickListener(this);
    }

    //연락처 정보를 가져오는 메서드
    public void getContacts() {
        //출력한 데이터를 한 번 clear 하고 읽어오도록
        list.clear();

        //ContentResolver 객체의 참조값 얻어오기
        ContentResolver resolver = getContentResolver();
        //연락처 정보의 Uri의 참조값 얻어오기 (상수 객체로 미리 정의되어 있음)
        Uri contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;    //관점: 이게 테이블 명이라고 보면 된다. 테이블로부터 Uri를 가져오겠다.
        //원하는 칼럼명 (아이디(primary key가 존재한다.), 전화번호, 이름, 이메일)
        String[] columns={  //테이블은 이미 만들어져있고 이를 통해 칼럼명은 상수로 이미 정의되어 있다고 추론할 수 있다.
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        };

        //키워드 읽어오기
        String keyword=editText.getText().toString();
        //where 절에 들어갈 조건
        String where= ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" LIKE ?";
        // ? 에 바인딩할 인자
        String[] args={"%"+keyword+"%"};

        //정렬 (이름에 대해서 오름차순)
        String order=ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC";
        //원하는 정보를 SELECT 하고 결과값을 Cursor type 으로 받는다.
        // .query(uri, columns, where, args, order)  => uri(테이블명), 셀렉할 칼럼명, 조건절(ex. "name LIKE ?" or "num >= ? AND num <= ?"), 조건절에 들어가는 바인딩 인자(ex. {"%"+kim+"%"} or {"10", "20"} String배열), 정렬
        Cursor cursor=resolver.query(contactUri, columns, where, args, order);

        //Cursor 객체에서 반복문 돌면서 데이터 추출하기  => JDBC의 resultSet과 비슷하다. 셀렉한 결과는 Cursor 객체에 담겨있다.
        while(cursor.moveToNext()){
            int id=(int)cursor.getLong(0);  //id는 long 기본적으로 롱타입으로 리턴되기 때문에
            String phoneNumber=cursor.getString(1);
            String name=cursor.getString(2);
            //연락처 정보를 ContactDto 에 담기
            ContactDto dto=new ContactDto(id, phoneNumber, name);
            //모델에 추가한다.
            list.add(dto);
        }
        //ListView 가 갱신되도록 아답타에 알린다.
        adapter.notifyDataSetChanged();
    }

    //리스너를 등록해놨기 때문에 버튼을 클릭하면 실행순서는 여기로 들어온다.
    @Override
    public void onClick(View v) {
        int permissionCheck=
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CONTACTS);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){ //권한이 없다면
            //권한을 얻어야하는 퍼미션 목록
            String[] permissions={Manifest.permission.READ_CONTACTS};
            //권한을 얻어내도록 유도한다.   (onRequestPermissionResult() 메서드)
            ActivityCompat.requestPermissions(this,
                    permissions,
                    Constants.REQUEST_CONTACTS);
        }else{//권한이 있다면
            getContacts();
        }
    }

    //퍼미션을 체크하거나 취소 했을때 호출되는 메서드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case Constants.REQUEST_CONTACTS: // 0 번 요청 코드인 경우(주소록)
                //퍼미션을 Allow 했을경우
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getContacts();
                }else{// Allow 하지 않았을 경우
                    Toast.makeText(this, "퍼미션을 체크해 주세요",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case Constants.REQUEST_CALL: // 1 번 요청 코드인 경우(전화)
                //퍼미션을 Allow 했을경우
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    call();
                }else{// Allow 하지 않았을 경우
                    Toast.makeText(this, "퍼미션을 체크해 주세요",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    //ListView 의 cell 을 클릭하면 호출되는 메서드
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
        //선택한 인덱스 값을 필드에 저장
        selectedIndex=i;

        //전화 권한이 있는지 체크
        int permissionCheck=
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){ //권한이 없다면
            //권한을 얻어야하는 퍼미션 목록
            String[] permissions={Manifest.permission.CALL_PHONE};
            //권한을 얻어내도록 유도한다.   (onRequestPermissionResult() 메서드)
            ActivityCompat.requestPermissions(this,
                    permissions,
                    Constants.REQUEST_CALL);
        }else{//권한이 있다면
            call();
        }
    }

    public void call(){
        //아답타로 부터 i 번째 아이템을 얻어온다.
        ContactDto dto=(ContactDto)adapter.getItem(selectedIndex);
        //전화 번호를 얻어낸다.
        String phone=dto.getPhone();
        //전화를 걸겠다는 Intent 객체를 생성한다.
        Intent intent=new Intent();
        /*
            - Intent.ACTION_DIAL 은 통화 버튼을 눌러야지 전화가 걸리므로
            permission 이 필요가 없다.
            - Intent.ACTION_CALL 은 바로 전화가 걸리기 때문에
            permission 을 얻어낼 필요가 있다.
         */
        intent.setAction(Intent.ACTION_CALL);   //바로 전화를 걸게 하면 permission이 필요하다. 왜냐하면 이 app에서 바로 전화를 거는 것이므로 거기서 발생하는 비용에 대한 책임은 이 app에 있기 때문이다. 전화번호를 넘기면 전화 app에 비용 발생 책임이 넘어가므로 다른 허가가 필요한 점이 아니다.
        //전화번호를 Uri 로 전달한다.
        Uri uri=Uri.parse("tel: "+phone);   //Uri 표준 형식으로 포장하기.
        intent.setData(uri);

        //해당 인텐트를 받아줄 수 있는 액티비티를 찾는다.
        startActivity(intent);
    }
}
