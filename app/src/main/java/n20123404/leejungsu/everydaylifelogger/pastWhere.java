package n20123404.leejungsu.everydaylifelogger;


import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


public class pastWhere extends AppCompatActivity {
    Double latP = 0.0;
    Double lngP = 0.0;
    String whereP="";

    LatLng PASTAREA = new LatLng(37.6264872, 126.8426235);
    private GoogleMap map2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_where);

        Intent intent4 = getIntent();

        latP = intent4.getDoubleExtra("LAT2", 0);
        lngP = intent4.getDoubleExtra("LNG2", 0);
        whereP = intent4.getStringExtra("LOCATE2");

        PASTAREA = new LatLng(latP,lngP);

        map2 = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        Marker seoul = map2.addMarker(new MarkerOptions().position(PASTAREA)
                .title(whereP));

        map2.moveCamera(CameraUpdateFactory.newLatLngZoom(PASTAREA, 15));

        map2.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

}

