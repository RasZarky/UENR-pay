package com.example.uenrpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.hover.sdk.actions.HoverAction;
import com.hover.sdk.api.Hover;
import com.hover.sdk.api.HoverParameters;
import com.hover.sdk.permissions.PermissionActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Hover.DownloadListener{
    private final String TAG = "MainActivity";

    TextView action,pay;
    ImageView image;
    TextView Name,Programme,Fees;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Hover.initialize(getApplicationContext(), this);
        action = findViewById(R.id.register);
        pay = findViewById(R.id.pay);
        Fees = findViewById(R.id.fees);
        image = findViewById(R.id.image);
        Programme = findViewById(R.id.Programme);
        Name = findViewById(R.id.name);
        db = new DBHelper(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Cursor index = db.viewIndexNumber();
        StringBuilder id = new StringBuilder();
        while (index.moveToNext()){
            id.append(index.getString(0));
        }
        String indexNumber = id.toString();


        Cursor res = db.viewFirstName(indexNumber);
        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()){
            buffer.append(res.getString(0));
        }
        String name = buffer.toString();
        Name.setText("Hello, "+name);

        Cursor viewProgramme = db.viewProgramme(indexNumber);
        StringBuilder buffer2 = new StringBuilder();
        while (viewProgramme.moveToNext()){
            buffer2.append(viewProgramme.getString(0));
        }
        String programme = buffer2.toString();
        Programme.setText(programme);

        Cursor viewPic = db.viewPic(indexNumber);
        StringBuilder buffer3 = new StringBuilder();
        while (viewPic.moveToNext()){
            buffer3.append(viewPic.getString(0));
        }
        String pic = buffer3.toString();
        if (pic.equals("")){
            image.setImageResource(R.drawable.profile);
        }else{
            Uri Pic = Uri.parse(pic);
            image.setImageURI(Pic);
        }

        Cursor viewFees = db.viewFees(indexNumber);
        StringBuilder buffer4 = new StringBuilder();
        while (viewFees.moveToNext()){
            buffer4.append(viewFees.getDouble(0)+"\n");
        }
        String FEES = buffer4.toString();
        float fees = Float.parseFloat(FEES);
        Fees.setText("GHC "+FEES);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Amount");
                builder.setMessage("Enter amount you wish to pay");
                final EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setBackgroundColor(R.drawable.yellow_background);
                input.setPadding(50,20,50,20);
                input.setHint("Amount in Ghana cedis");
                builder.setView(input);
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        Intent i = new HoverParameters.Builder(MainActivity.this)
                                .request("5bbdd637") // Add your action ID here
                                .extra("Amount", m_Text)
                                .buildIntent();
                        startActivityForResult(i, 0);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://regular.uenr.edu.gh/"));
                startActivity(browserIntent);
            }
        });

        double totalFees = 1000.0;
        if (fees < (0.5 * totalFees)){
            action.setVisibility(View.VISIBLE);
            pay.setVisibility(View.GONE);
            action.setText("Register");
        }else {
            action.setVisibility(View.GONE);
            pay.setVisibility(View.VISIBLE);
            action.setText("Pay");
        }
    }

    @Override
    public void onError(String message ) {
        Toast.makeText(this, "Error while attempting to download actions, see logcat for error", Toast.LENGTH_LONG).show();
        Log.e(TAG, "Error: " + message);
    }

    @Override
    public void onSuccess(ArrayList<HoverAction> actions) {
        Toast.makeText(this, "Successfully downloaded " + actions.size() + " actions", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Successfully downloaded " + actions.size() + " actions");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
                Intent logout = new Intent(getApplicationContext(),login.class);
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