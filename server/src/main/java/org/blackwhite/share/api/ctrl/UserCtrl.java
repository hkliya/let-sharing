package org.blackwhite.share.api.ctrl;

import org.blackwhite.share.model.BoughtHistoryModel;
import org.blackwhite.share.model.UserLocModel;
import org.blackwhite.share.model.UserModel;
import org.blackwhite.share.render.AjaxRender;
import org.blackwhite.share.util.StringUtils;
import org.blackwhite.share.validator.LoginValidator;
import org.blackwhite.share.validator.RegisterValidator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

/**
 * 用户
 * @author luojh
 *
 */
public class UserCtrl extends Controller{

	@Before(LoginValidator.class)
	public void login(){
		String username = getPara("username", "").trim();
		String password = getPara("password", "").trim();
		UserModel user = UserModel.dao.findByUsername(username);
		if(user == null){
			render(AjaxRender.error("用户不存在"));
			return;
		}
		if(!user.checkPwd(password)){
			render(AjaxRender.error("用户名或密码不正确"));
			return;
		}
		user.remove("password","salt");
		String accessToken = StringUtils.uuid();
		user.put("token",accessToken);
		Cache cache = Redis.use(accessToken);
		cache.setex(accessToken, 7*24*60*60,user);
		AjaxRender render = AjaxRender.success("登陆成功");
		render.addData("user", user);
		render(render);
	}
	
	@Before(RegisterValidator.class)
	public void regist(){
		String username = getPara("username", "").trim();
		String password = getPara("password", "").trim();
		UserModel.dao.add(username,password);
		AjaxRender render = AjaxRender.success("注册成功");
		render(render);
	}
	
	//电商购物历史导入
	public void historyImport(){
		String history = getPara("history", "").trim();
		String username = getPara("username","").trim();  
		UserModel user = UserModel.dao.findByUsername(username);
		if(user == null){
			render(AjaxRender.error("请先登录"));
			return;
		}
		int userId = user.getInt("id");
		JSONArray arr = JSON.parseArray(history);
		BoughtHistoryModel.dao.add(userId,arr);
		AjaxRender render = AjaxRender.success();
		render(render);
	}
	
	//历史查询
	public void historyList(){
		String username = getPara("username","").trim();
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",20);
		UserModel user = UserModel.dao.findByUsername(username);
		if(user == null){
			render(AjaxRender.error("请先登录"));
			return;
		}
		int userId = user.getInt("id");
		Page<BoughtHistoryModel> page = BoughtHistoryModel.dao.page(pageNumber, pageSize, userId);
		AjaxRender render = AjaxRender.success();
		render.addData("page", page);
		render(render);
	}
	
	public void uploadLoc(){
		int userId = 1;
		String lat = getPara("lat", "0").trim();
		String lng = getPara("lng", "0").trim();
		
		double latVal = Double.valueOf(lat);
		double lngVal = Double.valueOf(lng);
		
		if(latVal == 0 || lngVal == 0){
			render(AjaxRender.success());
			return;
		}
		UserLocModel.dao.save(userId,latVal,lngVal);
		render(AjaxRender.success());
	}
}
