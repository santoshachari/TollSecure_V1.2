package com.seoniproject.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seoniproject.entity.Pass;
import com.seoniproject.entity.PassConfiguration;
import com.seoniproject.entity.Shift;
import com.seoniproject.entity.TollPlaza;
import com.seoniproject.entity.User;
import com.seoniproject.service.PasService;
import com.seoniproject.service.PassService;
import com.seoniproject.service.ShiftService;
import com.seoniproject.service.TollConfigService;
import com.seoniproject.service.TollPlazaService;

@Controller
@RequestMapping("/pass")
public class PassController {

	@Autowired
	private TollPlazaService theTollPlazaService;
	
	@Autowired
	private PassService thePassService;
	
	@Autowired
	private PasService thePasService;
	
	@Autowired
	private TollConfigService theTollConfigService;
	
	@Autowired 
	private ShiftService theShiftService;
	
	@RequestMapping("/configurePass")
	public String configurePass(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		String message = request.getParameter("message");
		
		//get all tollPlazas
		List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
		
		if (allTollPlazas.size()==0) {
			theModel.addAttribute("error","Please add a TollPlaza first");
			return "redirect:/tollPlaza/showFormForAdd";
		}
		
		theModel.addAttribute("message", message);
		theModel.addAttribute("tollPlazas", allTollPlazas);
		return "configure_pass"; 
	}
	
