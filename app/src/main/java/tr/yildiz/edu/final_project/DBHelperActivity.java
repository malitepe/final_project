package tr.yildiz.edu.final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperActivity extends SQLiteOpenHelper {
    public static final String DBNAME="finalproject_activity";

    public DBHelperActivity(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table activity(name TEXT,type TEXT,date TEXT,kombin TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists activity");
    }

    public boolean insertActivity(String name ,String type ,String date ,String kombin ){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv =new ContentValues();

        cv.put("name",name);
        cv.put("type",type);

        cv.put("date",date);
        cv.put("kombin",kombin);
        long result= db.insert("activity",null,cv);
        if(result==-1){return false;}
        else{return true;}
    }
    public boolean deleteActivity(String name){
        SQLiteDatabase db =this.getWritableDatabase();
        long result=db.delete("activity","name=?",new String[] {name});

        if(result==0){return false;}
        else{return true;}
    }
    public Cursor getActivity(){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("Select * from activity",null);
        return cursor;
    }

}
