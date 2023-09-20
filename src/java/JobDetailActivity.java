package com.example.fypmypinterview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;

public class JobDetailActivity extends AppCompatActivity {
    public static String mjob_detail_tv_value = "";
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_detail);


        TextView jobtitle_tv = findViewById(R.id.jobtitle_tv);
        TextView company_tv = findViewById(R.id.company_tv);
        TextView location_tv = findViewById(R.id.location_tv);
        TextView salary_tv = findViewById(R.id.salary_tv);
        TextView job_description_details_tv = findViewById(R.id.job_des_details_tv);
        Button apply_job_btn = findViewById(R.id.apply_job_btn);


        Bundle bundle = getIntent().getExtras();

        String mjobtitle_tv = bundle.getString("job_title");
        String mcompany_tv = bundle.getString("company_name");
        String mlocation_tv = bundle.getString("job_location");
        String msalary_tv = bundle.getString("job_salary");
        String mjob_detail_tv = bundle.getString("job_description");

        jobtitle_tv.setText(mjobtitle_tv);
        company_tv.setText(mcompany_tv);
        location_tv.setText(mlocation_tv);
        salary_tv.setText(msalary_tv);
        job_description_details_tv.setText(mjob_detail_tv);

        System.out.println("mjobtitle_tv " + mjob_detail_tv);
        System.out.println("msalary_tv " + msalary_tv);

        apply_job_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobDetailActivity.this, InterviewMethodActivity.class );
                intent.putExtra("job_description",mjob_detail_tv);
                startActivity(intent);
            }
        });

        mjob_detail_tv_value = mjob_detail_tv;





    }




}
