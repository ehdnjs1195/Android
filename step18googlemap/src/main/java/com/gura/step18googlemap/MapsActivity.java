package com.gura.step18googlemap;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        // activity_my.xml 에 있는 SupportMapFragment 객체의 참조값 얻어내기
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);        // Activity의 메서드중에 getSupportFragmentManager()를 통해 프레그먼트 관리자 객체를 리턴받고           /findFragmentById의 메서드가 바로 있지 않은 이유는 프레그먼트라는 개념이 안드로이드 개발 이후에 새롭게 만들어 졌기때문에 추가를 해야 해서 그렇다.
                                                    // 구글맵과 관련된 프레그먼트를 가져와서     / 프레그먼트의 참조값을 얻어올 때 주의. (프레그먼트는 뷰가 아니므로)
                                                    // 프레그먼트를 가져올 때 Fragment인 부모 타입으로 리턴된다. 따라서 모든 기능을 다 사용하려면 SupportMapFragment로 캐스팅을 한다.

        //지도가 동작할 준비가 완료되면 사용될 리스너 객체 등록하기
        mapFragment.getMapAsync(this);      //지도를 동작하려면 시간이 좀 걸릴 수 있다. 따라서 this로 onMapReadyCallback을 전달하고 준비를 한다. 그리고 준비가 되면 onMapReady() 메서드를 호출한다.

        Button moveBtn = findViewById(R.id.moveBtn);
        moveBtn.setOnClickListener(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //에이콘 아카데미 위도, 경도 정보를 가지고 있는 LatiLng 객체
        LatLng acorn = new LatLng(37.498773, 127.031604);
        //마커 옵션 객체
        MarkerOptions option = new MarkerOptions();
        option.position(acorn);
        option.title("Acorn Academy");
        //지도상에 마커 표시하기
        mMap.addMarker(option);

        //지정한 위치와 배율로 카메라 이동하기
        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(acorn, 18);
        mMap.animateCamera(cu);
    }

    @Override
    public void onClick(View v) {
        LatLng home = new LatLng(37.259244, 127.138601);
        mMap.addMarker(new MarkerOptions().position(home).title("My Home"));
        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(home, 18);
        mMap.animateCamera(cu);
    }
}
