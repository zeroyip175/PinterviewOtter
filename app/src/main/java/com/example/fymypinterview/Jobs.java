package com.example.fypmypinterview;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

public class Jobs{
    private final String job_title;
    private final String company_name;
    private final String job_location;
    private final String salary;
    private final String job_description ;




    public Jobs(String job_title, String company_name, String job_location, String salary, String job_description) {
        this.job_title = job_title;
        this.company_name = company_name;
        this.job_location = job_location;
        this.salary = salary;
        this.job_description = job_description ;
}




    public String getjob_title() {
        return job_title;
    }


    public String getcompany_name() {
        return company_name;
    }

    public String getjob_location() { return job_location; }

    public String  getJob_description() {
        return job_description;
    }

    public String getSalary() {
        return salary;
    }


}


