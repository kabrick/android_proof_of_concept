package com.website.health.adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import com.website.health.R;
import com.website.health.helpers.DataRouter;
import com.website.health.helpers.HelperFunctions;
import com.website.health.models.PatientVitals;
import com.website.health.patients.ViewPatientVitalsActivity;

public class PatientVitalsAdapter extends RecyclerView.Adapter<PatientVitalsAdapter.MyViewHolder> {
    private List<PatientVitals> vitalsList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vitals_hr, vitals_rr, vitals_temp;

        MyViewHolder(View view) {
            super(view);

            vitals_hr = view.findViewById(R.id.vitals_hr);
            vitals_rr = view.findViewById(R.id.vitals_rr);
            vitals_temp = view.findViewById(R.id.vitals_temp);
        }
    }

    public PatientVitalsAdapter(List<PatientVitals> vitalsList) {
        this.vitalsList = vitalsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_vitals, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final PatientVitals vital = vitalsList.get(position);

        holder.vitals_hr.setText(vital.getHeartRate());
        holder.vitals_rr.setText(vital.getRespRate());
        holder.vitals_temp.setText(vital.getTemp());
    }

    @Override
    public int getItemCount() {
        return vitalsList.size();
    }

}
