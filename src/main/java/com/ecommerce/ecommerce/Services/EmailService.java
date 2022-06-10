package com.ecommerce.ecommerce.Services;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.ecommerce.ecommerce.Models.EmailValuesDTO;
import com.nylas.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

//https://developer.nylas.com/docs/learn-nylas/email/send-an-email-with-java/#reply-to-an-email
@Service
public class EmailService {
    NylasClient nylas = new NylasClient();
    NylasAccount account = nylas.account("0XLKKzuySb1OBIJqGWyZjyQ6NStKUU");
   /* @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    TemplateEngine templateEngine;
    @Value("${mail.urlFront}")
    private String urlFront;*/

    public void sendEmail(String emailAddress,String emailMsg,String subject) throws RequestFailedException, IOException {
        Draft draft = new Draft();
        draft.setSubject(subject);
        draft.setBody(emailMsg);
        draft.setTo(Arrays.asList(new NameEmail("My ResuelveAqui Friend", emailAddress)));
        account.drafts().send(draft);
    }

    /*public void sendEmail(EmailValuesDTO dto) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            Map<String, Object> model = new HashMap<>();
            model.put("userName", dto.getUserName());
            model.put("url", urlFront + dto.getTokenPassword());
            context.setVariables(model);
            String htmlText = templateEngine.process("email-template", context);
            helper.setFrom(dto.getMailFrom());
            helper.setTo(dto.getMailTo());
            helper.setSubject(dto.getSubject());
            helper.setText(htmlText, true);

            javaMailSender.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }*/
}
