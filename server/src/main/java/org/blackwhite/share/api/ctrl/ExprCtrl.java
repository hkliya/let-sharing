package org.blackwhite.share.api.ctrl;

import org.blackwhite.share.model.ExprModel;
import org.blackwhite.share.render.AjaxRender;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * 我的体验
 * @author luojh
 *
 */
public class ExprCtrl extends Controller{

	//我的体验
	public void list(){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",20);
		int userId = 1;
		Page<ExprModel> page = ExprModel.dao.page(pageNumber, pageSize, userId);
		AjaxRender render = AjaxRender.success();
		render.addData("page", page);
		render(render);
	}
	
	//轮询是否有未完成的评论
	public void uncomment(){
		int userId = 1;
		ExprModel expr = ExprModel.dao.uncomment(userId);
		AjaxRender render = AjaxRender.success();
		render.addData("uncomment", expr);
		render(render);
	}
	
	//添加体验
	public void add(){
		int shareId = getParaToInt("shareId",0);
		int userId = 1;
		int exprId = ExprModel.dao.add(shareId,userId);
		if(exprId != 0){
			AjaxRender render = AjaxRender.success();
			render.addData("exprId", exprId);
			render(render);
		}else{
			render(AjaxRender.error());
		}
	}
	
	//取消订单
	public void cancel(){
		int shareId = getParaToInt("shareId",0);
		int userId = 1;
		boolean flag = ExprModel.dao.cancel(shareId,userId);
		if(flag){
			render(AjaxRender.success());
		}else{
			render(AjaxRender.error());
		}
	}
	
	//开始体验
	public void start(){
		int id = getParaToInt("id", 0);
		int userId = 1;
		boolean flag = ExprModel.dao.start(id,userId);
		if(flag){
			render(AjaxRender.success());
		}else{
			render(AjaxRender.error());
		}
	}
	
	//结束体验
	public void end(){
		int id = getParaToInt("id", 0);
		int userId = 1;
		boolean flag = ExprModel.dao.end(id,userId);
		if(flag){
			render(AjaxRender.success());
		}else{
			render(AjaxRender.error());
		}
	}
	
	//评论
	public void comment(){
		int id = getParaToInt("id", 0);
		int userId = 1;
		String comment = getPara("comment", "").trim();
		String score = getPara("score", "").trim();
		double tscore = Double.valueOf(score);
		boolean flag = ExprModel.dao.comment(id,userId,tscore,comment);
		if(flag){
			render(AjaxRender.success());
		}else{
			render(AjaxRender.error());
		}
	}
}
