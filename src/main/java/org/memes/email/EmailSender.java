package org.memes.email;

import org.memes.context.BotContext;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    public static void main(String[] args) {
        final String username = "b76414024@gmail.com";
        final String password = BotContext.getToken();
        final String host = "smtp.gmail.com";
        final int port = 587;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.trust", host);


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("b76414024@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mykhailovaleksandr3@gmail.com"));
            message.setSubject("Test Email");
            message.setText("Hello, this is a test email from Java!");


            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
