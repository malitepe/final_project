package tr.yildiz.edu.final_project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_dolap;
    private Button btn_kabin;
    private Button btn_activity;
    private Button btn_kombin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defineVariables();
        defineListeners();
    }

    private void defineVariables() {
        btn_dolap=(Button)findViewById(R.id.btn_dolap);
        btn_kabin=(Button)findViewById(R.id.btn_kabin);
        btn_activity=(Button)findViewById(R.id.btn_activity);
        btn_kombin=(Button)findViewById(R.id.btn_kombin);
    }

    private void defineListeners() {

       btn_dolap.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent= new Intent( getApplicationContext(),Dolap.class);
               startActivity(intent);
           }
       });

        btn_kabin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( getApplicationContext(),KombineRoomActivity.class);
                startActivity(intent);
            }
        });

        btn_kombin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( getApplicationContext(),ListKombinActivity.class);
                startActivity(intent);
            }
        });

        btn_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( getApplicationContext(),EtkinlikActivity.class);
                startActivity(intent);
            }
        });
        
    }
}