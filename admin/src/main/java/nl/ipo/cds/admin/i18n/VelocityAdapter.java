package nl.ipo.cds.admin.i18n;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class VelocityAdapter implements ApplicationContextAware {	
	
	private final String resourceLoaderPath;
	private final LocaleProvider localeProvider;
	
	private ApplicationContext applicationContext;
	
	public VelocityAdapter(LocaleProvider localeProvider, String resourceLoaderPath) {
		this.localeProvider = localeProvider;
		this.resourceLoaderPath = resourceLoaderPath;
	}
	
	public String getLocaleView(String viewName) {
		final Locale locale = localeProvider.getLocale();
		final String localeResourceName = viewName.replaceAll("\\.vm$", "_" + locale.getLanguage() + ".vm");
		
		final String retval;
		if(applicationContext.getResource(resourceLoaderPath + localeResourceName).exists()) {
			retval = localeResourceName;
		} else {
			retval = viewName;
		}
		
		return retval;
	}

	public Object get(String propertyName) {
		try {
			return applicationContext.getBean("i18n." + propertyName);
		} catch(Exception e) {
			return null;
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;		
	}
}
