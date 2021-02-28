package com.shop.restaurant;
import java.io.File;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
@ComponentScan(basePackages = "com.shop.restaurant")
public class ApplicationConfig {

  @Bean
  public JavaMailSender getMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    Properties properties = new Properties();
    properties.setProperty("mail.mime.charset", "UTF-8");
    mailSender.setJavaMailProperties(properties);

    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);
    mailSender.setUsername("majewski.piotr.511@gmail.com");
    mailSender.setPassword("mcvnddyafdcsdbcs");

    Properties javaMailProperties = new Properties();
    javaMailProperties.put("mail.smtp.starttls.enable", "true");
    javaMailProperties.put("mail.smtp.auth", "true");
    javaMailProperties.put("mail.transport.protocol", "smtp");
    javaMailProperties.put("mail.debug", "true");

    mailSender.setJavaMailProperties(javaMailProperties);
    return mailSender;
  }

  @Bean("fmConfig")
  public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
    FreeMarkerConfigurationFactoryBean fmConfigFactoryBean = new FreeMarkerConfigurationFactoryBean();
    fmConfigFactoryBean.setDefaultEncoding("UTF-8");

    //THIS IS QUITE UGLY BUT I COULDNT FIGURE OUT PATH, NO ONE SEEMS TO WORK
    String basePath = new File("").getAbsolutePath();
    fmConfigFactoryBean.setTemplateLoaderPath("file:"+basePath+"\\src\\main\\resources\\templates");

    return fmConfigFactoryBean;
  }
  @Bean
  public FreeMarkerConfigurer freeMarkerConfigurer(){
    FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
    return freeMarkerConfigurer; }
}