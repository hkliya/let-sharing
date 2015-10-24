package org.blackwhite.share.cfg;

import org.blackwhite.share.ctrl.CircleCtrl;
import org.blackwhite.share.ctrl.IndexCtrl;
import org.blackwhite.share.ctrl.ProductCtrl;
import org.blackwhite.share.ctrl.UserCtrl;

import com.jfinal.config.Routes;

public class HackRoute extends Routes{

	@Override
	public void config() {
		add("/",IndexCtrl.class);
		add("/user",UserCtrl.class);
		add("/product",ProductCtrl.class);
		add("/circle",CircleCtrl.class);
	}
}
