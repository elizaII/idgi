package com.idgi;

import com.firebase.client.Firebase;
import com.idgi.services.FireDatabase;

public class Application extends android.app.Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Firebase.setAndroidContext(this);
		//Firebase.getDefaultConfig().setPersistenceEnabled(true);
		FireDatabase.getInstance().initialize();
		//FireDatabase.getInstance().createSchools();
	}
}
