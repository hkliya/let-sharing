package org.blackwhite.share.model;

import java.util.List;

import org.blackwhite.share.util.CollectionUtils;

import com.jfinal.plugin.activerecord.Model;

public class ServiceModel extends Model<ServiceModel>{

	private static final long serialVersionUID = 3214773443755836580L;
	public static final ServiceModel dao = new ServiceModel();
	
	public List<ServiceModel> search(String keyword, int pageSize){
		String sql = "select * from service limit ? ";
		List<ServiceModel> list = find(sql, pageSize);
		return CollectionUtils.toSafeList(list);
	}
}	
