package com.app.plugin.quartz;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.app.model.Topic;
import com.app.util.Const;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;

public class TopicJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		List<String> list = CacheKit.getKeys(Const.TOPIC_ACOUNT);
//		for (String key : list) {
//			if(StrKit.notBlank(key)){
//				String topicId = key.split("_")[2];
//				Integer count = CacheKit.get("topic_acount", key);
//				Topic.dao.updateViewCount(topicId, count);
//			}
//		}
	}

}
