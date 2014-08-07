package com.app.common.support.cache;

import com.app.util.Const;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

public class TopicCacheEventListener implements CacheEventListener{
	
	public static final CacheEventListener INSTANCE = new TopicCacheEventListener();
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyElementEvicted(Ehcache arg0, Element arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyElementExpired(Ehcache cache, Element e) {
		// TODO Auto-generated method stub
		String name = e.getObjectKey().toString();
		if(name.equals(Const.TOPIC_VIEW_ACOUNT)){
			Object obj = e.getObjectValue();
			if(obj == null){
				
			}
		}
	}

	@Override
	public void notifyElementPut(Ehcache arg0, Element arg1)
			throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyElementRemoved(Ehcache arg0, Element arg1)
			throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyElementUpdated(Ehcache arg0, Element arg1)
			throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyRemoveAll(Ehcache arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override  
    public Object clone() throws CloneNotSupportedException {  
        throw new CloneNotSupportedException("Singleton instance");  
    }  

}
