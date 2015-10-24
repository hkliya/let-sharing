package org.blackwhite.share.cfg;

import org.blackwhite.share.api.ctrl.CircleCtrl;
import org.blackwhite.share.api.ctrl.ExprCtrl;
import org.blackwhite.share.api.ctrl.IndexCtrl;
import org.blackwhite.share.api.ctrl.ProductCtrl;
import org.blackwhite.share.api.ctrl.ShareCtrl;
import org.blackwhite.share.api.ctrl.UserCtrl;

import com.jfinal.config.Routes;

public class HackApiRoute extends Routes{

	@Override
	public void config() {
		add("/api",IndexCtrl.class);//首页
		add("/api/user",UserCtrl.class);//用户模块
		add("/api/product",ProductCtrl.class);//商品相关
		add("/api/share",ShareCtrl.class);//分享
		add("/api/expr",ExprCtrl.class);//体验
		add("/api/circle",CircleCtrl.class);//圈子
		
	}
}
