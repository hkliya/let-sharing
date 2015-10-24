package org.blackwhite.share;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import junit.framework.Assert;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.JsonKit;

@SuppressWarnings("deprecation")
public class HistoryTest {

	@Test
	public void historyImport(){
		//组装数据
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		for (int i = 0; i < 20; i++) {
			Map<String, Object> obj = new HashMap<String,Object>();
			obj.put("name", "测试商品"+i);
			obj.put("url", "http://xxx");
			obj.put("imageURL", "http://xxx/xxx.png");
			obj.put("bought_at", "2015-10-24");
			list.add(obj);
		}
		//发送请求
		HttpRequest request = HttpRequest.post("http://localhost:1024/user/historyImport")
				 .form("history",JsonKit.toJson(list))
				 .form("username","tiger");
		HttpResponse resp = request.send();
		JSONObject json = JSON.parseObject(resp.bodyText());
		Assert.assertEquals(json.getIntValue("code"), 200);
	}
	
	@Test
	public void historyList(){
		//发送请求
		HttpRequest request = HttpRequest.post("http://localhost:1024/user/historyList")
				 .form("username","tiger")
				 .form("pageNumber",1);
		HttpResponse resp = request.send();
		JSONObject json = JSON.parseObject(resp.bodyText());
		System.out.println(json);
		Assert.assertEquals(json.getIntValue("code"), 200);
	}
}
