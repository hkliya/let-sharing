package org.blackwhite.share.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.blackwhite.share.util.CollectionUtils;
import org.blackwhite.share.util.ModelUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 体验表
 * @author luojh
 *
 */
public class ExprModel extends Model<ExprModel>{

	private static final long serialVersionUID = -2756889386917042920L;
	public static final ExprModel dao = new ExprModel();
	
	public Page<ExprModel> page(int pageNumber,int pageSize,int userId){
		String select = "select expr.id,expr.status,share.name,share.content,share.imageURL,user.username,user.avatar ";
		StringBuilder where = new StringBuilder();
		where.append(" from expr expr ")
			 .append(" inner join share share on share.id = expr.shareId ")
			 .append(" inner join user user on user.id = expr.userId ")
			 .append(" where expr.userId = ? ")
			 .append(" order by expr.createTime desc ");
		Page<ExprModel> page = paginate(pageNumber, pageSize, 
				select, where.toString(),userId);
		return page;
	}

	public ExprModel uncomment(int userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select expr.id,expr.status,share.name,share.content,share.imageURL,user.username,user.avatar ")
			 .append(" from expr expr ")
			 .append(" inner join share share on share.id = expr.shareId ")
			 .append(" inner join user user on user.id = expr.userId ")
			 .append(" where expr.userId = ? and status = 2 ")
			 .append(" order by expr.createTime desc ");
		return findFirst(sql.toString(),userId);
	}
	
	public boolean start(int exprId, int userId) {
		String sql = "update expr set status = 1,startTime=? where id=? and userId = ? and status = 0 ";
		int cnt = Db.update(sql,new Date(),exprId,userId);
		return cnt == 1;
	}
	
	public boolean end(int exprId, int userId) {
		String sql = "update expr set status = 2,endTime=? where id=? and userId = ? and status = 1 ";
		int cnt = Db.update(sql,new Date(),exprId,userId);
		return cnt == 1;
	}
	
	public boolean comment(int exprId, int userId, 
			double score, String comment) {
		String sql = "update expr set status=?,score=?,comment=?,commentTime=? where id=? and userId=? and status = ? ";
		int cnt = Db.update(sql,3,score,comment,new Date(),exprId,userId,2);
		return cnt == 1;
	}

	public int add(int shareId, int userId) {
		ExprModel expr = new ExprModel();
		expr.set("shareId", shareId)
			.set("userId", userId)
			.set("createTime", new Date());
		boolean flag =expr.save();
		if(flag){
			return expr.getInt("id");
		}
		return 0;
	}

	public boolean cancel(int exprId, int userId) {
		String sql = "update expr set status = -1,endTime=? where id=? and userId = ? and status = 0 ";
		int cnt = Db.update(sql,new Date(),exprId,userId);
		return cnt == 1;
	}

	public Page<ExprModel> commentPage(int pageNumber, int pageSize, int shareId,
			int userId) {
		String select = "select expr.id,expr.score,expr.comment,expr.createTime,user.username,user.avatar ";
		StringBuilder where = new StringBuilder();
		where.append(" from expr expr ")
			 .append(" inner join user user on user.id = expr.userId ")
			 .append(" where expr.shareId = ? and userId=? ")
			 .append(" order by expr.createTime desc ");
		Page<ExprModel> page = paginate(pageNumber, pageSize, 
				select, where.toString(),shareId,userId);
		return page;
		
	}

	public List<ExprModel> findByShareIds(List<Integer> shareIds) {
		if(CollectionUtils.isBlank(shareIds)){
			return new LinkedList<ExprModel>();
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" select expr.id,expr.shareId,expr.userId,user.username,expr.comment,expr.createTime,expr.score ")
		   .append(" from expr expr ")
		   .append(" left join user user on user.id = expr.userId ")
		   .append(" where expr.shareId in (??)");
		String tsql = ModelUtils.buildSqlIn(sql.toString(), "??", shareIds);
		List<ExprModel> list = find(tsql);
		return CollectionUtils.toSafeList(list); 
	}

}
