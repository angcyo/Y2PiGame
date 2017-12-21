package com.angcyo.y2pigame.fragment;

import java.util.Random;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.angcyo.set.Setting;
import com.angcyo.y2pigame.MainActivity;
import com.angcyo.y2pigame.R;

public class MainFragment extends BaseFragment implements View.OnClickListener {
	View logoContent;
	ImageView logo1, logo2;
	Animation animationLogo1, animationLogo2;
	Handler handle = new Handler();
	Random random = new Random();

	Button btGameStart, btGameSetting, btGamePai;
	TextView txThankView;

	@Override
	public void onDestroyView() {
		isDestroyAnim = false;

		super.onDestroyView();
		clearLogoAnimation();
		clearHeadTitleAnimation();
	}

	private void clearHeadTitleAnimation() {
		for (TextView textView : txTitleList) {
			if (textView != null) {
				textView.clearAnimation();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_main, container, false);

		initLogoView(rootView);
		initButtonView(rootView);
		initHeadTitleView(rootView);
		initOtherView(rootView);
		return rootView;
	}

	TextView[] txTitleList = new TextView[5];

	private void initHeadTitleView(View rootView) {
		txTitleList[0] = (TextView) rootView.findViewById(R.id.id_head_title1);
		txTitleList[1] = (TextView) rootView.findViewById(R.id.id_head_title2);
		txTitleList[2] = (TextView) rootView.findViewById(R.id.id_head_title3);
		txTitleList[3] = (TextView) rootView.findViewById(R.id.id_head_title4);
		txTitleList[4] = (TextView) rootView.findViewById(R.id.id_head_title5);

		Animation[] anims = new Animation[txTitleList.length];
		for (int i = 0; i < txTitleList.length; i++) {
			Animation animation = AnimationUtils.loadAnimation(context,
					R.anim.anim_set_downanda);
			animation.setStartOffset(200 * i);
			anims[i] = animation;
		}

		for (int i = 0; i < anims.length; i++) {
			txTitleList[i].startAnimation(anims[i]);
		}

	}

	private void initOtherView(View rootView) {
		txThankView = (TextView) rootView.findViewById(R.id.id_tx_thank_tip);
		txThankView.setMovementMethod(LinkMovementMethod.getInstance());

	}

	private void initButtonView(View rootView) {
		btGamePai = (Button) rootView.findViewById(R.id.id_bt_paishige);
		btGameSetting = (Button) rootView.findViewById(R.id.id_bt_setting);
		btGameStart = (Button) rootView.findViewById(R.id.id_bt_startgame);

		btGamePai.setOnClickListener(this);
		btGameSetting.setOnClickListener(this);
		btGameStart.setOnClickListener(this);

	}

	private void initLogoView(View rootView) {
		logoContent = rootView.findViewById(R.id.id_logo_content);
		logo1 = (ImageView) rootView.findViewById(R.id.id_img_logo_1);
		logo2 = (ImageView) rootView.findViewById(R.id.id_img_logo_2);

		resetLogoViewPostion();

		animationLogo2 = AnimationUtils.loadAnimation(context,
				R.anim.anim_set_sanda);
		animationLogo1 = AnimationUtils.loadAnimation(context,
				R.anim.anim_set_sanda_s);

		animationLogo2.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				resetLogoViewPostion();
				startLogoAnimation();

			}
		});

		startLogoAnimation();

	}

	void resetLogoViewPostion() {
		if (logoContent != null && logo1 != null && logo2 != null) {
			Point point = getRandomPoint();

			// logoContent.layout(point.x, point.y, logo, 0);
			logoContent.setPadding(point.x, point.y, 0, 0);

			initLogoRotation(getRandomRotation());
		}
	}

	float getRandomRotation() {
		float ret = 0;
		switch (random.nextInt(3)) {
		case 0:
			ret = 0;
			break;
		case 1:
			ret = 30;
			break;
		case 2:
			ret = -30;
			break;
		default:
			ret = 0;
			break;
		}

		return ret;// 30的倍数
	}

	Point getRandomPoint() {
		Point point = new Point();
		point.x = random
				.nextInt(context.getResources().getDisplayMetrics().widthPixels - 2 * 60) + 10;// 预留屏幕左右两边10的像素
		point.y = random
				.nextInt(context.getResources().getDisplayMetrics().heightPixels - 2 * 60) + 10;// 预留屏幕左右两边10的像素

		return point;

	}

	void startLogoAnimation() {
		if (logo1 != null && logo2 != null) {
			logo1.startAnimation(animationLogo1);
			logo2.startAnimation(animationLogo2);
		}
	}

	void clearLogoAnimation() {
		if (logo1 != null && logo2 != null) {
			logo1.clearAnimation();
			logo2.clearAnimation();
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	void initLogoRotation(float rotation) {
		if (logo1 != null && logo2 != null) {
			logo1.setRotation(rotation);
			logo2.setRotation(rotation);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_bt_paishige:
			addFragment(new PaiImageFragment());
			break;
		case R.id.id_bt_setting:
			addFragment(new SettingFragment());
			// addFragment(new GameFinishFragment());
			break;
		case R.id.id_bt_startgame:
			jumpGameFragment();
			break;

		default:
			break;
		}

		playSound();
	}

	private void jumpGameFragment() {
		int gameStyle = Setting.Get(getActivity()).getInt(
				SettingFragment.key_game_style, SettingFragment.game_style1);
		switch (gameStyle) {
		case SettingFragment.game_style1:
			replaceFragment(new GameFragment());
			break;
		case SettingFragment.game_style2:
			replaceFragment(new Game2Fragment());
			break;

		default:
			break;
		}

	}
}
