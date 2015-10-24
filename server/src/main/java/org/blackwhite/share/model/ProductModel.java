package org.blackwhite.share.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class ProductModel extends Model<ProductModel>{

	private static final long serialVersionUID = -2046078441411294185L;
	public static final ProductModel dao = new ProductModel();
	
	public Page<ProductModel> page(int pageNumber,int pageSize){
		String select = "select * ";
		String where = " from product ";
		return paginate(pageNumber, pageSize,select,where);
	}
}
