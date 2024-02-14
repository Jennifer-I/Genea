package com.genea.email;

import com.genea.dto.EmailDetailsRequest;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
@Slf4j

public class EmailService {
    private final JavaMailSender javaMailSender;
    private final ExecutorService executorService;


    public EmailService(JavaMailSender javaMailSender, ExecutorService executorService) {
        this.javaMailSender = javaMailSender;
        this.executorService = executorService;
    }

    public void sendEmailAsync(EmailDetailsRequest request) {
        executorService.submit(() -> sendEmail(request));

    }

    public String sendEmail(EmailDetailsRequest request) throws InterruptedException {
         int retryCount = 2;
        for (int i = 0; i < 3; i++) {
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(request.getMailFrom());
                helper.setTo(request.getMailTo());
                helper.setText(request.getText());
                helper.setSubject(request.getSubject());
                javaMailSender.send(message);
                log.info("Email sent successfully");
                break;

            } catch (Exception e) {
                Thread.sleep(15000);
                log.error("Failed to send email", e);
                if (i == retryCount - 1) {
                    throw new RuntimeException("Failed to send email");
                }
            }
        }
        return "Email sent successfully";
    }




}
