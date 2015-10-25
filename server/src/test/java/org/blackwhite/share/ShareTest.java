package org.blackwhite.share;

import java.io.File;

import org.junit.Test;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import junit.framework.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("deprecation")
public class ShareTest {
	
//	//@Test
//	public void list(){
//		//发送请求
//		HttpRequest request = HttpRequest.post("http://localhost:1024/share/list");
//		HttpResponse resp = request.send();
//		JSONObject json = JSON.parseObject(resp.bodyText());
//		System.out.println(json);
//		Assert.assertEquals(json.getIntValue("code"), 200);
//	}
//	
//	//@Test
//	public void add(){
//		//发送请求
//		HttpRequest request = HttpRequest.post("http://localhost:1024/share/add")
//										 .charset("utf-8")
//										 .formEncoding("utf-8")
//										 .form("name", "测试")
//										 .form("content","测试内容")
//										 .form("img1",new File("/Users/luojh/Desktop/f1.png"));
//		HttpResponse resp = request.send();
//		JSONObject json = JSON.parseObject(resp.bodyText());
//		System.out.println(json);
//		Assert.assertEquals(json.getIntValue("code"), 200);
//	}
//
//	//@Test
//	public void commentList(){
//		//发送请求
//		HttpRequest request = HttpRequest.post("http://localhost:1024/share/commentList");
//		HttpResponse resp = request.send();
//		JSONObject json = JSON.parseObject(resp.bodyText());
//		System.out.println(json);
//		Assert.assertEquals(json.getIntValue("code"), 200);
//	}
}
