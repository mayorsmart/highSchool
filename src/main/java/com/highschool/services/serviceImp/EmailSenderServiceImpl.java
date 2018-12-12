package com.highschool.services.serviceImp;

import com.highschool.entity.Code;
import com.highschool.entity.User;
import com.highschool.services.CodeService;
import com.highschool.services.EmailSenderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class EmailSenderServiceImpl implements EmailSenderService {

	@Autowired
	private CodeService codeService;
	@Autowired
	private MailSender mailSender;
	@Value("ifabiyimo@gmail.com")
	private String fromAddress;
	@Value("${mayowa}")
	private String websiteAddr;


	public void sendMail(String toAddress, String subject, String msgBody){
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(fromAddress);
		msg.setTo(toAddress);
		msg.setSubject(subject);
		msg.setText(msgBody);
		mailSender.send(msg);
	}


	@Override
	public void sendActiveCode(User user) {
		List<Code> codes = codeService.findByCodeTypeAndUser(0, user);
		String msgBody = "Click or copy this link to the browser -> http://" + websiteAddr + "/rg/";
		for(Code code : codes){
			msgBody = msgBody + code.getCodeStr();
			break;
		}
		sendMail(user.getEmail(), "Active Your Account", msgBody);

	}



	@Override
	public void sendResetPasswordCode(User user) {
		Code code = new Code();
		code.setCodeDate(new Date());
		code.setCodeType(1);
		code.setUser(user);

		codeService.save(code);
		String msgBody = websiteAddr + "/rp/" + code.getCodeStr();
		sendMail(user.getEmail(), "Reset Your Passowrd", msgBody);

	}
}
