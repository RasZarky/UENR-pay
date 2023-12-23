package com.example.uenrpay;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class profile extends AppCompatActivity {

    TextView Name,StudentNumber,Session,Index,Department,School,Programme,EntryYear;
    CardView PickImage;
    ImageView back,image;
    DrawerLayout drawer;
    DBHelper db;


    int REQUEST_PERMISSION_CODE = 99;
    int RESULT_LOAD_IMAGE = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Cursor index = db.viewIndexNumber();
        StringBuilder id = new StringBuilder();
        while (index.moveToNext()){
            id.append(index.getString(0));
        }
        String indexNumber = id.toString();

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(selectedImage,filePathColumn,null,null,null);
            if (cursor != null){
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                image = findViewById(R.id.image);
                image.setImageURI(selectedImage);
                String uri = selectedImage.toString();

                Cursor viewAllInStudent = db.viewData(indexNumber);
                StringBuilder img = new StringBuilder();
                while (viewAllInStudent.moveToNext()){
                    img.append(viewAllInStudent.getString(10));
                }
                String IMG = img.toString();

                if (IMG == null){
                    db.insertPic(indexNumber,uri);
                }else {
                    db.updateData(indexNumber, uri);
                }
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        PickImage = findViewById(R.id.pick_image);
        image = findViewById(R.id.image);
        Programme = findViewById(R.id.Programme);
        EntryYear = findViewById(R.id.entry_year);
        Session = findViewById(R.id.session);
        Index = findViewById(R.id.index);
        Department = findViewById(R.id.department);
        School = findViewById(R.id.school);
        StudentNumber = findViewById(R.id.student_number);
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

        back = findViewById(R.id.back);
        drawer = findViewById(R.id.RelativeLayout);


        Cursor viewAll = db.viewData(indexNumber);
        StringBuilder buffer1 = new StringBuilder();
        StringBuilder buffer2 = new StringBuilder();
        StringBuilder buffer3 = new StringBuilder();
        StringBuilder buffer4 = new StringBuilder();
        StringBuilder buffer5 = new StringBuilder();
        StringBuilder buffer6 = new StringBuilder();
        StringBuilder buffer7 = new StringBuilder();
        StringBuilder buffer8 = new StringBuilder();
        StringBuilder buffer9 = new StringBuilder();


        while (viewAll.moveToNext()) {
            buffer1.append(viewAll.getString(1));
            buffer2.append(viewAll.getString(2));
            buffer3.append(viewAll.getString(3));
            buffer4.append(viewAll.getString(4));
            buffer5.append(viewAll.getString(5));
            buffer6.append(viewAll.getString(6));
            buffer7.append(viewAll.getString(7));
            buffer8.append(viewAll.getString(8));
            buffer9.append(viewAll.getString(10));
        }
        String fName = buffer1.toString();
        String sName = buffer2.toString();
        String FullName = fName+" "+sName;
        Name.setText(FullName);

        String studentNumber = buffer3.toString();
        StudentNumber.setText(studentNumber);

        String session = buffer4.toString();
        Session.setText(session);

        String school = buffer5.toString();
        School.setText(school);

        String department = buffer6.toString();
        Department.setText(department);

        String programme = buffer7.toString();
        Programme.setText(programme);

        Index.setText(indexNumber);

        String entry = buffer8.toString();
        EntryYear.setText(entry);

        String pic = buffer9.toString();
        if (pic == ""){
            image.setImageResource(R.drawable.profile);
        }else{
            if (ActivityCompat.checkSelfPermission(profile.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(profile.this,Manifest.permission.READ_EXTERNAL_STORAGE)){

                }else {
                    ActivityCompat.requestPermissions(profile.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION_CODE);
                }
            }

            Uri Pic = Uri.parse(pic);
            image.setImageURI(Pic);
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        PickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Pick_pic = new Intent();
                Pick_pic.setType("image/*");
                Pick_pic.setAction(Intent.ACTION_OPEN_DOCUMENT);
                Pick_pic.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                Pick_pic.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                if (ActivityCompat.checkSelfPermission(profile.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(profile.this,Manifest.permission.READ_EXTERNAL_STORAGE)){

                    }else {
                        ActivityCompat.requestPermissions(profile.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION_CODE);
                    }
                }
                startActivityForResult(Pick_pic.createChooser(Pick_pic,"Select Picture"),RESULT_LOAD_IMAGE);
            }
        });



    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
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