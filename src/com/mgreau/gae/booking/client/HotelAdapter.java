package com.mgreau.gae.booking.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appspot.api.services.bookingendpoint.Bookingendpoint.Hotels;
import com.appspot.api.services.bookingendpoint.model.Hotel;

public class HotelAdapter extends BaseAdapter {

	private final Context context;
	private List<Hotel> hotels;

	public HotelAdapter(Context context, List<Hotel> hotels) {
		super();
		this.context = context;
		this.hotels = hotels;
	}

	@Override
	public int getCount() {
		return hotels.size();
	}

	@Override
	public Object getItem(int index) {
		return hotels.get(index);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.booking_hotel_item, parent, false);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.imageHotel);
		TextView textView = (TextView) rowView.findViewById(R.id.textHotel);
		
		Hotel hotel = hotels.get(position);
		textView.setText(hotel.getHotelName());
		if(hotel.getImage() != null && hotel.getImage().getValue() != null) {
			imageView.setImageURI(Uri.parse(hotel.getImage().getValue()));
		}

		return rowView;
	}

}
