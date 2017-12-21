package com.example.androidmusicrestest;

import java.io.IOException;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.angcyo.music.MusicRes;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.id_bt1).setOnClickListener(this);
		findViewById(R.id.id_bt2).setOnClickListener(this);

	}

	@Override
	protected void onPause() {
		super.onPause();

		MusicRes.pauseAll();
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onResume() {
		super.onResume();

		Log.i("congqi----", "----chongqi");
		MusicRes.resumeAll();
	}

	@Override
	public void onClick(View v) {
		String filePath = "hit.mp3";

		switch (v.getId()) {
		case R.id.id_bt1:
			filePath = "hit.mp3";
			((Button) v).setText("angcyo".substring(2, 4));
			break;
		case R.id.id_bt2:
			filePath = "music.mp3";
			break;
		default:
			break;
		}

		try {
			MusicRes.play(getApplicationContext(), filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
