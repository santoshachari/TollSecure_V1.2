package com.seoniproject.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seoniproject.entity.Lane;
import com.seoniproject.entity.Shift;
import com.seoniproject.entity.TollPlaza;
import com.seoniproject.entity.User;
import com.seoniproject.service.LaneService;
import com.seoniproject.service.ShiftService;
import com.seoniproject.service.TollPlazaService;
import com.seoniproject.service.UserService;

@Controller
@RequestMapping("/jasper")
public class JasperController {
	
	@Autowired
	private TollPlazaService theTollPlazaService;
	
	@Autowired
	private LaneService theLaneService;
	
	@Autowired
	private ShiftService theShiftService;
	
	@Autowired
	private UserService theUserService;

	//the below two reports are sample reports
	@RequestMapping("/sampleReport")
	public String lanesList() {
		
		return "sample_report";
	}
	
	@RequestMapping("/loginLogoutSample")
	public String loginLogout(Model theModel, HttpSession session) {
		//handling session
				User userFromSession = (User) session.getAttribute("userFromSession");
				if(userFromSession==null) return "redirect:/";
				if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				

				//send all tollPlazas and lanes
				List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
				TollPlaza lastTollPlaza = allTollPlazas.get(allTollPlazas.size()-1);
				
				//get all the lanes, shifts, TC for last toll plaza
				List<Lane> allLanes = theLaneService.getAllLanes(lastTollPlaza.getTollPlazaId());
				List<Shift> allShifts = theShiftService.getAllShifts(lastTollPlaza.getTollPlazaId());
				List<User> allUsers = theUserService.getAllUsers(lastTollPlaza.getTollPlazaId());

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				theModel.addAttribute("allTollPlazas", allTollPlazas);
				theModel.addAttribute("allLanes", allLanes);
				theModel.addAttribute("allShifts", allShifts);
				theModel.addAttribute("allOperators", allUsers);
				theModel.addAttribute("today", sdf.format(today));
		return "login_logout_sample";
	}
	
	
	@RequestMapping("/consolidatedTrafficRevenue")
	public String consolidatedTrafficRevenue(Model theModel, HttpSession session) {
		//handling session
				User userFromSession = (User) session.getAttribute("userFromSession");
				if(userFromSession==null) return "redirect:/";
				if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				

				//send all tollPlazas and lanes
				List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
				TollPlaza lastTollPlaza = allTollPlazas.get(allTollPlazas.size()-1);
				
				//get all the lanes, shifts, TC for last toll plaza
				List<Lane> allLanes = theLaneService.getAllLanes(lastTollPlaza.getTollPlazaId());
				List<Shift> allShifts = theShiftService.getAllShifts(lastTollPlaza.getTollPlazaId());
				List<User> allOperators = theUserService.getAllOperators(lastTollPlaza.getTollPlazaId());

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				theModel.addAttribute("allTollPlazas", allTollPlazas);
				theModel.addAttribute("allLanes", allLanes);
				theModel.addAttribute("allShifts", allShifts);
				theModel.addAttribute("allOperators", allOperators);
				theModel.addAttribute("today", sdf.format(today));
		return "consolidate_traffic_revenue_report";
	}
	
	@RequestMapping("/consolidatedTraffic") 
	public String consolidatedTraffic(Model theModel, HttpSession session) {
		//handling session
				User userFromSession = (User) session.getAttribute("userFromSession");
				if(userFromSession==null) return "redirect:/";
				if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				

				//send all tollPlazas and lanes
				List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
				TollPlaza lastTollPlaza = allTollPlazas.get(allTollPlazas.size()-1);
				
				//get all the lanes, shifts, TC for last toll plaza
				List<Lane> allLanes = theLaneService.getAllLanes(lastTollPlaza.getTollPlazaId());
				List<Shift> allShifts = theShiftService.getAllShifts(lastTollPlaza.getTollPlazaId());
				List<User> allOperators = theUserService.getAllOperators(lastTollPlaza.getTollPlazaId());

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				theModel.addAttribute("allTollPlazas", allTollPlazas);
				theModel.addAttribute("allLanes", allLanes);
				theModel.addAttribute("allShifts", allShifts);
				theModel.addAttribute("allOperators", allOperators);
				theModel.addAttribute("today", sdf.format(today));
		return "consolidate_traffic_report";
	}
	
