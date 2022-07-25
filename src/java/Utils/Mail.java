/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author Benjamin
 */
public class Mail {
    public boolean sendMailConfirm(String emailReceiver, String subject, String message) {
        String id = "Laptop89";
        String sender = "se1621.net.shopping@gmail.com";
        String receiver = emailReceiver;
        String password = "Anhviet123";
        return sendMail(id, receiver, sender, subject, message, true, password);
    }

    public boolean sendMail(String id, String to, String from,
            String subject, String body, boolean content,
            String password) {
        try {
            // acquire a secure SMTPs session
            Properties pros = new Properties();
            pros.put("mail.transport.protocol", "smtps");
            pros.put("mail.smtps.host", "smtp.gmail.com");
            pros.put("mail.smtps.port", 465);
            pros.put("mail.smtps.auth", "true");
            pros.put("mail.smtps.quitwait", "false");
            Session session = Session.getDefaultInstance(pros);
            session.setDebug(true);
            // Wrap a message in session
            Message message = new MimeMessage(session);
            message.setSubject(subject);
            if (content) {
                message.setContent(body, "text/html;charset=UTF-8");
            } else {
                message.setText(body);
            }
            // specify E-mail address of Sender and Reciever
            Address sender = new InternetAddress(from, id);
            Address receiver = new InternetAddress(to);
            message.setFrom(sender);
            message.setRecipient(Message.RecipientType.TO,
                    receiver);
            // sending an E-mail
            try {
                Transport tt = session.getTransport(); // acqruiring a connection to remote server
                tt.connect(from, password);
                tt.sendMessage(message,
                        message.getAllRecipients());
                System.out.println("E-Mail Sent Successfully");
                return true;
            } catch (MessagingException e) {
            }
        } catch (MessagingException e) {
            System.out.println(e.toString());
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.toString());
        }
        // return the status of email
        return false;
    }
}
