package com.app.support.cache;

import java.util.HashMap;
import java.util.Map;

import com.app.util.Const;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

public class TopicCache {
	
	public static int setViewCount(Record topic){
		int viewCount = topic.get("view_count", 0);
		String key = topic.getInt("user_id")+"_"+topic.getInt("category_id")+"_"+topic.getInt("id");
		Object obj = CacheKit.get(Const.TOPIC_ACOUNT, Const.TOPIC_VIEW_ACOUNT);
		Map<String, Integer> map = new HashMap<String, Integer>();
		if(obj == null){
			map.put(key, ++viewCount);
			CacheKit.put(Const.TOPIC_ACOUNT, Const.TOPIC_VIEW_ACOUNT, map);
		}else{
			map = (Map<String, Integer>)obj;
			if(map.get(key) !=null){
				viewCount = map.get(key);
			}
			map.put(key, ++viewCount);
			CacheKit.put("topic_acount", key, map);
		}
		return viewCount;
	}
	
	public static int getViewCount(Record topic){
		int viewCount = topic.get("view_count", 0);
		String key = topic.getInt("user_id")+"_"+topic.getInt("category_id")+"_"+topic.getInt("id");
		Object obj = CacheKit.get(Const.TOPIC_ACOUNT, Const.TOPIC_VIEW_ACOUNT);
		Map<String, Integer> map = new HashMap<String, Integer>();
		if(obj == null){
			map.put(key, viewCount);
			CacheKit.put(Const.TOPIC_ACOUNT, Const.TOPIC_VIEW_ACOUNT, map);
		}else{
			map = (Map<String, Integer>)obj;
			if(map.get(key) !=null){
				viewCount = map.get(key);
			}else{
				map.put(key, viewCount);
				CacheKit.put("topic_acount", key, map);
			}
		}
		
		return viewCount;
	}
}
