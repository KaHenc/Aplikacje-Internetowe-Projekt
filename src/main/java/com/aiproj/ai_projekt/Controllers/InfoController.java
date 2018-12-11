package com.aiproj.ai_projekt.Controllers;

import com.aiproj.ai_projekt.Services.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Controller
@RequestMapping
public class InfoController {
	private final EmailSender emailSender;
	private final TemplateEngine templateEngine;
	
	@Autowired
	public InfoController(EmailSender emailSender,
	                      TemplateEngine templateEngine){
		this.emailSender = emailSender;
		this.templateEngine = templateEngine;
	}
	private String data_rez;
	
	@RequestMapping("/")
	public String rezerwacje() {
		return "homeinfo";
	}
	
	@RequestMapping("/user/kontakt")
	public String kontakt() {
		return "kontaktrecepcja";
	}
	
	
	@RequestMapping(value = "/user/kontakt/mail",method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public  void  send(@RequestParam (value="x[]") String x[]) {
		
		Context context = new Context();
		context.setVariable("header", "Odpowiedz na: "+x[4]);
		context.setVariable("title", x[2]+" "+x[3]);
		context.setVariable("description", x[5]);
		
		String body = templateEngine.process("mail",context);
		emailSender.sendEmail(x[1], body);
		
	}
	
	
}