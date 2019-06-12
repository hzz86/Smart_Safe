package com.example.smartsafe;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    dbhelper openHelper;
    EditText id, pw;
    SQLiteDatabase db;
    Button loginBtn, joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1) ;
        imageView1.setImageResource(R.drawable.mainicon) ;

        openHelper = new dbhelper(this);
        db = openHelper.getWritableDatabase();
        id = findViewById(R.id.edit_id);
        pw =  findViewById(R.id.edit_pw);
        loginBtn = findViewById(R.id.loginBtn);
        joinBtn = findViewById(R.id.joinBtn);
        loginBtn.setOnClickListener(listener);
        joinBtn.setOnClickListener(listener);
    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Log.e("YHJ","" + "checking.........................");
            switch (v.getId()) {
                case R.id.joinBtn:
                    startActivity(new Intent(MainActivity.this, JoinActivity.class));
                    finish();
                    break;
                case R.id.loginBtn:
                    String Id = id.getText().toString();
                    String pwd = pw.getText().toString();

                    String sql = "select * from Member where name = '"+Id+"' and pw = '"+pwd+"'";
                    Cursor cursor = db.rawQuery(sql, null);

                    Log.e("YHJ","" + "Value :  " + cursor.getCount());

                    while (cursor.moveToNext()) {
                        String no = cursor.getString(0);
                        String rest_id = cursor.getString(1);
                        Log.d("select ", "no : " + no + "\nrest_id : " + rest_id);
                    }

                    if(cursor.getCount() == 1) {
                        // 해당 아이디와 비번이 있으면 1개의 row를 가져옴
                        Toast.makeText(MainActivity.this, Id+ "님 환영합니다", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(MainActivity.this, MainActivity.class);
                        intent2.putExtra("name", Id);
                        intent2.putExtra("pw", pwd);
                        startActivity(new Intent(MainActivity.this, Home.class));
                        finish();
                    } else {
                        // 없다면 아무 값도 가져오지 않으므로 count 가 0
                        Toast.makeText(MainActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                    break;
            }
        }
    };


}

