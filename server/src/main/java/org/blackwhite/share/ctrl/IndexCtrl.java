package org.blackwhite.share.ctrl;

import java.util.List;

import org.blackwhite.share.model.ServiceModel;
import org.blackwhite.share.model.UserModel;
import org.blackwhite.share.render.AjaxRender;
import org.blackwhite.share.util.ModelUtils;

import com.jfinal.core.Controller;

/**
 * 首页
 * @author luojh
 *
 */
public class IndexCtrl extends Controller{
	
	public void index(){
		renderText("Hackathon ~!");
	}
	
	public void experience(){
		String keyword = getPara("keyword", "").trim();
		String expect = getPara("expect", "").trim();
		
		//返回数据，同时推送给附近用户
		List<ServiceModel> serviceList = ServiceModel.dao.search(keyword, 20);
		List<Integer> userIds = ModelUtils.getFieldFromModel(serviceList, "userId", Integer.class);
		List<UserModel> userList = UserModel.dao.listByIds(userIds);
		
		AjaxRender render = AjaxRender.success();
		render(render);
	}
	
}
