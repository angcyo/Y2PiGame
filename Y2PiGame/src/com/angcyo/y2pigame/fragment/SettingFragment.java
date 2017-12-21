package com.angcyo.y2pigame.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.angcyo.set.Setting;
import com.angcyo.y2pigame.MainActivity;
import com.angcyo.y2pigame.R;
import com.material.widget.RadioButton;
import com.material.widget.Switch;

public class SettingFragment extends BaseFragment implements
		OnCheckedChangeListener {

	public static String key_play = "play_music";
	public static String key_play_sound = "play_sound";
	public static String key_game_style = "game_style";// 滚动模式,默认
	public static final int game_style1 = 1;// 滚动模式,默认
	public static final int game_style2 = 2;// 填字模式

	Switch musicSwitch;// 游戏声音的开关
	TextView musicSwitchTip;

	Switch soundSwitch;// 游戏声效开关
	TextView soundSwitchTip;

	RadioButton gameRb1, gameRb2;// 游戏模式1,2

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater
				.inflate(R.layout.fragment_setting, container, false);

		initView(rootView);

		initViewData();
		return rootView;
	}

	private void initViewData() {
		boolean isPlay = Setting.Get(getActivity()).getBoolean(key_play, true);
		boolean isSoundPlay = Setting.Get(getActivity()).getBoolean(
				key_play_sound, true);
		musicSwitch.setChecked(isPlay);
		if (isPlay) {
			musicSwitchTip.setText("音乐已打开");
		} else {
			musicSwitchTip.setText("音乐已关闭");
		}

		soundSwitch.setChecked(isSoundPlay);
		if (isSoundPlay) {
			soundSwitchTip.setText("音笑已打开");
		} else {
			soundSwitchTip.setText("音笑已关闭");
		}

		int gameStyle = Setting.Get(getActivity()).getInt(key_game_style,
				game_style1);

		switch (gameStyle) {
		case game_style1:
			gameRb1.setChecked(true);
			gameRb2.setChecked(false);
			break;
		case game_style2:
			gameRb1.setChecked(false);
			gameRb2.setChecked(true);
			break;
		default:
			gameRb1.setChecked(true);
			gameRb2.setChecked(false);
			break;
		}

		gameRb1.invalidate();
		gameRb2.invalidate();

	}

	private void initView(View rootView) {
		musicSwitch = (Switch) rootView.findViewById(R.id.id_switch_music);
		musicSwitchTip = (TextView) rootView
				.findViewById(R.id.id_tx_switch_tip);

		soundSwitch = (Switch) rootView.findViewById(R.id.id_switch_sound);
		soundSwitchTip = (TextView) rootView
				.findViewById(R.id.id_tx_switch_sound_tip);

		gameRb1 = (RadioButton) rootView.findViewById(R.id.id_game_sytle_1);
		gameRb2 = (RadioButton) rootView.findViewById(R.id.id_game_sytle_2);

		musicSwitch.setOnCheckedChangeListener(this);
		soundSwitch.setOnCheckedChangeListener(this);
		gameRb1.setOnCheckedChangeListener(this);
		gameRb2.setOnCheckedChangeListener(this);

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.id_switch_music:
			Setting.Set(getActivity()).putBoolean(key_play, isChecked).commit();
			if (isChecked) {
				((MainActivity) context).playMusic();

			} else {
				((MainActivity) context).pauseMusic();
			}
			break;
		case R.id.id_switch_sound:
			Setting.Set(getActivity()).putBoolean(key_play_sound, isChecked)
					.commit();
			break;
		case R.id.id_game_sytle_1:
			if (isChecked) {
				changeGameStyle(game_style1);
			} else {
				changeGameStyle(game_style2);
			}

			break;
		case R.id.id_game_sytle_2:
			gameRb1.setChecked(!isChecked);
			if (isChecked) {
				changeGameStyle(game_style2);
			} else {
				changeGameStyle(game_style1);
			}

			break;
		default:
			break;
		}

		initViewData();
	}

	private void changeGameStyle(int style) {
		switch (style) {
		case game_style1:
			Setting.Set(getActivity()).putInt(key_game_style, game_style1)
					.commit();
			break;
		case game_style2:
			Setting.Set(getActivity()).putInt(key_game_style, game_style2)
					.commit();
			break;
		default:
			Setting.Set(getActivity()).putInt(key_game_style, game_style1)
					.commit();
			break;
		}

	}
}
