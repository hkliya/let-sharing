package org.blackwhite.share.cfg;

import org.blackwhite.share.interceptor.CommonInterceptor;
import org.blackwhite.share.model.BoughtHistoryModel;
import org.blackwhite.share.model.ProductModel;
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
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.RedisPlugin;

public class HackConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		loadPropertyFile("ShareConfig.txt");
		me.setDevMode(getPropertyToBoolean("devMode", false));
	}

	@Override
	public void configRoute(Routes me) {
		me.add(new HackRoute());
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
		arp.addMapping("user", UserModel.class);
		arp.addMapping("product", ProductModel.class);
		arp.addMapping("bought_history", BoughtHistoryModel.class);
		me.add(arp);
		
		RedisPlugin redis = new RedisPlugin(
				getProperty("redis.cacheName",""), 
				getProperty("redis.host",""), 
				getPropertyToInt("redis.port", Protocol.DEFAULT_PORT), 
				getPropertyToInt("redis.timeout", Protocol.DEFAULT_TIMEOUT), 
				getProperty("redis.password", ""));
		me.add(redis);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.addGlobalActionInterceptor(new CommonInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
	}

}
