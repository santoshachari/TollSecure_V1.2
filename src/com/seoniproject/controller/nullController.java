package com.seoniproject.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/null")
public class nullController {

	@RequestMapping("/getIt")
	public String getIt(HttpServletRequest request, Model theModel, HttpSession session) {
		String address = "ws://10.0.2.27:8081/cam/realmonitor";
		theModel.addAttribute("address", address);
		return "cam";
	}
	
	@RequestMapping("/saveImg")
	public String saveImg(HttpServletRequest request) {
		
		String imgSrc = request.getParameter("image");
		System.out.println("-=-=-=-=Image: "+imgSrc);
		System.out.println("hiiiiiiiiiiiii");
		return null;
	}
	
	@RequestMapping("/image")
	public String getImage() {
		return "image";
	}
}







