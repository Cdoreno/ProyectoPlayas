package com.example.playasarc.proyectoplayas;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.UUID;

public class ReportAddActivity extends AppCompatActivity {

    private UUID id_beach;
    static final int ADD_REPORT_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id_beach = UUID.fromString(getIntent().getExtras().getString("id_beach"));
        setContentView(R.layout.activity_report_add);
        final Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (createReport()){
                    Toast succ = Toast.makeText(getApplicationContext(), "Informe creado", Toast.LENGTH_SHORT);
                    succ.show();
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }

            }
        });
    }

    private boolean createReport(){
        BeachReport br = new BeachReport(id_beach);

        EditText temp = findViewById(R.id.tempImput);
        double tempDouble;

        String tempString = temp.getText().toString();
        if (tempString.equals("")){
            Toast succ = Toast.makeText(getApplicationContext(), "Por favor, introduce una temperatura", Toast.LENGTH_LONG);
            succ.show();
            return false;
        }else{
tempDouble = Double.parseDouble(tempString);
br.setmTemperature(tempDouble);
        }

        RadioGroup rg = findViewById(R.id.radioGrupFlag);
        int rgFlag = rg.getCheckedRadioButtonId();


        int rgVer = findViewById(R.id.radioButton4).getId();
        int rgAma = findViewById(R.id.radioButton3).getId();
        int rgRoj = findViewById(R.id.radioButton2).getId();
        int rgNo = findViewById(R.id.radioButton).getId();

        if (rgFlag == rgVer){
            br.setmColorFlag("Verde");
        }else if(rgFlag==rgAma){
            br.setmColorFlag("Amarilla");
        }else if (rgFlag==rgRoj){
            br.setmColorFlag("Roja");
        }else if(rgFlag==rgNo){
            br.setmColorFlag("Sin bandera");
        }else{
            Toast succ = Toast.makeText(getApplicationContext(), "Por favor introduce el color de la bandera", Toast.LENGTH_LONG);
            succ.show();
            return false;
        }

        RadioGroup rg2 = findViewById(R.id.radioGrupAflu);
        int rgAflu = rg2.getCheckedRadioButtonId();


        int rgAlt = findViewById(R.id.alta).getId();
        int rgMed = findViewById(R.id.media).getId();
        int rgBaj = findViewById(R.id.baja).getId();

        if (rgAflu == rgAlt){
            br.setmAfluencia("Alta");
        }else if(rgAflu==rgMed){
            br.setmAfluencia("Media");
        }else if (rgAflu==rgBaj){
            br.setmAfluencia("Baja");
        }else{
            Toast succ = Toast.makeText(getApplicationContext(), "Por favor introduce la afluencia", Toast.LENGTH_LONG);
            succ.show();
            return false;
        }
BeachReportManager.get(this).addReport(br);
        return true;
    }
}
