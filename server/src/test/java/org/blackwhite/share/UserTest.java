package org.blackwhite.share;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import junit.framework.Assert;

import org.apache.shiro.crypto.hash.Sha1Hash;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("deprecation")
public class UserTest {

	@Test
	public void login(){
		HttpRequest request = HttpRequest.post("http://localhost:1024/user/login")
										 .form("username", "tiger")
										 .form("password",new Sha1Hash("123").toHex());
		HttpResponse resp = request.send();
		JSONObject json = JSON.parseObject(resp.bodyText());
		Assert.assertEquals(json.getIntValue("code"), 200);
	}
	
	@Test
	public void regist(){
		HttpRequest request = HttpRequest.post("http://localhost:1024/user/regist")
										 .form("username", "tiger")
										 .form("password",new Sha1Hash("123").toHex());
		HttpResponse resp = request.send();
		System.out.println(resp.bodyText());
		JSONObject json = JSON.parseObject(resp.bodyText());
		Assert.assertEquals(json.getIntValue("code"), 200);
	}
	
	@Test
	public void uploadLoc(){
		HttpRequest request = HttpRequest.post("http://localhost:1024/user/uploadLoc")
				 .form("lat", 22.529214)
				 .form("lng", 113.942519);
		HttpResponse resp = request.send();
		System.out.println(resp.bodyText());
		JSONObject json = JSON.parseObject(resp.bodyText());
		Assert.assertEquals(json.getIntValue("code"), 200);
	}
}
