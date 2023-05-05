package com.indocosmo.mrp.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.cache.concurrent.ConcurrentMapCacheManager;*/
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/*import org.springframework.context.annotation.Primary;*/
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
/*import org.springframework.core.io.ClassPathResource;*/
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;

import com.indocosmo.mrp.config.bean.DBSettings;
import com.indocosmo.mrp.config.bean.SystemSettings;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.CombineMode;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.DailyproductionView;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.ImplementationMode;
import com.indocosmo.mrp.web.core.interceptor.AuthenticationInterceptor;
/*import org.springframework.cache.CacheManager;*/



@EnableWebMvc
@Configuration
/*@EnableCaching*/
@ComponentScan({ "com.indocosmo.mrp.web.*" })
@PropertySource("classpath:system.properties")
@PropertySource("classpath:database.properties")
public class AppConfig extends WebMvcConfigurerAdapter{
	
	@Autowired
	Environment env;
	
	/**
	 * @return
	 */
	@Bean(name = "internalResourceViewResolver")
	public InternalResourceViewResolver getViewResolver()
	{
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		internalResourceViewResolver.setSuffix(".jsp");
		return internalResourceViewResolver;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	/**
	 * @return
	 */
	@Bean(name = "localeChangeInterceptor")
	 public LocaleChangeInterceptor getLocaleChangeInterceptor(){
	     LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
	     localeChangeInterceptor.setParamName("language");
	     return localeChangeInterceptor;
	 }
	 
	 /**
	 * @return
	 */
	@Bean(name = "localeResolver")
	 public LocaleResolver getSessionLocaleResolver(){
	     SessionLocaleResolver localeResolver=new SessionLocaleResolver();
	     localeResolver.setDefaultLocale(new Locale("en"));	      
	     return localeResolver;
	 }  
	  
	
	 
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		
	     registry.addInterceptor(getLocaleChangeInterceptor());
	     registry.addInterceptor(new AuthenticationInterceptor());
	 }
	  
	 /**
	 * @return
	 */
	@Bean(name = "messageSource")
	 public MessageSource getMessageSource() {
	        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
         messageSource.setBasenames("classpath:messages","classpath:mrp.settings");
	        // messageSource.setBasename("classpath:messages");
	        //String mode = env.getProperty("implementation_mode");
	        
	        return messageSource;
	 } 
	
	@Bean(name = "systemSettings")
	 public SystemSettings getsystemSettings() {
	      
		SystemSettings settings=new SystemSettings();
		Integer mode =Integer.parseInt(env.getProperty("implementation_mode"));
		settings.setImplementationMode(ImplementationMode.get(mode));
		
		Integer combinemode =Integer.parseInt(env.getProperty("combine_purchase"));
		settings.setCombineModePurchase(CombineMode.get(combinemode));
		
		final String type =env.getProperty("salesURL");
		settings.setSalesUrl(type);	
		
		Integer dailyprodview =Integer.parseInt(env.getProperty("dailyproduction_view"));
		settings.setDailyproductionView(DailyproductionView.get(dailyprodview));
		

				
		return settings;
	 } 
	
	
	@Bean(name = "DBettings")
	 public DBSettings getDBSettings() {
	      
		final DBSettings settings;
		
		if(DBConfig.getInstance().getDBSettings()==null){
			
			settings=new DBSettings();
			final String type =env.getProperty("dbType");
			final String connectionString =env.getProperty("connectionUrl");
			final String driverClassName=env.getProperty("driverClassName");
			settings.setDatabaseType(type);
			settings.setDbConnectionString(connectionString);
			settings.setDriverClassName(driverClassName);
			DBConfig.getInstance().setDBSettings(settings);
			
		}else
			settings=DBConfig.getInstance().getDBSettings();
		
		return settings;
	 } 
		
	/**
	 * @return
	 */
	@Bean
	public ViewResolver viewResolver1(){
		ResourceBundleViewResolver rviewResolver= new ResourceBundleViewResolver();
		rviewResolver.setOrder(1);
		rviewResolver.setBasename("views");
		return rviewResolver;
	}
	
	
	@Bean
	  public MultipartResolver multipartResolver() {
	    CommonsMultipartResolver resolver = new CommonsMultipartResolver();
	    resolver.setMaxUploadSize(500*1024);
	    return resolver;
	  }
//	@Primary
/*	@Bean
	public CacheManager jdkCacheManager() {
		return new ConcurrentMapCacheManager("stockItemsCache");
	}*/
	/*@Bean
	   public CacheManager cacheManager() {
	        return new ConcurrentMapCacheManager("stockItemsCache");
	    }*/
	
	/*@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
		cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cmfb.setShared(true);
		return cmfb;
	}*/
	
}
