package com.example.serverless;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.*;

public class Test {

    public static void main(String[] args) {
//        SNSEvent snsEvent = new SNSEvent();
//        ArrayList<SNSEvent.SNSRecord> records = new ArrayList<SNSEvent.SNSRecord>();
//
//        SNSEvent.SNSRecord e = new SNSEvent.SNSRecord();
//        SNSEvent.SNS sns = new SNSEvent.SNS();
//        sns.setMessage("{\"msg_type\":\"JsonString\",\"email\":\"ethanzhang1997@gmail.com\",\"token\":\"dynamodbUser.getToken()\",\"used\":\"false\"}");
//        e.setSns(sns);
//        records.add(e);
//        snsEvent.setRecords(records);
//        EmailSender emailSender = new EmailSender();
//        emailSender.handleRequest(snsEvent, null);

//        String emailAddress = "ethanzhang1997@gmail.com";
//        String token = "1231231231";
//
//        String body = "Please verify your email with this link: " +
//                "http://prod.ethanzhang1997.me/v1/verifyUserEmail?email=" + emailAddress + "&token=" + token;
//
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "email-smtp.us-east-1.amazonaws.com"); //SMTP Host
//        props.put("mail.smtp.port", "587"); //TLS Port
//        props.put("mail.smtp.auth", "true"); //enable authentication
//        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
//
//        //create Authenticator object to pass in Session.getInstance argument
//        Authenticator auth = new Authenticator() {
//            //override the getPasswordAuthentication method
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("AKIAQSRZMGLTPZE7FMMP", "BMCoOXsuzuC+dvKebIX1h0gOIz1/tvUhLyL28nZSMwUa");
//            }
//        };
//        Session session = Session.getInstance(props, auth);
//        EmailSender.sendEmail(session, emailAddress, "Verify your email!", body);
    }
}
