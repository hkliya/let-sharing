package org.blackwhite.share.model;

import java.util.Date;
import java.util.List;

import org.blackwhite.share.util.CollectionUtils;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class ShareModel extends Model<ShareModel>{

	private static final long serialVersionUID = 3214773443755836580L;
	public static final ShareModel dao = new ShareModel();
	
	public List<ShareModel> search(String keyword, int pageSize){
		String sql = "select * from share limit ? ";
		List<ShareModel> list = find(sql, pageSize);
		return CollectionUtils.toSafeList(list);
	}

	public Page<ShareModel> page(int pageNumber, int pageSize, int userId) {
		String select ="select share.id,share.name,share.content,share.imageURL,share.createTime,user.username,user.avatar ";
		StringBuilder where = new StringBuilder();
		where.append(" from share share ")
			 .append(" inner join user user on user.id = share.userId ")
			 .append(" where share.userId = ? ")
			 .append(" order by share.createTime desc ");
		return paginate(pageNumber, pageSize, 
				select, where.toString(),userId);
	}

	public ShareModel add(int userId, String name, String imageURL,
			String content) {
		ShareModel share = new ShareModel();
		share.set("userId", userId)
			 .set("name", name)
			 .set("imageURL", imageURL)
			 .set("content", content)
			 .set("createTime", new Date());
		boolean flag = share.save();
		if(flag){
			int id = share.getInt("id");
			return detail(id);
		}
		return null;
	}
	
	public ShareModel detail(int id){
		StringBuilder where = new StringBuilder();
		where.append("select share.id,share.name,share.content,share.imageURL,share.createTime,user.username,user.avatar ")
		     .append(" from share share ")
			 .append(" inner join user user on user.id = share.userId ")
			 .append(" where share.id = ?");
		return findFirst(where.toString(),id);
	}
	
}	
