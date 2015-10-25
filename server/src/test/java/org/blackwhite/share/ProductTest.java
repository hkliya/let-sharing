package org.blackwhite.share;

import java.io.File;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import junit.framework.Assert;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("deprecation")
public class ProductTest {

//	//@Test
//	public void search(){
//		HttpRequest request = HttpRequest.post("http://localhost:1024/product/search")
//										 .form("keyword","测试");
//		HttpResponse resp = request.send();
//		JSONObject json = JSON.parseObject(resp.bodyText());
//		System.out.println(json);
//		Assert.assertEquals(json.getIntValue("code"), 200);
//	}
//	
//	//@Test
//	public void searchPic(){
//		HttpRequest request = HttpRequest.post("http://localhost:1024/product/search")
//										 .form("img",new File("/Users/luojh/Desktop/c326a363-6743-4ed0-ae89-921f5740effe.gif"));
//		HttpResponse resp = request.send();
//		JSONObject json = JSON.parseObject(resp.bodyText());
//		System.out.println(json);
//		Assert.assertEquals(json.getIntValue("code"), 200);
//	}
}
