package tr.yildiz.edu.final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperKombin extends SQLiteOpenHelper {

    public static final String DBNAME="finalproject_kombin";


    public DBHelperKombin(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table kombin(name TEXT,path1 TEXT,path2 TEXT,path3 TEXT,path4 TEXT,path5 TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists kombin");
    }

    public boolean insertKombin(String name ,String path1 ,String path2,String path3 ,String path4 ,String path5){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("name",name);
        cv.put("path1",path1);
        cv.put("path2",path2);
        cv.put("path3",path3);
        cv.put("path4",path4);
        cv.put("path5",path5);
        long result= db.insert("kombin",null,cv);
        if(result==-1){return false;}
        else{return true;}
    }

    public boolean deleteKombin(String name){
        SQLiteDatabase db =this.getWritableDatabase();
        long result=db.delete("kombin","name=?",new String[] {name});

        if(result==0){return false;}
        else{return true;}
    }

    public Cursor getKombins(){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("Select * from kombin ",null);
        return cursor;
    }
    public Cursor getSelectedKombin(String name){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("Select * from kombin where name=?",new String[] {name});
        return cursor;
    }




}
