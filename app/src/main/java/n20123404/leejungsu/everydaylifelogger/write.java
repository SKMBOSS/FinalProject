package n20123404.leejungsu.everydaylifelogger;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class write extends AppCompatActivity {

    SQLiteDatabase db;
    String dbName = "MYDataBase12.db"; // name of Database;
    String tableName = "LJSTable12"; // name of Table;
    int dbMode = Context.MODE_PRIVATE;

    Double latw=0.0;
    Double lngw=0.0;
    String localNameW="";
    String time="";
    String memo="";

    TextView timeView;
    TextView whereView;
    EditText writeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        // Database 생성 및 열기
        db = openOrCreateDatabase(dbName, dbMode, null);
        // 테이블 생성
        createTable();

        Intent intent = getIntent();
         latw = intent.getDoubleExtra("lat", 0);
         lngw = intent.getDoubleExtra("lng", 0);
         localNameW = intent.getStringExtra("LN");

        timeView = (TextView)findViewById(R.id.tv_timeOwn);
        time = getDayOfWeek();
        timeView.setText(time);

        whereView = (TextView)findViewById(R.id.tv_whereOwn);
        whereView.setText(localNameW);
    }

    //요일

    public static String getDayOfWeek() {

        String time="";
        String dayOfWeek = "";

        Calendar now = Calendar.getInstance();

        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH)+1;
        int date = now.get(Calendar.DATE);
        int hour = now.get(Calendar.HOUR);
        int min = now.get(Calendar.MINUTE);
        int day_of_week = now.get(Calendar.DAY_OF_WEEK);

        switch (day_of_week) {
            case 1:
                dayOfWeek = "일요일";
                break;
            case 2:
                dayOfWeek = "월요일";
                break;
            case 3:
                dayOfWeek = "화요일";
                break;
            case 4:
                dayOfWeek = "수요일";
                break;
            case 5:
                dayOfWeek = "목요일";
                break;
            case 6:
                dayOfWeek = "금요일";
                break;
            case 7:
                dayOfWeek = "토요일";
                break;
        }

        time=year +"년 "+ month +"월 "+ date + "일 " + hour +"시 " + min +"분" + dayOfWeek;
        return time;
    }

    public void myClickHandler(View v) {

        switch (v.getId()){
            case R.id.complete_button: //쓰기버튼

               writeText = (EditText)findViewById(R.id.et_text);
               memo = writeText.getText().toString();

                insertData(time,localNameW,memo,latw,lngw);

                Intent write_intent = new Intent(getApplicationContext(),list.class);
                finish();
                startActivity(write_intent);
                break;


        }
    }

    //테이블생성
    public void createTable() {
        try {
            db.execSQL("create table " + tableName + "(id integer primary key autoincrement," + "time textW, locationW text, memoW text, latW real, lngW real);");
        } catch (android.database.sqlite.SQLiteException e) {
            Log.d("Lab sqlite", "error: " + e);
        }
    }

    // Data 추가
    public void insertData(String timeW, String locationW, String memoW, Double latW, Double lngW) {

        String sql = "insert into " + tableName + " values(NULL,'" +timeW+"','"+locationW+"','"+memoW+"','"+latW+"','"+lngW+"');";
        db.execSQL(sql);
    }
}
