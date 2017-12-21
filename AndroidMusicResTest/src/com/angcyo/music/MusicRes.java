package com.angcyo.music;

import java.io.IOException;

import com.angcyo.music.core.Music;
import com.angcyo.music.core.MusicFactory;
import com.angcyo.music.core.MusicManager;

import android.content.Context;

public class MusicRes {
	static MusicManager musicManager = new MusicManager();

	public static Music play(Context pContext, String pAssetPath)
			throws IOException {
		Music music = MusicFactory.createMusicFromAsset(musicManager, pContext,
				pAssetPath);
		music.play();

		return music;
	}

	public static void releaseAll() {
		musicManager.releaseAll();
	}

	public static void pauseAll() {
		musicManager.pause();
	}

	public static void resumeAll() {
		musicManager.resume();
	}

}
