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

import com.seoniproject.entity.FloatAmountDetails;
import com.seoniproject.entity.Lane;
import com.seoniproject.entity.Shift;
import com.seoniproject.entity.TollPlaza;
import com.seoniproject.entity.User;
import com.seoniproject.service.FloatAmountDetailsService;
import com.seoniproject.service.LaneService;
import com.seoniproject.service.ShiftService;
import com.seoniproject.service.TollPlazaService;
import com.seoniproject.service.UserService;

@Controller
@RequestMapping("/floatAmountDetails")
public class FloatAmountDetailsController {
	
	@Autowired
	private FloatAmountDetailsService theFloatAmountDetailsService;
	
	@Autowired
	private TollPlazaService theTollPlazaService;
	
	@Autowired
	private LaneService theLaneService;
	
	@Autowired
	private UserService theUserService;
	
	@Autowired 
	private ShiftService theShiftService;
	
	

	@RequestMapping("/showForm")
	public String AddFloatAmountDetails(HttpSession session, Model theModel, HttpServletRequest request) {

		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		
		//send all tollPlazas and lanes
		List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
		List<Lane> allLanes = new ArrayList<Lane>();
		List<Shift> allShifts = new ArrayList<Shift>();
		List<User> allUsers = new ArrayList<User>();
		
		//if there is an error coming from redirection
		String error = request.getParameter("error");
		if (error!=null) theModel.addAttribute("error",error);
		
		if (allTollPlazas.size()!=0) {
			allLanes = theLaneService.getAllLanes(allTollPlazas.get(0).getTollPlazaId());
			allUsers = theUserService.getAllOperators(allTollPlazas.get(0).getTollPlazaId());
			allShifts = theShiftService.getAllShifts(allTollPlazas.get(0).getTollPlazaId());
		}

		
		theModel.addAttribute("allTollPlazas", allTollPlazas);
		theModel.addAttribute("allLanes", allLanes);
		theModel.addAttribute("allShifts", allShifts);
		theModel.addAttribute("allUsers", allUsers);
		
		String message = request.getParameter("message");
		String tDate = request.getParameter("date");
		if (tDate==null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			tDate = sdf.format(date);
		}
		String userId = request.getParameter("userId");
		String floatAmount = request.getParameter("amount");
		String laneId = request.getParameter("laneId");
		String tollPlazaId = request.getParameter("plazaId");
		String shiftId = request.getParameter("shift");
		
		theModel.addAttribute("laneId", laneId);
		theModel.addAttribute("plazaId", tollPlazaId);
		theModel.addAttribute("shift", shiftId);
		
		theModel.addAttribute("message", message);
		theModel.addAttribute("date", tDate);
		theModel.addAttribute("userId", userId);
		theModel.addAttribute("amount", floatAmount);
		
		return "add_float_amount_details";
	}
	
	//for Ajax
	@GetMapping("/listUpdateDetails")
	public String getDetailsForUpdation(HttpServletRequest request, Model theModel) {
		String plazaId = request.getParameter("plazaId");
		String laneId = request.getParameter("laneId");
		String date = request.getParameter("date");
		String shiftId = request.getParameter("shiftId");
		
		FloatAmountDetails theFloatAmountDetails = 
				theFloatAmountDetailsService.getFloatAmountAndOperatorFrom(plazaId, laneId, date, shiftId);
		
		theModel.addAttribute("floatAmountUser", theFloatAmountDetails);
		return "float_amount_user";
	}
	
