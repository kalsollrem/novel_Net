package com.project.novelnet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
public class MailService {
    @Autowired
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    //메일용난수
    public String codemaker()
    {
        String code = "";
        for (int i=0; i<15; i++){
            code += (char)((Math.random()*26)+97);
        }
        return code;
    }

    //메일서비스
    public void mailSend(String toEmail, String code){
        SimpleMailMessage message = new SimpleMailMessage();

        System.out.println("이메일 : " + toEmail +  "/ 코드 : " + code );

        try {
            message.setFrom("gomservice31@gmail.com");

            //수신자
            message.setTo(toEmail);

            //메일제목
            message.setSubject("NovelNet에 가입하신 것을 환영합니다.");

            //메일내용
            message.setText("NovelNet에 가입하신 것을 환영합니다. 가입인증을 위해 하단의 링크를 눌러주세요 \n \n \n" + "http://localhost:8080/novelnet/JoinCheak?code=" + code);

            //메일발송
            mailSender.send(message);
        }
        catch (MailParseException e) {
            e.printStackTrace();
        }
        catch (MailAuthenticationException e) {
            e.printStackTrace();
        }
        catch (MailSendException e) {
            e.printStackTrace();
        }
        catch (MailException e) {
            e.printStackTrace();
        }

    }
    //메일서비스
    public void passFinemailSend(String mailname, String password){
        SimpleMailMessage message = new SimpleMailMessage();

        System.out.println("이메일 : " + mailname +  "/ 비번 : " + password );

        try {
            message.setFrom("gomservice31@gmail.com");

            //수신자
            message.setTo(mailname);

            //메일제목
            message.setSubject("NovelNet에서 잃어버린 계정 비밀번호가 도착했습니다.");

            //메일내용
            message.setText("잃어버리신 비밀번호는 \n \n \n" +password+ "\n \n \n 입니다. 그럼 즐거운 시간되세요. \n \n \n"+ "http://localhost:8080/novelnet");

            //메일발송
            mailSender.send(message);
        }
        catch (MailParseException e) {
            e.printStackTrace();
        }
        catch (MailAuthenticationException e) {
            e.printStackTrace();
        }
        catch (MailSendException e) {
            e.printStackTrace();
        }
        catch (MailException e) {
            e.printStackTrace();
        }

    }
}
