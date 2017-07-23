package com.codeitsuisse.team92.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Jay Lohokare on 11-Sep-15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME= "money";

    // Contacts table name
   public static String EXPENSE;

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";





    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            EXPENSE =Display_Main.name;
        }
        catch(Exception e){

        }


        try {
            EXPENSE =Sign_Up.name;
        }
        catch(Exception e){

        }



        String CREATE_EXPENSE_TABLE = "CREATE TABLE "+EXPENSE+"(ID INTEGER PRIMARY KEY,froma TEXT,toa TEXT,DATE INTEGER,MONTH INTEGER,YEAR INTEGER,AMOUNT INTEGER,DESCRIPTION TEXT,CATEGORY TEXT, LIABILITY INTEGER)";
        db.execSQL(CREATE_EXPENSE_TABLE);



    }

    public void createCat()
    {
        /***************************************CATEGORIES*******************************************/
        String catergory_table_create = "CREATE TABLE catergory_table (categories TEXT)";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(catergory_table_create);
        db.close();
        /***************************************CATEGORIES*******************************************/
    }
    public void insertcat() {
        SQLiteDatabase db = this.getWritableDatabase();

        String KEY = "categories";
        ContentValues values2 = new ContentValues();
        values2.put(KEY, "food");
        db.insert("catergory_table", null, values2);
        values2.put(KEY, "shopping");
        db.insert("catergory_table", null, values2);
        values2.put(KEY, "entertainment");
        db.insert("catergory_table", null, values2);
        values2.put(KEY, "other");
        db.insert("catergory_table", null, values2);
        db.close();
    }


    public void createIncome()
    {
        /***************************************CATEGORIES*******************************************/
        String income_table_create = "CREATE TABLE income_table (amount INTEGER, DATE INTEGER, MONTH INTEGER, YEAR INTEGER)";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(income_table_create);
        db.close();
        /***************************************CATEGORIES*******************************************/
    }

    public void createFrom()
    {
        /***************************************CATEGORIES*******************************************/
        String from_table_create = "CREATE TABLE from_table (from_column TEXT)";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(from_table_create);
        db.close();
        /***************************************CATEGORIES*******************************************/
    }
    public void insertFrom() {
        SQLiteDatabase db = this.getWritableDatabase();

        String KEY = "from_column";
        ContentValues values2 = new ContentValues();
        values2.put(KEY, "Cash");
        db.insert("from_table", null, values2);
        values2.put(KEY, "Bank 1");
        db.insert("from_table", null, values2);
        values2.put(KEY, "Bank 2");
        db.insert("from_table", null, values2);
        values2.put(KEY, "Other");
        db.insert("from_table", null, values2);
        db.close();
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXPENSE);

        // Create tables again
        onCreate(db);
    }

    public void insertData(String name, ContentValues values){


        SQLiteDatabase db2 = this.getReadableDatabase();


        db2.insert(name, null, values);

            db2.close();


    }

    public void insertIncome(String name, ContentValues values){
        SQLiteDatabase db2 = this.getReadableDatabase();
        db2.insert("income_table", null, values);
        db2.close();
    }

    public List<String> getCat(){
        List<String> labels = new ArrayList<String>();


        // Select All Query
              String selectQuery = "SELECT * FROM catergory_table";

        SQLiteDatabase db = this.getReadableDatabase();




        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();

        // returning lables
        return labels;
    }

    public List<String> getFrom(){
        List<String> labels1 = new ArrayList<String>();


        // Select All Query
        String selectQuery1 = "SELECT * FROM from_table";

        SQLiteDatabase db = this.getReadableDatabase();




        Cursor cursor1 = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor1.moveToFirst()) {
            do {
                labels1.add(cursor1.getString(0));

            } while (cursor1.moveToNext());
        }

        // closing connection
        cursor1.close();

        // returning lables
        return labels1;
    }

    public List<ExpenseDetails> getDaily(int date, int month, int year) {
        List<ExpenseDetails> messageList = new ArrayList<ExpenseDetails>();
        // Select All Query
        //final Calendar c = Calendar.getInstance();
       /* int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);*/

        // set current date into textview

       /* String finaldate= new StringBuilder()
                // Month is 0 based, just add 1
                .append(year).append("-").append(month + 1).append("-").append(day).toString();*/
        // set current date into datepicker
        String name = Display_Main.name;

        String selectQuery = "SELECT  * FROM "+ name + " WHERE DATE=\"" + date + "\" AND MONTH=\"\" + month + \"\" AND YEAR=\"\" + year + \"\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);




        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ExpenseDetails cm = new ExpenseDetails();
                cm.setSr(cursor.getInt(0));
                cm.setFrom(cursor.getString(1));
                cm.setTo(cursor.getString(2));
                cm.setDate(cursor.getInt(3));
                cm.setMonth(cursor.getInt(4));
                cm.setYear(cursor.getInt(5));
                cm.setAmount(Integer.parseInt(cursor.getString(6)));
                cm.setDescription(cursor.getString(7));
                cm.setCategory(cursor.getString(8));
                cm.setLiability(cursor.getInt(9));


                messageList.add(cm);
            } while (cursor.moveToNext());
        }


        return messageList;
    }

    public List<ExpenseDetails> getDetails(String primaryKey)
    { List<ExpenseDetails> messageList = new ArrayList<ExpenseDetails>();
        Integer.parseInt(primaryKey);
        String name = Display_Main.name;
        String selectQuery = "SELECT * FROM "+name+" WHERE ID=\"" + primaryKey + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        { do
        { ExpenseDetails cm = new ExpenseDetails();
            cm.setFrom(cursor.getString(1));
            cm.setTo(cursor.getString(2));
            cm.setDate(cursor.getInt(3));
            cm.setMonth(cursor.getInt(4));
            cm.setYear(cursor.getInt(5));
            cm.setAmount(Integer.parseInt(cursor.getString(6)));
            cm.setDescription(cursor.getString(7));
            cm.setCategory(cursor.getString(8));
            cm.setLiability(cursor.getInt(9));

            messageList.add(cm);
        }
        while (cursor.moveToNext());
        }
        return messageList;
    }

    public void updateData(String primaryKey,ContentValues value3) {

        Log.d("Error dadadada", value3.toString());
        Log.d("Error dadadada", primaryKey.toString());


        final String KEY_ID = "ID";
        SQLiteDatabase db = this.getWritableDatabase();

        String name = Display_Main.name;


        // updating row
        db.update(name, value3, KEY_ID + " = ?",
                new String[] { String.valueOf(primaryKey) });
    }

    public List<ExpenseDetails> getCategoryWise(String categoryName) {
        List<ExpenseDetails> messageList = new ArrayList<ExpenseDetails>();

        String name = Display_Main.name;

        String selectQuery = "SELECT  * FROM "+ name+ " WHERE CATEGORY=\"" + categoryName + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ExpenseDetails cm = new ExpenseDetails();
                cm.setSr(Integer.parseInt(cursor.getString(0)));
                cm.setFrom(cursor.getString(1));
                cm.setTo(cursor.getString(2));
                cm.setDate(cursor.getInt(3));
                cm.setMonth(cursor.getInt(4));
                cm.setYear(cursor.getInt(5));
                cm.setAmount(Integer.parseInt(cursor.getString(6)));
                cm.setDescription(cursor.getString(7));
                cm.setCategory(cursor.getString(8));
                cm.setLiability(cursor.getInt(9));


                messageList.add(cm);
            } while (cursor.moveToNext());
        }
        return messageList;
    }

    public void deleteData(String primaryKey) {

        final String KEY_ID = "ID";
        SQLiteDatabase db = this.getWritableDatabase();

        String name = Display_Main.name;

        // updating row
        db.delete(name,KEY_ID + " = ?",
                new String[] { String.valueOf(primaryKey) });
    }


    public List<ExpenseDetails> getIncome(String month) {
        List<ExpenseDetails> messageList = new ArrayList<ExpenseDetails>();

        String selectQuery = "SELECT  * FROM income_table where MONTH = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM income_table where MONTH = ?", new String[]{month}, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ExpenseDetails cm = new ExpenseDetails();
                cm.setAmount(Integer.parseInt(cursor.getString(0)));
                cm.setDate(cursor.getInt(1));
                cm.setMonth(cursor.getInt(2));
                cm.setYear(cursor.getInt(3));
                messageList.add(cm);
            } while (cursor.moveToNext());
        }
        return messageList;
    }



    public int getCurrentSaving(String month,int budget){

        SQLiteDatabase db = this.getWritableDatabase();
        int totalIncome;

        Cursor c = db.rawQuery("select sum(amount) from income_table where MONTH=?", new String[]{month});
        if(c.moveToFirst())
            totalIncome = c.getInt(0);
        else
            totalIncome = -1;
        c.close();

        int currentSaving = totalIncome - budget;

        return currentSaving;

    }


    public int getTotalIncome(String month){


        Log.d(month,"this is it");
        SQLiteDatabase db = this.getWritableDatabase();
        int totalIncome;

        Cursor c = db.rawQuery("select sum(amount) from income_table where MONTH=?", new String[]{month});
        if(c.moveToFirst())
            totalIncome = c.getInt(0);
        else
            totalIncome = -1;
        c.close();

        return totalIncome;
    }

    public int getTotalExpense(String month){

        SQLiteDatabase db = this.getWritableDatabase();
        int totalExpense;

        String name = Display_Main.name;


        Cursor c2 = db.rawQuery("select sum(amount) from " + name + " where MONTH=?", new String[]{month});
        if(c2.moveToFirst())
            totalExpense = c2.getInt(0);
        else
            totalExpense = -1;
        c2.close();

        return totalExpense;
    }

    public int getCurrentBalance(String month){
        return (getTotalIncome(month)-getTotalExpense(month));
    }


    public List<ExpenseDetails> getAllLiability(int month) {
        List<ExpenseDetails> messageList = new ArrayList<ExpenseDetails>();

        String name = Display_Main.name;

        Log.d(String.valueOf(month), "Dekhle");
        String selectQuery = "SELECT  * FROM " +name +" WHERE LIABILITY = \"0\" + \"\" AND MONTH=\"\" + month  + \"\"";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ExpenseDetails cm = new ExpenseDetails();
                cm.setFrom(cursor.getString(1));
                cm.setTo(cursor.getString(2));
                cm.setDate(cursor.getInt(3));
                cm.setMonth(cursor.getInt(4));
                cm.setYear(cursor.getInt(5));
                cm.setAmount(Integer.parseInt(cursor.getString(6)));
                cm.setDescription(cursor.getString(7));
                cm.setCategory(cursor.getString(8));
                messageList.add(cm);
            } while (cursor.moveToNext());
        }
        Log.d(messageList.toString(), "Dekhle");
        return messageList;
    }



    public float[] getPieData(){


        SQLiteDatabase db = this.getWritableDatabase();
        int totalExpense;

        String name = Display_Main.name;
        float values[]={};

        String cat;
        int part;
        int x = 0;
        Cursor c2 = db.rawQuery("select * from catergory_table", null);

        if (c2.moveToFirst()) {
            do {
                cat = c2.getString(0);
                Log.d(cat.toString(), "Cat here");
                Cursor c3 = db.rawQuery("select sum(amount) from " + name + " where CATEGORY= ?", new String[]{cat});
                if (c3.moveToFirst())
                {       part = c3.getInt(0);
                values[x] = part;
                x++;
            }
                else
                    part = -1;
                c3.close();

                Log.d(c3.toString(),"c3 here");


            } while (c2.moveToNext());
        }

        return values;
    }
}
