package com.tollsecure.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.tollsecure.entity.Lane;
import com.tollsecure.entity.Shift;
import com.tollsecure.entity.TollPlaza;
import com.tollsecure.entity.User;
import com.tollsecure.service.LaneService;
import com.tollsecure.service.ShiftService;
import com.tollsecure.service.TollPlazaService;
import com.tollsecure.service.UserService;

@Controller
@RequestMapping("/lane")
public class LaneController {
	
	@Autowired
	private LaneService theLaneService;
	
	//this is mainly for version 2 kind of form
	@Autowired
	private TollPlazaService theTollPlazaService;
	
	@Autowired
	private ShiftService theShiftService; 
	
	@Autowired
	private UserService theUserService;
	
	@RequestMapping("/list")
	public String lanesList(@RequestParam("plazaId") Integer tollPlazaId, Model theModel, HttpSession session) {
System.out.println();
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		List<Lane> theLanes = theLaneService.getAllLanes(tollPlazaId); 
		
		//add attributes to the model
		theModel.addAttribute("lanes",theLanes);
		theModel.addAttribute("tollPlazaId",tollPlazaId);
		
		return "lane_list";
	}
	
	@GetMapping("/listfromfile")
	public String lanesListFromFile(@RequestParam("plazaId") Integer plazaId, Model theModel) {
		
		//get all the lanes in that tollPlaza
		List<Lane> theLanes = theLaneService.getAllLanes(plazaId); 
		
		//add the list and send to file
		theModel.addAttribute("lanes", theLanes);
		
		return "lane_list_for_drop_down";
	}
	
	@GetMapping("/listfromfile1")
	public String lanesListFromFile1(@RequestParam("plazaId") Integer plazaId, Model theModel) {
		
		//get all the lanes in that tollPlaza
		List<Lane> theLanes = theLaneService.getDistinctLaneDirections(plazaId);  
		
		//add the list and send to file
		theModel.addAttribute("lanes", theLanes);
		
		return "lane_list_for_drop_down1";
	}
	
	//updating RequestParam to HttpRequest for version 2
	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(HttpServletRequest request, Model theModel, HttpSession session) {

		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		//may be due to duplicates or not entering fields
		//but generally latter does not occur as we are taking care in the js
		String error = request.getParameter("error"); 
		
		//if there is an error
		if(error!=null && error!="") {
			theModel.addAttribute("error",error);
		}
		
		//if we are entering from tollPlaza add form link
		String plazaId1 = request.getParameter("tollPlazaId");
		
		//version 2 also needs to show tollPlazas in a drop down
		List<TollPlaza> theTollPlazas = theTollPlazaService.getAllTollPlazas();
		theModel.addAttribute("allTollPlazas", theTollPlazas);
		
		//let us say a single tollPlaza is not yet added
		if ((plazaId1 == null || plazaId1.equals("")) && theTollPlazas.size()==0) {
			theModel.addAttribute("error", "Please add a Toll Plaza first before adding Lanes");
			return "redirect:/tollPlaza/showFormForAdd";
		}
		
		//get all lanes for the given tollPlaza --version 2.0
		//let us take the first tollPlaza is displayed unlike TollPlaza in which last tollPlaza is displayed first
		//this should be changed if a different tollPlaza is shown
		List<Lane> theLanes = null;
		
		if(plazaId1==null) { //request is coming from home page so simply return page
			theLanes = theLaneService.getDistinctLaneDirections(theTollPlazas.get(0).getTollPlazaId()); 
			theModel.addAttribute("allLanes", theLanes);
			return "lane_add_form";
		}
		
		
		Integer plazaId = Integer.parseInt(plazaId1);
		
		//this time the lanes are different as tollPlaza is selected
		theLanes = theLaneService.getDistinctLaneDirections(plazaId); 
		theModel.addAttribute("allLanes", theLanes);
		
		//this should be the default tollPlaza
		theModel.addAttribute("plazaId",plazaId);
		
		return "lane_add_form";
	}
	
	//we may not need this in the version 2
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("laneId") Integer laneId, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		//get lane from service
		List<Lane> theLane = theLaneService.getLaneFromLaneId(laneId);
		
		String from_to = theLane.get(0).getLaneDirection();
		String[] strs = from_to.split(" ");
		
