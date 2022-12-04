package com.cibnvideo.jobservice.config;

import com.cibnvideo.jobservice.quartz.factory.JobFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfigration {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	JobFactory jobFactory;

	@Autowired
	DataSource dataSource;


	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		try {
			schedulerFactoryBean.setOverwriteExistingJobs(true);
			schedulerFactoryBean.setQuartzProperties(quartzProperties());
			schedulerFactoryBean.setDataSource(dataSource);
			schedulerFactoryBean.setAutoStartup(true);
			schedulerFactoryBean.setJobFactory(jobFactory);
		} catch (IOException e) {
			logger.error(e.toString());
		}
		return schedulerFactoryBean;
	}

	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/config/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	@Bean
	public QuartzInitializerListener executorListener() {
		return new QuartzInitializerListener();
	}

	@Bean(name = "scheduler")
	public Scheduler scheduler() {
		return schedulerFactoryBean().getScheduler();
	}
}
