package org.blackwhite.share.admin.ctrl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.blackwhite.share.model.BoughtHistoryModel;
import org.blackwhite.share.model.UserModel;
import org.blackwhite.share.util.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

public class AdminIndexCtrl extends Controller{

	@RequiresAuthentication
	public void index(){
		render("index.jsp");
	}
	
	public void login(){
		render("/login.jsp");
	}
	
	public void doLogin(){
		String username = getPara("username","").trim();
		String password = getPara("password","").trim();
		keepPara("username");
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		//记录该令牌，如果不记录则类似购物车功能不能使用。
		token.setRememberMe(true);
		//subject理解成权限对象。类似user
		Subject subject = SecurityUtils.getSubject();
		boolean flag = false;
		try {
			subject.login(token);
			flag = true;
		} catch (UnknownAccountException ex) {//用户名没有找到
			setAttr("msg", "该用户不存在");
		} catch (IncorrectCredentialsException ex) {//用户名密码不匹配
			setAttr("msg", "用户名或密码不正确");
		}catch (AuthenticationException e) {//其他的登录错误
			setAttr("msg", "用户名或密码不正确");
		}
		if(flag){
			redirect("/index");
			return;
		}
		render("/login.jsp");
	}
	
	@RequiresAuthentication
	public void buyList(){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",20);
		Subject subject = SecurityUtils.getSubject();
		UserModel user = (UserModel)subject.getSession().getAttribute("user");
		int userId = user.getInt("id");
		Page<BoughtHistoryModel> page = BoughtHistoryModel.dao.page(pageNumber, pageSize, userId);
		setAttr("list", page.getList());
		setAttr("pageNum", page.getPageNumber());
		setAttr("pageSize", page.getPageSize());
		setAttr("totalRow", page.getTotalRow());
		setPageUrl();
		render("buyList.jsp");
	}
	
	public void setPageUrl() {
		String pageUrl = getRequest().getRequestURL().toString();
		String params = getRequest().getQueryString();
		if(params != null && params.trim().length() > 0){
			pageUrl = StringUtils.concat(pageUrl, "?", params);
		}
		setAttr("page_url", pageUrl);
	}
}
