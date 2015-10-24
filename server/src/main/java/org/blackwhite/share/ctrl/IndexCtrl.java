package org.blackwhite.share.ctrl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.blackwhite.share.render.AjaxRender;
import org.blackwhite.share.util.StringUtils;

import com.jfinal.core.Controller;

/**
 * 首页
 * @author luojh
 *
 */
public class IndexCtrl extends Controller{
	
	public void index(){
		renderText("Hello Hackathon!");
	}
	
	//TODO 静态数据
	public void experience(){
//		String keyword = getPara("keyword", "").trim();
//		String expect = getPara("expect", "").trim();
		//返回数据
		/**
		 * 数据结构如下
		 *[{
		 *	user:{id:1,username:"tiger",avatar:"http://xxx","lat":22.529665,lng:113.944419},
		 *  share:{id:1,name:"hhkb键盘",imageURL:"http://xxx",content:"xxx"},
		 *  comments:[{id:1,userId:1,username:"11",comment:"111",createTime:"2015-10-21 22:53:11"}]
		 *}] 
		 */
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		for (int i =0 ; i < 20; i++) {
			Map<String, Object> data = new HashMap<String,Object>();
			Map<String, Object> user = new HashMap<String,Object>();
			user.put("id", i+1);
			user.put("username", "小黑"+i);
			user.put("avatar", "http://xxx");
			user.put("score", 5.0);
			user.put("lat", 22.529665);
			user.put("lng", 113.944419);
			Map<String, Object> share = new HashMap<String,Object>();
			share.put("id", i+1);
			share.put("name", "hhkb键盘分享");
			share.put("imageURL", "http://xxx");
			share.put("content", "这个键盘值得分享");
			List<Map<String, Object>> comments = new LinkedList<Map<String,Object>>();
			for (int j = 0; j < 3; j++) {
				Map<String, Object> comment = new HashMap<String,Object>();
				comment.put("id", 1);
				comment.put("userId", 1);
				comment.put("username", "小白"+i);
				comment.put("avatar", "小白"+i);
				comment.put("comment", "好玩，下次还来玩");
				comment.put("createTime", StringUtils.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
				comments.add(comment);
			}
			data.put("user",user);
			data.put("share",share);
			data.put("comments",comments);
			list.add(data);
		}
		//同时推送通知给附近用户
		AjaxRender render = AjaxRender.success();
		render.addData("list", list);
		render(render);
	}
	
}
