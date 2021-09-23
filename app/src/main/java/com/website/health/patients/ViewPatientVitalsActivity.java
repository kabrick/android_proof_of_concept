package com.website.health.patients;

import com.website.health.R;
import com.website.health.adapters.PatientVitalsAdapter;
import com.website.health.helpers.DataRouter;
import com.website.health.helpers.HelperFunctions;
import com.website.health.helpers.PreferenceManager;
import com.website.health.models.PatientVitals;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ViewPatientVitalsActivity extends AppCompatActivity {

    String patient_id;
    DataRouter router;
    FloatingActionButton fab_add_vitals;
    HelperFunctions helperFunctions;
    PatientVitalsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_vitals);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        router = new DataRouter(this);
        helperFunctions = new HelperFunctions(this);

        patient_id = new PreferenceManager(this).getPatientId();

        getVitals();

        fab_add_vitals = findViewById(R.id.fab_add_vitals);

        fab_add_vitals.setOnClickListener(view -> addPatientVitals());
    }

    public void getVitals(){
        /*String url = router.ip_address + "get_patient_vitals/" + patient_id;

        JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {

                    ArrayList<PatientVitals> vitalsList = new ArrayList<>();

                    for (int i = response.length()-1; i >= 0; i--) {
                        try {
                            JSONObject jsonobject = response.getJSONObject(i);
                            vitalsList.add(new PatientVitals( jsonobject.getInt("id"), jsonobject.getInt("heart_rate"), jsonobject.getInt("respiratory_rate"), jsonobject.getInt("temperature")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    mAdapter = new PatientVitalsAdapter(vitalsList);

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ViewPatientVitalsActivity.this);

                    RecyclerView recyclerView = ViewPatientVitalsActivity.this.findViewById(R.id.recycler_view_patient_vitals);

                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }, error -> {
                    //
                });

        Volley.newRequestQueue(this).add(request);*/

        ArrayList<PatientVitals> vitalsList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            vitalsList.add(new PatientVitals(1, 30, 34, 54));
        }

        mAdapter = new PatientVitalsAdapter(vitalsList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ViewPatientVitalsActivity.this);

        RecyclerView recyclerView = ViewPatientVitalsActivity.this.findViewById(R.id.recycler_view_patient_vitals);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    public void addPatientVitals(){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_vitals_dialog, null);

        final EditText heart_rate = view.findViewById(R.id.heart_rate);
        final EditText resp_rate = view.findViewById(R.id.resp_rate);
        final EditText temperature = view.findViewById(R.id.temperature);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setView(view);

        builder.setCancelable(false)
                .setPositiveButton("Save",
                        (dialog, id) -> {
                            if(heart_rate.getText().toString().isEmpty()){
                                helperFunctions.genericDialog("Please fill in the heart rate");
                                return;
                            }

                            if(resp_rate.getText().toString().isEmpty()){
                                helperFunctions.genericDialog("Please fill in the respiration rate");
                                return;
                            }

                            if(temperature.getText().toString().isEmpty()){
                                helperFunctions.genericDialog("Please fill in the temperature");
                                return;
                            }

                            int heart_rate_int = Integer.parseInt(heart_rate.getText().toString());
                            int resp_rate_int = Integer.parseInt(resp_rate.getText().toString());
                            double temperature_int = Double.parseDouble(temperature.getText().toString());

                            router.addVitals(patient_id, heart_rate_int, resp_rate_int, temperature_int);

                            // refresh the layout
                            ViewPatientVitalsActivity.this.getVitals();
                        })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());

        final Dialog dialog = builder.create();

        dialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
