package org.zerock.wego.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
//
//@RequestMapping("/sendEmail")
//public class sendEmail(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        //메일 관련 정보 
//        String host = "smtp.naver.com"; 
//        final String username = "*******"; //네이버 이메일 주소중 @ naver.com 앞주소만 작성
//        final String password = "*******"; //네이버 이메일 비밀번호를 작성
//        int port=465;                      //네이버 STMP 포트 번호
//        
//        //메일 내용 
//        String recipient = "*******@naver.com"; //메일을 발송할 이메일 주소를 기재해 줍니다.
//        String subject = "이메일 발송시 제목";    //메일 발송시 제목을 작성
//        String body = "이름: 홍길동입니다. \n\n"; //메일 발송시 내용 작성
//        
//        Properties props = System.getProperties();
//       
//        props.put("mail.smtp.host", host);
//        props.put("mail.smtp.port", port);
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.ssl.enable", "true");
//        props.put("mail.smtp.ssl.trust", host);
// 
//        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() { 
//            String un=username;
//            String pw=password; 
//            protected PasswordAuthentication getPasswordAuthentication() { 
//                return new PasswordAuthentication(un, pw); 
//            } 
//        });
//        session.setDebug(true); //for debug 
//        
//        Message mimeMessage = new MimeMessage(session); 
//        mimeMessage.setFrom(new InternetAddress("*******@naver.com")); 
//        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); 
//        mimeMessage.setSubject(subject); 
//        mimeMessage.setText(body); 
//        Transport.send(mimeMessage);
//    }
//}