	@RequestMapping("/DatewiseLanewiseTraffic")
	public String DatewiseLanewiseTraffic(Model theModel, HttpSession session) {
		//handling session
				User userFromSession = (User) session.getAttribute("userFromSession");
				if(userFromSession==null) return "redirect:/";
				if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				

				//send all tollPlazas and lanes
				List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
				TollPlaza lastTollPlaza = allTollPlazas.get(allTollPlazas.size()-1);
				
				//get all the lanes, shifts, TC for last toll plaza
				List<Lane> allLanes = theLaneService.getAllLanes(lastTollPlaza.getTollPlazaId());
				List<Shift> allShifts = theShiftService.getAllShifts(lastTollPlaza.getTollPlazaId());
				List<User> allOperators = theUserService.getAllOperators(lastTollPlaza.getTollPlazaId());

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				theModel.addAttribute("allTollPlazas", allTollPlazas);
				theModel.addAttribute("allLanes", allLanes);
				theModel.addAttribute("allShifts", allShifts);
				theModel.addAttribute("allOperators", allOperators);
				theModel.addAttribute("today", sdf.format(today));
		return "datewise_lanewise_traffic_report";
	}
	
	@RequestMapping("/DatewiseLanewiseRevenue")
	public String DatewiseLanewiseRevenue(Model theModel, HttpSession session) {
		//handling session
				User userFromSession = (User) session.getAttribute("userFromSession");
				if(userFromSession==null) return "redirect:/";
				if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				

				//send all tollPlazas and lanes
				List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
				TollPlaza lastTollPlaza = allTollPlazas.get(allTollPlazas.size()-1);
				
				//get all the lanes, shifts, TC for last toll plaza
				List<Lane> allLanes = theLaneService.getAllLanes(lastTollPlaza.getTollPlazaId());
				List<Shift> allShifts = theShiftService.getAllShifts(lastTollPlaza.getTollPlazaId());
				List<User> allOperators = theUserService.getAllOperators(lastTollPlaza.getTollPlazaId());

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				theModel.addAttribute("allTollPlazas", allTollPlazas);
				theModel.addAttribute("allLanes", allLanes);
				theModel.addAttribute("allShifts", allShifts);
				theModel.addAttribute("allOperators", allOperators);
				theModel.addAttribute("today", sdf.format(today));
		return "datewise_lanewise_revenue_report";
	}
	
	@RequestMapping("/shortExcess")
	public String shortExcess(Model theModel, HttpSession session) {
		//handling session
				User userFromSession = (User) session.getAttribute("userFromSession");
				if(userFromSession==null) return "redirect:/";
				if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				

				//send all tollPlazas and lanes
				List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
				TollPlaza lastTollPlaza = allTollPlazas.get(allTollPlazas.size()-1);
				
				//get all the lanes, shifts, TC for last toll plaza
				List<Lane> allLanes = theLaneService.getAllLanes(lastTollPlaza.getTollPlazaId());
				List<Shift> allShifts = theShiftService.getAllShifts(lastTollPlaza.getTollPlazaId());
				List<User> allOperators = theUserService.getAllOperators(lastTollPlaza.getTollPlazaId());

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				theModel.addAttribute("allTollPlazas", allTollPlazas);
				theModel.addAttribute("allLanes", allLanes);
				theModel.addAttribute("allShifts", allShifts);
				theModel.addAttribute("allOperators", allOperators);
				theModel.addAttribute("today", sdf.format(today));
		return "short_excess_report";
	}
	
	@RequestMapping("/DatewiseRevenue")
	public String DatewiseRevenue(Model theModel, HttpSession session) {
		//handling session
				User userFromSession = (User) session.getAttribute("userFromSession");
				if(userFromSession==null) return "redirect:/";
				if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				

				//send all tollPlazas and lanes
				List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
				TollPlaza lastTollPlaza = allTollPlazas.get(allTollPlazas.size()-1);
				
				//get all the lanes, shifts, TC for last toll plaza
				List<Lane> allLanes = theLaneService.getAllLanes(lastTollPlaza.getTollPlazaId());
				List<Shift> allShifts = theShiftService.getAllShifts(lastTollPlaza.getTollPlazaId());
				List<User> allOperators = theUserService.getAllOperators(lastTollPlaza.getTollPlazaId());

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				theModel.addAttribute("allTollPlazas", allTollPlazas);
				theModel.addAttribute("allLanes", allLanes);
				theModel.addAttribute("allShifts", allShifts);
				theModel.addAttribute("allOperators", allOperators);
				theModel.addAttribute("today", sdf.format(today));
		return "datewise_revenue_report";
	}
	
