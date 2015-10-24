package org.blackwhite.share.cfg;

import org.blackwhite.share.ctrl.CircleCtrl;
import org.blackwhite.share.ctrl.ExprCtrl;
import org.blackwhite.share.ctrl.IndexCtrl;
import org.blackwhite.share.ctrl.ProductCtrl;
import org.blackwhite.share.ctrl.ShareCtrl;
import org.blackwhite.share.ctrl.UserCtrl;

import com.jfinal.config.Routes;

public class HackRoute extends Routes{

	@Override
	public void config() {
		add("/",IndexCtrl.class);//首页
		add("/user",UserCtrl.class);//用户模块
		add("/product",ProductCtrl.class);//商品相关
		add("/share",ShareCtrl.class);//分享
		add("/expr",ExprCtrl.class);//体验
		add("/circle",CircleCtrl.class);//圈子
		
	}
}
