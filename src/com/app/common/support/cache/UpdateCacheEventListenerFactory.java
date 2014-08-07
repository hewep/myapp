package com.app.common.support.cache;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

public class UpdateCacheEventListenerFactory extends CacheEventListenerFactory{

	@Override
	public CacheEventListener createCacheEventListener(Properties prop) {
		// TODO Auto-generated method stub
		if(prop.containsKey("topic")){
			return TopicCacheEventListener.INSTANCE;
		}
		return null;
	}

}
