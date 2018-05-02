package com.seoniproject.controller;

import java.text.ParseException;
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

import com.seoniproject.entity.Shift;
import com.seoniproject.entity.TollPlaza;
import com.seoniproject.entity.User;
import com.seoniproject.service.ShiftService;
import com.seoniproject.service.TollPlazaService;


@Controller
@RequestMapping("/shift")
public class ShiftController {
	
	@Autowired
	private ShiftService theShiftService; 
	
	@Autowired
	private TollPlazaService theTollPlazaService;

	@RequestMapping("/list")
	public String showShifts(@RequestParam("plazaId") Integer tollPlazaId, Model theModel, HttpSession session) {

		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		List<Shift> theShifts = theShiftService.getAllShifts(tollPlazaId);
		
		//add attributes to the model
		theModel.addAttribute("shifts",theShifts);
		theModel.addAttribute("tollPlazaId",tollPlazaId);
		
		return "show_shifts_list";
	}
	
	//we don't need plaza id in version 2
	@GetMapping("/shiftConfigure")
	public String shiftConfigure(Model theModel, HttpServletRequest request, HttpSession session) {
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		//Instead of one tollPlaza he can view all tollPlazas
		List<TollPlaza> theTollPlazas = theTollPlazaService.getAllTollPlazas();
		
		//if request is coming from tollPlaza page
		String plazaId = request.getParameter("tollPlazaId");
		
		List<Shift> allShifts = null;                          
		
		//let us say a single tollPlaza is not yet added
		if ((plazaId == null || plazaId.equals("")) && theTollPlazas.size()==0) {
			theModel.addAttribute("error", "Please add a Toll Plaza first before Configuring Shifts");
			return "redirect:/tollPlaza/showFormForAdd";
		}
		
		if (plazaId != null) {
			theModel.addAttribute("plazaId", plazaId);
			
			//similarly get all shifts for that tollPlaza
			allShifts = theShiftService.getAllShifts(Integer.parseInt(plazaId));
		} else {
			//here get shifts for first tollPlaza
			if(theTollPlazas.size()>0) {
				allShifts = theShiftService.getAllShifts(theTollPlazas.get(0).getTollPlazaId());
			}
		}
		
		theModel.addAttribute("allTollPlazas", theTollPlazas);
		theModel.addAttribute("allShifts", allShifts);
		
		
		return "shift_configuration_form";
	}
	
	@PostMapping("/saveShiftConfiguration")
	public String saveShiftConfiguration(HttpServletRequest  request,  HttpSession session, Model theModel) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		String thePlazaId1 = request.getParameter("plazaId");
		Integer thePlazaId = Integer.parseInt(thePlazaId1);
		
		//get the number of shifts
		String noOfShifts1 = request.getParameter("nusfts");
		Integer noOfShifts = Integer.parseInt(noOfShifts1);
		
		//format it to date object
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		//version 2 -- we decided to have only 4 shifts with a flag(can use start and end times for that) in it
		//I am using the same times forwarded by the javascript because if hacker wants to 
		//manipulate time then he can change the start time entered by the user itself
		//so using them makes no difference
		
		//now let us bring all 4 shifts belong to the tollPlaza
		List<Shift> existingShifts = theShiftService.getAllShiftsActiveAndDeActive(thePlazaId);
		
		//In case no shifts in DB. Even though we can use existingShift list but for clarity 
		List<Shift> newShifts = null;
		
		boolean createdNow=false;
		
		//if the size of the list is 0 then create 4 new records 
		if(existingShifts.size() == 0) { //tollPlaza not configured even once
			//create one list of initial capacity 4
			newShifts = new ArrayList<Shift>(4);
			
			//keep a boolean to identify that you are creating new shifts
			createdNow=true;
		}
	
	
		String aShiftStart1 = null;
		String aShiftEnd1 = null;
		String bShiftStart1 = null;
		String bShiftEnd1 = null;
		String cShiftStart1 = null;
		String cShiftEnd1 = null;
		String dShiftStart1 = null;
		String dShiftEnd1 = null;
		
