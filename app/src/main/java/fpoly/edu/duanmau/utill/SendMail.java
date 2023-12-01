package fpoly.edu.duanmau.utill;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
    private final String username = "lexuanthao.tk7@gmail.com";
    private final String password = "odxtrdxbvwxjybjo";

    /*
    String emailTo: Địa chỉ email của người nhận.
    String subject: Tiêu đề email.
    String body: Nội dung email.
    */
    public void Send(Context context, String emailTo, String subject, String body) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Properties props = new Properties();
                props.setProperty("mail.transport.protocol", "smtp"); // Simple Mail Transfer Protocol. // cài đặt sever của gmail
                props.setProperty("mail.host", "smtp.gmail.com");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.socketFactory.fallback", "false");
                props.setProperty("mail.smtp.quitwait", "false");

                // để gửi email, sử dụng thông tin đăng nhập (username và password).
                Session session = Session.getInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
                // thiết lập người gửi, người nhận, tiêu đề và nội dung email
                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(emailTo));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
                    message.setSubject(subject);
                    message.setText(body);
                    Transport.send(message); // để gửi email.

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Send mail Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}