package com.example.uenrpay;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
   public DBHelper(@Nullable Context context) {
       super(context,"Student.db",null,1);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
       db.execSQL("Create table StudentDetails(indexNUmber text PRIMARY KEY,FirstName text,SecondName text,StudentNumber text,Session text," +
               "School text,Department text,Programme text,EntryYear text,Password text, pic BLOB, fees double)");
       db.execSQL("Create table LoggedIn(Log integer PRIMARY KEY)");
       db.execSQL("Create table IndexNumber(indexNumber text PRIMARY KEY)");

   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("drop table if exists StudentDetails");
       db.execSQL("drop table if exists LoggedIn");
       db.execSQL("drop table if exists IndexNumber");
   }

   public boolean insertIntoIndexNumber (String indexNumber){
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put("indexNumber",indexNumber);
       long result = db.insert("IndexNumber",null,contentValues);
       return result != -1;
   }

   public boolean insertIntoLog (int Log){
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put("Log",Log);
       long result = db.insert("LoggedIn",null,contentValues);
       return result != -1;
   }

   public boolean insertData(String index, String FirstName, String SecondName, String StudentNumber, String Session, String School, String Department, String Programme,
                             String EntryYear, String Password, String pic, double Fees){
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put("indexNumber",index);
       contentValues.put("FirstName",FirstName);
       contentValues.put("SecondName",SecondName);
       contentValues.put("StudentNumber",StudentNumber);
       contentValues.put("Session",Session);
       contentValues.put("School",School);
       contentValues.put("Department",Department);
       contentValues.put("Programme",Programme);
       contentValues.put("EntryYear",EntryYear);
       contentValues.put("Password",Password);
       contentValues.put("pic", String.valueOf(pic));
       contentValues.put("Fees",Fees);
       long result = db.insert("StudentDetails",null,contentValues);
       return result != -1;
   }

   public boolean insertPic(String index,String pic){
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put("pic",pic);
       long result = db.insert("StudentDetails", null, contentValues);
       return result != -1;
   }

   public boolean updateData(String index,String pic){
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put("pic",pic);
       @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from StudentDetails where indexNumber = ?",new String[]{index});
       if(cursor.getCount()>0) {
           long result = db.update("StudentDetails", contentValues, "indexNumber=?", new String[]{index});
           return result != -1;
       }else {
           return false;
       }
   }

   public boolean updateIndexNumber(String indexNumber){
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put("indexNumber",indexNumber);
       @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from IndexNumber",null);
       if(cursor.getCount()>0) {
           long result = db.update("IndexNumber", contentValues,null,null);
           return result != -1;
       }else {
           return false;
       }
   }

   public boolean updateLog(int Log){
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put("Log",Log);
       @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from StudentDetails",null);
       if(cursor.getCount()>0) {
           long result = db.update("LoggedIn", contentValues,null,null);
           return result != -1;
       }else {
           return false;
       }
   }

   public boolean deleteData(String indexNumber){
       SQLiteDatabase db = this.getWritableDatabase();
       @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from StudentDetails where indexNumber = ?",new String[]{indexNumber});
       if(cursor.getCount()>0) {
           long result = db.delete("StudentDetails", "indexNumber=?", new String[]{indexNumber});
           return result != -1;
       }else {
           return false;
       }
   }

   public Cursor viewData(String indexNumber){
       SQLiteDatabase db = this.getWritableDatabase();
       return db.rawQuery("select * from StudentDetails where indexNumber=?",new String[]{indexNumber});
   }

   public Cursor viewLoggedIn(){
       SQLiteDatabase db = this.getWritableDatabase();
       return db.rawQuery("select * from LoggedIn",null);
   }

   public Cursor viewIndexNumber(){
       SQLiteDatabase db = this.getWritableDatabase();
       return db.rawQuery("select * from IndexNumber",null);
   }

   public Cursor viewData1(){
       SQLiteDatabase db = this.getWritableDatabase();
       return db.rawQuery("select * from StudentDetails",null);
   }

   public Cursor viewFirstName(String indexNumber){
       SQLiteDatabase db = this.getWritableDatabase();
       return db.rawQuery("select FirstName from StudentDetails where indexNumber=?",new String[]{indexNumber});
   }

   public Cursor viewProgramme(String indexNumber){
       SQLiteDatabase db = this.getWritableDatabase();
       return db.rawQuery("select Programme from StudentDetails where indexNumber=?",new String[]{indexNumber});
   }

   public Cursor viewPic(String indexNumber){
       SQLiteDatabase db = this.getWritableDatabase();
       return db.rawQuery("select pic from StudentDetails where indexNumber=?",new String[]{indexNumber});
   }
   public Cursor viewFees(String indexNumber){
       SQLiteDatabase db = this.getWritableDatabase();
       return db.rawQuery("select Fees from StudentDetails where indexNumber=?",new String[]{indexNumber});
   }

   @SuppressLint("Recycle")
   public int Login(String Index, String Password){
       SQLiteDatabase db = this.getWritableDatabase();
       String[] selectionArgs = new String[]{Index,Password};
       try{
           int i;
           Cursor c;
           c = db.rawQuery("select * from StudentDetails where indexNumber=? and Password=?",selectionArgs);
           c.moveToFirst();
           i = c.getCount();
           return i;
       }catch (Exception e){
           e.printStackTrace();
       }
       return 0;
   }

}
