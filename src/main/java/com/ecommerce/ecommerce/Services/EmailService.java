package com.ecommerce.ecommerce.Services;

import java.io.IOException;
import java.util.Arrays;

import com.nylas.*;
//https://developer.nylas.com/docs/learn-nylas/email/send-an-email-with-java/#reply-to-an-email
public class EmailService {
    NylasClient nylas = new NylasClient();
    NylasAccount account = nylas.account("0XLKKzuySb1OBIJqGWyZjyQ6NStKUU");

    public void sendEmail(String emailAddress,String emailMsg,String subject) throws RequestFailedException, IOException {
        Draft draft = new Draft();
        draft.setSubject(subject);
        draft.setBody(emailMsg);
        draft.setTo(Arrays.asList(new NameEmail("My ResuelveAqui Friend", emailAddress)));
        account.drafts().send(draft);
    }
}
