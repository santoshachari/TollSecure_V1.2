package com.seoniproject.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seoniproject.entity.TollPlaza;
import com.seoniproject.entity.User;
import com.seoniproject.service.TollPlazaService;

@Controller
@RequestMapping("/tollPlaza")
public class TollPlazaController {

	@Autowired
	private TollPlazaService tollPlazaService;
	
	@GetMapping("/list")
	public String tollPlazaList(Model theModel, HttpSession session) {

		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		//get list of toll Plazas from service 
		List<TollPlaza> theTollPlazas = tollPlazaService.getAllTollPlazas();
		
		//add attributes to the model
		theModel.addAttribute("tollPlazas",theTollPlazas);
		
		return "tollplaza_list";
	}
	
	@PostMapping("/saveTollPlaza")
	public String saveOrUpdateTollPlaza(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		String tollPlazaName = request.getParameter("tollPlazaName");
		
		//if the tollPlaza name already exist
		List<TollPlaza> plazaWithSameName = tollPlazaService.getTollPlazaFromName(tollPlazaName);
		
		//if you don't to warn for same tollPlaza then check for ids also
		if(plazaWithSameName.size() != 0) {
			
			//get all tollPlazas and list them as per new instructions
			List<TollPlaza> theTollPlazas = tollPlazaService.getAllTollPlazas();
			
			//create a new TollPlaza
			TollPlaza thePlaza = new TollPlaza();
			
			//bind data
			theModel.addAttribute("tollPlaza",thePlaza);
			
			// This is added in 2nd version -- version 2.0
			theModel.addAttribute("allTollPlazas", theTollPlazas);
			
			theModel.addAttribute("error", "Toll Plaza Name already exist");
			
			return "redirect:/tollPlaza/showFormForAdd";
		}
		
		
		//if it is an update
		//if (previousUserId != null && theTollPlazaId != null) { //this means it is an update
		String theTollPlazaId1 = request.getParameter("tollPlazaId");
		
		if (theTollPlazaId1 != null && !theTollPlazaId1.equals("")) {//this means it is an update
			Integer theTollPlazaId = Integer.parseInt(theTollPlazaId1);
			List<TollPlaza> theTollPlazaPrevious = tollPlazaService.getTollPlaza(theTollPlazaId);
			
			//modify the previous tollPlaza
			theTollPlazaPrevious.get(0).setTollPlazaName(tollPlazaName);
			theTollPlazaPrevious.get(0).setModifiedUserId(userFromSession.getUserId());
			theTollPlazaPrevious.get(0).setModificationTimeStamp(new Date());
			tollPlazaService.saveTollPlaza(theTollPlazaPrevious.get(0));
		} else { //this is an insert and not update
			TollPlaza theTollPlaza = new TollPlaza();
			theTollPlaza.setTollPlazaName(tollPlazaName);
			theTollPlaza.setCreateUserId(userFromSession.getUserId());
			theTollPlaza.setCreateTimeStamp(new Date());
			tollPlazaService.saveTollPlaza(theTollPlaza);
		}
		
		return "redirect:/tollPlaza/showFormForAdd";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel, HttpSession session, HttpServletRequest request) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		//create a model attribute to bind the data
		TollPlaza theTollPlaza = new TollPlaza();
		
		//get all tollPlazas and list them as per new instructions
		List<TollPlaza> theTollPlazas = tollPlazaService.getAllTollPlazas();
		
		//bind data
		theModel.addAttribute("tollPlaza",theTollPlaza);
		
		// This is added in 2nd version -- version 2.0
		theModel.addAttribute("allTollPlazas", theTollPlazas);
		
		//if request is coming from lanes or shifts or employee configuration --this is not yet implemented
		String plazaId = request.getParameter("tollPlazaId");
		if (plazaId != null) theModel.addAttribute("plazaId", plazaId);
		
		
		//sometimes there may be an error for entering an existing name
		String error = request.getParameter("error");
		
		if(error != null && error != "") {
			theModel.addAttribute("error", error);
		}
		
		return "tollplaza_add_form";
	}
	
	//we may not need this in the version 2
/*	@GetMapping("/showFormForUpdate")
	public String shhowFormForUpdate(@RequestParam("plazaId") int theId, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		//get the Plaza from service
		TollPlaza theTollPlaza = tollPlazaService.getTollPlaza(theId);
		
		//set customer as a model attribute to pre-populate the form
		theModel.addAttribute("tollPlaza",theTollPlaza);
		
		return "tollplaza_add_form";
	}*/
	
	@GetMapping("/delete")
	public String deleteTollPlaza(@RequestParam("plazaId") int theId, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		tollPlazaService.deletePlaza(theId);
		
		return "redirect:/tollPlaza/list";
	}
	
}
