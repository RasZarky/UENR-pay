package com.example.uenrpay;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class login extends AppCompatActivity {

    DBHelper db;
    EditText Password,Student_id;
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Password = findViewById(R.id.password);
        Student_id = findViewById(R.id.student_id);
        db = new DBHelper(this);
        login = findViewById(R.id.btn_login);


        Cursor viewLogin = db.viewLoggedIn();
        StringBuilder buffer = new StringBuilder();
        if (viewLogin.getCount() == 0) {
            db.insertIntoLog(0);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String password = Password.getText().toString();
                    String student_id = Student_id.getText().toString();

                    if (password.isEmpty() || student_id.isEmpty()) {
                        Snackbar.make(findViewById(android.R.id.content), "All fields are required.", BaseTransientBottomBar.LENGTH_SHORT).show();
                    } else {
                        int x = db.Login(student_id, password);
                        if (x == 1) {
                            db.updateLog(1);
                            Cursor viewIndex = db.viewIndexNumber();
                            if (viewIndex.getCount() == 0) {
                                db.insertIntoIndexNumber(student_id);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                db.updateIndexNumber(student_id);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "Password or ID is wrong", BaseTransientBottomBar.LENGTH_SHORT).show();
                        }
                    }


                }
            });

        } else {
            while (viewLogin.moveToNext()) {
                buffer.append(viewLogin.getInt(0));
            }
            String log = buffer.toString();
            int LOG = Integer.parseInt(log);

            if (LOG != 0) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else {
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = Password.getText().toString();
                        String student_id = Student_id.getText().toString();

                        if (password.isEmpty() || student_id.isEmpty()) {
                            Snackbar.make(findViewById(android.R.id.content), "All fields are required.", BaseTransientBottomBar.LENGTH_SHORT).show();
                        } else {
                            int x = db.Login(student_id, password);
                            if (x == 1) {
                                db.updateLog(1);
                                Cursor viewIndex = db.viewIndexNumber();
                                if (viewIndex.getCount() == 0) {
                                    db.insertIntoIndexNumber(student_id);
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    db.updateIndexNumber(student_id);
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } else {
                                Snackbar.make(findViewById(android.R.id.content), "Password or ID is wrong", BaseTransientBottomBar.LENGTH_SHORT).show();
                            }
                        }


                    }
                });

            }
        }


        Cursor getAll = db.viewData1();
        if (getAll.getCount() == 0) {
            db.insertData("UED2900419", "Abdul Razak", "Abubakari", "1909000000", "2020/2021", "Sciences", "Comp. Sci. And Infomatics", "Dip. Comp. Sci", "2019/2020", "ARADCS", "", 720.00);
            db.insertData("UED2706819", "Clinton", "Amponsah", "1909000000", "2020/2021", "Sciences", "Comp. Sci. And Infomatics", "Diploma IT", "2019/2020", "CADIT", "", 650.00);
            db.insertData("UED2708019", "David", "Amo", "1909000000", "2020/2021", "Sciences", "Comp. Sci. And Infomatics", "Diploma IT", "2019/2020", "DADIT", "", 20.00);
            db.insertData("UED2709219", "Issac", "Ofei Aboagye", "1909000000", "2020/2021", "Sciences", "Comp. Sci. And Infomatics", "Diploma IT", "2019/2020", "IOADIT", "", 0.00);
            db.insertData("UED2903319", "John", "Biney Jnr", "1909000000", "2020/2021", "Sciences", "Comp. Sci. And Infomatics", "Diploma Comp. Sci.", "2019/2020", "JBJDCS", "", 30.00);
            db.insertData("UED2902619", "George", "Boamah", "1909000000", "2020/2021", "Sciences", "Comp. Sci. And Infomatics", "Diploma Comp. Sci.", "2019/2020", "GBDCS", "", 0.00);
            db.insertData("UED2904819", "Ishmael", "Ofori", "1909000000", "2020/2021", "Sciences", "Comp. Sci. And Infomatics", "Diploma Comp. Sci.", "2019/2020", "IODCS", "", 0.00);
            db.insertData("UED2905319", "Shaibu", "Mohammed", "1909000000", "2020/2021", "Sciences", "Comp. Sci. And Infomatics", "Diploma Comp. Sci.", "2019/2020", "SMDCS", "", 1000.00);
            db.insertData("UED2900619", "Christiana", "Darkowaa", "1909000000", "2020/2021", "Sciences", "Comp. Sci. And Infomatics", "Diploma Comp. Sci.", "2019/2020", "CDDCS", "", 550.00);
            db.insertData("UED2904619", "Regina", "Akuriba Atampoka", "1909000000", "2020/2021", "Sciences", "Comp. Sci. And Infomatics", "Diploma Comp. Sci.", "2019/2020", "RAADCS", "", 0.00);

        }

    }
}