package emailHelper;

import com.selenium.testng.elite.utils.PathHelper;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.*;
import java.util.Properties;

public class sendEmailWithAttachment {

  public static void main(String[] args) throws IOException {

    // Load environment variables from .env file
    Dotenv dotenv = Dotenv.load();
    String emailSendTo = dotenv.get("EMAIL");
    String txtFilePath = PathHelper.getTestResultTextFilePath();
    String failedTestCasesFilePath = PathHelper.getListOfFailedTestCasesFile();

    // Read the content of the text file
    String subject = dotenv.get("SUBJECT");
    StringBuilder content = (!subject.contains("Re-run")) ? null : readTextFile(txtFilePath);
    StringBuilder failedTextCasesList = readFailedTextCaseListFile(failedTestCasesFilePath);

    // Build the email body
    String body =
        """
          Hello Team,

          Please find attached the report for Automation Test. You can get overview of all the test results here:"""
            + "\n\n"
            + content
            + "\n\n"
            + failedTextCasesList
            + """
          -------------------------------------------------------------------------------

          This is an auto-generated email. Please do not reply to it.

          Thanks,
          Automation Team""";

    // Fetch sender email and password from GitHub Secrets
    String senderEmail = System.getenv("EMAIL_USER");
    String senderAPPPassword = System.getenv("EMAIL_PASSWORD");

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
      e.printStackTrace();
      System.out.println("Failed to send email: " + e.getMessage());
    }
  }

  private static StringBuilder readFailedTextCaseListFile(String filePath) throws IOException {
    StringBuilder content = new StringBuilder();
    BufferedReader reader = new BufferedReader(new FileReader(filePath));
    String line;
    while ((line = reader.readLine()) != null) {
      content.append(line).append("\n"); // Append the line to the content
    }
    reader.close();
    return content;
  }

  private static StringBuilder readTextFile(String filePath) {
    StringBuilder content = new StringBuilder();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filePath));
      String line;
      int lineCount = 0;
      while ((line = reader.readLine()) != null && lineCount < 4) {
        // Remove the unwanted string from the 4th line
        if (line.contains("<<< FAILURE! -- in TestSuite")) {
          line = line.replace("<<< FAILURE! -- in TestSuite", "");
        }
        content.append(line).append("\n"); // Append the line to the content
        lineCount++;
      }
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
      content.append("Congratulations! No failed test cases found");
    }
    return content;
  }
}
