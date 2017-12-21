package com.angcyo.music.core;

import java.util.ArrayList;

/**
 * (c) 2010 Nicolas Gramlich (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 15:01:23 - 13.06.2010
 */
public class MusicManager extends BaseAudioManager<Music> {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public MusicManager() {

	}

	public void pause() {
		final ArrayList<Music> audioEntities = this.mAudioEntities;
		for (int i = audioEntities.size() - 1; i >= 0; i--) {
			final IAudioEntity audioEntity = audioEntities.get(i);
			audioEntity.pause();
		}
	}

	public void resume() {
		final ArrayList<Music> audioEntities = this.mAudioEntities;
		for (int i = audioEntities.size() - 1; i >= 0; i--) {
			final IAudioEntity audioEntity = audioEntities.get(i);
			audioEntity.resume();
		}
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
