package tr.yildiz.edu.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EtkinlikActivity extends AppCompatActivity {
    EditText nameActivity;
    EditText typeActivity;
    EditText dateActivity;
    Button btnSave;
    Button btnList;
    TextView SelectedKombinName;
    ListView Liste;
    String[] KombinsArray;
    DBHelperActivity DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkinlik);
        defineVariables();
        defineListeners();
        FillListView();
    }


    private void defineVariables(){
         nameActivity=(EditText)findViewById(R.id.editTextTextPersonName2);
         typeActivity=(EditText)findViewById(R.id.editTextTextPersonName3);
         dateActivity=(EditText)findViewById(R.id.editTextDate2);
        btnSave=(Button)findViewById(R.id.buttonPlacePicker);
         SelectedKombinName=(TextView)findViewById(R.id.textView);
         Liste= (ListView) findViewById(R.id.SelectActivityKombinList);
        DB=new DBHelperActivity(getApplicationContext());
        btnList=(Button)findViewById(R.id.buttonListele);

    }

    private void defineListeners(){
        Liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectedKombinName.setText(KombinsArray[position]);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameActivity.getText().toString().isEmpty()){
                    Toast.makeText(EtkinlikActivity.this, "Alanları doldurunuz.", Toast.LENGTH_SHORT).show();
                }else{
                    DB.insertActivity(nameActivity.getText().toString(),typeActivity.getText().toString(),dateActivity.getText().toString(),SelectedKombinName.getText().toString());
                    Toast.makeText(EtkinlikActivity.this, "Etkinlik eklendi.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillListViewActivity();

            }
        });

    }
    private void FillListViewActivity(){

        Cursor cursor =DB.getActivity();
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
            Liste.setAdapter(adapter);
            Toast.makeText(this, " Listelendi.", Toast.LENGTH_SHORT).show();
        }
    }


    private void FillListView(){
        DBHelperKombin db=new DBHelperKombin(getApplicationContext());
        Cursor cursor =db.getKombins();
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
            Liste.setAdapter(adapter);
            Toast.makeText(this, " Listelendi.", Toast.LENGTH_SHORT).show();
        }
    }
}