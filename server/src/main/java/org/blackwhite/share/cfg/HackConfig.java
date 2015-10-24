package org.blackwhite.share.cfg;

import org.blackwhite.share.interceptor.CommonInterceptor;
import org.blackwhite.share.model.BoughtHistoryModel;
import org.blackwhite.share.model.ExprModel;
import org.blackwhite.share.model.ProductModel;
import org.blackwhite.share.model.ShareModel;
import org.blackwhite.share.model.UserLocModel;
import org.blackwhite.share.model.UserModel;

import redis.clients.jedis.Protocol;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.plugin.shiro.ShiroInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.ViewType;

public class HackConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		loadPropertyFile("ShareConfig.txt");
		me.setDevMode(getPropertyToBoolean("devMode", false));
		me.setViewType(ViewType.JSP);
		me.setError404View("/404.jsp");
		me.setError500View("/500.jsp");
	}

	@Override
	public void configRoute(Routes me) {
		me.add(new HackApiRoute());
		me.add(new HackAdminRoute());
	}

	@Override
	public void configPlugin(Plugins me) {
		//druid plugin
		DruidPlugin dp =  new DruidPlugin(
				getProperty("jdbcUrl", ""), 
				getProperty("user",""), 
				getProperty("password",""));
		dp.addFilter(new StatFilter());
		WallFilter wall = new WallFilter();
		wall.setDbType("mysql");
		dp.addFilter(wall);
		me.add(dp);
		
		//active record plugin
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		arp.addMapping("user", UserModel.class);//用户表
		arp.addMapping("user_loc", UserLocModel.class);//用户地理位置表
		arp.addMapping("product", ProductModel.class);//产品表
		arp.addMapping("share", ShareModel.class);//分享表
		arp.addMapping("expr", ExprModel.class);//体验表
		arp.addMapping("bought_history", BoughtHistoryModel.class);//购买货物历史
		me.add(arp);
		
		//redisplugin
		RedisPlugin redis = new RedisPlugin(
				getProperty("redis.cacheName",""), 
				getProperty("redis.host",""), 
				getPropertyToInt("redis.port", Protocol.DEFAULT_PORT), 
				getPropertyToInt("redis.timeout", Protocol.DEFAULT_TIMEOUT), 
				getProperty("redis.password", ""));
		me.add(redis);
		
		//shiro plugin
		HackAdminRoute routes = new HackAdminRoute();
		routes.config();
		ShiroPlugin shiro = new ShiroPlugin(routes);
		shiro.setSuccessUrl("/");
		shiro.setLoginUrl("/login");
		shiro.setUnauthorizedUrl("/login");
		me.add(shiro);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.addGlobalActionInterceptor(new CommonInterceptor());
		me.addGlobalActionInterceptor(new ShiroInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
	}

}
