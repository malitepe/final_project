package tr.yildiz.edu.final_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ClothesListViewCustomAdapter extends BaseAdapter {

    Context context;
    String [] ClothesName;
    int length;
    String [] ClothesPath;

    LayoutInflater inflater;
    public ClothesListViewCustomAdapter(Context applicationContext, Cursor cursor) {
        this.context=applicationContext;

        length=cursor.getCount();
        ClothesName=new String[length];
        ClothesPath=new String[length];
        int i;
        if(length!=0){
            cursor.moveToFirst();
            for(i=0;i<length;i++){
                ClothesName[i]=cursor.getString(cursor.getColumnIndex("name"));
                ClothesPath[i]=cursor.getString(cursor.getColumnIndex("path"));
                cursor.moveToNext();
            }
        }



        inflater=LayoutInflater.from(applicationContext);
    }

    @Override
    public int getCount() {
        return length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.activitiy_listview_content,null);
        TextView ClothName=(TextView)view.findViewById(R.id.textViewListViewContent);
        ImageView image=(ImageView)view.findViewById(R.id.iconListViewContent);
        try {
            ClothName.setText(ClothesName[i]);
            Bitmap bitmap = BitmapFactory.decodeFile(ClothesPath[i]);
            image.setImageBitmap(bitmap);

        }catch (ExceptionInInitializerError e){
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }


        return view;
    }
}
