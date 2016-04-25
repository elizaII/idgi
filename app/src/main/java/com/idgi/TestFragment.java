package com.idgi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.OnClick;

public class TestFragment extends Fragment implements View.OnClickListener {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_fragment_test, container, false);
	}

	@OnClick(R.id.fragment_container)
	public void onClick(View v) {
		Toast.makeText(v.getContext(), "Clicked.", Toast.LENGTH_SHORT).show();
	}
}
