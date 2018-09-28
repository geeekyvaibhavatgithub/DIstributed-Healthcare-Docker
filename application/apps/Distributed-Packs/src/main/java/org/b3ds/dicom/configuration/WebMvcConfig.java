package org.b3ds.dicom.configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@org.springframework.context.annotation.Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	private final static Logger logger = LogManager.getLogger(WebMvcConfig.class);
	
/*	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        final ObjectMapper objectMapper = this.buildObjectMapper();
        objectMapper.registerModule(this.getModule());
        converter.setObjectMapper(objectMapper);
        converters.add(converter);
        super.configureMessageConverters(converters);
    }

    public ObjectMapper	 buildObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    private SimpleModule getModule() {
        // Register custom serializers
        SimpleModule module = new SimpleModule("DefaultModule");

        return module;
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/app/").setCachePeriod(31556926);
    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/View/");
        resolver.setSuffix(".html");
        resolver.setOrder(1);
        return resolver;
    }

/*    @Bean
    public InternalResourceViewResolver getFreemarkerConfig() {
        FreeMarkerConfigurer result = new FreeMarkerConfigurer();
        result.setTemplateLoaderPath("/pages/");
        return result;
    }   
    
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/pages/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(2);
        return resolver;
    }*/
}