	@RequestMapping("/DatewiseTraffic")
	public String DatewiseTraffic(Model theModel, HttpSession session) {
		//handling session
				User userFromSession = (User) session.getAttribute("userFromSession");
				if(userFromSession==null) return "redirect:/";
				if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				

				//send all tollPlazas and lanes
				List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
				TollPlaza lastTollPlaza = allTollPlazas.get(allTollPlazas.size()-1);
				
				//get all the lanes, shifts, TC for last toll plaza
				List<Lane> allLanes = theLaneService.getAllLanes(lastTollPlaza.getTollPlazaId());
				List<Shift> allShifts = theShiftService.getAllShifts(lastTollPlaza.getTollPlazaId());
				List<User> allOperators = theUserService.getAllOperators(lastTollPlaza.getTollPlazaId());

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				theModel.addAttribute("allTollPlazas", allTollPlazas);
				theModel.addAttribute("allLanes", allLanes);
				theModel.addAttribute("allShifts", allShifts);
				theModel.addAttribute("allOperators", allOperators);
				theModel.addAttribute("today", sdf.format(today));
		return "datewise_traffic_report";
	}
	
	@RequestMapping("/LanewiseTraffic")
	public String laneWiseTraffic(Model theModel, HttpSession session) {
		//handling session
				User userFromSession = (User) session.getAttribute("userFromSession");
				if(userFromSession==null) return "redirect:/";
				if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				

				//send all tollPlazas and lanes
				List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
				TollPlaza lastTollPlaza = allTollPlazas.get(allTollPlazas.size()-1);
				
				//get all the lanes, shifts, TC for last toll plaza
				List<Lane> allLanes = theLaneService.getAllLanes(lastTollPlaza.getTollPlazaId());
				List<Shift> allShifts = theShiftService.getAllShifts(lastTollPlaza.getTollPlazaId());
				List<User> allOperators = theUserService.getAllOperators(lastTollPlaza.getTollPlazaId());

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				theModel.addAttribute("allTollPlazas", allTollPlazas);
				theModel.addAttribute("allLanes", allLanes);
				theModel.addAttribute("allShifts", allShifts);
				theModel.addAttribute("allOperators", allOperators);
				theModel.addAttribute("today", sdf.format(today));
		return "lanewise_traffic_report";
	}
	
	@RequestMapping("/LanewiseRevenue")
	public String laneWiseRevenue(Model theModel, HttpSession session) {
		//handling session
				User userFromSession = (User) session.getAttribute("userFromSession");
				if(userFromSession==null) return "redirect:/";
				if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				

				//send all tollPlazas and lanes
				List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
				TollPlaza lastTollPlaza = allTollPlazas.get(allTollPlazas.size()-1);
				
				//get all the lanes, shifts, TC for last toll plaza
				List<Lane> allLanes = theLaneService.getAllLanes(lastTollPlaza.getTollPlazaId());
				List<Shift> allShifts = theShiftService.getAllShifts(lastTollPlaza.getTollPlazaId());
				List<User> allOperators = theUserService.getAllOperators(lastTollPlaza.getTollPlazaId());

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				theModel.addAttribute("allTollPlazas", allTollPlazas);
				theModel.addAttribute("allLanes", allLanes);
				theModel.addAttribute("allShifts", allShifts);
				theModel.addAttribute("allOperators", allOperators);
				theModel.addAttribute("today", sdf.format(today));
		return "lanewise_revenue_report";
	}
	
	@RequestMapping("/cancelTicketsTransaction")
	public String cancelTicketsTransactionReport(Model theModel, HttpSession session) {
		//handling session
				User userFromSession = (User) session.getAttribute("userFromSession");
				if(userFromSession==null) return "redirect:/";
				if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				

				//send all tollPlazas and lanes
				List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
				TollPlaza lastTollPlaza = allTollPlazas.get(allTollPlazas.size()-1);
				
				//get all the lanes, shifts, TC for last toll plaza
				List<Lane> allLanes = theLaneService.getAllLanes(lastTollPlaza.getTollPlazaId());
				List<Shift> allShifts = theShiftService.getAllShifts(lastTollPlaza.getTollPlazaId());
				List<User> allOperators = theUserService.getAllOperators(lastTollPlaza.getTollPlazaId());

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				theModel.addAttribute("allTollPlazas", allTollPlazas);
				theModel.addAttribute("allLanes", allLanes);
				theModel.addAttribute("allShifts", allShifts);
				theModel.addAttribute("allOperators", allOperators);
				theModel.addAttribute("today", sdf.format(today));
		return "cancel_tickets_transaction";
	}
	
