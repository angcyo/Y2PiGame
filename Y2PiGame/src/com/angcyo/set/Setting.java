package com.angcyo.set;

import android.content.Context;
import android.content.SharedPreferences;

public class Setting {
	// 偏好设置
	static String SETTING_FILENAME = "APP_SETTING";

	public static SharedPreferences Get(Context context) {
		return context.getSharedPreferences(SETTING_FILENAME,
				Context.MODE_PRIVATE);
	}

	public static SharedPreferences.Editor Set(Context context) {
		return Get(context).edit();
	}

}
