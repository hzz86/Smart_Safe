package com.example.smartsafe;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {
    //디비작업
    dbhelper openHelper;
    SQLiteDatabase db;
    EditText id, pw, phone, email;
    Button joinBtn;
    //여기까지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //디비작업
        openHelper = new dbhelper(this);
        db = openHelper.getWritableDatabase();
        id = findViewById(R.id.editname);
        pw = findViewById(R.id.editpwd);
        phone = findViewById(R.id.editphone);
        email =  findViewById(R.id.editemail);
        joinBtn = findViewById(R.id.joinokBtn);
        joinBtn.setOnClickListener(listener);
        //여기까지

    }

    //디비작업
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.joinokBtn:
                    String id2 = id.getText().toString();
                    String pwd = pw.getText().toString();
                    String phone2=phone.getText().toString();
                    String email2=email.getText().toString();
                    String sql = "select * from Member where name = '"+id+"'";
                    Cursor cursor = db.rawQuery(sql, null);
                    if(cursor.getCount() == 1) {
                        // 해당 아이디가 있으면 1개의 row를 가져옴
                        Toast.makeText(JoinActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(JoinActivity.this, MainActivity.class));
                        finish();
                    } else {
                        // 없다면 아무 값도 가져오지 않으므로 count 가 0 가져옴
                        String sql2 ="insert into Member(name, pw, mobile, email) values('"+id2+"','"+pwd+"'+'"+phone2+"'+'"+email2+"')";
                        db.execSQL(sql2);
                        Toast.makeText(JoinActivity.this, "회원가입을 축하합니다.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(JoinActivity.this, MainActivity.class));
                    }
                    cursor.close();
                    break;
            }
        }
    };
    //여기까지

    public void onClickMain(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

    }
}
