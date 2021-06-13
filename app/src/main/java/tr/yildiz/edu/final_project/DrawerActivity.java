package tr.yildiz.edu.final_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DrawerActivity extends AppCompatActivity {
    String nameSelected;
    String DrawerName;
    EditText ClothesType;
    EditText Color;
    EditText Date;
    EditText Price;
    EditText Pattern;
    EditText ClothesName;
    Button Save;
    Button Update;
    TextView Title;
    ImageView img;
    int SELECT_PICTURE = 200;
    DBHelperDrawerActivity DB;
    Uri selectedImageURI;
    ListView ClothesView;
    Cursor cursorTmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        defineVariables();
        FillListView();
        defineListeners();
    }

    public void defineVariables(){
         DrawerName=getIntent().getExtras().getString("DRAWER_NAME");
         ClothesName=(EditText)findViewById(R.id.editTextClothesName);
         ClothesType=(EditText)findViewById(R.id.editTextClothesType);
         Color=(EditText)findViewById(R.id.editTextColor);
         Date=(EditText)findViewById(R.id.editTextDate);
         Price=(EditText)findViewById(R.id.editTextPrice);
         Pattern=(EditText)findViewById(R.id.editTextPattern);
         Save=(Button)findViewById(R.id.btn_save);
         Update=(Button)findViewById(R.id.btn_update);
         Title=(TextView)findViewById(R.id.textViewListTitle);
         img=(ImageView)findViewById(R.id.img_clothes);
         DB=new DBHelperDrawerActivity(getApplicationContext());
         ClothesView=(ListView)findViewById(R.id.ListViewClothes);
    }


    private void FillListView(){
        Cursor cursor =DB.getClothes(DrawerName);
        ClothesListViewCustomAdapter customAdapter=new ClothesListViewCustomAdapter(getApplicationContext(),cursor);
        ClothesView.setAdapter(customAdapter);
    }


    public void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                selectedImageURI = data.getData();
                InputStream imageStream ;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImageURI);
                    img.setImageBitmap(BitmapFactory.decodeStream(imageStream));
                   // Bitmap image = ((BitmapDrawable)img.getDrawable()).getBitmap();
                    //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    //image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    //byteArray = stream.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public File saveImage(String pictureFile) throws FileNotFoundException {
        if (selectedImageURI != null) {
            File picName = getOutputMediaFile(pictureFile);
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageURI);
                FileOutputStream fos = new FileOutputStream(picName);
                image.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
                return picName;
            } catch (FileNotFoundException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }


    private File getOutputMediaFile(String filename){
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/images");

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + filename+ ".jpg" );
        return mediaFile;
    }




    public void defineListeners(){
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ClothesName.getText().toString().isEmpty()){
                    Toast.makeText(DrawerActivity.this, "kıyafet ismi boş bırakılamaz.", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        File imgFile=saveImage(ClothesName.getText().toString());

                        DB.insertCloth(DrawerName,ClothesName.getText().toString(),ClothesType.getText().toString(),
                                Color.getText().toString(),Pattern.getText().toString(),Price.getText().toString(),
                                Date.getText().toString(),imgFile.getAbsolutePath() );

                        Toast.makeText(DrawerActivity.this, "dosyaya eklnedi.", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(DrawerActivity.this, "FileNotFoundException", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                FillListView();
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        if(cursorTmp==null){
                            Toast.makeText(DrawerActivity.this, "Lütfen listeden eleman seçiniz.", Toast.LENGTH_SHORT).show();
                        }else{

                            try {
                                File imgFile = saveImage(ClothesName.getText().toString());
                                DB.updateSelectedItem(nameSelected,ClothesName.getText().toString(),ClothesType.getText().toString(),
                                        Color.getText().toString(),Pattern.getText().toString(),Price.getText().toString(),
                                        Date.getText().toString(),imgFile.getAbsolutePath());
                                Toast.makeText(DrawerActivity.this, "Güncellendi.", Toast.LENGTH_SHORT).show();

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            FillListView();
                        }


            }
        });

        ClothesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=(TextView)view.findViewById(R.id.textViewListViewContent);
                nameSelected=textView.getText().toString();
                AlertDialog.Builder diyalog = new AlertDialog.Builder(DrawerActivity.this);
                String message= nameSelected +" için işlem seçiniz.";
                diyalog.setMessage(message).setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //update


                        cursorTmp=DB.getSelectedCloth(nameSelected);
                        cursorTmp.moveToFirst();
                        ClothesType.setText(cursorTmp.getString(cursorTmp.getColumnIndex("type")));
                        Color.setText(cursorTmp.getString(cursorTmp.getColumnIndex("color")));
                        Date.setText(cursorTmp.getString(cursorTmp.getColumnIndex("date")));
                        Price.setText(cursorTmp.getString(cursorTmp.getColumnIndex("price")));
                        Pattern.setText(cursorTmp.getString(cursorTmp.getColumnIndex("pattern")));
                        ClothesName.setText(cursorTmp.getString(cursorTmp.getColumnIndex("name")));
                        String path=cursorTmp.getString(cursorTmp.getColumnIndex("path"));
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        img.setImageBitmap(bitmap);

                    }
                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       //delete
                        DB.deleteCloth(nameSelected);
                        FillListView();
                    }
                }).show();
            }
        });



    }



}