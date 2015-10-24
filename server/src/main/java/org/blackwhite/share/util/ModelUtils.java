package org.blackwhite.share.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jodd.bean.BeanUtil;
import jodd.typeconverter.TypeConverterManager;
import jodd.util.StringUtil;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class ModelUtils {

	public static String buildSqlIn(String sql,String reg, List<Integer> idList) {
		String ids = StringUtils.join(idList, ",");
		return StringUtil.replace(sql,reg,ids);
	}
	
	@SuppressWarnings("rawtypes")
	public static<T> List<T> getFieldFromModel(
			List<? extends Model> list,String key, Class<T> cls){
		List<T> tmp = new LinkedList<T>();
		if(list == null || list.isEmpty()){
			return tmp;
		}
		if(StringUtil.isBlank(key)){
			return tmp;
		}
		if(cls == null){
			return tmp;
		}
		for (Model model : list) {
			Object obj = model.get(key);
			T t = null;
			try {
				t = obj == null ? null : TypeConverterManager.convertType(obj, cls);
			} catch (Exception e) {
			}
			if(t != null){
				tmp.add(t);	
			}
		}
		return tmp;
	}
	
	public static <K> Map<K, Record> recordListToMap(
			List<Record> list, String key,
			Class<K> cls) {
		Map<K, Record> map = new HashMap<K, Record>();
		if(list == null 
				|| list.isEmpty() 
				|| StringUtil.isEmpty(key) 
				|| cls == null){
			return map;
		}
		for (Record v : list) {
			Object obj = v.get(key);
			if(obj != null){
				K k = TypeConverterManager.convertType(obj, cls);
				map.put(k, v);
			}
		}
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	public static <K,V> Map<K, V> modelListToMap(
			List<V> list, String key,
			Class<K> cls) {
		Map<K, V> map = new HashMap<K, V>();
		if(list == null 
				|| list.isEmpty() 
				|| StringUtil.isEmpty(key) 
				|| cls == null){
			return map;
		}
		for (V v : list) {
			Object obj = ((Model)v).get(key);
			if(obj != null){
				K k = TypeConverterManager.convertType(obj, cls);
				map.put(k, v);
			}
		}
		return map;
	}
	
	public static <K,V> Map<K, V> listToMap(
			List<V> list, String key,
			Class<K> cls) {
		Map<K, V> map = new HashMap<K, V>();
		if(list == null 
				|| list.isEmpty() 
				|| StringUtil.isEmpty(key) 
				|| cls == null){
			return map;
		}
		for (V v : list) {
			Object obj = BeanUtil.getPropertySilently(v, key);
			if(obj != null){
				K k = TypeConverterManager.convertType(obj, cls);
				map.put(k, v);
			}
		}
		return map;
	}
	
	public static<T> List<T> getFieldFromRecord(
			List<Record> list,String key, Class<T> cls){
		List<T> tmp = new LinkedList<T>();
		if(list == null || list.isEmpty()){
			return tmp;
		}
		if(StringUtil.isBlank(key)){
			return tmp;
		}
		if(cls == null){
			return tmp;
		}
		for (Record record : list) {
			Object obj = record.get(key);
			T t = null;
			try {
				t = obj == null ? null : TypeConverterManager.convertType(obj, cls);
			} catch (Exception e) {
			}
			if(t != null){
				tmp.add(t);	
			}
		}
		return tmp;
	} 
	
	@SuppressWarnings("rawtypes")
	public void merge(List<Model> froms,List<Model> tos,String fromKey,String toKey,String newKey){
		Map<Object, Model> fromMap = listToMap(froms, fromKey, Object.class);
		for (Model to : tos) {
			Object val = to.get(toKey);
			Model from = fromMap.get(val);
			Object tval = from != null ? from.get(fromKey) : null;
			to.put(newKey,tval);
		}
	}
}
