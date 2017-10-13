package com.wonder.SendMail;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.util.MailSSLSocketFactory;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

public class SendMail {
	public static String TEXT = "text/plain;charset=gb2312";
	public static String HTML = "text/html;charset=gb2312";
	private String host;
	private String user;
	private String password;
	private String from;
	private String to;
	private String subject;
	private BodyPart body;
	private boolean needAuth;
	private List attaches;
	
	
	public SendMail(){
		needAuth =true;
		attaches =new ArrayList();		
	}
	
	public SendMail(String host){
		needAuth =true;
		attaches =new ArrayList();
		this.host=host;
	}
	
	public SendMail(String host,String user,String password){
		needAuth =true;
		attaches =new ArrayList();		
		this.host=host;
		this.user=user;
		this.password=password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean isNeedAuth() {
		return needAuth;
	}

	public void setNeedAuth(boolean needAuth) {
		this.needAuth = needAuth;
	}
	public void setBody(String string,String contentType){		
		try {
			body = new MimeBodyPart();
			DataHandler dh=new DataHandler(string,contentType);
			body.setDataHandler(dh);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public void setBodyAsText(String string){
		setBody(string,TEXT);
	}
	public void setBodyAsHTML(String string){
		setBody(string,HTML);
	}
	public void setBodyFromFile(String filename){

		try {
			BodyPart mdp =new MimeBodyPart();
			FileDataSource fds = new FileDataSource(filename);
			DataHandler dh = new DataHandler(fds);
			mdp.setDataHandler(dh);
			attaches.add(mdp);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void setBodyFromUrl(String url){
		
		try {
			BodyPart mdp =new MimeBodyPart();
			URLDataSource ur = new URLDataSource(new URL(url));
			DataHandler dh =new DataHandler(ur);
			mdp.setDataHandler(dh);
			attaches.add(dh);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addAttachFromString(String string,String showname){
		
		try {
			BodyPart mdp =new MimeBodyPart();
			DataHandler dh=new DataHandler(string,TEXT);
			mdp.setFileName(MimeUtility.encodeWord(showname,"gb2312",null));
			mdp.setDataHandler(dh);
			attaches.add(dh);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addAttachFromFile(String filename,String showname){
		
		try {
			BodyPart mdp=new MimeBodyPart();
			FileDataSource fds=new FileDataSource(filename);
			DataHandler dh =new DataHandler(fds);
			mdp.setFileName(MimeUtility.encodeWord(showname,"gb2312",null));
			mdp.setDataHandler(dh);
			attaches.add(mdp);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void addAttachFromUrl(String url,String showname){
		
		try {
			BodyPart mdp =new MimeBodyPart();
			URLDataSource ur;
			ur = new URLDataSource(new URL(url));
			DataHandler dh=new DataHandler(ur);
			mdp.setFileName(MimeUtility.encodeWord(showname, "gb2312", null));
			mdp.setDataHandler(dh);
			attaches.add(mdp);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public void send() throws Exception{
		try{
		Properties props=new Properties();
		String SSL_FACTORY="javax.net.ssl.SSLSocketFactory";
		if(host!=null &&!host.trim().equals(""))
			props.setProperty("mail.smtp.host", host);
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.port", "465");
			props.put("mail.smtp.starttls.enable","true"); 
			props.setProperty("mail.smtp.socketFactory.port","465") ;
			Session s =Session.getInstance(props,null);
			props.setProperty("mail.smtp.auth","true");
			MailSSLSocketFactory sf=null;
			sf=new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			props.put("mail.smtp.ssl.socketFactory", sf);
			MimeMessage msg =new MimeMessage(s);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			if(to!=null)
				msg.addFrom(InternetAddress.parse(from));
			else
				throw new Exception("没有指定的收件人");
			if(to!=null)
				msg.addRecipients(javax.mail.Message.RecipientType.TO,InternetAddress.parse(to));
			else
					throw new Exception("没有制定收件人地址");
			Multipart mm =new MimeMultipart();
		if(body!=null)
			mm.addBodyPart(body);
		for(int i =0;i<attaches.size();i++){
			BodyPart part =(BodyPart)attaches.get(i);
			mm.addBodyPart(part);
		}
		msg.setContent(mm);
		msg.saveChanges();
		Transport transport =s.getTransport("smtp");
		transport.connect(host,user,password);
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
	}catch(Exception e){
		e.printStackTrace();
		throw new Exception("发送邮件失败",e);
	}
	}
}
