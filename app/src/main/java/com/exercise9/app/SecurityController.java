package com.exercise9.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.apache.log4j.Logger;

@Controller
public class SecurityController {
	private Logger logger = Logger.getLogger(SecurityController.class);

	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView adminLogin() {
		logger.info("Admin Login");

		ModelAndView modelAndView = new ModelAndView("login");

		return modelAndView;
	}

	@RequestMapping(value="/loginfailed", method=RequestMethod.GET)
	public ModelAndView loginFailed() {
		logger.info("Admin Login Failed");

		ModelAndView modelAndView = new ModelAndView("message");
		modelAndView.addObject("message", "Incorrect Login Details");
		modelAndView.addObject("redirectView", "http://localhost:8080/");				

		return modelAndView;
	}	
}