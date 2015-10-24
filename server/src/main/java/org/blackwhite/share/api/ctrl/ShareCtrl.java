package org.blackwhite.share.api.ctrl;

import java.util.List;

import jodd.util.StringUtil;

import org.blackwhite.share.model.ExprModel;
import org.blackwhite.share.model.ShareModel;
import org.blackwhite.share.render.AjaxRender;
import org.blackwhite.share.service.QiniuFileService;
import org.blackwhite.share.util.StringUtils;

import com.jfinal.core.Const;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

/**
 * 我的分享
 * @author luojh
 *
 */
public class ShareCtrl extends Controller{

	private static final String SAVE_PATH = PropKit.use("ShareConfig.txt").get("savePath");
	
	public void list(){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",20);
		int userId = 1;
		Page<ShareModel> page = ShareModel.dao.page(pageNumber,pageSize,userId);
		AjaxRender render = AjaxRender.success();
		render.addData("page", page);
		render(render);
	}
	
	public void add(){
		String imageURL = "";
		try{
			List<UploadFile> list = getFiles(SAVE_PATH,Const.DEFAULT_MAX_POST_SIZE,"utf-8");
			for (UploadFile uploadFile : list) {
				String tUrl = QiniuFileService.upload(uploadFile.getFile(), StringUtils.uuid());
				//TODO 保存多张图片
				imageURL = tUrl;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		int userId = 1;
		String name = getPara("name", "").trim();
		String content = getPara("content", "").trim();
		if(StringUtil.isBlank(name)){
			render(AjaxRender.error("请输入标题"));
			return;
		}
		if(StringUtil.isBlank(content)){
			render(AjaxRender.error("请输入描述"));
			return;
		}
		//TODO 汉字乱码
		ShareModel share = ShareModel.dao.add(userId,name,imageURL,content);
		if(share == null){
			render(AjaxRender.error());
			return;
		}
		AjaxRender render = AjaxRender.success();
		render.addData("share", share);
		render(render);
	}
	
	public void commentList(){
		int shareId = getParaToInt("shareId",1);
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",20);
		int userId = 1;
		Page<ExprModel> page = ExprModel.dao.commentPage(
				pageNumber,pageSize,shareId,userId);
		AjaxRender render = AjaxRender.success();
		render.addData("page", page);
		render(render);
	}
	
}
