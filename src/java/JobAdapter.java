package com.example.fypmypinterview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobHolder> {

    private Context context;
    private List<Jobs> jobsList;

    public JobAdapter(Context context, List<Jobs> jobs){
        this.context = context;
        jobsList = jobs;
    }

    @NonNull
    @Override
    public  JobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_iteam_jobs_listview, parent, false);
        return new JobHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobHolder holder, int position) {

        Jobs jobs = jobsList.get(position);
        holder.job_title.setText(jobs.getjob_title());
        holder.company_name.setText(jobs.getcompany_name());
        holder.job_location.setText(jobs.getjob_location());
        holder.job_salary.setText(jobs.getSalary());
        holder.job_description.setText(jobs.getJob_description());
        holder.noteCardView.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
           Intent intent = new Intent(context, JobDetailActivity.class);

                intent.putExtra("job_title", jobs.getjob_title());
                intent.putExtra("company_name", jobs.getcompany_name());
                intent.putExtra("job_location", jobs.getjob_location());
                intent.putExtra("job_salary", jobs.getSalary());
                intent.putExtra("job_description", jobs.getJob_description());

                context.startActivity(intent);


           }
       });

    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    public class JobHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView job_title, company_name, job_location, job_salary,job_description;
        MaterialCardView noteCardView;


        public JobHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.IndeedPreview);
            job_title = itemView.findViewById(R.id.job_title);
            company_name = itemView.findViewById(R.id.company_name);
            job_location = itemView.findViewById(R.id.job_location);
            job_salary = itemView.findViewById(R.id.job_salary);
            job_description = itemView.findViewById(R.id.job_description);


            noteCardView = itemView.findViewById(R.id.good_main_layout);


        }
    }

}
