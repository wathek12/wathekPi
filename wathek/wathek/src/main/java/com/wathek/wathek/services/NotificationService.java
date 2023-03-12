package com.wathek.wathek.services;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {

    private final JavaMailSender javaMailSender;
    public static final String ACCOUNT_SID = "AC1f044f59e22a88f3539f4ea626695e82";
    public static final String AUTH_TOKEN = "34506ae8a95a41f03eab3ad51c509035";

    @Autowired
    public NotificationService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendMailNotification(String email, String subject, String text)
            throws MailException, InterruptedException {

        System.out.println("Sending email...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom("wathek.mathlouthi1@esprit.tn");
        mail.setSubject(subject);
        mail.setText(text);
        javaMailSender.send(mail);

        System.out.println("Email Sent!");
    }

    @Async
    public void sendMessageNotification(String to, String from, String text) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(from),
                        text)
                .create();
    }
}
