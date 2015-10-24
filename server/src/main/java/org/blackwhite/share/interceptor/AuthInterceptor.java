package org.blackwhite.share.interceptor;

import org.blackwhite.share.model.UserModel;
import org.blackwhite.share.render.AjaxRender;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

public class AuthInterceptor implements Interceptor{

	
	public void intercept(Invocation inv) {
		String token = inv.getController()
						  .getPara("_token", "").trim();
		Cache cache = Redis.use();
		UserModel user = cache.get(token);
		if(user == null){
			inv.getController().render(AjaxRender.unAuth());
			return;
		}
		inv.invoke();
	}
}
