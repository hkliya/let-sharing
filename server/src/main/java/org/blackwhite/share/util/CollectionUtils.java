package org.blackwhite.share.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 常用集合工具类
 * @author luojh
 *
 */
public class CollectionUtils {

	public static boolean isBlank(Object[] arr){
		return arr == null || arr.length == 0;
	}
	
	public static boolean isNotBlank(Object[] arr){
		return arr != null && arr.length > 0;
	}
	
	public static boolean isBlank(List<?> list){
		return list == null || list.isEmpty();
	}
	
	public static boolean isNotBlank(List<?> list){
		return list != null && !list.isEmpty();
	}

	public static<T> List<T> toSafeList(List<T> list) {
		if(CollectionUtils.isNotBlank(list)){
			return list;
		}
		return new ArrayList<T>();
	}
}
