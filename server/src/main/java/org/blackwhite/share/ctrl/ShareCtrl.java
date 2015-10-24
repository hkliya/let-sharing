package org.blackwhite.share.ctrl;

import org.blackwhite.share.render.AjaxRender;

import com.jfinal.core.Controller;

public class ShareCtrl extends Controller{

	public void add(){
		render(AjaxRender.success());
	}
	
	public void list(){
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 1);
		AjaxRender render = AjaxRender.success();
		render(render);
	}
}
