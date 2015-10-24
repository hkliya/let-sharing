package org.blackwhite.share.api.ctrl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.blackwhite.share.model.ExprModel;
import org.blackwhite.share.model.ShareModel;
import org.blackwhite.share.model.UserModel;
import org.blackwhite.share.render.AjaxRender;
import org.blackwhite.share.util.CollectionUtils;
import org.blackwhite.share.util.ModelUtils;

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
	
	/**
	 * 数据结构如下
	 *[{
	 *	user:{id:1,username:"tiger",avatar:"http://xxx","lat":22.529665,lng:113.944419},
	 *  share:{id:1,name:"hhkb键盘",imageURL:"http://xxx",content:"xxx"},
	 *  comments:[{id:1,userId:1,username:"11",comment:"111",createTime:"2015-10-21 22:53:11"}]
	 *}] 
	 */
	public void experience(){
		String keyword = getPara("keyword", "").trim();
		int userId = 2;
//		String expect = getPara("expect", "").trim();
		int pageSize = getParaToInt("pageSize", 20);
		List<ShareModel> shares = ShareModel.dao.search(pageSize,userId,keyword);
		List<Integer> userIds = ModelUtils.getFieldFromModel(shares, "userId", Integer.class);
		List<UserModel> users = UserModel.dao.findByIds(userIds);
		Map<Integer, UserModel> userMap = ModelUtils.modelListToMap(users, "id", Integer.class);
		List<Integer> shareIds = ModelUtils.getFieldFromModel(shares, "id", Integer.class);
		List<ExprModel> exprs = ExprModel.dao.findByShareIds(shareIds);
		//分组组装
		Map<Integer, List<ExprModel>> exprMap = new HashMap<Integer, List<ExprModel>>();
		for (ExprModel expr : exprs) {
			int shareId = expr.getInt("shareId");
			List<ExprModel> tExprs = exprMap.get(shareId);
			if(tExprs == null){
				tExprs = new LinkedList<ExprModel>();
				exprMap.put(shareId,tExprs);
			}
			tExprs.add(expr);
		}
		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		for (ShareModel share : shares) {
			
			int shareId = share.getInt("id");
			int tUserId = share.getInt("userId");
			UserModel user = userMap.get(tUserId);
			List<ExprModel> tExprs = CollectionUtils.toSafeList(exprMap.get(shareId));
			
			Map<String, Object> data = new HashMap<String,Object>();
			data.put("user",user);
			data.put("share",share);
			data.put("comments",tExprs);
			list.add(data);
		}
		//TODO 同时推送通知给附近用户
		
		AjaxRender render = AjaxRender.success();
		render.addData("list", list);
		render(render);
	}
	
}
