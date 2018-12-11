package com.aiproj.ai_projekt.Controllers;
import com.aiproj.ai_projekt.DAO_repos.UserRepository;
import com.aiproj.ai_projekt.POJO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
@RequestMapping
public class LoginController {
	@Autowired
	UserRepository userRepository;

    /*public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String login(Map model) {
		
		return "login";
	}
	
	
	
	
	
	@ModelAttribute(value = "User")
	public User newuser()
	{
		return new User();
	}
	@RequestMapping(value = "/login/register",method = RequestMethod.POST)
	public  String register(@ModelAttribute("User") User user, Map model)
	{
		if(userRepository.check(user.getLogin())){
			
			model.put("x",userRepository.check(user.getLogin()));
			return "login";
		}
		userRepository.save(user);
		
		return "redirect:/login";
	}
	
	
	
}
