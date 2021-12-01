package com.example.serverless;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class EmailSender implements RequestHandler<SNSEvent, Object> {

    public String handleRequest(SNSEvent event, Context context) {
        Logger logger = Logger.getLogger("LoggingDemo");
        logger.info("start handleRequest!");
        String record = event.getRecords().get(0).getSNS().getMessage();
        JSONObject jsonstr = JSON.parseObject(record);
        String emailAddress = jsonstr.getString("email");
        String token = jsonstr.getString("token");
//        Boolean used = jsonstr.getBoolean("used");

//        logger.info("jsonStr: " + jsonstr);
//
//        logger.info("emailAddress: " + emailAddress);
//        logger.info("token: " + token);
//        logger.info("used: " + used);
//
//        if (used == null || used) {
//            logger.info("used is null or it has been used!");
//            return null;
//        }

        String body = "Please verify your email with this link: " +
                "http://prod.ethanzhang1997.me/v1/verifyUserEmail?email=" + emailAddress + "&token=" + token;

        Properties props = new Properties();
        props.put("mail.smtp.host", "email-smtp.us-east-1.amazonaws.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("AKIAQSRZMGLTPZE7FMMP", "BMCoOXsuzuC+dvKebIX1h0gOIz1/tvUhLyL28nZSMwUa");
            }
        };
        Session session = Session.getInstance(props, auth);
        logger.info("Get session!");
        sendEmail(session, emailAddress, "Verify your email!", body);

        DynamoDBClientFactory.getClient().updateItem(new Consumer<UpdateItemRequest.Builder>() {
            @Override
            public void accept(UpdateItemRequest.Builder req) {
                req.tableName("User-Tokens");

                Map<String, AttributeValue> map = new HashMap<>();
                map.put("user_email", AttributeValue.builder().s(emailAddress).build());

                req.key(map);

                Map<String, AttributeValueUpdate> attributeUpdates = new HashMap<>();
                attributeUpdates.put("used",
                        AttributeValueUpdate.builder().value(AttributeValue.builder().bool(true).build()).build());

                req.attributeUpdates(attributeUpdates);
            }
        });

        logger.info("Successful!");
        return null;
    }

    public static void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("no_reply@prod.ethanzhang1997.me"));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
