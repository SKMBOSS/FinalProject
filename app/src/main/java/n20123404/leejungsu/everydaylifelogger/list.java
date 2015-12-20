package n20123404.leejungsu.everydaylifelogger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class list extends AppCompatActivity {

    // Database 관련 객체들
    SQLiteDatabase db;
    String dbName = "MYDataBase12.db"; // name of Database;
    String tableName = "LJSTable12"; // name of Table;
    int dbMode = Context.MODE_PRIVATE;


    Double latL = 0.0;
    Double lngL = 0.0;
    String timeL = "";
    String localNameL = "";
    String memoL = "";
    Boolean onlyOne = true;

    ListView mList;
    ArrayAdapter<String> baseAdapter;
    ArrayList<String> nameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Database 생성 및 열기
        db = openOrCreateDatabase(dbName, dbMode, null);
        // 테이블 생성
        createTable();


        mList = (ListView) findViewById(R.id.listView);

        // Create listview
        nameList = new ArrayList<String>();
        baseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nameList);
        mList.setAdapter(baseAdapter);


        nameList.clear();
        selectAll();
        baseAdapter.notifyDataSetChanged();


        // ListView의 이벤트 설정
        mList.setOnItemClickListener(new ListViewItemClickListener());

    }

    private class ListViewItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long _id) {
            Intent list_intent = new Intent(getApplicationContext(), ownList.class);

            String sql = "select * from " + tableName + ";";
            Cursor result = db.rawQuery(sql, null);
            result.moveToPosition(position);


            timeL=result.getString(1);
            localNameL=result.getString(2);
            memoL = result.getString(3);
            latL=result.getDouble(4);
            lngL=result.getDouble(5);
            result.close();

            list_intent.putExtra("TIME", timeL); //시간 전달
            list_intent.putExtra("LOCATE", localNameL); //위치 전달
            list_intent.putExtra("MEMO", memoL); //메모 전달
            list_intent.putExtra("LAT", latL);
            list_intent.putExtra("LNG", lngL);

            startActivity(list_intent);
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

    // 모든 Data 읽기
    public void selectAll() {
        String sql = "select * from " + tableName + ";";
        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        while (!results.isAfterLast()) {
            int id = results.getInt(0);
            String name = results.getString(0) + ". " + results.getString(1);
//            Toast.makeText(this, "index= " + id + " name=" + name, Toast.LENGTH_LONG).show();
            Log.d("lab_sqlite", "index= " + id + " name=" + name);

            nameList.add(name);
            results.moveToNext();
        }
        results.close();
    }

    // 모든 Data 읽기
    public void selectAll2() {
        String sql = "select * from " + tableName + ";";
        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        while (!results.isAfterLast()) {
            int id = results.getInt(0);
            String name = results.getString(0) + ". " + results.getString(2);
//            Toast.makeText(this, "index= " + id + " name=" + name, Toast.LENGTH_LONG).show();
            Log.d("lab_sqlite", "index= " + id + " name=" + name);

            nameList.add(name);
            results.moveToNext();
        }
        results.close();
    }


    public void myClickHandler(View v) {

        switch (v.getId()) {
            case R.id.time_button:
                nameList.clear();
                selectAll();
                baseAdapter.notifyDataSetChanged();
                break;

            case R.id.where_button:
                nameList.clear();
                selectAll2();
                baseAdapter.notifyDataSetChanged();
                break;
        }
    }
}
