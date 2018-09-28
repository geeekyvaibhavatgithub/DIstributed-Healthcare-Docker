package org.b3ds.dicom.configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = "com.b3ds.dicom")
public class AppConfig {
	private final static Logger logger = LogManager.getLogger(AppConfig.class);
}
