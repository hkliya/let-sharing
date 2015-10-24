package org.blackwhite.share.interceptor;

import org.blackwhite.share.render.AjaxRender;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class CommonInterceptor implements Interceptor{

	public void intercept(Invocation inv) {
		try{
			inv.invoke();
		}catch(Exception e){
			e.printStackTrace();
			inv.getController()
			   .render(AjaxRender.error("服务器出错了"));
		}
	}
}
