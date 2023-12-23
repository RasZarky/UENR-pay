package com.example.uenrpay;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class refunds extends AppCompatActivity {

    DBHelper db;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refunds);

        db = new DBHelper(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Snackbar.make(findViewById(android.R.id.content),"You don't have not received any refunds", BaseTransientBottomBar.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = getIntent();
        String indexNumber = intent.getStringExtra("indexNumber");
        switch (item.getItemId()){
            case R.id.nav_Profile:
                Intent intent1 = new Intent(getApplicationContext(), profile.class);
                startActivity(intent1);
                return true;
            case R.id.nav_contact:
                Toast.makeText(this, "contact Not Available", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_logout:
                db.updateLog(0);
                Intent logout = new Intent(getApplicationContext(), login.class);
                logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logout);
                return true;
            case R.id.nav_message:
                Intent intent2 = new Intent(getApplicationContext(), Message.class);
                intent2.putExtra("indexNumber",indexNumber);
                startActivity(intent2);
                return true;
            case R.id.nav_payment:
                Intent intent3 = new Intent(getApplicationContext(), payment.class);
                startActivity(intent3);
                return true;
            case R.id.nav_receipt:
                startActivity(new Intent(getApplicationContext(),
                        receipt.class));
                return true;
            case R.id.nav_Refunds:
                startActivity(new Intent(getApplicationContext(),
                        refunds.class));
                return true;
            case R.id.nav_share:
                Toast.makeText(this, "Share Not Available", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}