	@PostMapping("/saveFloatAmount")
	public String saveFloatDetailAmount(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		String shiftDate1 = request.getParameter("date");
		String tollPlazaId1 = request.getParameter("tollPlazaId");
		String laneId1 = request.getParameter("laneId");
		String shiftId1 = request.getParameter("shiftId");
		String userId1 = request.getParameter("userId");
		String floatAmount1 = request.getParameter("floatamt");
		String message = "";
		
		//see whether there is date and shift entered previously
		List<FloatAmountDetails> sameDetailsIfExist = theFloatAmountDetailsService.getSameDetailIfExist(shiftDate1, tollPlazaId1, laneId1, shiftId1);
		
		Date tDate = null;
		Integer tollPlazaId = null;
		String error = null;
		Integer laneId = null;
		Integer shiftId = null;
		Integer userId = null;
		Float floatAmount = null;
		
		if (userId1 != null) userId = Integer.parseInt(userId1);
		else error = "Should not submit with out selecting a user<br>";
		
		if (floatAmount1 != null) floatAmount = Float.parseFloat(floatAmount1);
		else error = "Should not submit form withput entering floatAmount<br>";

		if (error == null) {
			
			if (sameDetailsIfExist.size()>0) { //this means he is updating
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (shiftDate1 != null)
					try {
						tDate = sdf.parse(shiftDate1);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				//check if user and amount are equal 
				FloatAmountDetails sameDetails = sameDetailsIfExist.get(0);
				if (sameDetails.getUserId().equals(userId) && sameDetails.getFloatAmount().equals(floatAmount) ) {
					//that means he is trying to update with same values
					error = "Please change the values to update<br>";	
					theModel.addAttribute("error", error);
					return "redirect:/floatAmountDetails/showForm";
				} else { //if not equal update them
					sameDetails.setModificationTimeStamp(new Date());
					sameDetails.setModifiedUserId(userFromSession.getUserId());
					sameDetails.setFloatAmount(floatAmount);
					sameDetails.setUserId(userId);
					theFloatAmountDetailsService.saveOrUpdate(sameDetails);
					message = "Successfully updated, ";
				}

			} else { //he is inserting
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (shiftDate1 != null)
					try {
						tDate = sdf.parse(shiftDate1);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				else error = "Should enter a date before submitting";

				if (tollPlazaId1 != null) tollPlazaId = Integer.parseInt(tollPlazaId1);
				else error = "Should not submmit without selecting tollPlaza<br>";

				if (laneId1 != null) laneId = Integer.parseInt(laneId1);
				else error = "Should not submit without selecting Lane<br>";
				
				if (shiftId1 != null) shiftId = Integer.parseInt(shiftId1);
				else error = "should not submit with out selecting a shift<br>";
				
				//create a new detail and insert
				FloatAmountDetails theFloatAmountDetails = new FloatAmountDetails();
				theFloatAmountDetails.settDate(tDate);
				theFloatAmountDetails.setTollPlazaId(tollPlazaId);
				theFloatAmountDetails.setLaneId(laneId);
				theFloatAmountDetails.setShiftId(shiftId);
				theFloatAmountDetails.setUserId(userId);
				theFloatAmountDetails.setFloatAmount(floatAmount);
				theFloatAmountDetails.setCreateUserId(userFromSession.getUserId());
				theFloatAmountDetails.setCreateTimeStamp(new Date());
				theFloatAmountDetailsService.saveOrUpdate(theFloatAmountDetails);
				message ="Successfully inserted, ";
			}
		} else {
			error = "Please change the values to update";	
			theModel.addAttribute("error", error);
		}
		
		List<User> user = theUserService.getUserFromId(userId);
		
		message = message + " for the operator: "+user.get(0).getUserFirstName()+" "+user.get(0).getUserLastName()+", Amount: "+floatAmount;
		theModel.addAttribute("laneId", laneId);
		theModel.addAttribute("plazaId", tollPlazaId);
		theModel.addAttribute("shift", shiftId);
		
		theModel.addAttribute("message", message);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(tDate);
		
		theModel.addAttribute("date", date);
		theModel.addAttribute("userId", userId);
		theModel.addAttribute("amount", floatAmount);
		return "redirect:/floatAmountDetails/showForm";
	}
	

	@GetMapping("/getOperator")
	public String getTheOperator(HttpSession session, Model theModel, HttpServletRequest request) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "user1";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "user1";
		
		String plazaId = request.getParameter("plazaId");
		String laneId = request.getParameter("laneId");
		String shiftId = request.getParameter("shiftId");
		String checkDate = request.getParameter("checkDate");
		
		if (plazaId==null||laneId==null||shiftId==null||checkDate==null) return "user1";

		//get the floatAmountDetails if exist
		List<FloatAmountDetails> floatAmountDetails = theFloatAmountDetailsService.getSameDetailIfExist(checkDate, plazaId, laneId,  shiftId);
		
		if (floatAmountDetails.size()==0) {
			return "user1";
		}
		
		//now get the user from user id
		List<User> theUser = theUserService.getUserFromId(floatAmountDetails.get(0).getUserId());
		
		if (theUser.size()==0) {
			return "user1";
		}

		theModel.addAttribute("theUser", theUser.get(0));
		theModel.addAttribute("floatingAmount", floatAmountDetails.get(0).getFloatAmount());
		return "user1";
	}
}






















