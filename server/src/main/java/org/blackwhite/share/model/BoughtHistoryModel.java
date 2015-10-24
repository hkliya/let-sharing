package org.blackwhite.share.model;

import java.util.Date;

import org.blackwhite.share.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class BoughtHistoryModel extends Model<BoughtHistoryModel>{

	private static final long serialVersionUID = -1204150762784113025L;
	public static final BoughtHistoryModel dao = new BoughtHistoryModel();
	
	public Page<BoughtHistoryModel> page(int pageNumber,int pageSize, int userId){
		String select = "select * ";
		String where = " from bought_history where userId = ? order by bought_at desc ";
		return paginate(pageNumber, pageSize, select, where, userId);
	}

	public void add(int userId,JSONArray arr) {
		for (int i = 0; i < arr.size(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			String name = obj.getString("name");
			String url = obj.getString("url");
			String imageURL = obj.getString("imageURL");
			String bought_at = obj.getString("bought_at");
			Date date = StringUtils.convertToDate(bought_at, "yyyy-MM-dd");
			date = date == null ? new Date() : date;
			if(findByname(userId, name)){
				continue;
			}
			BoughtHistoryModel history = new BoughtHistoryModel();
			history.set("userId", userId)
				   .set("name", name)
				   .set("url", url)
				   .set("imageURL", imageURL)
				   .set("bought_at", bought_at)
				   .save();
		}
	}
	
	public boolean findByname(int userId, String name){
		String where = " select count(id) from bought_history where userId = ? and name = ? ";
		Long cnt = Db.queryLong(where,userId,name);
		return cnt !=null && cnt > 0;
	}
}