		theModel.addAttribute("from", strs[0]);
		theModel.addAttribute("to", strs[2]);
		theModel.addAttribute("lane", theLane.get(0));
		theModel.addAttribute("plazaId",theLane.get(0).getTollPlazaId());
		
		return "lane_add_form";
	}
	
	@GetMapping("/delete")
	public String deleteLane(@RequestParam("laneId") Integer laneId,  Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		//get lane from service
		Lane theLane = theLaneService.getLaneFromLaneId(laneId).get(0);
		theModel.addAttribute("plazaId",theLane.getTollPlazaId());
		
		theLaneService.deleteLane(laneId);
		
		return "redirect:/lane/list";
	}
	
	//this is completely changed in version 2
	@PostMapping("/saveLane")
	public String saveLane(HttpServletRequest request, Model theModel, HttpSession session) {

		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";

		//always check whether direction already exist if so 
		//also check number of records for that tollPlaza in that direction
		String plazaId1 = request.getParameter("tollPlazaId");
		//to upper case because we need to compare
		String laneDirection = request.getParameter("laneDirection").toUpperCase(); 
		String nooflanes1 = request.getParameter("nooflanes");
		
		//check if both directions are same and return error if they are
		System.out.println(laneDirection.split("TO")[0]+">>>>>>>>"+laneDirection.split("TO")[1]+">>>>>>>>");
		if(laneDirection.split("TO")[0].trim().equals(laneDirection.split("TO")[1].trim())) { //this is also handled in frontend
			theModel.addAttribute("error", "Destination cannot be source for a lane");
			if (plazaId1!=null) {
				theModel.addAttribute("tollPlazaId", plazaId1);
			}
			return "redirect:/lane/showFormForAdd";
		}
		
		Integer plazaId = null;
		Integer nooflanes = null;
		List<Lane> lanesWithAllDirections = null;
		List<Lane> lanesWithGivenDirection = new ArrayList<Lane>();
		
		if (plazaId1==null || laneDirection==null || nooflanes1==null) {
			theModel.addAttribute("error", "Please enter values before submitting");
			if (plazaId1!=null) {
				theModel.addAttribute("tollPlazaId", plazaId1);
			}
			return "redirect:/lane/showFormForAdd";
		} 
		
		plazaId = Integer.parseInt(plazaId1);
		nooflanes = Integer.parseInt(nooflanes1);
		//get the lanes with same directions in same tollPlaza
		lanesWithAllDirections = theLaneService.getAllLanes(plazaId);
		
		//now from the list take all the lanes with our required direction and add to lanesWithGivenDirection
		for(Lane lane : lanesWithAllDirections) {
			if (laneDirection.equals(lane.getLaneDirection())) {
				System.out.println("Given direction: "+laneDirection+" all direction: "+lane.getLaneDirection());
				lanesWithGivenDirection.add(lane);
			} 
		}
		
		if (lanesWithGivenDirection.size()==0) {//this laneDirection DoesNot exist so create this direction after existing 
			//create lanes one by one and add to Data base
			int beginingCode = lanesWithAllDirections.size()+1;
			for (int i=0; i<nooflanes; i++) {
				Lane lane = new Lane();
				lane.setTollPlazaId(plazaId);
				lane.setLaneDirection(laneDirection);
				int code = beginingCode+i;
				lane.setLaneCode("L"+code);
				lane.setStatusFlag("ACTIVE");
				lane.setCreateTimeStamp(new Date());
				lane.setCreateUserId(userFromSession.getUserId());
				theLaneService.saveLane(lane);
			}
			
		} else if (lanesWithGivenDirection.size()==nooflanes) { //if both fields are equal send error/ do nothing
			theModel.addAttribute("error", "The Lane Direction Already Exist With Same Number Of Lanes");
			theModel.addAttribute("tollPlazaId", plazaId1);
			return "redirect:/lane/showFormForAdd";
		} else { //update the entire lanes for this tollPlaza
			//deactivate all the lanes of that tollPlaza
			for (Lane lane : lanesWithAllDirections) {
				lane.setStatusFlag("DEACTIVE");
				lane.setModificationTimeStamp(new Date());
				lane.setModifiedUserId(userFromSession.getUserId());
				theLaneService.saveLane(lane);
			}
			//check whether to add in the first or in the end
			if (lanesWithAllDirections.get(0).getLaneDirection().equals(laneDirection)) {
				//add the updating lanes in the beginning
				int beginingCode = 1;
				for (int i=0; i<nooflanes; i++) {
					Lane lane = new Lane();
					lane.setTollPlazaId(plazaId);
					lane.setLaneDirection(laneDirection);
					lane.setLaneCode("L"+beginingCode);
					lane.setStatusFlag("ACTIVE");
					lane.setCreateTimeStamp(new Date());
					lane.setCreateUserId(userFromSession.getUserId());
					theLaneService.saveLane(lane);
					beginingCode++;
				}
				//now get other lanes one by one and add 
				for (Lane lane : lanesWithAllDirections) {
					if (!lane.getLaneDirection().equals(laneDirection)) {
						lane.setLaneId(null);
						lane.setLaneCode("L"+beginingCode);
						lane.setCreateTimeStamp(new Date());
						lane.setCreateUserId(userFromSession.getUserId());
						lane.setModificationTimeStamp(null);
						lane.setModifiedUserId(null);
						lane.setStatusFlag("ACTIVE");
						theLaneService.saveLane(lane);
						beginingCode++;
					}
				}
			} else {
				
				//add the updating lanes after other lane Directions at the end
				int beginingCode = 1;
				//now get other lanes one by one and add 
				for (Lane lane : lanesWithAllDirections) {
					if (!lane.getLaneDirection().equals(laneDirection)) {
						//nullify lane id and add change create user id and create user time and code
						lane.setLaneId(null);
						lane.setLaneCode("L"+beginingCode);
						lane.setCreateTimeStamp(new Date());
						lane.setCreateUserId(userFromSession.getUserId());
						lane.setModificationTimeStamp(null);
						lane.setModifiedUserId(null);
						lane.setStatusFlag("ACTIVE");
						theLaneService.saveLane(lane);
						beginingCode++;
					}
				}
				//add the updating lanes in the end
				for (int i=0; i<nooflanes; i++) {
					Lane lane = new Lane();
					lane.setTollPlazaId(plazaId);
					lane.setLaneDirection(laneDirection);
					lane.setLaneCode("L"+beginingCode);
					lane.setStatusFlag("ACTIVE");
					lane.setCreateTimeStamp(new Date());
					lane.setCreateUserId(userFromSession.getUserId());
					theLaneService.saveLane(lane);
					beginingCode++;
				}
			}
		}
		
		theModel.addAttribute("tollPlazaId", plazaId1);
		return "redirect:/lane/showFormForAdd";
	}
	
	@GetMapping("/noOfLanes")
	public String gettheNumberOfLanesInThatDirection(@RequestParam("plazaId") Integer plazaId, @RequestParam("laneDirection") String laneDirection, Model theModel) {
		List<Lane> lanesWithGivenDirection = theLaneService.getLaneWithSameDirection(laneDirection, plazaId);
		theModel.addAttribute("nooflanes",lanesWithGivenDirection.size());
		return "nooflanes";
	}
	
	@GetMapping("/listLaneCodes")
	public String listLaneCodes(@RequestParam("plazaId") Integer plazaId, Model theModel) {
		
		List<Lane> allLanes = new ArrayList<Lane>();
		List<User> allUsers = new ArrayList<User>();
		List<Shift> allShifts = new ArrayList<Shift>();
		
		if (plazaId != null) {
			allLanes = theLaneService.getAllLanes(plazaId);
			allShifts = theShiftService.getAllShifts(plazaId);
			allUsers = theUserService.getAllOperators(plazaId);
		}
		
		theModel.addAttribute("allLanes", allLanes);
		theModel.addAttribute("allShifts",allShifts);
		theModel.addAttribute("allUsers", allUsers);

		return "lanecodes";
	}
	
	
	@GetMapping("/listLaneCodes1")
	public String listLaneCodes1(@RequestParam("plazaId") Integer plazaId, Model theModel) {
		
		List<Lane> allLanes = new ArrayList<Lane>();
		List<User> allUsers = new ArrayList<User>();
		List<Shift> allShifts = new ArrayList<Shift>();
		
		if (plazaId != null) {
			allLanes = theLaneService.getAllLanes(plazaId);
			allShifts = theShiftService.getAllShifts(plazaId);
			allUsers = theUserService.getAllExceptOperators(plazaId);
		}
		
		theModel.addAttribute("allLanes", allLanes);
		theModel.addAttribute("allShifts",allShifts);
		theModel.addAttribute("allUsers", allUsers);

		return "lanecodes";
	}
}











