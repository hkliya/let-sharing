package org.blackwhite.share.render;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

public class AjaxRender extends Render {
	
	public static final String CONTENT_TYPE = "text/html;charset=" + getEncoding();
	
	private int code;
	private String msg;
	private Map<String, Object> data;
	private Object sData;
	
	public AjaxRender(Status status){
		this.code = status.getCode();
		this.msg = status.getMsg();
	}
	public AjaxRender(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public static AjaxRender success(){
		AjaxRender render = new AjaxRender(Status.SUCCESS);
		return render;
	}
	public static AjaxRender success(String msg) {
		AjaxRender render = new AjaxRender(Status.SUCCESS);
		render.msg = msg;
		return render;
	}
	
	public static AjaxRender success(Map<String, Object> data){
		AjaxRender render = new AjaxRender(Status.SUCCESS);
		render.setData(data);
		return render;
	}
	
	public static AjaxRender success(Page<?> page){
		AjaxRender render = new AjaxRender(Status.SUCCESS);
		render.sData = page;
		return render;
	}
	
	public static AjaxRender error(){
		AjaxRender render = new AjaxRender(Status.ERROR);
		return render;
	}
	public static AjaxRender unAuth(){
		AjaxRender render = new AjaxRender(Status.UNAUTH);
		return render;
	}
	
	public static AjaxRender error(String msg){
		AjaxRender render = new AjaxRender(Status.ERROR);
		render.msg = msg;
		return render;
	}
	
	public static AjaxRender timeOut(){
		AjaxRender render = new AjaxRender(Status.TIMEOUT);
		return render;
	}
	
	public static AjaxRender timeOut(String msg){
		AjaxRender render = new AjaxRender(Status.TIMEOUT);
		render.msg = msg;
		return render;
	}
	
	public void setData(Map<String, Object> data){
		this.data = data;
	}
	
	public AjaxRender addData(String key,Object value){
		if(data == null){
			data = new HashMap<String,Object>();
		}
		data.put(key, value);
		return this;
	}
	
	private String buildJson(){
		Map<String, Object> json = new TreeMap<String,Object>();
		json.put("code", code);
		json.put("msg", msg);
		if(data != null && !data.isEmpty()){
			json.put("data", data);
		}
		if(sData != null){
			json.put("data", sData);
		}
		return JsonKit.toJson(json);
	}
	
	@Override
	public void render() {
		PrintWriter writer = null;
        String json = buildJson();
        try {
            response.setHeader("Pragma", "no-cache"); // HTTP/1.0 caches might not implement Cache-Control and might
                                                      // only implement Pragma:
                                                      // no-cache
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType(CONTENT_TYPE);
            writer = response.getWriter();
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            throw new RenderException(e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
	}
	
	public enum Status{
		SUCCESS(200,"操作成功"), 
		ERROR(300,"服务器故障"), 
		TIMEOUT(301,"连接超时，请重新登陆"),
		UNAUTH(401,"未授权");
       
		private String msg;
        private int code;

        private Status(int code,String msg) {
            this.msg = msg;
            this.code = code;
        }

		public String getMsg() {
			return msg;
		}

		public int getCode() {
			return code;
		}
	}

	@SuppressWarnings("rawtypes")
	public static AjaxRender page(List<? extends Model> list, boolean hasNext) {
		AjaxRender render = new AjaxRender(Status.SUCCESS);
		render.addData("list", list);
		render.addData("hasNext", hasNext);
		return render;
	}
	
	@SuppressWarnings("rawtypes")
	public static AjaxRender page(List<? extends Model> list) {
		AjaxRender render = new AjaxRender(Status.SUCCESS);
		render.addData("list", list);
		return render;
	}
}
