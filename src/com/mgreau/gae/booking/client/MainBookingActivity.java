package com.mgreau.gae.booking.client;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appspot.api.services.bookingendpoint.Bookingendpoint;
import com.appspot.api.services.bookingendpoint.model.Dashboard;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

public class MainBookingActivity extends Activity {

	final HttpTransport transport = AndroidHttp.newCompatibleTransport();
	final JsonFactory jsonFactory = new JacksonFactory();
	Bookingendpoint service;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_booking);
		
		service = new Bookingendpoint(transport, jsonFactory, null);
		showDashboard();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main_booking, menu);
		return true;
	}
	
	private void showDashboard() {
		new DashboardTask(this).execute();
	}

	private class DashboardTask extends AsyncTask<Void, Void, Dashboard> {
		Context context;

		public DashboardTask(Context context) {
			this.context = context;
		}

		protected Dashboard doInBackground(Void... unused) {
			Dashboard dashboard = null;
			try {
				dashboard = service.getDashboard().execute();
			} catch (IOException e) {
				Log.d("Booking Client Endpoints", e.getMessage(), e);
			}
			return dashboard;
		}

		protected void onPostExecute(Dashboard dashboard) {
			TextView text = (TextView)findViewById(R.id.dashboard);
			if (dashboard != null)
			text.setText(dashboard.getNbHotels() + " / " + dashboard.getCityList() + " / " + dashboard.getCountryList());
		}
	}

}
