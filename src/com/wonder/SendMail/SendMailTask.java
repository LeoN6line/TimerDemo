package com.wonder.SendMail;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.TimerTask;

public class SendMailTask extends TimerTask {
	public static long runTime = 1;
	public SendMailTask(){
		
	};
	
	public void run() {
		// TODO Auto-generated method stub
		java.util.Date today =new java.util.Date();
		today.setTime(today.getTime());
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startdate = formatter.format(today);
		String day =String.valueOf(Integer.parseInt(startdate.substring(8,10))-1);
		if(day.length()==1)
			startdate = startdate.substring(0,8)+"0"+day+"00:00:00";
		else
			startdate = startdate.substring(0,8)+"0"+day+"00:00:00";
		String date =startdate.substring(0,4)+startdate.substring(5,7)+startdate.substring(8,10);
//		try{
//			IncomeReport.statData();
//	}catch(IOException e1){
//		e1.printStackTrace();
//	}catch(SQLException e1){
//		e1.printStackTrace();
//	}
		if(runTime>=100)
			runTime = 0;
		String host = "smtp.163.com";
		String user = "xxxxxxx@163.com";
		String password = "0809sr";
		String subject = "网上租赁续提统计报表";
		String to = "1807671877@qq.com";
		String from = "haholh@163.com";
		String bodyashtml = "请查看附件（网上租赁续提统计报表）！";
		String attachfile ="C:\\Users\\15036\\Desktop\\查询数据.xlsx";
		try{
			SendMail send = new SendMail();
			send.setHost(host);
			send.setUser(user);
			send.setPassword(password);
			send.setSubject(subject);
			send.setTo(to);
			send.setFrom(from);
			send.setBodyAsHTML(bodyashtml);
			send.addAttachFromFile(attachfile,""+date+"_income.xls");
			send.send();
			System.out.println("邮件发送成功！");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
