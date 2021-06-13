   package tr.yildiz.edu.final_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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


public class Dolap extends AppCompatActivity {

    TextView title;
    EditText DrawerName;
    Button btn_addDrawer;
    ListView listofDrawers;
    DBHelper DB;
    String[] drawersArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dolap);
        defineVariables();
        FillListView();
        defineListeners();
    }

    private void FillListView(){
        Cursor cursor =DB.getDrawers();
        int length=cursor.getCount();
        int i;
        drawersArray=new String[length];

        if(length!=0){
            cursor.moveToFirst();
            for(i=0;i<length;i++){
                drawersArray[i]=cursor.getString(cursor.getColumnIndex("name"));
                cursor.moveToNext();
            }
            ArrayAdapter<String> adapter= new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_list_item_1,
                    android.R.id.text1,drawersArray);
            listofDrawers.setAdapter(adapter);
        }
    }


    private void defineVariables(){
        title=(TextView)findViewById(R.id.textListViewTitle);
        DrawerName=(EditText)findViewById(R.id.editTextDrawerName);
        btn_addDrawer=(Button)findViewById(R.id.btn_addDrawer);
        listofDrawers=(ListView)findViewById(R.id.ListViewDrawers);
        DB=new DBHelper(this);
    }

    private void defineListeners(){

        btn_addDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                name=DrawerName.getText().toString();
                if(!name.isEmpty()){
                    DB.insertDrawer(name);
                    FillListView();
                    Toast.makeText(Dolap.this, "Çekmece Eklendi.", Toast.LENGTH_SHORT).show();
                    DrawerName.getText().clear();
                }else{
                    Toast.makeText(Dolap.this, "Çekmece ismi boş bırakılamaz.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listofDrawers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder diyalog = new AlertDialog.Builder(Dolap.this);
                String message=drawersArray[position]+" için işlem seçiniz.";
                diyalog.setMessage(message).setPositiveButton("Open", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent= new Intent( getApplicationContext(),DrawerActivity.class);
                        intent.putExtra("DRAWER_NAME",drawersArray[position]);
                        startActivity(intent);
                    }
                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DB.deleteDrawer(drawersArray[position]);
                        FillListView();
                    }
                }).show();



            }
        });


    }
}