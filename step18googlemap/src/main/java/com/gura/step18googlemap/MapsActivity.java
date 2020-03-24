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
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);        // 구글맵과 관련된 프레그먼트를 가져와서
        mapFragment.getMapAsync(this);  //리스너를 등록한다.

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
