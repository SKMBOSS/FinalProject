package n20123404.leejungsu.everydaylifelogger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ownList extends AppCompatActivity {

    String timeO="";
    String whereO="";
    String memoO="";

    Double latO=0.0;
    Double lngO=0.0;

    TextView timeViewOwn;
    TextView whereViewOwn;
    EditText writeTextOwn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_list);

        Intent intent3 = getIntent();
        timeO =intent3.getStringExtra("TIME");
        whereO =intent3.getStringExtra("LOCATE");
        memoO = intent3.getStringExtra("MEMO");
        latO = intent3.getDoubleExtra("LAT",0);
        lngO = intent3.getDoubleExtra("LNG",0);



        timeViewOwn= (TextView)findViewById(R.id.tv_timeOwn);
        timeViewOwn.setText(timeO);

        whereViewOwn= (TextView)findViewById(R.id.tv_whereOwn);
        whereViewOwn.setText(whereO);

        writeTextOwn = (EditText)findViewById(R.id.et_textOwn);
        writeTextOwn.setText(memoO);


    }

    public void myClickHandler(View v) {

        switch (v.getId()) {
            case R.id.tv_whereOwn:
                Intent own_intent = new Intent(getApplicationContext(),pastWhere.class);
                own_intent.putExtra("LAT2",latO); //위도 전달
                own_intent.putExtra("LNG2",lngO);//경도 전달
                own_intent.putExtra("LOCATE2",whereO);//정보전달

                startActivity(own_intent);
                break;

        }
    }
}