	@RequestMapping("/PassRevenue")
	public String passRevenue(Model theModel, HttpSession session) {
		//handling session
				User userFromSession = (User) session.getAttribute("userFromSession");
				if(userFromSession==null) return "redirect:/";
				if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				

				//send all tollPlazas and lanes
				List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
				TollPlaza lastTollPlaza = allTollPlazas.get(allTollPlazas.size()-1);
				
				//get all the lanes, shifts, TC for last toll plaza
				List<Lane> allLanes = theLaneService.getAllLanes(lastTollPlaza.getTollPlazaId());
				List<Shift> allShifts = theShiftService.getAllShifts(lastTollPlaza.getTollPlazaId());
				List<User> allOperators = theUserService.getAllOperators(lastTollPlaza.getTollPlazaId());
				

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				theModel.addAttribute("allTollPlazas", allTollPlazas);
				theModel.addAttribute("allLanes", allLanes);
				theModel.addAttribute("allShifts", allShifts);
				theModel.addAttribute("allOperators", allOperators);
				theModel.addAttribute("today", sdf.format(today));
		return "pass_revenue_report";
	}
	
	@RequestMapping("/cashupSummary")
	public String cashup(Model theModel, HttpSession session) {
		//handling session
				User userFromSession = (User) session.getAttribute("userFromSession");
				if(userFromSession==null) return "redirect:/";
				if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				

				//send all tollPlazas and lanes
				List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
				TollPlaza lastTollPlaza = allTollPlazas.get(allTollPlazas.size()-1);
				
				//get all the lanes, shifts, TC for last toll plaza
				List<Lane> allLanes = theLaneService.getAllLanes(lastTollPlaza.getTollPlazaId());
				List<Shift> allShifts = theShiftService.getAllShifts(lastTollPlaza.getTollPlazaId());
				List<User> allOperators = theUserService.getAllOperators(lastTollPlaza.getTollPlazaId());
				

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				theModel.addAttribute("allTollPlazas", allTollPlazas);
				theModel.addAttribute("allLanes", allLanes);
				theModel.addAttribute("allShifts", allShifts);
				theModel.addAttribute("allOperators", allOperators);
				theModel.addAttribute("today", sdf.format(today));
		return "cashup_summary_report";
	}
	
	@RequestMapping("/monthlyPassDetail")
	public String monthlyPassDetail(Model theModel, HttpSession session) {
		//handling session
				User userFromSession = (User) session.getAttribute("userFromSession");
				if(userFromSession==null) return "redirect:/";
				if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				

				//send all tollPlazas and lanes
				List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
				TollPlaza lastTollPlaza = allTollPlazas.get(allTollPlazas.size()-1);
				
				//get all the lanes, shifts, TC for last toll plaza
				List<Lane> allLanes = theLaneService.getAllLanes(lastTollPlaza.getTollPlazaId());
				List<Shift> allShifts = theShiftService.getAllShifts(lastTollPlaza.getTollPlazaId());
				List<User> allOperators = theUserService.getAllExceptOperators(lastTollPlaza.getTollPlazaId());
				

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				theModel.addAttribute("allTollPlazas", allTollPlazas);
				theModel.addAttribute("allLanes", allLanes);
				theModel.addAttribute("allShifts", allShifts);
				theModel.addAttribute("allOperators", allOperators);
				theModel.addAttribute("today", sdf.format(today));
		return "monthly_pass_detail";
	}
	
	@RequestMapping("/exemptedTraffic")
	public String exemptedTraffic(Model theModel, HttpSession session) {
		//handling session
				User userFromSession = (User) session.getAttribute("userFromSession");
				if(userFromSession==null) return "redirect:/";
				if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				

				//send all tollPlazas and lanes
				List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
				TollPlaza lastTollPlaza = allTollPlazas.get(allTollPlazas.size()-1);
				
				//get all the lanes, shifts, TC for last toll plaza
				List<Lane> allLanes = theLaneService.getAllLanes(lastTollPlaza.getTollPlazaId());
				List<Shift> allShifts = theShiftService.getAllShifts(lastTollPlaza.getTollPlazaId());
				List<User> allOperators = theUserService.getAllExceptOperators(lastTollPlaza.getTollPlazaId());
				

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				theModel.addAttribute("allTollPlazas", allTollPlazas);
				theModel.addAttribute("allLanes", allLanes);
				theModel.addAttribute("allShifts", allShifts);
				theModel.addAttribute("allOperators", allOperators);
				theModel.addAttribute("today", sdf.format(today));
		return "exempt_traffic_report";
	}
	
	@RequestMapping("/iframe")
	public String iframe(HttpServletRequest request, Model theModel, HttpSession session) {
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "blank_page1";
		if(userFromSession.getUserRole().equals("Operator")) return "blank_page1"; 
		
		String action = request.getParameter("action");
		String type = request.getParameter("type");
		
		theModel.addAttribute("action", action);
		theModel.addAttribute("type", type);
		return "report_iframe";
	}
}



