		Date aShiftStart = null;
		Date aShiftEnd = null;
		Date bShiftStart = null;
		Date bShiftEnd = null;
		Date cShiftStart = null;
		Date cShiftEnd = null;
		Date dShiftStart = null;
		Date dShiftEnd = null;
		

		Shift theShift = new Shift();
		Shift theShift1 = new Shift();
		Shift theShift2 = new Shift();
		Shift theShift3 = new Shift();
		
		//in all the cases send the four records only with unused records start and end times as nulls
		switch (noOfShifts) {
		case 2:
			//get from generated time stamps
			aShiftStart1 = request.getParameter("aShiftStart")+":00";
			aShiftEnd1 = request.getParameter("aShiftEnd")+":00";
			bShiftStart1 = request.getParameter("bShiftStart")+":00";
			bShiftEnd1 = request.getParameter("bShiftEnd")+":00";
			cShiftStart1 = null;
			cShiftEnd1 = null;
			dShiftStart1 = null;
			dShiftEnd1 = null;
			
			aShiftStart = null;
			aShiftEnd = null;
			bShiftStart = null;
			bShiftEnd = null;
			cShiftStart = null;
			cShiftEnd = null;
			dShiftStart = null;
			dShiftEnd = null;

			try {
				aShiftStart = sdf.parse(aShiftStart1);
				aShiftEnd = sdf.parse(aShiftEnd1);
				bShiftStart = sdf.parse(bShiftStart1);
				bShiftEnd = sdf.parse(bShiftEnd1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			//object for binding
			if (createdNow) { //if it is being newly created
				
				//Add A shift to DB
				theShift.setTollPlazaId(thePlazaId);
				theShift.setShiftDesc("A");
				theShift.setStartTime(aShiftStart);
				theShift.setEndTime(aShiftEnd);
				theShift.setCreateTimeStamp(new Date());
				theShift.setCreateUserId(userFromSession.getUserId());
				theShift.setStatusFlag("ACTIVE");
				
				//Add B shift to DB
				theShift1.setTollPlazaId(thePlazaId);
				theShift1.setShiftDesc("B");
				theShift1.setStartTime(bShiftStart);
				theShift1.setEndTime(bShiftEnd);
				theShift1.setCreateTimeStamp(new Date());
				theShift1.setCreateUserId(userFromSession.getUserId());
				theShift1.setStatusFlag("ACTIVE");
				
				//even though it is deactive but we are creating it now so we are create user and create time stamp now itself
				//Add C shift to DB
				theShift2.setTollPlazaId(thePlazaId);
				theShift2.setShiftDesc("C");
				theShift2.setStartTime(cShiftStart);
				theShift2.setEndTime(cShiftEnd);
				theShift2.setCreateTimeStamp(new Date());
				theShift2.setCreateUserId(userFromSession.getUserId());
				theShift2.setStatusFlag("DEACTIVE");
				
				//Add D shift to DB
				theShift3.setTollPlazaId(thePlazaId);
				theShift3.setShiftDesc("D");
				theShift3.setStartTime(dShiftStart);
				theShift3.setEndTime(dShiftEnd);
				theShift3.setCreateTimeStamp(new Date());
				theShift3.setCreateUserId(userFromSession.getUserId());
				theShift3.setStatusFlag("DEACTIVE");
				
				//add shifts to list and send
				newShifts.add(theShift);
				newShifts.add(theShift1);
				newShifts.add(theShift2);
				newShifts.add(theShift3);
				
				//save the shifts
				theShiftService.saveShiftList(newShifts);
				
			} else { //add them to existing shift
				
				//A shift to DB "A is in the 0th index as we are setting like that"
				existingShifts.get(0).setStartTime(aShiftStart);
				existingShifts.get(0).setEndTime(aShiftEnd);
				existingShifts.get(0).setModificationTimeStamp(new Date());
				existingShifts.get(0).setModifiedUserId(userFromSession.getUserId());
				existingShifts.get(0).setStatusFlag("ACTIVE");
				
				//B shift to DB 
				existingShifts.get(1).setStartTime(bShiftStart);
				existingShifts.get(1).setEndTime(bShiftEnd);
				existingShifts.get(1).setModificationTimeStamp(new Date());
				existingShifts.get(1).setModifiedUserId(userFromSession.getUserId());
				existingShifts.get(1).setStatusFlag("ACTIVE");
				
				//C shift to DB
				existingShifts.get(2).setStartTime(cShiftStart);
				existingShifts.get(2).setEndTime(cShiftEnd);
				existingShifts.get(2).setModificationTimeStamp(new Date());
				existingShifts.get(2).setModifiedUserId(userFromSession.getUserId());
				existingShifts.get(2).setStatusFlag("DEACTIVE");
				
				//D shift to DB
				existingShifts.get(3).setStartTime(dShiftStart);
				existingShifts.get(3).setEndTime(dShiftEnd);
				existingShifts.get(3).setModificationTimeStamp(new Date());
				existingShifts.get(3).setModifiedUserId(userFromSession.getUserId());
				existingShifts.get(3).setStatusFlag("DEACTIVE");
				
				//save the shifts
				theShiftService.saveShiftList(existingShifts);
			}
			
			
			break;
			
		case 3:
			//get from generated time stamps
			aShiftStart1 = request.getParameter("aShiftStart")+":00";
			aShiftEnd1 = request.getParameter("aShiftEnd")+":00";
			bShiftStart1 = request.getParameter("bShiftStart")+":00";
			bShiftEnd1 = request.getParameter("bShiftEnd")+":00";
			cShiftStart1 = request.getParameter("cShiftStart")+":00";
			cShiftEnd1 = request.getParameter("cShiftEnd")+":00";
			dShiftStart1 = null;
			dShiftEnd1 = null;
			
			aShiftStart = null;
			aShiftEnd = null;
			bShiftStart = null;
			bShiftEnd = null;
			cShiftStart = null;
			cShiftEnd = null;
			dShiftStart = null;
			dShiftEnd = null;
			
			try {
				aShiftStart = sdf.parse(aShiftStart1);
				aShiftEnd = sdf.parse(aShiftEnd1);
				bShiftStart = sdf.parse(bShiftStart1);
				bShiftEnd = sdf.parse(bShiftEnd1);
				cShiftStart = sdf.parse(cShiftStart1);
				cShiftEnd = sdf.parse(cShiftEnd1);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			//object for binding
			if (createdNow) { //if it is being newly created
				
				//Add A shift to DB
				theShift.setTollPlazaId(thePlazaId);
				theShift.setShiftDesc("A");
				theShift.setStartTime(aShiftStart);
				theShift.setEndTime(aShiftEnd);
				theShift.setCreateTimeStamp(new Date());
				theShift.setCreateUserId(userFromSession.getUserId());
				theShift.setStatusFlag("ACTIVE");
				
				//Add B shift to DB
				theShift1.setTollPlazaId(thePlazaId);
				theShift1.setShiftDesc("B");
				theShift1.setStartTime(bShiftStart);
				theShift1.setEndTime(bShiftEnd);
				theShift1.setCreateTimeStamp(new Date());
				theShift1.setCreateUserId(userFromSession.getUserId());
				theShift1.setStatusFlag("ACTIVE");
				
				//even though it is deactive but we are creating it now so we are create user and create time stamp now itself
				//Add C shift to DB
				theShift2.setTollPlazaId(thePlazaId);
				theShift2.setShiftDesc("C");
				theShift2.setStartTime(cShiftStart);
				theShift2.setEndTime(cShiftEnd);
				theShift2.setCreateTimeStamp(new Date());
				theShift2.setCreateUserId(userFromSession.getUserId());
				theShift2.setStatusFlag("ACTIVE");
				
				//Add D shift to DB
				theShift3.setTollPlazaId(thePlazaId);
				theShift3.setShiftDesc("D");
				theShift3.setStartTime(dShiftStart);
				theShift3.setEndTime(dShiftEnd);
				theShift3.setCreateTimeStamp(new Date());
				theShift3.setCreateUserId(userFromSession.getUserId());
				theShift3.setStatusFlag("DEACTIVE");
				
				//add shifts to list and send
				newShifts.add(theShift);
				newShifts.add(theShift1);
				newShifts.add(theShift2);
				newShifts.add(theShift3);
				
				//save the shifts
				theShiftService.saveShiftList(newShifts);
				
			} else { //add them to existing shift
				
				//A shift to DB "A is in the 0th index as we are setting like that"
				existingShifts.get(0).setStartTime(aShiftStart);
				existingShifts.get(0).setEndTime(aShiftEnd);
				existingShifts.get(0).setModificationTimeStamp(new Date());
				existingShifts.get(0).setModifiedUserId(userFromSession.getUserId());
				existingShifts.get(0).setStatusFlag("ACTIVE");
				
				//B shift to DB 
				existingShifts.get(1).setStartTime(bShiftStart);
				existingShifts.get(1).setEndTime(bShiftEnd);
				existingShifts.get(1).setModificationTimeStamp(new Date());
				existingShifts.get(1).setModifiedUserId(userFromSession.getUserId());
				existingShifts.get(1).setStatusFlag("ACTIVE");
				
				//C shift to DB
				existingShifts.get(2).setStartTime(cShiftStart);
				existingShifts.get(2).setEndTime(cShiftEnd);
				existingShifts.get(2).setModificationTimeStamp(new Date());
				existingShifts.get(2).setModifiedUserId(userFromSession.getUserId());
				existingShifts.get(2).setStatusFlag("ACTIVE");
				
				//D shift to DB
				existingShifts.get(3).setStartTime(dShiftStart);
				existingShifts.get(3).setEndTime(dShiftEnd);
				existingShifts.get(3).setModificationTimeStamp(new Date());
				existingShifts.get(3).setModifiedUserId(userFromSession.getUserId());
				existingShifts.get(3).setStatusFlag("DEACTIVE");
				
				//save the shifts
				theShiftService.saveShiftList(existingShifts);
			}
			
			break;
			
		case 4:
			//get from generated time stamps
			aShiftStart1 = request.getParameter("aShiftStart")+":00";
			aShiftEnd1 = request.getParameter("aShiftEnd")+":00";
			bShiftStart1 = request.getParameter("bShiftStart")+":00";
			bShiftEnd1 = request.getParameter("bShiftEnd")+":00";
			cShiftStart1 = request.getParameter("cShiftStart")+":00";
			cShiftEnd1 = request.getParameter("cShiftEnd")+":00";
			dShiftStart1 = request.getParameter("dShiftStart")+":00";
			dShiftEnd1 = request.getParameter("dShiftEnd")+":00";
			
			aShiftStart = null;
			aShiftEnd = null;
			bShiftStart = null;
			bShiftEnd = null;
			cShiftStart = null;
			cShiftEnd = null;
			dShiftStart = null;
			dShiftEnd = null;
			
			try {
				aShiftStart = sdf.parse(aShiftStart1);
				aShiftEnd = sdf.parse(aShiftEnd1);
				bShiftStart = sdf.parse(bShiftStart1);
				bShiftEnd = sdf.parse(bShiftEnd1);
				cShiftStart = sdf.parse(cShiftStart1);
				cShiftEnd = sdf.parse(cShiftEnd1);
				dShiftStart = sdf.parse(dShiftStart1);
				dShiftEnd = sdf.parse(dShiftEnd1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			//object for binding
			if (createdNow) { //if it is being newly created
				
				//Add A shift to DB
				theShift.setTollPlazaId(thePlazaId);
				theShift.setShiftDesc("A");
				theShift.setStartTime(aShiftStart);
				theShift.setEndTime(aShiftEnd);
				theShift.setCreateTimeStamp(new Date());
				theShift.setCreateUserId(userFromSession.getUserId());
				theShift.setStatusFlag("ACTIVE");
				
				//Add B shift to DB
				theShift1.setTollPlazaId(thePlazaId);
				theShift1.setShiftDesc("B");
				theShift1.setStartTime(bShiftStart);
				theShift1.setEndTime(bShiftEnd);
				theShift1.setCreateTimeStamp(new Date());
				theShift1.setCreateUserId(userFromSession.getUserId());
				theShift1.setStatusFlag("ACTIVE");
				
				//even though it is deactive but we are creating it now so we are create user and create time stamp now itself
				//Add C shift to DB
				theShift2.setTollPlazaId(thePlazaId);
				theShift2.setShiftDesc("C");
				theShift2.setStartTime(cShiftStart);
				theShift2.setEndTime(cShiftEnd);
				theShift2.setCreateTimeStamp(new Date());
				theShift2.setCreateUserId(userFromSession.getUserId());
				theShift2.setStatusFlag("ACTIVE");
				
				//Add D shift to DB
				theShift3.setTollPlazaId(thePlazaId);
				theShift3.setShiftDesc("D");
				theShift3.setStartTime(dShiftStart);
				theShift3.setEndTime(dShiftEnd);
				theShift3.setCreateTimeStamp(new Date());
				theShift3.setCreateUserId(userFromSession.getUserId());
				theShift3.setStatusFlag("ACTIVE");
				
				//add shifts to list and send
				newShifts.add(theShift);
				newShifts.add(theShift1);
				newShifts.add(theShift2);
				newShifts.add(theShift3);
				
				//save the shifts
				theShiftService.saveShiftList(newShifts);
				
			} else { //add them to existing shift
				
				//A shift to DB "A is in the 0th index as we are setting like that"
				existingShifts.get(0).setStartTime(aShiftStart);
				existingShifts.get(0).setEndTime(aShiftEnd);
				existingShifts.get(0).setModificationTimeStamp(new Date());
				existingShifts.get(0).setModifiedUserId(userFromSession.getUserId());
				existingShifts.get(0).setStatusFlag("ACTIVE");
				
				//B shift to DB 
				existingShifts.get(1).setStartTime(bShiftStart);
				existingShifts.get(1).setEndTime(bShiftEnd);
				existingShifts.get(1).setModificationTimeStamp(new Date());
				existingShifts.get(1).setModifiedUserId(userFromSession.getUserId());
				existingShifts.get(1).setStatusFlag("ACTIVE");
				
				//C shift to DB
				existingShifts.get(2).setStartTime(cShiftStart);
				existingShifts.get(2).setEndTime(cShiftEnd);
				existingShifts.get(2).setModificationTimeStamp(new Date());
				existingShifts.get(2).setModifiedUserId(userFromSession.getUserId());
				existingShifts.get(2).setStatusFlag("ACTIVE");
				
				//D shift to DB
				existingShifts.get(3).setStartTime(dShiftStart);
				existingShifts.get(3).setEndTime(dShiftEnd);
				existingShifts.get(3).setModificationTimeStamp(new Date());
				existingShifts.get(3).setModifiedUserId(userFromSession.getUserId());
				existingShifts.get(3).setStatusFlag("ACTIVE");
				
				//save the shifts
				theShiftService.saveShiftList(existingShifts);
			}
			break;
		}
		
		theModel.addAttribute("tollPlazaId", thePlazaId);
		return "redirect:/shift/shiftConfigure";
	}
	
	@GetMapping("/listFromFile")
	public String shiftsFromfile(@RequestParam("plazaId") Integer plazaId, Model theModel) {
		List<Shift> shifts = theShiftService.getAllShifts(plazaId);
		theModel.addAttribute("allShifts",shifts);
		return "shifts_list_for_given_plaza";
	}
	
	@GetMapping("/fillIt")
	public String fillIt(@RequestParam("plazaId") Integer plazaId, Model theModel) {
		List<Shift> shifts = theShiftService.getAllShifts(plazaId);
		theModel.addAttribute("allShifts",shifts);
		return "fillIt";
	}
}


















