package tr.yildiz.edu.final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperDrawerActivity extends SQLiteOpenHelper {
    public static final String DBNAME="finalproject_clothes";

    public DBHelperDrawerActivity(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table clothes(drawer TEXT,name TEXT,type TEXT,color TEXT,pattern TEXT,price TEXT,date TEXT,path TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists clothes");
    }

    public boolean insertCloth(String drawer ,String name ,String type,String color ,String pattern ,String price ,String date ,String path){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("drawer",drawer);
        cv.put("name",name);
        cv.put("type",type);
        cv.put("color",color);
        cv.put("pattern",pattern);
        cv.put("price",price);
        cv.put("date",date);
        cv.put("path",path);
        long result= db.insert("clothes",null,cv);
        if(result==-1){return false;}
        else{return true;}
    }

    public boolean deleteCloth(String name){
        SQLiteDatabase db =this.getWritableDatabase();
        long result=db.delete("clothes","name=?",new String[] {name});

        if(result==0){return false;}
        else{return true;}
    }

    public Cursor getClothes(String drawername){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("Select * from clothes where drawer=?",new String[] {drawername});
        return cursor;
    }

    public Cursor getSelectedCloth(String name){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("Select * from clothes where name=?",new String[] {name});
        return cursor;
    }

    public boolean updateSelectedItem(String nameSelected,String name,String type,String color,String pattern,String price,String date,String path){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("name",name);
        cv.put("type",type);
        cv.put("color",color);
        cv.put("pattern",pattern);
        cv.put("price",price);
        cv.put("date",date);
        cv.put("path",path);


        long result=db.update("clothes",cv,"name=?",new String[] {nameSelected});

        if(result==0){return false;}
        else{return true;}
    }


}
