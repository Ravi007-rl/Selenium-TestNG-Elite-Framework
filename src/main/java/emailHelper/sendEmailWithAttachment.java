package emailHelper;

import com.selenium.testng.elite.utils.PathHelper;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.util.Properties;

public class sendEmailWithAttachment {

  public static void main(String[] args) {

    // Load environment variables from .env file
    Dotenv dotenv = Dotenv.load();
    String emailSendTo = dotenv.get("EMAIL");

    // Fetch subject and body from .env
    String subject = "Automation Test Report Results";
    String body =
        """
          Hello Team,

          Please find attached the report for Automation Test.

          Thanks,
          Automation Team""";

    // Fetch sender email and password from GitHub Secrets
    String senderEmail = System.getenv("SENDER_EMAIL");
    String senderAPPPassword = System.getenv("SENDER_APP_PASSWORD");

    // SMTP server information
    String host = "smtp.gmail.com";
    int port = 587;

    // SMTP server properties
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", port);

    // Get email session
    Session session =
        Session.getInstance(
            props,
            new Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderAPPPassword);
              }
            });

    try {
      // Create a new email message
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(senderEmail));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailSendTo));
      message.setSubject(subject);

      MimeBodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setText(body);

      // Create multipart message (to include attachment)
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);

      // Add attachment
      MimeBodyPart attachmentBodyPart = new MimeBodyPart();
      attachmentBodyPart.attachFile(new File(PathHelper.getPathForReport()));
      multipart.addBodyPart(attachmentBodyPart);

      // Set the complete message content
      message.setContent(multipart);

      // Send the email
      Transport.send(message);
      System.out.println("Email sent successfully!");
    } catch (Exception e) {
      System.out.println("Failed to send email: " + e.getMessage());
    }
  }
}
