package config;

import com.github.pagehelper.PageInterceptor;
import com.nf.security.RestAuthEntryPoint;
import com.nf.security.RestAuthFailHandler;
import com.nf.security.RestAuthSuccessHandler;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.*;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author XJ
 * Configuration:声明该类是一个配置文件类
 * ComponentScan:扫描当前包下的注解,进行spring管理
 * MapperScan:扫描当前包下的Mapper,目的是获得dao的实现类
 * PropertySource:资源来源
 * EnableTransactionManagement:配置事务,然后在需要添加事务的地方添加@Transactional注解即可
 * EnableWebMvc(WebMvcConfigurer): 开启MVC配置,实现这个类,可以进行mvc的一些配置
 */
@Configuration
@ComponentScan("com.nf.controller")
@ComponentScan("com.nf.service")
@Import(JdbcConfig.class)
@MapperScan("com.nf.dao")
@EnableWebMvc
@EnableTransactionManagement
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    @Autowired
    DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //配置资源匹配解析器
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver
                = new PathMatchingResourcePatternResolver();
        //将Mapper进行注入
        Resource[] resources
                = pathMatchingResourcePatternResolver.getResources("classpath*:*.xml");
        sqlSessionFactoryBean.setPlugins(pageInterceptor());
        sqlSessionFactoryBean.setMapperLocations(resources);
        sqlSessionFactoryBean.setConfiguration(configuration());
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 配置分页
     * @return
     */
    private PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.put("supportMethodsArguments", "true");
        properties.put("reasonable", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    /**
     * 配置日志
     * @return
     */
    private org.apache.ibatis.session.Configuration configuration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(StdOutImpl.class);
        configuration.setMapUnderscoreToCamelCase(true);
        return configuration;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    /**
     * 配置事务
     * @return
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration registration
                = registry.addResourceHandler("/static/**");
        registration.addResourceLocations("classpath:/static/");


        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private UrlBasedCorsConfigurationSource configurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://127.0.0.1:8848");

        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("HEAD");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("123")).authorities("xxxx");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(configurationSource())
                .and()
                .csrf().disable()
                .formLogin().
                successHandler(new RestAuthSuccessHandler())
                .failureHandler(new RestAuthFailHandler())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/query").authenticated()
                .antMatchers("/admin").authenticated();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    /**
//     * 这里添加的转换器会不会添加默认转换器,
//     * 如果想在保留默认转换器的情况下添加消息转换器,可以重写extendMessageConverters方法
//     * @param converters
//     */
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setDateFormat(sdf);
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
//        converters.add(converter);
//    }

}
