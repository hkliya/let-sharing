package org.blackwhite.share.ctrl;

import org.blackwhite.share.render.AjaxRender;

import com.jfinal.core.Controller;

/**
 * 圈子
 * @author luojh
 *
 */
public class CircleCtrl extends Controller{

	public void catgory(){
		AjaxRender render = AjaxRender.success();
		render(render);
	}
}
