import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;
import javax.mail.Transport;

/**
 * Name: Audrey Rovero
 * File: SendEmail.java
 * Description: SendEmail.java handles sending an email
 * to the given client. Java Mail and SMTP are used to send the email.
 * The sender email is hardcoded as bombohelp01@gmail.com
 */

public class SendEmail {

    private String receiver;
    private final String sender = "bombohelp01@gmail.com";
    private String text;
    private String host = "smtp.gmail.com";
    private final String bombo_ps = "luv2helpu01*";

    public SendEmail(String r, String status, String comment) {
        this.receiver = r;
        this.text = "Your ticket status has updated: New Status -  " + status + " Comment - " + comment;

    }

    public void sendOutEmail() {

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        // get session
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, bombo_ps);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject("Bombo Support Ticket Update");
            message.setText(text);

            // send the email
            Transport.send(message);
            System.out.println("Mail successfully sent");

        } catch (MessagingException m) {
            m.printStackTrace();
        }

    }

}
