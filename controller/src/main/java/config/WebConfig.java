package config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

/**
 * @author XJ
 */

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        //容器启动(配置父容器的地方)
        return new Class[]{MyConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        //配置子容器的地方
        return new Class[0];
    }

    @Override
    protected String[] getServletMappings() {
        //配置 "/" 代表所有请求都接受
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement(""));
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] {characterEncodingFilter};
    }
}
