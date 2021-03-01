package com.shop.restaurant.service;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import freemarker.template.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;




@Service
public class EmailService {

  @Autowired
  JavaMailSender mailSender;

  @Autowired
  @Qualifier("fmConfig")
  Configuration fmConfiguration;

  public void sendEmail(String subject, String from,String to,Map < String, Object > model) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();

    try {

      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true,  "UTF-8");

      mimeMessageHelper.setSubject(subject);
      mimeMessageHelper.setFrom(from);
      mimeMessageHelper.setTo(to);
      mimeMessageHelper.setText(geContentFromTemplate(model), true);

      mailSender.send(mimeMessageHelper.getMimeMessage());
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  public String geContentFromTemplate(Map < String, Object > model) {
    StringBuffer content = new StringBuffer();

    try {
      content.append(FreeMarkerTemplateUtils
          .processTemplateIntoString(fmConfiguration.getTemplate("email-template.txt"), model));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return content.toString();
  }

  private String UTF_8Encoder(String toEncode){
    ByteBuffer buffer = StandardCharsets.ISO_8859_1.encode(toEncode);
    String utf8EncodedString = StandardCharsets.ISO_8859_1.decode(buffer).toString();
    return utf8EncodedString;
  }


}