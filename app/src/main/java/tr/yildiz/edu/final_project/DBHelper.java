package tr.yildiz.edu.final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME="finalproject";

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table drawer(name TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists drawer");
    }

    public boolean deleteDrawer(String name){
        SQLiteDatabase db =this.getWritableDatabase();
        long result=db.delete("drawer","name=?",new String[] {name});

        if(result==-0){return false;}
        else{return true;}
    }
    public boolean insertDrawer(String name){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("name",name);

        long result= db.insert("drawer",null,cv);
        if(result==-1){return false;}
        else{return true;}
    }


    public Cursor getDrawers(){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("Select * from drawer",null);
        return cursor;
    }

}
