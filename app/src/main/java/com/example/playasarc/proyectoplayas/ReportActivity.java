package com.example.playasarc.proyectoplayas;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.UUID;

public class ReportActivity extends FragmentActivity {

    private UUID id_beach;
    static final int ADD_REPORT_REQUEST = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id_beach = UUID.fromString(getIntent().getExtras().getString("id_beach"));
        setContentView(R.layout.activity_report);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment =
                    createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        final Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createReport();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_REPORT_REQUEST) {

            if (resultCode == RESULT_OK) {
                Intent intent = new Intent();
                intent.setClass(this, ReportActivity.class);
                intent.putExtra("id_beach",String.valueOf(id_beach));
                startActivity(intent);
                this.finish();
            }
        }
    }


    private void createReport() {
        Intent intent = new Intent();
        intent.setClass(this, ReportAddActivity.class);
        intent.putExtra("id_beach",String.valueOf(id_beach));
        startActivityForResult(intent,ADD_REPORT_REQUEST);
    }

    protected Fragment createFragment() {
        ReportListFragment rlf = new ReportListFragment();
        rlf.setArguments(getIntent().getExtras());

        return rlf;
    }

}
