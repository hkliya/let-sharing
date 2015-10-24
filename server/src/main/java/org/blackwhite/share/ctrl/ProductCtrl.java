package org.blackwhite.share.ctrl;

import java.util.List;
import java.util.UUID;

import jodd.util.StringUtil;

import org.blackwhite.share.model.ProductModel;
import org.blackwhite.share.render.AjaxRender;
import org.blackwhite.share.service.ImageRecgService;
import org.blackwhite.share.service.QiniuFileService;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
/**
 * 物品搜索
 * @author luojh
 *
 */
public class ProductCtrl extends Controller{

	//图片、文字搜索
	public void search(){
		String keyword = getKeyword();
		//TODO 未实现关键字查询
		Page<ProductModel> page = ProductModel.dao.page(1, 20,keyword);
		AjaxRender render = AjaxRender.success();
		ProductModel product = page.getList().get(0);
		render.addData("product", product);
		render(render);
	}
	
	//搜索
	private String getKeyword() {
		String keyword = "";
		try{
			List<UploadFile> files = getFiles();
			if(files != null && !files.isEmpty()){
				//图片上传
				String imgUrl = QiniuFileService.upload(
						files.get(0).getFile(), 
						UUID.randomUUID().toString());
				//图片识别
				List<String> tags = ImageRecgService.reg(imgUrl);
				if(!tags.isEmpty()){
					keyword = tags.get(0);
				}
			}
		}catch(Exception e){
		}
		if(StringUtil.isBlank(keyword)){
			keyword = getPara("keyword");
		}
		return keyword;
	}
	
}
