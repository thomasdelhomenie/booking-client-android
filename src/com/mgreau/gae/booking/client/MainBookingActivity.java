package com.mgreau.gae.booking.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.appspot.api.services.bookingendpoint.Bookingendpoint;
import com.appspot.api.services.bookingendpoint.Bookingendpoint.Hotels;
import com.appspot.api.services.bookingendpoint.model.Dashboard;
import com.appspot.api.services.bookingendpoint.model.Hotel;
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

	private class DashboardTask extends AsyncTask<Void, Void, Boolean> {
		Context context;
		
		Dashboard dashboard = null;
		List<Hotel> hotels = null;

		public DashboardTask(Context context) {
			this.context = context;
		}

		protected Boolean doInBackground(Void... unused) {			
			try {
				dashboard = service.dashboard().execute();
				hotels = service.hotels().list().execute().getItems();
			} catch (IOException e) {
				Log.d("Booking Client Endpoints", e.getMessage(), e);
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			TextView textNbHotels = (TextView) findViewById(R.id.nbHotels);
			ListView listView = (ListView) findViewById(R.id.hotelsListView);
			
			textNbHotels.setVisibility(View.GONE);
			
			listView.setAdapter(new HotelAdapter(context, hotels));
		}
	}

}
