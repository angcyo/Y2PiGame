package com.angcyo.y2pigame.fragment;

import com.angcyo.y2pigame.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PaiImageFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_paiimage, container,
				false);
		return rootView;
	}

}
