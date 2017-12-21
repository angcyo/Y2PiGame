package com.angcyo.y2pigame.fragment;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.angcyo.y2pigame.R;

public class GameFragment extends BaseFragment implements View.OnClickListener {

	View txGameStartTip;
	TextView txGameContent, txGameScore, txGameOther, txGameTip;
	ImageButton btGamePlay, btGamePause;
	Animation gameTipAnim, gameContentAnim;
	DiscreteSeekBar seekBar;

	int gameScore = 0;// 游戏分数
	String gameData = "";// 数据源
	int gameOtherScore = 0;// 需要完成的分数
	int gameCount = 0;// 游戏计数器,

	int gameSpeed = 1000;// 游戏的速度,默认一秒

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_game, container, false);

		initView(rootView);
		startGameTipAnim();
		return rootView;
	}

	private void initView(View rootView) {
		txGameContent = (TextView) rootView
				.findViewById(R.id.id_tx_game_main_content);
		txGameScore = (TextView) rootView.findViewById(R.id.id_tx_game_score);
		txGameOther = (TextView) rootView.findViewById(R.id.id_tx_game_other);
		txGameStartTip = rootView.findViewById(R.id.id_layout_startgame_tip);
		txGameTip = (TextView) rootView.findViewById(R.id.id_tx_game_tip);

		btGamePlay = (ImageButton) rootView.findViewById(R.id.id_bt_game_play);
		btGamePause = (ImageButton) rootView
				.findViewById(R.id.id_bt_game_pause);

		seekBar = (DiscreteSeekBar) rootView
				.findViewById(R.id.id_seekbar_speed);
		seekBar.setVisibility(View.GONE);

		btGamePause.setOnClickListener(this);
		btGamePlay.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_bt_game_pause:
			togglePlayButton(true);
			gamePause();
			break;
		case R.id.id_bt_game_play:
			togglePlayButton(false);
			gameStart();
			break;

		default:
			break;
		}

		playSound();
	}

	void initGameView() {
		txGameScore.setVisibility(View.VISIBLE);
		txGameOther.setVisibility(View.VISIBLE);

		txGameStartTip.setVisibility(View.GONE);
		seekBar.setVisibility(View.VISIBLE);

		txGameScore.setText("分数:" + getGameScore());
		txGameOther.setText("剩余:" + (getOtherGameScore() - gameCount));
	}

	int getGameScore() {
		return gameScore;
	}

	int getOtherGameScore() {
		return gameOtherScore;
	}

	int getGameSpeed() {
		if (seekBar != null) {
			gameSpeed = seekBar.getProgress();
		}
		return gameSpeed;
	}

	void initGameData() {

		gameData = getResources().getString(R.string.str_pai);
		gameCount = 0;
		gameOtherScore = gameData.length();

		if (gameContentAnim == null) {
			gameContentAnim = AnimationUtils.loadAnimation(context,
					R.anim.anim_set_scale_alpha_btos);
		}
	}

	Handler handler = new Handler();

	void gameStart() {
		clearGameTipAnim();
		initGameData();
		initGameView();

		handler.post(gameRun);
	}

	Runnable gameRun = new Runnable() {

		@Override
		public void run() {
			if (gameCount >= gameOtherScore) {
				gameFinish();
				return;
			}

			setGameContent(gameData.charAt(gameCount));
			setGameScore(1);

			handler.postDelayed(gameRun, getGameSpeed());
		}
	};

	protected void setGameContent(char charAt) {
		txGameContent.setText(charAt + "");
		txGameContent.startAnimation(gameContentAnim);
	}

	protected void setGameScore(int i) {
		gameScore += i;// 分数添加i
		gameCount += 1;// 计数器添加1
		initGameView();
	}

	void gamePause() {
		handler.removeCallbacks(gameRun);
	}

	void gameFinish() {
		handler.removeCallbacks(gameRun);
		addFragment(new GameFinishFragment());

		initGameData();
		initGameView();
		togglePlayButton(true);
	}

	void startGameTipAnim() {
		if (gameTipAnim == null) {
			gameTipAnim = AnimationUtils.loadAnimation(context,
					R.anim.scale_small_to_big);
			gameTipAnim.setFillAfter(true);
			gameTipAnim.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					txGameTip.startAnimation(gameTipAnim);
				}
			});
		}

		txGameTip.startAnimation(gameTipAnim);
	}

	void clearGameTipAnim() {
		if (txGameTip != null) {
			txGameTip.clearAnimation();
		}

	}

	void togglePlayButton(boolean isPlay) {
		if (isPlay) {
			btGamePlay.setVisibility(View.VISIBLE);
			btGamePause.setVisibility(View.GONE);
		} else {
			btGamePlay.setVisibility(View.GONE);
			btGamePause.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onDestroyView() {
		isDestroyAnim = false;
		super.onDestroyView();
	}

}
