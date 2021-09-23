package com.website.health.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.website.health.R;
import com.website.health.helpers.HelperFunctions;
import com.website.health.helpers.PreferenceManager;
import com.website.health.models.Patient;
import com.website.health.patients.ViewPatientVitalsActivity;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.MyViewHolder> implements Filterable {

    private final List<Patient> patientsList;
    private List<Patient> patientsListFiltered;
    private final Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView active_patient_details;

        MyViewHolder(View view) {
            super(view);

            // Text views
            active_patient_details = view.findViewById(R.id.active_patient_details);

            view.setOnClickListener(v -> {
                Patient patient = patientsList.get(MyViewHolder.this.getAdapterPosition());
                String patient_id = patient.getId();

                // set the patient id in the prefs
                new PreferenceManager(context).setPatientId(patient_id);

                Intent intent = new Intent(context, ViewPatientVitalsActivity.class);
                context.startActivity(intent);
            });
        }
    }

    public PatientsAdapter(Context context, List<Patient> patientsList) {
        this.patientsList = patientsList;
        this.patientsListFiltered = patientsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_active_patients, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Patient patient = patientsListFiltered.get(position);

        HelperFunctions helperFunctions = new HelperFunctions(context);

        String text = helperFunctions.capitalize_first_letter(patient.getName()) + " - " + patient.getDate_of_birth() + ", " + patient.getGender();
        holder.active_patient_details.setText(text);
    }

    @Override
    public int getItemCount() {
        return patientsListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    patientsListFiltered = patientsList;
                } else {
                    List<Patient> filteredList = new ArrayList<>();
                    for (Patient patient : patientsList) {
                        if (patient.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(patient);
                        }
                    }

                    patientsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = patientsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                patientsListFiltered = (ArrayList<Patient>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
