package com.example.vraman.infinitecube.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppSharedPreference {

	private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
	public static AppSharedPreference appSharedPreference;
	private SharedPreferences sharedPreferences;
	private Editor editor;

	private AppSharedPreference(Context context) {
		this.sharedPreferences = context.getSharedPreferences("User_detail",
				Context.MODE_PRIVATE);
		this.editor = sharedPreferences.edit();
	}
	public static AppSharedPreference getInstance(Context context) {
		if (appSharedPreference == null) {
			appSharedPreference = new AppSharedPreference(context);
		}
		return appSharedPreference;
	}

	public String getAuthToken() {
		return sharedPreferences.getString("auth_token", "");
	}

	public void setAuthToken(String authToken) {
		editor.putString("auth_token", authToken);
		editor.commit();
	}


	public String getOwnerId() {
		return sharedPreferences.getString("owner_id", "");
	}

	public void setOwnerId(String ownerId) {
		editor.putString("owner_id", ownerId);
		editor.commit();
	}

	public String getContactId() {
		return sharedPreferences.getString("contact_id", "");
	}

	public void setContactId(String contactId) {
		editor.putString("contact_id", contactId);
		editor.commit();
	}


	public String getLeadId() {
		return sharedPreferences.getString("lead_id", "");
	}

	public void setLeadId(String leadId) {
		editor.putString("lead_id", leadId);
		editor.commit();
	}


	public void setFirstTimeLaunch(boolean isFirstTime) {
		editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
		editor.commit();
	}

	public boolean isFirstTimeLaunch() {
		return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
	}

	public void clearPreference() {
		editor.clear();
	}
}
