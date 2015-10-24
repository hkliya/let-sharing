package org.blackwhite.share.ctrl;

import org.blackwhite.share.render.AjaxRender;

import com.jfinal.core.Controller;

/**
 * 我的分享
 * @author luojh
 *
 */
public class ShareCtrl extends Controller{

	public void add(){
		render(AjaxRender.success());
	}
	
	/**
	 * 选择成功
	 * 下单,进入体验流程
	 */
	public void choose(){
		AjaxRender render = AjaxRender.success();
		render(render);
	}
	
	//取消订单
	public void cancel(){
		AjaxRender render = AjaxRender.success();
		render(render);
	}
	
	//完成
	public void finish(){
		AjaxRender render = AjaxRender.success();
		render(render);
	}
	
	//评论
	public void comment(){
		AjaxRender render = AjaxRender.success();
		render(render);
	}
}
