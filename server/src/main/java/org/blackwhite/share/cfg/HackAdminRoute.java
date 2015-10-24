package org.blackwhite.share.cfg;

import org.blackwhite.share.admin.ctrl.AdminIndexCtrl;

import com.jfinal.config.Routes;

public class HackAdminRoute extends Routes{

	@Override
	public void config() {
		add("/",AdminIndexCtrl.class);
	}
}
