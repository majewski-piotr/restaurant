package com.shop.restaurant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.util.Properties;

@Configuration
public class EmailConfig {
  @Value("${mail.sender.port}")
  private int port;
  @Value("${mail.sender.host}")
  private String host;
  @Value("${mail.sender.username}")
  private String username;
  @Value("${mail.sender.password}")
  private String password;


  @Bean
  public JavaMailSender getMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    Properties properties = new Properties();
    properties.setProperty("mail.mime.charset", "UTF-8");
    mailSender.setJavaMailProperties(properties);

    mailSender.setHost(host);
    mailSender.setPort(port);
    mailSender.setUsername(username);
    mailSender.setPassword(password);

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

    String basePath = new File("").getAbsolutePath();
    fmConfigFactoryBean.setTemplateLoaderPath("file:"+basePath+"\\src\\main\\resources\\templates");

    return fmConfigFactoryBean;
  }
  @Bean
  public FreeMarkerConfigurer freeMarkerConfigurer(){
    FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
    return freeMarkerConfigurer; }
}
