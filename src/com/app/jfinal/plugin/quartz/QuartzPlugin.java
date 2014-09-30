package com.app.jfinal.plugin.quartz;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.util.DateUtils;
import com.jfinal.plugin.IPlugin;

public class QuartzPlugin implements IPlugin {
    private static final String JOB = "job";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static String JOB_GROUP_NAME = "group1";
	private static String TRIGGER_GROUP_NAME = "trigger1";
    private SchedulerFactory sf;
    private Scheduler sched;
    private String config = "job.properties";
    private Properties properties;

    public QuartzPlugin(String config) {
        this.config = config;
    }

    public QuartzPlugin() {
    }

    @Override
    public boolean start() {
        sf = new StdSchedulerFactory();
        try {
			sched = sf.getScheduler();
			loadProperties();
	        Enumeration<Object> enums = properties.keys();
	        while (enums.hasMoreElements()) {
	            String jobName = enums.nextElement() + "";		// 任务名称	
	            if (!jobName.endsWith(JOB) || !isEnableJob(enable(jobName))) {
	                continue;
	            }
	            String jobClassName = properties.get(jobName) + "";
	            
	            String jobCronExp = properties.getProperty(cronKey(jobName)) + "";
	            Class jobClass = Class.forName(jobClassName);
	            
	            this.addJob(jobName, jobClass, jobCronExp);	// 添加任务 

	        }
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
		}
       
        return true;
    }
    @Override
    public boolean stop() {
        try {
			sched.shutdown();
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			logger.debug("定时插件停止错误:"+e.getMessage());
		}
        return true;
    }
    /**
	 *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 */
	private void addJob(String jobName, Class jobClass, String time)
			throws SchedulerException, ParseException {
		Scheduler sched = sf.getScheduler();
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(
				jobName, JOB_GROUP_NAME).build(); //任务名，任务组，任务执行类
		//触发器
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName,
				TRIGGER_GROUP_NAME).withSchedule(
				CronScheduleBuilder.cronSchedule(time)).build();

		 Date startTime = sched.scheduleJob(jobDetail, trigger);
		 
         sched.start();
         
         logger.debug(jobDetail.getKey() + " has been scheduled to run at: " +
         				DateUtils.getDateTimeFormat(startTime) + 
         				" and repeat based on expression: "+ 
         				trigger.getCronExpression());
		
	}
	
    private String enable(String key) {
        return key.substring(0, key.lastIndexOf(JOB)) + "enable";
    }

    private String cronKey(String key) {
        return key.substring(0, key.lastIndexOf(JOB)) + "cron";
    }

    private boolean isEnableJob(String enableKey) {
        Object enable = properties.get(enableKey);
        if (enable != null && "false".equalsIgnoreCase((enable + "").trim())) {
            return false;
        }
        return true;
    }

    private void loadProperties() throws IOException {
        properties = new Properties();
        InputStream is = QuartzPlugin.class.getClassLoader().getResourceAsStream(config);
        properties.load(is);
        logger.debug("------------加载 QuartzPlugin---------------");
        logger.debug(properties.toString());
        logger.debug("------------------------------------------");
    }
    
}

