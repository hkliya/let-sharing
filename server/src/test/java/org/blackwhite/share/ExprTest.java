package org.blackwhite.share;

import org.junit.Test;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import junit.framework.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("deprecation")
public class ExprTest {
	
//	//@Test
//	public void list(){
//		//发送请求
//		HttpRequest request = HttpRequest.post("http://localhost:1024/expr/list");
//		HttpResponse resp = request.send();
//		JSONObject json = JSON.parseObject(resp.bodyText());
//		System.out.println(json);
//		Assert.assertEquals(json.getIntValue("code"), 200);
//	}
//
//	//@Test
//	public void uncomment(){
//		//发送请求
//		HttpRequest request = HttpRequest.post("http://localhost:1024/expr/uncomment");
//		HttpResponse resp = request.send();
//		JSONObject json = JSON.parseObject(resp.bodyText());
//		System.out.println(json);
//		Assert.assertEquals(json.getIntValue("code"), 200);
//	}
//	
//	//@Test
//	public void start(){
//		//发送请求
//		HttpRequest request = HttpRequest.post("http://localhost:1024/expr/start")
//										 .form("id", 2);
//		HttpResponse resp = request.send();
//		JSONObject json = JSON.parseObject(resp.bodyText());
//		System.out.println(json);
//		Assert.assertEquals(json.getIntValue("code"), 200);
//	}
//	
//	//@Test
//	public void cancel(){
//		//发送请求
//		HttpRequest request = HttpRequest.post("http://localhost:1024/expr/cancel")
//				.form("id", 2);
//		HttpResponse resp = request.send();
//		JSONObject json = JSON.parseObject(resp.bodyText());
//		System.out.println(json);
//		Assert.assertEquals(json.getIntValue("code"), 200);
//	}
//	
//	//=======================
//	//@Test
//	public void chooseLoop(){
//		//发送请求
//		HttpRequest request = HttpRequest.post("http://10.2.200.93:1024/api/expr/chooseLoop");
//		HttpResponse resp = request.send();
//		JSONObject json = JSON.parseObject(resp.bodyText());
//		System.out.println(json);
//		Assert.assertEquals(json.getIntValue("code"), 200);
//	}
//	//@Test
//	public void add(){
//		//发送请求
//		HttpRequest request = HttpRequest.post("http://10.2.200.93:1024/api/expr/add")
//										 .form("shareId", 1);
//		HttpResponse resp = request.send();
//		JSONObject json = JSON.parseObject(resp.bodyText());
//		System.out.println(json);
//		Assert.assertEquals(json.getIntValue("code"), 200);
//	}
//	
//	//@Test
//	public void end(){
//		//发送请求
//		HttpRequest request = HttpRequest.post("http://10.2.200.93:1024/api/expr/end")
//										 .form("id", 4);
//		HttpResponse resp = request.send();
//		JSONObject json = JSON.parseObject(resp.bodyText());
//		System.out.println(json);
//		Assert.assertEquals(json.getIntValue("code"), 200);
//	}
//	
//	//@Test
//	public void comment(){
//		//发送请求
//		HttpRequest request = HttpRequest.post("http://10.2.200.93:1024/api/expr/comment")
//										 .form("id", 2)
//										 .form("score",5.0)
//										 .form("comment", "很好");
//		HttpResponse resp = request.send();
//		JSONObject json = JSON.parseObject(resp.bodyText());
//		System.out.println(json);
//		Assert.assertEquals(json.getIntValue("code"), 200);
//	}
}
