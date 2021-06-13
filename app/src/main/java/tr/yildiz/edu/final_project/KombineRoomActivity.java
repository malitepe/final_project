package tr.yildiz.edu.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class KombineRoomActivity extends AppCompatActivity {


    ImageView basustu;
    ImageView surat;
    ImageView ustgovde;
    ImageView altgovde;
    ImageView ayak;
    Button KombinKaydet;
    RadioGroup radioGroup;
    RadioButton radioButton;
    DBHelper dbDrawers;
    DBHelperDrawerActivity dbClothes;
    DBHelperKombin dbKombin;
    ListView ListofDrawers;
    ListView ListofClothes;
    String[] drawers;
    String SelectedDrawerName;
    String SelectedClothPath1;
    String SelectedClothPath2;
    String SelectedClothPath3;
    String SelectedClothPath4;
    String SelectedClothPath5;
    EditText KombinName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kombine_room);
        defineVariables();
        defineListeners();
        FillListViewDrawers();

    }
    private void defineVariables(){
         basustu=(ImageView)findViewById(R.id.imageView);
         surat=(ImageView)findViewById(R.id.imageView2);
         ustgovde=(ImageView)findViewById(R.id.imageView3);
         altgovde=(ImageView)findViewById(R.id.imageView4);
         ayak=(ImageView)findViewById(R.id.imageView5);
        KombinKaydet=(Button)findViewById(R.id.button2);
        radioGroup=(RadioGroup)findViewById(R.id.RadioGroup);
        dbDrawers = new DBHelper(getApplicationContext());
        dbClothes=new DBHelperDrawerActivity(getApplicationContext());
        dbKombin=new DBHelperKombin(getApplicationContext());
        ListofDrawers=(ListView)findViewById(R.id.ListViewCombineDrawers);
        ListofClothes=(ListView)findViewById(R.id.ListViewCombineClothes);
        KombinName=(EditText)findViewById(R.id.editTextTextPersonName);
    }


    private void defineListeners(){

        ListofDrawers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectedDrawerName=drawers[position];
                FillListViewClothes();
            }
        });

        ListofClothes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=(TextView)view.findViewById(R.id.textViewListViewContent);
                String nameSelected=textView.getText().toString();
                Cursor cursorTmp=dbClothes.getSelectedCloth(nameSelected);
                cursorTmp.moveToFirst();
                String path=cursorTmp.getString(cursorTmp.getColumnIndex("path"));
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                try {
                    String radiotext=radioButton.getText().toString();
                    switch (radiotext){
                        case "basustu":
                            basustu.setImageBitmap(bitmap);
                            SelectedClothPath1=path;
                            break;
                        case "surat":
                            surat.setImageBitmap(bitmap);
                            SelectedClothPath2=path;
                            break;
                        case "ustgovde":
                            ustgovde.setImageBitmap(bitmap);
                            SelectedClothPath3=path;
                            break;
                        case "altgovde":
                            altgovde.setImageBitmap(bitmap);
                            SelectedClothPath4=path;
                            break;
                        case "ayak":
                            ayak.setImageBitmap(bitmap);
                            SelectedClothPath5=path;
                            break;
                        default:
                            Toast.makeText(KombineRoomActivity.this, "Lütfen resmi yerleştireceğiniz yeri seçiniz.", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(KombineRoomActivity.this, radiotext+" bölümü dolduruldu.", Toast.LENGTH_SHORT).show();


                }catch (Exception e){
                    Toast.makeText(KombineRoomActivity.this, "Lütfen resmi yerleştireceğiniz yeri seçiniz.", Toast.LENGTH_SHORT).show();

                }

            }
        });
        KombinKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(KombinName.getText().toString().isEmpty()){
                    Toast.makeText(KombineRoomActivity.this, "Tüm resimleri ve kombin ismini doldurunuz.", Toast.LENGTH_SHORT).show();
                }else{
                    if(dbKombin.insertKombin(KombinName.getText().toString(),SelectedClothPath1,SelectedClothPath2,SelectedClothPath3,SelectedClothPath4,SelectedClothPath5)){
                        Toast.makeText(KombineRoomActivity.this, "Kombin eklendi", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(KombineRoomActivity.this, "Kombin eklenemedi.", Toast.LENGTH_SHORT).show();
                    }

                    KombinName.setText("");
                }
            }
        });


    }

    private void FillListViewClothes(){
        Cursor cursor =dbClothes.getClothes(SelectedDrawerName);
        ClothesListViewCustomAdapter customAdapter=new ClothesListViewCustomAdapter(getApplicationContext(),cursor);
        ListofClothes.setAdapter(customAdapter);

    }

    private void FillListViewDrawers(){
        Cursor cursor =dbDrawers.getDrawers();
        int length=cursor.getCount();
        int i;
        drawers=new String[length];

        if(length!=0){
            cursor.moveToFirst();
            for(i=0;i<length;i++){
                drawers[i]=cursor.getString(cursor.getColumnIndex("name"));
                cursor.moveToNext();
            }
            ArrayAdapter<String> adapter= new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_list_item_1,
                    android.R.id.text1,drawers);
            ListofDrawers.setAdapter(adapter);
        }
    }

    public void checkButton(View view){
        int radioID=radioGroup.getCheckedRadioButtonId();
        radioButton=(RadioButton)findViewById(radioID);
        Toast.makeText(this, radioButton.getText()+" selected.", Toast.LENGTH_SHORT).show();
    }

}