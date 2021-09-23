package com.website.health.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "preferences";
    private static final String PATIENT_ID = "patient_id";

    private SharedPreferences.Editor editor;
    private SharedPreferences pref;

    /**
     * Class constructor to set privacy mode and create new editor
     *
     * @param context application context
     *
     * suppress for adding commit to created prefs editor
     */
    @SuppressLint("CommitPrefEdits")
    public PreferenceManager(Context context) {
        int PRIVATE_MODE = 0;
        this.pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public String getPatientId() {
        return this.pref.getString(PATIENT_ID, "");
    }

    public void setPatientId(String id) {
        this.editor.putString(PATIENT_ID, id);
        this.editor.commit();
    }
}
