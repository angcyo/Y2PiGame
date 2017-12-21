package com.angcyo.y2pigame.fragment;

import com.angcyo.y2pigame.MainActivity;
import com.angcyo.y2pigame.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GameFinishFragment extends BaseFragment implements
		View.OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_finish, container, false);
		rootView.findViewById(R.id.id_layout_game_finish).setOnClickListener(
				this);

		return rootView;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.id_layout_game_finish) {
			((MainActivity) context).onBackPressed();
		}
	}

}