	@PostMapping("/savePassConfig")
	public String savePassConfiguration(HttpServletRequest request, Model theModel, HttpSession session) {

		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				
				
		//get all the values
		String tollPlazaId = request.getParameter("tollPlazaId");
		//String effectiveFrom1 =  request.getParameter("effectiveFrom");
		String effectiveTo1 = request.getParameter("effectiveTo");
		String vehicleClass = request.getParameter("vehicleClass");
		String passType = request.getParameter("passType");
		String newAmount = request.getParameter("newAmt");
		String message = "";
		
		//now get all the pass configurations which are active with same toll plaza and vehicle class and pass type and withh in that date 
		List<PassConfiguration> allActivePassConfigurations = 
				thePassService.getAllActivePassConfigurations(tollPlazaId, effectiveTo1, vehicleClass, passType);
		
		for (PassConfiguration passConf: allActivePassConfigurations) {
			//just deactivate them
			passConf.setStatusFlag("DEACTIVE");
			passConf.setModificationTimeStamp(new Date());
			passConf.setModifiedUserId(userFromSession.getUserId());
			thePassService.saveOrUpdate(passConf);
			
			message = "Successfully updated: ";
		}
		
		//create a new pass configuration
		PassConfiguration newPassConfiguration = new PassConfiguration();
		newPassConfiguration.setTollPlazaId(Integer.parseInt(tollPlazaId));
		newPassConfiguration.setEffectiveFrom(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			newPassConfiguration.setEffectiveTo(sdf.parse(effectiveTo1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newPassConfiguration.setAmount(Float.parseFloat(newAmount));
		newPassConfiguration.setPassType(passType);
		newPassConfiguration.setCreateTimeStamp(new Date());
		newPassConfiguration.setCreateUserId(userFromSession.getUserId());
		newPassConfiguration.setVehicleClass(vehicleClass);
		newPassConfiguration.setStatusFlag("ACTIVE");
		thePassService.saveOrUpdate(newPassConfiguration);
		
		if (message.equals("")) message = "Successfully Added: ";
		
		message = message + "newAmount: "+newAmount+" to: "+vehicleClass;
		
		theModel.addAttribute("message", message);
		return "redirect:/pass/configurePass";
	}
	
	@GetMapping("/rate")
	public String getThePassRate(HttpServletRequest request, Model theModel) {
		
		String plazaId = request.getParameter("plazaId");
		String effectiveTo = request.getParameter("effectiveTo");
		String vehicleClass =request.getParameter("vehicleClass");
		String passType = request.getParameter("passType");
		
		//get the configured pass if exist
		PassConfiguration pass = thePassService.getPassConfiguration(plazaId, effectiveTo, vehicleClass, passType);
		
		System.out.println(pass);
		
		if (pass==null) return "pass_rate";
		
		theModel.addAttribute("rate", pass.getAmount());
		return "pass_rate";
	}
	
	@RequestMapping("/newPass")
	public String getNewPass(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				
		//get all tollPlazas
		List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
		
		theModel.addAttribute("tollPlazas", allTollPlazas);
		
		return "pass_print";
	}
	
	@GetMapping("/printReceipt")
	public String printReceipt(HttpServletRequest request, Model theModel, HttpSession session) {

		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		String plazaId1 = request.getParameter("plazaId");
		String effectiveTo1 = request.getParameter("effectiveTo");
		System.out.println("-=-=-=>>>"+effectiveTo1);
		String vehicleClass = request.getParameter("vehicleClass");
		String passType = request.getParameter("passType");
		String vehicleNumber = request.getParameter("vehicleNumber");
		String amt = request.getParameter("amt");
		String tripValidity = request.getParameter("validity");
		
		Pass pass = new Pass();
		pass.setTollPlazaId(Integer.parseInt(plazaId1));
		
		System.out.println("plazaId: "+plazaId1+" effectiveTo: "+effectiveTo1+" vehicleClass: "+vehicleClass+" passType: "+passType+" vehicleNumber: "+vehicleNumber+" amount: "+amt);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			pass.setEffectiveTo(sdf.parse(effectiveTo1));
			
			//setting period
			Date sd=sdf.parse(sdf.format(new Date()));
			Date ed=sdf.parse(effectiveTo1);
			long diff = ed.getTime() - sd.getTime();
			Long days1 = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			String days = days1.toString()+" days";
			pass.setPeriod(days);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pass.setEffectiveFrom(new Date());
		pass.setVehicleClass(vehicleClass);
		pass.setPassType(passType);
		pass.setVehicleNumber(vehicleNumber);
		pass.setCreateTimeStamp(new Date());
		pass.setCreateUserId(userFromSession.getUserId());
		pass.setAmount(Float.parseFloat(amt));
		
		if (pass.getPassType().equals("MONTHLY")) {
			pass.setTripValidity(Integer.parseInt(tripValidity));
			pass.setTripCount(0);
		}
		
		//just save the pass for now
		thePasService.saveOrUpdate(pass);
		
		//now get the last based on vehicle number 
		Pass savedOne = thePassService.getSavedOne(vehicleNumber);
		
		theModel.addAttribute("savedOne", savedOne);
		return "pass_receipt";
	}
	
	@GetMapping("/passTable")
	public String passTable(HttpServletRequest request, Model theModel, HttpSession session) {
		String vehicleNumber = request.getParameter("vehicleNumber");
		
		List<Pass> passes =  thePassService.getPasses(vehicleNumber);
		
		theModel.addAttribute("passes", passes);
		return "pass_table";
	}
	
	@GetMapping("/checkPassIsValid")
	public String checkPassIfValid(HttpServletRequest request, Model theModel, HttpSession session) {
		
		String vehicleNumber = request.getParameter("vehicleNumber");
		
		//this method gets the message for displaying
		Pass lastPassForTheVehicle = thePassService.lastPassForTheVehicle(vehicleNumber);
		
		//lately added functionality cancelled passes -- any how we are checking this condition in the query also
		if (lastPassForTheVehicle!=null && lastPassForTheVehicle.getCancellationCode()!=null) {
			lastPassForTheVehicle = null;
		}
		
		String message = null;
		
		//now lets check various aspects of the pass
		
		//if the pass is local pass no need to check the tripValidation but, we have to check date
		//this is applicable for old passes which are not given valid number of trips
		if(lastPassForTheVehicle!=null) {
			//add one day because we have to take last day also
			//get the vehicle class id for the vehicle class
			Integer vehicleClassId = theTollConfigService.getVehicleClassIdFrom(lastPassForTheVehicle.getVehicleClass(), "S");
			Date effectiveTill = new Date(lastPassForTheVehicle.getEffectiveTo().getTime()+ (1000 * 60 * 60 * 24));
			if (!lastPassForTheVehicle.getPassType().equals("MONTHLY") || lastPassForTheVehicle.getTripValidity()==null || lastPassForTheVehicle.getTripCount()==null) {
				if (effectiveTill.after(new Date())) {
					//pass is valid
					//fill all the fields corresponding to the vehicle
					
					message = 
							"<div id='vci'>"+vehicleClassId+"</div>"+
							"<div id='vc'>"+lastPassForTheVehicle.getVehicleClass()+"</div>"+
							"<div id='msg'><span style='color:#006600'>Valid pass! Vehicle pass valid till <b>"+effectiveTill+"</b></span></div>";

				} else {
					//not valid 
					message = 
							"<div id='vci'></div>"+
							"<div id='vc'></div>"+
							"<div id='msg'><span style='color:red'> Not a valid pass! Vehicle pass valid till <b>"+effectiveTill+"</b> only.</span></div>";
				}
			} else {
				//check for both date and number of trips
				Integer numberOfTrips = lastPassForTheVehicle.getTripCount();
				
				int num = numberOfTrips+1;
				String num1=null;
				if (num==1) {
					num1=num+" st";
				} else if (num==2) {
					num1=num+" nd";
				} else if (num==3) {
					num1=num+" rd";
				} else {
					num1=num+" th";
				}
				
				if (numberOfTrips<lastPassForTheVehicle.getTripValidity() && effectiveTill.after(new Date())) {
					//valid vehicle also display trip number

					message = 
							"<div id='vci'>"+vehicleClassId+"</div>"+
							"<div id='vc'>"+lastPassForTheVehicle.getVehicleClass()+"</div>"+
							"<div id='msg'><span style='color:#006600'>Valid pass! This is <b>"+num1+"</b> time. Vehicle pass valid till <b>"+effectiveTill+"</b></span></div>";
				} else {
					message = 
							"<div id='vci'></div>"+
							"<div id='vc'></div>"+
							"<div id='msg'><span style='color:red'>Not a valid pass! This is <b>"+num1+"</b> time. Vehicle pass valid till <b>"+effectiveTill+"</b></span></div>";
				}
			}
		} else { //equals to null so not valid
			//no pass for that vehicle number
			message = 
					"<div id='vci'></div>"+
					"<div id='vc'></div>"+
					"<div id='msg'><span style='color:red'>Vehicle not found. Not a valid pass!</span></div>";
		}
		
		theModel.addAttribute("message", message);
		return "pass_msg";
	}

	@RequestMapping("/passAnalysis")
	public String getPassAnalysis(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		List<Shift> allShifts = theShiftService.getAllShifts(1);
		
		theModel.addAttribute("allShifts", allShifts);
		return "pass_analysis";
	}
	
	@GetMapping("/getPassesReport")
	public String getPassReport(HttpServletRequest request, Model theModel, HttpSession session) {
		
		String shiftId = request.getParameter("shiftId");
		String from = request.getParameter("from")+" 00:00:00";
		String to = request.getParameter("to")+" 23:59:59";
		
		//two cases with shift Id and without shift id
		//when there is shift id
		List<Object[]> pass_shifts = null;
		if (shiftId!=null && !shiftId.equals("")) {
			pass_shifts = thePasService.getPassShifts(from, to, shiftId);
		} else {
			pass_shifts = thePasService.getPassShifts(from, to);
		}
		
		theModel.addAttribute("passShifts", pass_shifts);
		return "pass_report_table";
	}
	
	@GetMapping("/cancelPass")
	public String cancelPass(HttpServletRequest request, Model theModel, HttpSession session) {
		
		String passId = request.getParameter("passId");
		String remarks = request.getParameter("remarks");
		
		System.out.println(">>>>>>>>>>>>> CancelPass: passId: "+passId+" remarks: "+remarks);
		
		//get the pass with passId
		Pass thePass = thePasService.getPassFromId(passId);
		String message = null;
		
		if (thePass==null) {
			 message = "<span class='error_show'>Pass is not Cancelled as the Pass Id does not exist</span>";
		} else {
			thePass.setRemarks(remarks);
			thePass.setCancellationCode("CANCELLED");
			thePasService.saveOrUpdate(thePass);
			message = "<span style='color:#006600'>Pass bearing the following Pass Id has been cancelled: '"+passId+"' </span>";
		}
		
		theModel.addAttribute("message", message);
		return "pass_cancel_result";
	}
}








