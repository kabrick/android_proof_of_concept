package com.website.health.patients;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.Objects;

import com.website.health.R;
import com.website.health.helpers.DataRouter;
import com.website.health.helpers.HelperFunctions;
import com.website.health.helpers.PreferenceManager;

public class ViewPatientDetailsActivity extends AppCompatActivity {

    DataRouter router;
    HelperFunctions helperFunctions;
    TextView patient_names, date_of_birth, gender;
    LinearLayout details_linear;
    String patient_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_details);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        router = new DataRouter(this);
        helperFunctions = new HelperFunctions(this);

        patient_id = new PreferenceManager(this).getPatientId();

        details_linear = findViewById(R.id.details_linear);
        patient_names = findViewById(R.id.patient_names);
        date_of_birth = findViewById(R.id.date_of_birth);
        gender = findViewById(R.id.gender);

        getPatientDetails();
    }


    public void getPatientDetails() {
        helperFunctions.genericProgressBar("Fetching patient demographic...");
        String network_address = router.ip_address + "get_patient_details_full/" + patient_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    helperFunctions.stopProgressBar();

                    try {

                        patient_names.setText(response.getString("name"));
                        date_of_birth.setText(response.getString("date_of_birth"));
                        gender.setText(response.getString("gender"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> helperFunctions.stopProgressBar());

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
