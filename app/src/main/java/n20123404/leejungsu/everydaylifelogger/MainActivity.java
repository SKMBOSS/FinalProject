package n20123404.leejungsu.everydaylifelogger;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private GoogleMap map;
    static final LatLng MYHOME = new LatLng(37.6264872, 126.8426235);
    Double curLat = null;
    Double curLng = null;
    String localNmae = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);


        map = mapFragment.getMap();


        //현재 위치로 가는 버튼 표시
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(MYHOME, 15));//초기 위치는 우리집으로

        MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {

            @Override
            public void gotLocation(Location location) {

                String msg = "현재위치 찾는중....";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                drawMarker(location);
            }
        };


        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(getApplicationContext(), locationResult);

    }

    /** 위도와 경도 기반으로 주소를 리턴하는 메서드*/
    public String getAddress(double lat, double lng){
        String address = null;

        //위치정보를 활용하기 위한 구글 API 객체
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        //주소 목록을 담기 위한 HashMap
        List<Address> list = null;

        try{
            list = geocoder.getFromLocation(lat, lng, 1);
        } catch(Exception e){
            e.printStackTrace();
        }

        if(list == null){
            Log.e("getAddress", "주소 데이터 얻기 실패");
            return null;
        }

        if(list.size() > 0){
            Address addr = list.get(0);
            address = addr.getAdminArea() + " " //도
                    + addr.getLocality() + " " //시
                    + addr.getSubLocality() +" " //구
                    + addr.getThoroughfare() + " "//동
                    + addr.getFeatureName(); //지번
        }
        localNmae = address;
        return address;

    }


    private void drawMarker(Location location) {

        curLat =location.getLatitude();
        curLng =location.getLongitude();

        //기존 마커 지우기
        map.clear();
        LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

        //currentPosition 위치로 카메라 중심을 옮기고 화면 줌을 조정한다. 줌범위는 2~21, 숫자클수록 확대
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 17));
        map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);


        //마커 추가
        map.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet(getAddress(location.getLatitude(),location.getLongitude()))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("현재위치"));
    }


    public void myClickHandler(View v) {

        switch (v.getId()){
            case R.id.what_bt: //쓰기버튼

                Intent what_intent = new Intent(getApplicationContext(),write.class);
                what_intent.putExtra("lat",curLat); //위도 전달
                what_intent.putExtra("lng",curLng);//경도 전달
                what_intent.putExtra("LN",localNmae); //주소전달
                startActivity(what_intent);
                break;

            case R.id.search_bt: //보기버튼
                Intent search_intent = new Intent(getApplicationContext(),list.class);
                startActivity(search_intent);
                break;


        }
    }
}

