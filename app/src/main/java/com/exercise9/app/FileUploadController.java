package com.exercise9.app;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.exercise9.core.service.CreateEmployeeFromFile;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadController {

	@Autowired
	private CreateEmployeeFromFile createEmployeeFromFile;
	private Logger logger = Logger.getLogger(FileUploadController.class);

	@RequestMapping(value="/fileupload", method=RequestMethod.GET)
	public ModelAndView showUploadForm() {
		logger.info("File Upload showForm()");
		ModelAndView modelAndView = new ModelAndView("uploadform");
		return modelAndView;
	}

	@RequestMapping(value="/fileupload", method=RequestMethod.POST)
	public ModelAndView submitFile(@RequestParam MultipartFile file) {
		logger.info("File Upload");
		String message = null;

		if(!file.isEmpty()) {
			message = createEmployeeFromFile.parseFile(file).toString();
		} else {
			message = "No File Chosen";
		}

		ModelAndView modelAndView = new ModelAndView("message");
		modelAndView.addObject("message", message);
		modelAndView.addObject("redirectView", "http://localhost:8080/employee");		

		return modelAndView;
	}
}