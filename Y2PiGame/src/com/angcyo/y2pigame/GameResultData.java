package com.angcyo.y2pigame;

import java.util.ArrayList;
import java.util.List;

public class GameResultData {
	public List<Integer> list;// 保存位置信息
	public int nRightCount;// 正确数据的计数器
	public int nErrorCount;// 错误数据的计数器

	public GameResultData() {
		this.list = new ArrayList<Integer>();
		this.nRightCount = 0;
		this.nErrorCount = 0;
	}

}
