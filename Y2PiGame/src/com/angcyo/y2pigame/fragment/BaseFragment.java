package com.angcyo.y2pigame.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.angcyo.y2pigame.MainActivity;
import com.easyandroidanimations.library.ExplodeAnimation;

public class BaseFragment extends Fragment {

	protected Context context;
	protected LayoutInflater inflater;
	protected boolean isDestroyAnim = true;
	protected View rootView;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
		LayoutInflater.from(context);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onDestroyView() {
		if (isDestroyAnim) {
			View view = getView();
			if (view != null) {
				new ExplodeAnimation(getView()).animate();
			}

		}
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	public void addFragment(BaseFragment fragment) {
		((MainActivity) context).addFragment(fragment);
	}

	public void replaceFragment(BaseFragment fragment) {
		((MainActivity) context).replaceFragment(fragment);
	}

	protected void playSound() {
		((MainActivity) context).playSound();
	}

}
