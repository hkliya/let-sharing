package org.blackwhite.share.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.jerry.Jerry;
import jodd.jerry.JerryFunction;

public class ImageRecgService {

	public static final String URL = "http://image.baidu.com/n/pc_search?fm=stuhome&uptype=urlsearch&queryImageUrl=";
	private List<String> tags = new LinkedList<String>();
	
	public static List<String> reg(String url){
		ImageRecgService service = new ImageRecgService();
		return service.recognize(url);
	}
	
	private List<String> recognize(String url){
		try {
			String turl = URLEncoder.encode(url, "utf-8");
			HttpResponse resp = HttpRequest.get(URL+turl).send();
			String body = resp.bodyText();
			Jerry doc = Jerry.jerry(body);
			doc.$(".guess-info-word-link").each(new JerryFunction() {
				public boolean onNode(Jerry $this, int index) {
					tags.add($this.html());
					return true;
				}
			});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return tags;
	}
	
	public static void main(String[] args) {
		String url = "http://img13.360buyimg.com/da/jfs/t2185/361/780280689/96469/97fc291b/562a02d3Ne12de30b.jpg";
		System.out.println(ImageRecgService.reg(url));
	}
}
