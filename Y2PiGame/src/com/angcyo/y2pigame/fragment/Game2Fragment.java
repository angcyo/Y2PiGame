package com.angcyo.y2pigame.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.angcyo.y2pigame.GameResultData;
import com.angcyo.y2pigame.R;

public class Game2Fragment extends BaseFragment implements OnClickListener {

	View viewLayoutFirst, viewLayoutContent;
	TextView txGameScore, txGameOther, txGameStartTip;
	ImageButton btGamePlay;
	Animation gameTipAnim;

	TextView txGameData;// 显示的数据
	EditText etGameData;// 游戏编辑框

	Button btGameCalc, btGameNext;

	int gameScore = 0;// 游戏分数
	String gameData = "";// 数据源
	String gameDataCn;// 中文游戏数据
	String gameDataShow;// 显示的游戏数据,包含字符的
	int gameOtherScore = 0;// 需要完成的分数
	int gameCount = 0;// 游戏计数器,

	int curScoreFlag = -1;// 当前计分的标记

	final int readNum = 20;// 每次读取的字符

	boolean isGameFinish = false;// 游戏是否结束

	// List<Integer> gameResultList = new ArrayList<Integer>();//
	// 游戏匹配结果保存,保存了错误的字符位置
	GameResultData gameResult;//
	View viewLayoutGameResult;
	TextView txGameRightCount, txGameErrorCount, txGameSpaceCount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_game2, container, false);

		initView(rootView);
		initGameStartAnim();
		return rootView;
	}

	private void initGameStartAnim() {
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
					txGameStartTip.startAnimation(gameTipAnim);
				}
			});
		}

		txGameStartTip.startAnimation(gameTipAnim);
	}

	private void initView(View rootView) {
		viewLayoutFirst = rootView.findViewById(R.id.id_layout_game2_first);
		viewLayoutContent = rootView.findViewById(R.id.id_layout_game2_content);
		txGameScore = (TextView) rootView.findViewById(R.id.id_tx_game_score);// 游戏分数
		txGameOther = (TextView) rootView.findViewById(R.id.id_tx_game_other);// 游戏总大小

		txGameData = (TextView) rootView.findViewById(R.id.id_text_game_data);
		etGameData = (EditText) rootView.findViewById(R.id.id_edit_game_data);

		btGamePlay = (ImageButton) rootView.findViewById(R.id.id_bt_game_play);
		txGameStartTip = (TextView) rootView.findViewById(R.id.id_tx_game_tip);

		btGameCalc = (Button) rootView.findViewById(R.id.id_bt_game_calc);
		btGameNext = (Button) rootView.findViewById(R.id.id_bt_game_next);

		viewLayoutGameResult = rootView
				.findViewById(R.id.id_layout_game_result);
		viewLayoutGameResult.setVisibility(View.GONE);// 默认隐藏
		txGameErrorCount = (TextView) rootView
				.findViewById(R.id.id_tx_error_count);
		txGameRightCount = (TextView) rootView
				.findViewById(R.id.id_tx_right_count);
		txGameSpaceCount = (TextView) rootView
				.findViewById(R.id.id_tx_space_count);

		btGamePlay.setOnClickListener(this);
		btGameCalc.setOnClickListener(this);
		btGameNext.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_bt_game_play:
			playGame();
			break;

		case R.id.id_bt_game_calc:
			gameCalc();
			break;

		case R.id.id_bt_game_next:
			gameNext();
			break;
		default:
			break;
		}

		playSound();
	}

	private void gameCalc() {
		String strPut = getGamePuText();// 获取游戏输入的数据
		if (strPut == null || strPut.length() == 0) {
			etGameData.setError("这怎么玩?");
			return;
		}
		String strRightData = getGameRightData();// 游戏正确的数据

		compareString(strPut, strRightData);// 比较2个字符,结果保存在list中

		showGameResult();
	}

	private void showGameResult() {
		if (gameResult == null) {
			return;
		}

		if (curScoreFlag != gameCount) {
			gameScore += gameResult.nRightCount;
			curScoreFlag = gameCount;
			txGameScore.setText("分数:" + getGameScore());
		}

		viewLayoutGameResult.setVisibility(View.VISIBLE);
		String strRight = "个...继续加油哦!";
		String strError = "个...不够努力哦!";
		String strSpace = "个...不要留空白哦^_^";
		if (gameResult.nRightCount >= Math.min(gameOtherScore, readNum)) {
			strRight = "个...全对哟,我已无法计算了!!!";
		}
		if (gameResult.nErrorCount == 0) {
			strError = "个...0错误,你已经超神了!!!";
		}
		int spaceLen = Math.min(gameOtherScore, readNum)
				- etGameData.getText().toString().length();
		if (spaceLen == 0) {
			strSpace = "个...此处为你独守 ︿(￣︶￣)︿";
		}

		txGameRightCount.setText("正确->" + gameResult.nRightCount + strRight);
		txGameErrorCount.setText("错误->" + gameResult.nErrorCount + strError);
		txGameSpaceCount.setText("未填->" + spaceLen + strSpace);

		// 高亮错误位置
		SpannableString ss = new SpannableString(etGameData.getText()
				.toString());
		for (Integer pos : gameResult.list) {
			// ss.setSpan(new BackgroundColorSpan(Color.RED), pos, pos + 1,
			// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			ss.setSpan(new ForegroundColorSpan(Color.RED), pos, pos + 1,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		etGameData.setText(ss);
		if (gameResult.nErrorCount != 0) {
			etGameData.setError("亲,记得检查哦!");
		}

		etGameData.setSelection(etGameData.getText().toString().length(),
				etGameData.getText().toString().length());
	}

	// 比较2个字符串
	void compareString(String strPut, String strRight) {
		if (strPut == null || strRight == null) {
			return;
		}
		gameResult = new GameResultData();
		int len = Math.min(strPut.length(), strRight.length());
		for (int i = 0; i < len; i++) {
			if (strPut.charAt(i) != strRight.charAt(i)) {
				gameResult.list.add(i);
				gameResult.nErrorCount += 1;
			} else {
				gameResult.nRightCount += 1;
			}
		}

		return;
	}

	String getGameRightData() {
		int end;
		if (gameOtherScore > readNum) {
			end = readNum;
		} else {
			end = gameOtherScore;
		}

		return gameData.substring(gameCount * readNum, gameCount * readNum
				+ end + 1);
	}

	String getGamePuText() {
		return etGameData.getText().toString();
	}

	private void gameNext() {
		gameCount += 1;// 下一段

		viewLayoutGameResult.setVisibility(View.GONE);
		etGameData.setError(null);
		initGameViewData();

	}

	void playGame() {
		showGameContentView();
		initGameData();
		initGameViewData();
	}

	// private void initGameNextData() {
	// loadGameData();
	// }

	private void initGameData() {
		gameScore = getGameScore();
		gameData = getString(R.string.str_pai);//
		gameDataCn = getString(R.string.str_pai_data);// 不含标点符号
		gameOtherScore = gameDataCn.length();//
		gameCount = 0;// 从1开始,每次读取20个字符
	}

	private int getGameScore() {
		return gameScore;
	}

	private int getGameOtherScore() {
		return gameOtherScore;
	}

	private void playFinish() {
		addFragment(new GameFinishFragment());
		isGameFinish = false;
		initGameData();
		initGameViewData();
	}

	private void initGameViewData() {
		txGameScore.setText("分数:" + getGameScore());
		txGameOther.setText("剩余:" + getGameOtherScore());

		txGameData.setText(loadGameData());
		etGameData.setText("");
	}

	String loadGameData() {
		if (isGameFinish) {
			playFinish();
		}

		// 装载游戏数据
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < readNum; i++) {
			if ((i + readNum * gameCount) >= gameDataCn.length()) {
				isGameFinish = true;
				break;
			} else {
				gameOtherScore -= 1;
				stringBuilder
						.append(gameDataCn.charAt(i + readNum * gameCount));
			}
		}

		return stringBuilder.toString();
	}

	// 显示游戏布局
	private void showGameContentView() {
		if (viewLayoutContent.getVisibility() == View.VISIBLE) {
			return;
		}

		gameTipAnim.cancel();
		txGameStartTip.clearAnimation();
		viewLayoutFirst.setVisibility(View.INVISIBLE);
		viewLayoutContent.setVisibility(View.VISIBLE);
		viewLayoutContent.startAnimation(AnimationUtils.loadAnimation(
				getActivity(), R.anim.scale_small_to_big_2));
	}

	@Override
	public void onDestroyView() {
		isDestroyAnim = false;
		super.onDestroyView();
	}
	
	
	
}
