package com.tollsecure.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tollsecure.entity.TollConfig;
import com.tollsecure.entity.TollPlaza;
import com.tollsecure.entity.User;
import com.tollsecure.service.TollConfigService;
import com.tollsecure.service.TollPlazaService;


@Controller
@RequestMapping("/tollConfig")
public class TollConfigController {
	
	@Autowired 
	private TollConfigService theTollConfigService;
	
	@Autowired
	private TollPlazaService theTollPlazaService;

	@GetMapping("/list")
	public String getTollConfig(@RequestParam("plazaId") Integer plazaId, Model theModel, HttpSession session) {

		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		List<TollConfig> theTollConfigs = theTollConfigService.getAllConfigs(plazaId);
		
		theModel.addAttribute("tollConfigs", theTollConfigs);
		
		return "toll_config_list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForTollConfigUpdate(Model theModel, HttpSession session, HttpServletRequest request) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";

		//get all the toll tollPlazas
		List<TollPlaza> tollPlazas = theTollPlazaService.getAllTollPlazas();
		
		//let us say a single tollPlaza is not yet added
		if (tollPlazas.size()==0) {
			theModel.addAttribute("error", "Please add a Toll Plaza first before Configuring Vehicle Class");
			return "redirect:/tollPlaza/showFormForAdd";
		}
		
		theModel.addAttribute("allTollPlazas", tollPlazas);
		
		//add message
		String message = request.getParameter("message");
		String date = request.getParameter("date");
		theModel.addAttribute("message", message);
		theModel.addAttribute("date", date);
		
		return "toll_config_form";
	}
	
	@PostMapping("/saveTollConfig")
	public String saveForm(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		//get all the parameters required
		Integer plazaId = Integer.parseInt(request.getParameter("plazaId"));
		String vehicleClass = request.getParameter("vehicleClass");
		Float tollAmt = Float.parseFloat(request.getParameter("tollAmt"));
		String journeyType = request.getParameter("journeyType");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		//get the tollConfig for given tollPlaza and given vehicleClass and journey type
		List<TollConfig> tollConfig = theTollConfigService.getTollConfigForUpdate(plazaId, vehicleClass, journeyType, startDate+" "+"00:00:00", endDate+" "+"00:00:00");
		
//		System.out.println(startDate+"-=-=-=-===>>>"+endDate);
		//if object does not exist
		if(tollConfig==null) { //create new object and store it
			//do nothing 
		} else {
			//update the old records
	
			//deactivate all the old records
			for (TollConfig tollConfiga : tollConfig) {
				tollConfiga.setModificationTimeStamp(new Date());
				tollConfiga.setModifiedUserId(userFromSession.getUserId());
				tollConfiga.setStatusFlag("DEACTIVE");
				theTollConfigService.saveTollConfig(tollConfiga);
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		TollConfig newTollConfig = new TollConfig();
		newTollConfig.setTollPlazaId(plazaId);
		newTollConfig.setVehicleClass(vehicleClass);
		newTollConfig.setTollAmt(tollAmt);
		newTollConfig.setJourneyType(journeyType);
		newTollConfig.setCreateTimeStamp(new Date());
		newTollConfig.setCreateUserId(userFromSession.getUserId());
		try {
			newTollConfig.setEffectiveFrom(sdf.parse(startDate+" "+"00:00:00"));
			newTollConfig.setEffectiveTo(sdf.parse(endDate+" "+"00:00:00"));
		} catch (Exception e) {
			System.out.println("e");
		}
		
		newTollConfig.setStatusFlag("ACTIVE");
		
		//save the new user
		theTollConfigService.saveTollConfig(newTollConfig);
		
		theModel.addAttribute("message", "Successfully added: "+vehicleClass+", Journe Type: "+journeyType+", Amount: "+tollAmt+", till date: "+endDate+" "+"00:00:00");
		theModel.addAttribute("date", endDate);
		return "redirect:/tollConfig/showFormForUpdate";
	}
	
}


