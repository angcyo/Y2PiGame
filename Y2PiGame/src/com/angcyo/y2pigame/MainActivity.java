package com.angcyo.y2pigame;

import java.io.IOException;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.angcyo.music.MusicRes;
import com.angcyo.music.core.Music;
import com.angcyo.set.Setting;
import com.angcyo.sound.core.Sound;
import com.angcyo.sound.core.SoundFactory;
import com.angcyo.sound.core.SoundManager;
import com.angcyo.y2pigame.fragment.BaseFragment;
import com.angcyo.y2pigame.fragment.ExitFragment;
import com.angcyo.y2pigame.fragment.MainFragment;
import com.angcyo.y2pigame.fragment.SettingFragment;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

public class MainActivity extends FragmentActivity {

	Music musicBg;
	Sound gameSound;// 游戏点击的音效
	ExitFragment exitFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			gameSound = SoundFactory.createSoundFromAsset(new SoundManager(),
					getApplicationContext(), "merge.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}

		fManager = this.getSupportFragmentManager();
		replaceFragment(new MainFragment());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	FragmentManager fManager;// 碎片管理对象

	/**
	 * 添加Fragment到主界面
	 * 
	 * @param fragment
	 */
	public void addFragment(BaseFragment fragment) {
		if (fManager != null) {
			FragmentTransaction fTransaction = fManager.beginTransaction();
			fTransaction.setCustomAnimations(R.anim.tran_ttb_t,
					R.anim.tran_ttb_b);
			fTransaction.add(R.id.id_layout_content, fragment, fragment
					.getClass().getSimpleName());
			fTransaction.addToBackStack(fragment.getClass().getSimpleName());
			fTransaction.commit();
		}
	}

	/**
	 * 用Fragment替换主界面
	 * 
	 * @param fragment
	 */
	public void replaceFragment(BaseFragment fragment) {
		if (fManager != null) {
			FragmentTransaction fTransaction = fManager.beginTransaction();
			fTransaction.setCustomAnimations(R.anim.tran_ttb_t,
					R.anim.tran_ttb_b);
			fTransaction.replace(R.id.id_layout_content, fragment, fragment
					.getClass().getSimpleName());

			fTransaction.commit();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MusicRes.releaseAll();
		gameSound.release();
	}

	@Override
	protected void onPause() {
		super.onPause();
		pauseMusic();

	}

	@Override
	protected void onResume() {
		super.onResume();

		playMusic();
	}

	@Override
	protected void onStart() {
		super.onStart();

		playMusic();
	}

	public void playMusic() {
		if (Setting.Get(getApplicationContext()).getBoolean(
				SettingFragment.key_play, true)) {
			if (musicBg == null) {
				try {
					musicBg = MusicRes.play(getApplicationContext(),
							"music_bg.mp3");
					musicBg.setLooping(true);
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				MusicRes.resumeAll();
			}
		}

	}

	public void pauseMusic() {
		// if (Setting.Get(getApplicationContext()).getBoolean(
		// SettingFragment.key_play, true)) {
		MusicRes.pauseAll();
		// }
	}

	// 播放音效
	public void playSound() {
		if (Setting.Get(getApplicationContext()).getBoolean(
				SettingFragment.key_play_sound, true)) {
			gameSound.play();
		}
	}

	@Override
	public void onBackPressed() {
		if (fManager.getBackStackEntryCount() > 0) {
			fManager.popBackStack();
			return;
		}

		showExitDialog();

		// super.onBackPressed();
	}

	private void showExitDialog() {
		final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
		Effectstype effect = Effectstype.Newspager;
		dialogBuilder
				.withTitle(null)
				.withDialogColor("#50003552")
				// .withTitle(null) no title
				// .withTitleColor("#FFFFFF")
				// def
				// .withDividerColor("#11000000")
				// def
				// .withMessage("接下来你要")
				.withMessage(null)
				// no Msg
				.withMessageColor("#FFFFFFFF")
				// def | withMessageColor(int resid)
				// def | withDialogColor(int resid) //def
				.withIcon(getResources().getDrawable(R.drawable.ic_launcher))
				.isCancelableOnTouchOutside(true) // def | isCancelable(true)
				.withDuration(700) // def
				.withEffect(effect) // def Effectstype.Slidetop
				.withButton1Text("退出游戏") // def gone
				.withButton2Text("回到首页") // def gone
				.setCustomView(R.layout.layout_exit, getApplicationContext()) // .setCustomView(View
				// or
				// ResId,context)
				.setButton1Click(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						MainActivity.super.onBackPressed();
					}
				}).setButton2Click(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						BaseFragment baseFragment = (BaseFragment) fManager
								.findFragmentByTag(MainFragment.class
										.getSimpleName());
						if (baseFragment == null) {
							replaceFragment(new MainFragment());
						}
						dialogBuilder.dismiss();
					}
				}).show();
	}

}
