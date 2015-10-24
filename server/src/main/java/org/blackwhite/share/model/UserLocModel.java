package org.blackwhite.share.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 用户地理位置
 * @author luojh
 *
 */
public class UserLocModel extends Model<UserLocModel>{

	private static final long serialVersionUID = -409748046742248494L;
	public static final UserLocModel dao = new UserLocModel();
	
	public void save(int userId,double lat,double lng){
		UserLocModel loc = findByUserId(userId);
		if(loc == null){
			loc = new UserLocModel();
			loc.set("userId", userId)
			   .set("lat", lat)
			   .set("lng",lng)
			   .save();
			return;
		}
		loc.set("lat", lat)
			.set("lng", lng)
			.update();
	}
	
	private UserLocModel findByUserId(int userId){
		String sql = "select * from user_Loc where userId = ? ";
		return findFirst(sql,userId);
	}
}
