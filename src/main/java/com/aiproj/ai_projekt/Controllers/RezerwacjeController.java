package com.aiproj.ai_projekt.Controllers;

import com.aiproj.ai_projekt.DAO_repos.ResPokRepository;
import com.aiproj.ai_projekt.POJO.pokoj;
import com.aiproj.ai_projekt.POJO.reshist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
@RequestMapping
public class RezerwacjeController {
	
	@Autowired
	ResPokRepository respokrepo ;
	
	@RequestMapping("/user/rezerwacja")
	public String rezerwacje()
	{
		return "Rezerwacje";
	}
	
	@RequestMapping(value = "/user/rezerwacja",method =RequestMethod.POST)
	public @ResponseBody List<pokoj> controllerMethod(@RequestParam(value="x[]") String x[]){
		
		List<pokoj> pokoje = respokrepo.findfreerooms(x[1],x[2]);
		return pokoje;
	}
	
	@RequestMapping(value = "/user/rezerwacja/confirm",method =RequestMethod.POST)
	public @ResponseBody void controllerMethod2(@RequestParam(value="x[]") String x[]){
		
		respokrepo.MakeReservation(x[1],Integer.parseInt(x[2]));
		
	}
	
	
	
	
	@RequestMapping ("/user/mojerez")
	public String mojerez ()
	{
		return ("historiarezerwacji");
	}
	
	@RequestMapping (value = "/user/mojerez",method = RequestMethod.POST)
	public @ResponseBody List<reshist> mojerez2 ()
	{
		return respokrepo.myres();
	}
	
	
	
}


