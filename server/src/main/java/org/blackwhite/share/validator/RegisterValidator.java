package org.blackwhite.share.validator;

import jodd.util.StringUtil;

import org.blackwhite.share.model.UserModel;
import org.blackwhite.share.render.AjaxRender;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class RegisterValidator extends Validator{

private String msg;
	
	@Override
	protected void validate(Controller c) {
		String username = c.getPara("username", "").trim();
		String password = c.getPara("password", "").trim();
		
		if(StringUtil.isBlank(username)){
			addError("msg", "请输入用户名");
			return;
		}
		if(UserModel.dao.exist(username)){
			addError("msg", "该用户名已经存在");
			return;
		}
		if(StringUtil.isBlank(password)){
			addError("msg", "请输入密码");
			return;
		}
	}

	@Override
	protected void handleError(Controller c) {
		c.render(AjaxRender.error(msg));
	}
	
	@Override
	protected void addError(String errorKey, String errorMessage) {
		super.addError(errorKey, errorMessage);
		msg = errorMessage;
	}
}
