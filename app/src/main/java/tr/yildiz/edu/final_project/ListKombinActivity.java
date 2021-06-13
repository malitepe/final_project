package tr.yildiz.edu.final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.OutputStream;
import java.security.Permission;
import java.util.ArrayList;

public class ListKombinActivity extends AppCompatActivity {

    ImageView KombinImage1;
    ImageView KombinImage2;
    ImageView KombinImage3;
    ImageView KombinImage4;
    ImageView KombinImage5;
    Button DeleteKombin;
    Button ShareKombin;
    TextView KombinName;
    ListView listKombin;
    DBHelperKombin DB;
    String[] KombinsArray;
    String selectedKombin;
    String path1;
    String path2;
    String path3;
    String path4;
    String path5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kombin);


        defineVariables();
        FillListView();
        defineListeners();
    }

    private void defineVariables(){
         KombinImage1=(ImageView)findViewById(R.id.imageViewKombin1);
         KombinImage2=(ImageView)findViewById(R.id.imageViewKombin2);
         KombinImage3=(ImageView)findViewById(R.id.imageViewKombin3);
         KombinImage4=(ImageView)findViewById(R.id.imageViewKombin4);
         KombinImage5=(ImageView)findViewById(R.id.imageViewKombin5);
         DeleteKombin=(Button)findViewById(R.id.buttonDeleteKombin);
         KombinName=(TextView)findViewById(R.id.textViewKombinName);
         listKombin=(ListView)findViewById(R.id.ListViewListKombin);
         DB=new DBHelperKombin(getApplicationContext());
        ShareKombin=(Button)findViewById(R.id.buttonShare);
    }

    private void defineListeners(){
        listKombin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedKombin=KombinsArray[position];
                KombinName.setText(selectedKombin);
                Cursor cursor =DB.getSelectedKombin(selectedKombin);
                cursor.moveToFirst();
                 path1=cursor.getString(cursor.getColumnIndex("path1"));
                 path2=cursor.getString(cursor.getColumnIndex("path2"));
                 path3=cursor.getString(cursor.getColumnIndex("path3"));
                 path4=cursor.getString(cursor.getColumnIndex("path4"));
                 path5=cursor.getString(cursor.getColumnIndex("path5"));
                KombinImage1.setImageBitmap(BitmapFactory.decodeFile(path1));
                KombinImage2.setImageBitmap(BitmapFactory.decodeFile(path2));
                KombinImage3.setImageBitmap(BitmapFactory.decodeFile(path3));
                KombinImage4.setImageBitmap(BitmapFactory.decodeFile(path4));
                KombinImage5.setImageBitmap(BitmapFactory.decodeFile(path5));
            }
        });

       DeleteKombin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(selectedKombin.isEmpty()){
                   Toast.makeText(ListKombinActivity.this, "Lütfen bir kombin seçiniz.", Toast.LENGTH_SHORT).show();
               }else{
                   DB.deleteKombin(selectedKombin);
                   Toast.makeText(ListKombinActivity.this, selectedKombin+" kombini silindi.", Toast.LENGTH_SHORT).show();
                   selectedKombin="";
                   FillListView();
               }
           }
       });

        ShareKombin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();

            }
        });

    }

    private void share(){

        ArrayList<Uri> imageUris = new ArrayList<>();


        imageUris.add(Uri.parse(path1)); // Add your image URIs here
        imageUris.add(Uri.parse(path2)); // Add your image URIs here
        imageUris.add(Uri.parse(path3)); // Add your image URIs here
        imageUris.add(Uri.parse(path4)); // Add your image URIs here
        imageUris.add(Uri.parse(path5)); // Add your image URIs here

        Intent shareIntent = new Intent();
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.setType("image/jpg");
        startActivity(Intent.createChooser(shareIntent, "Share images to.."));

    }

    private void FillListView(){
        Cursor cursor =DB.getKombins();
        int length=cursor.getCount();
        int i;
        KombinsArray=new String[length];
        Toast.makeText(this, Integer.toString(length), Toast.LENGTH_SHORT).show();
        if(length!=0){
            cursor.moveToFirst();
            for(i=0;i<length;i++){
                KombinsArray[i]=cursor.getString(cursor.getColumnIndex("name"));
                cursor.moveToNext();
            }
            ArrayAdapter<String> adapter= new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_list_item_1,
                    android.R.id.text1,KombinsArray);
            listKombin.setAdapter(adapter);
            Toast.makeText(this, " Listelendi.", Toast.LENGTH_SHORT).show();
        }
    }


}