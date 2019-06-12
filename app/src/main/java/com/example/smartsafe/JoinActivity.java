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

    dbhelper openHelper;
    SQLiteDatabase db;
    EditText id, pw, phone, email,salt;
    Button joinBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        openHelper = new dbhelper(this);
        db = openHelper.getWritableDatabase();
        id = findViewById(R.id.editname);
        pw = findViewById(R.id.editpwd);
        phone = findViewById(R.id.editphone);
        email =  findViewById(R.id.editemail);
        joinBtn = findViewById(R.id.로그아웃);
        joinBtn.setOnClickListener(listener);

    }

    //디비작업
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String pwd=null;
            switch (v.getId()) {
                case R.id.로그아웃:
                    String id2 = id.getText().toString();
                    pwd = pw.getText().toString();
                    String phone2 = phone.getText().toString();
                    String email2 = email.getText().toString();



                    String sql = "select * from Member where name = '" + id2 + "'";
                    Cursor cursor = db.rawQuery(sql, null);
                    String a = "select * from Member where name = '" + pw + "'";
                    db.rawQuery(a,null);
                    String salt = SHA256Util.generateSalt();
                    String password=a;

                    String newpassword = SHA256Util.getEncrypt(password,salt);
                    if (cursor.getCount() > 0) {
                        Toast.makeText(JoinActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(JoinActivity.this, JoinActivity.class));
                        finish();
                    } else {
                        if (pwd.toString().length() >= 8){
                        String sql2 = "insert into Member(name, pw, pw2, mobile, email,salt) values('" + id2 + "','" + pwd + "','"+newpassword+"','" + phone2 + "','" + email2 + "','"+salt+"')";
                        db.execSQL(sql2);
                        Toast.makeText(JoinActivity.this, "회원가입을 축하합니다.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(JoinActivity.this, MainActivity.class));}
                    }
                    cursor.close();
                    break;

            }
            if (pwd.toString().length() < 8) {
                Toast.makeText(JoinActivity.this, "비밀번호 8자리이상 입력하세요", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(JoinActivity.this, JoinActivity.class));

            }

        }
        };


    public void onClickMain(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

}


