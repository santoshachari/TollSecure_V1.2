package com.tollsecure.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.tollsecure.entity.Cashup;
import com.tollsecure.entity.CashupDeclaration;
import com.tollsecure.entity.Lane;
import com.tollsecure.entity.Shift;
import com.tollsecure.entity.TollPlaza;
import com.tollsecure.entity.TollTransaction;
import com.tollsecure.entity.User;
import com.tollsecure.service.CashupDeclarationService;
import com.tollsecure.service.CashupService;
import com.tollsecure.service.LaneService;
import com.tollsecure.service.ShiftService;
import com.tollsecure.service.TollConfigService;
import com.tollsecure.service.TollPlazaService;
import com.tollsecure.service.TollTransactionService;
import com.tollsecure.service.UserService;

@Controller
@RequestMapping("/cashup")
public class CashUpController {
	
	@Autowired
	private CashupDeclarationService theCashupDeclarationService;
	
	@Autowired 
	private LaneService theLaneService;
	
	@Autowired
	private TollPlazaService theTollPlazaService;

	@Autowired
	private CashupService theCashUpService;
	
	@Autowired
	private ShiftService theShiftService;
	
	@Autowired
	private TollTransactionService theTollTransactionService;
	
	@Autowired
	private UserService theUserService;
	
	
	@Autowired 
	private TollConfigService theTollConfigService;

	@RequestMapping("/declareCash")
	public String declarationPage(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		//get all the lanes and tollPlazas
		List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
		//let there be initially tollPlaza 0 be selected
		List<Lane> allLanes = null;
		List<Shift> allShifts = null;
		
		if (allTollPlazas.size()>0) {
			allLanes = theLaneService.getAllLanes(allTollPlazas.get(0).getTollPlazaId());
			//Also set initial shifts
			allShifts = theShiftService.getAllShifts(allTollPlazas.get(0).getTollPlazaId());
		}
		
		//sometimes there may be an error for entering an existing name
		theModel.addAttribute("allTollPlazas",allTollPlazas);
		theModel.addAttribute("allLanes",allLanes);
		theModel.addAttribute("allShifts", allShifts);
		
		String error = request.getParameter("error");
		
		if(error != null && error != "") {
			theModel.addAttribute("error", error);
		}
		
		//set default date as today
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(today);
		
		theModel.addAttribute("today", date);
		return "cashup_declaration";
	}
	
	@PostMapping("/showBalance")
	public String balanceSheet(HttpServletRequest request, HttpSession session, Model theModel) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		//first fill the cashup declaration table
		Integer d2000 = 0;
		Integer d1000 = 0;
		Integer d500 = 0;
		Integer d100 = 0;
		Integer d200 = 0;
		Integer d50 = 0;
		Integer d10 = 0;
		Integer d20 = 0;
		Integer d5 = 0;
		Integer d2 = 0;
		Integer d1 = 0;
		try {
			d2000 = Integer.parseInt(request.getParameter("d2000"));
			d1000 = Integer.parseInt(request.getParameter("d1000"));
			d500 = Integer.parseInt(request.getParameter("d500"));
			d100 = Integer.parseInt(request.getParameter("d100"));
			d200 = Integer.parseInt(request.getParameter("d200"));
			d50 = Integer.parseInt(request.getParameter("d50"));
			d20 = Integer.parseInt(request.getParameter("d20"));
			d10 = Integer.parseInt(request.getParameter("d10"));
			d5 = Integer.parseInt(request.getParameter("d5"));
			d2 = Integer.parseInt(request.getParameter("d2"));
			d1 = Integer.parseInt(request.getParameter("d1"));
		} catch (Exception e) {
			System.out.println("Ettor is: "+e+" ====>>>>");
		}
		
		Integer plazaId = Integer.parseInt(request.getParameter("tollPlazaId"));
		Integer laneId = Integer.parseInt(request.getParameter("laneId"));
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		Integer shiftId = Integer.parseInt(request.getParameter("shiftId"));
		Integer totalAmount = Integer.parseInt(request.getParameter("Total"));
		String checkdate = request.getParameter("checkingDate");
		Float floatingAmount = Float.parseFloat(request.getParameter("floatingAmount"));
		
		//subtracting floating amount
		totalAmount = (int) (totalAmount - floatingAmount);
		
		//get the check date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date checkingDate1=null;
		try {
			checkingDate1 = sdf.parse(checkdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//get the declaration if exist
		CashupDeclaration theCashupDeclarationIfExist = theCashupDeclarationService.getDeclarationIfExist(checkdate, laneId, shiftId);
		Integer totalCount = 0;
		String shiftDesc = null;
		
		if (theCashupDeclarationIfExist==null) { //create new one and save it as he is inserting a new one
			//getting the shift description
			Shift shiftForDesc = theShiftService.getShiftFromId(shiftId);
			shiftDesc = shiftForDesc.getShiftDesc(); 
			
			try {
				if(d2000!=0)totalCount=totalCount+d2000;
				if(d1000!=0)totalCount=totalCount+d1000;
				if(d500!=0)totalCount=totalCount+d500;
				if(d200!=0)totalCount=totalCount+d200;
				if(d100!=0)totalCount=totalCount+d100;
				if(d50!=0)totalCount=totalCount+d50;
				if(d20!=0)totalCount=totalCount+d20;
				if(d10!=0)totalCount=totalCount+d10;
				if(d5!=0)totalCount=totalCount+d5;
				if(d2!=0)totalCount=totalCount+d2;
				if(d1!=0)totalCount=totalCount+d1;
			} catch (Exception e) {
				System.out.println("Ettor is: "+e+" ====>>>>");
			}
			
			
			
			Integer accountantId = userFromSession.getUserId();
			CashupDeclaration theCashupDelaration = new CashupDeclaration();
			
			//setting all values into it
			//add userid and shiftid after wards while checking with transaction table
			theCashupDelaration.setTollPlazaId(plazaId);
			theCashupDelaration.setShiftDesc(shiftDesc);
			theCashupDelaration.setLaneId(laneId);
			theCashupDelaration.setD2000(d2000);
			theCashupDelaration.setD1000(d1000);
			theCashupDelaration.setD500(d500);
			theCashupDelaration.setD200(d200);
			theCashupDelaration.setD100(d100);
			theCashupDelaration.setD50(d50);
			theCashupDelaration.setD20(d20);
			theCashupDelaration.setD10(d10);
			theCashupDelaration.setD5(d5);
			theCashupDelaration.setD2(d2);
			theCashupDelaration.setD1(d1);
			
			theCashupDelaration.setTotalCount(totalCount);
			theCashupDelaration.setTotalAmount(totalAmount);
			theCashupDelaration.setShiftId(shiftId);
			theCashupDelaration.setUserId(userId);
			theCashupDelaration.setAccountantId(accountantId);
			theCashupDelaration.setCreateUserID(accountantId);
			theCashupDelaration.setCreateTimeStamp(new Date());
			theCashupDelaration.setShiftDesc(shiftDesc);
			

			theCashupDelaration.setCheckDate(checkingDate1);

			//save the transaction			
			theCashupDeclarationService.saveDeclaration(theCashupDelaration);	
			
			theModel.addAttribute("shiftDesc", shiftDesc);
			

		} else { //it must be an update so update user and counts only add modification time stamp and modification user
			
			try {
				if(d2000!=0)totalCount=totalCount+d2000;
				if(d1000!=0)totalCount=totalCount+d1000;
				if(d500!=0)totalCount=totalCount+d500;
				if(d200!=0)totalCount=totalCount+d200;
				if(d100!=0)totalCount=totalCount+d100;
				if(d50!=0)totalCount=totalCount+d50;
				if(d20!=0)totalCount=totalCount+d20;
				if(d10!=0)totalCount=totalCount+d10;
				if(d5!=0)totalCount=totalCount+d5;
				if(d2!=0)totalCount=totalCount+d2;
				if(d1!=0)totalCount=totalCount+d1;
			} catch (Exception e) {
				System.out.println("Ettor is: "+e+" ====>>>>");
			}
			
			theCashupDeclarationIfExist.setD2000(d2000);
			theCashupDeclarationIfExist.setD1000(d1000);
			theCashupDeclarationIfExist.setD500(d500);
			theCashupDeclarationIfExist.setD200(d200);
			theCashupDeclarationIfExist.setD100(d100);
			theCashupDeclarationIfExist.setD50(d50);
			theCashupDeclarationIfExist.setD20(d20);
			theCashupDeclarationIfExist.setD10(d10);
			theCashupDeclarationIfExist.setD5(d5);
			theCashupDeclarationIfExist.setD2(d2);
			theCashupDeclarationIfExist.setD1(d1);
			
			theCashupDeclarationIfExist.setTotalCount(totalCount);
			theCashupDeclarationIfExist.setTotalAmount(totalAmount);
			theCashupDeclarationIfExist.setModificationTimeStamp(new Date());
			theCashupDeclarationIfExist.setModifiedUserId(userFromSession.getUserId());
			
			//save the transaction	
			theCashupDeclarationService.saveDeclaration(theCashupDeclarationIfExist);
			shiftDesc = theCashupDeclarationIfExist.getShiftDesc();
			theModel.addAttribute("shiftDesc", shiftDesc);
		}
		
		
		//now get the shift information from tollTransaction table
		List<TollTransaction> thetollTransactions = theTollTransactionService.getTollTransactionsForShift(laneId, plazaId, shiftId.toString(), checkingDate1);
		
		if(thetollTransactions==null) {
			theModel.addAttribute("error", "Reasons for error may be <br> 1. Entered date should not be greater than today <br> 2. shift selected is not valid");
			
			return "redirect:/cashup/declareCash";
		}
		
		//now just check totals
		Float transactionTotal = (float) 0;
		Integer TransacionCount = 0;
		Float excess = (float) 0;
		Float shorti = (float) 0;
		
		Integer car_jeep_count = 0;
		Integer lcv_count = 0;
		Integer bus_truck_count = 0;
		Integer _3_axel_count = 0;
		Integer hcm_mav_count = 0;
		Integer oversized_count = 0;
		
		Float car_jeep_tot = (float) 0;
		Float lcv_tot = (float)0;
		Float bus_truck_tot = (float)0;
		Float _3_axel_tot = (float)0;
		Float hcm_mav_tot = (float)0;
		Float oversized_tot = (float)0;
		
		//get the actual S type rates for each vehicle from t_toll_config
		Float s_car_jeep = theTollConfigService.getRateFromClassAndJType("CAR/ JEEP", "S");
		Integer s_car_jeep_id = theTollConfigService.getVehicleClassIdFrom("CAR/ JEEP", "S");
		Float s_lcv = theTollConfigService.getRateFromClassAndJType("LCV", "S");
		Integer s_lcv_id = theTollConfigService.getVehicleClassIdFrom("LCV", "S");
		Float s_bus_truck = theTollConfigService.getRateFromClassAndJType("BUS/ TRUCK", "S");
		Integer s_bus_truck_id = theTollConfigService.getVehicleClassIdFrom("BUS/ TRUCK", "S");
		Float s_3_axel = theTollConfigService.getRateFromClassAndJType("3 AXEL", "S");
		Integer s_3_axel_id = theTollConfigService.getVehicleClassIdFrom("3 AXEL", "S");
		Float s_hcm_mav = theTollConfigService.getRateFromClassAndJType("HCM/ MAV", "S");
		Integer s_hcm_mav_id = theTollConfigService.getVehicleClassIdFrom("HCM/ MAV", "S");
		Float s_oversized = theTollConfigService.getRateFromClassAndJType("OVERSIZED", "S");
		Integer s_oversized_id = theTollConfigService.getVehicleClassIdFrom("OVERSIZED", "S");
		
		Float r_car_jeep = theTollConfigService.getRateFromClassAndJType("CAR/ JEEP", "R");
		Integer r_car_jeep_id = theTollConfigService.getVehicleClassIdFrom("CAR/ JEEP", "R");
		Float r_lcv = theTollConfigService.getRateFromClassAndJType("LCV", "R");
		Integer r_lcv_id = theTollConfigService.getVehicleClassIdFrom("LCV", "R");
		Float r_bus_truck = theTollConfigService.getRateFromClassAndJType("BUS/ TRUCK", "R");
		Integer r_bus_truck_id = theTollConfigService.getVehicleClassIdFrom("BUS/ TRUCK", "R");
		Float r_3_axel = theTollConfigService.getRateFromClassAndJType("3 AXEL", "R");
		Integer r_3_axel_id = theTollConfigService.getVehicleClassIdFrom("3 AXEL", "R");
		Float r_hcm_mav = theTollConfigService.getRateFromClassAndJType("HCM/ MAV", "R");
		Integer r_hcm_mav_id = theTollConfigService.getVehicleClassIdFrom("HCM/ MAV", "R");
		Float r_oversized = theTollConfigService.getRateFromClassAndJType("OVERSIZED", "R");
		Integer r_oversized_id = theTollConfigService.getVehicleClassIdFrom("OVERSIZED", "R");
		
		for (TollTransaction t : thetollTransactions) {
			if(t.getCancellationCode()==null) {
				transactionTotal = transactionTotal+t.getTollAmt();
				TransacionCount = TransacionCount + 1;
				
				// for s and r types type 
				if (t.getVehicleClassId().equals(s_car_jeep_id)) {
					car_jeep_tot = car_jeep_tot + t.getTollAmt();
					car_jeep_count = car_jeep_count+1;
				} else if (t.getVehicleClassId().equals(r_car_jeep_id)) {
					car_jeep_tot = car_jeep_tot + t.getTollAmt();
					car_jeep_count = car_jeep_count+1;
				} else if (t.getVehicleClassId().equals(s_lcv_id)) {
					lcv_tot = lcv_tot + t.getTollAmt();
					lcv_count = lcv_count+1;
				} else if (t.getVehicleClassId().equals(r_lcv_id)) {
					lcv_tot = lcv_tot + t.getTollAmt();
					lcv_count = lcv_count+1;
				} else if (t.getVehicleClassId().equals(s_bus_truck_id)) {
					bus_truck_tot = bus_truck_tot + t.getTollAmt();
					bus_truck_count = bus_truck_count+1;
				} else if (t.getVehicleClassId().equals(r_bus_truck_id)) {
					bus_truck_tot = bus_truck_tot + t.getTollAmt();
					bus_truck_count = bus_truck_count+1;
				} else if (t.getVehicleClassId().equals(s_3_axel_id)) {
					_3_axel_tot = _3_axel_tot + t.getTollAmt();
					_3_axel_count = _3_axel_count+1;
				} else if (t.getVehicleClassId().equals(r_3_axel_id)) {
					_3_axel_tot = _3_axel_tot + t.getTollAmt();
					_3_axel_count = _3_axel_count+1;
				} else if (t.getVehicleClassId().equals(s_hcm_mav_id)) {
					hcm_mav_tot = hcm_mav_tot + t.getTollAmt();
					hcm_mav_count = hcm_mav_count+1;
				} else if (t.getVehicleClassId().equals(r_hcm_mav_id)) {
					hcm_mav_tot = hcm_mav_tot + t.getTollAmt();
					hcm_mav_count = hcm_mav_count+1;
				} else if (t.getVehicleClassId().equals(s_oversized_id)) {
					oversized_tot = oversized_tot + t.getTollAmt();
					oversized_count = oversized_count+1;
				} else if (t.getVehicleClassId().equals(r_oversized_id)) {
					oversized_tot = oversized_tot + t.getTollAmt();
					oversized_count = oversized_count+1;
				} 
			}
		}
		
		Float diff = totalAmount - transactionTotal;
		
		
		if (diff>=0) {//transaction total is less we have excess
			excess = Math.abs(diff);
			shorti=(float)0;
		} else {
			shorti = Math.abs(diff);
			excess=(float)0;
		}
		
		
		
		//now also enter into Cashup table 
		//just check if there is a row for the given date
		Cashup theExistingCashup = theCashUpService.getCashUpIfExists(checkdate, laneId, shiftId);
	
		if (theExistingCashup == null) { //this means insert
			Cashup newCashup = new Cashup();
			
			newCashup.setCheckDate(checkingDate1);
			newCashup.setAccountantId(userFromSession.getUserId());
			newCashup.setTollPlazaId(plazaId);
			newCashup.setLaneId(laneId);
			newCashup.setShiftId(shiftId);
			newCashup.setUserId(userId);
			newCashup.setSystemAmount(transactionTotal);
			newCashup.setTotalAmt((float)totalAmount);
			newCashup.setExcessAmount(excess);
			newCashup.setShortageAmount(shorti);
			newCashup.setCreateTimeStamp(new Date());
			newCashup.setCreateUserId(userFromSession.getUserId());
			
			theCashUpService.saveOrUpdate(newCashup);
			
		} else { //this means update
			theExistingCashup.setUserId(userId);
			theExistingCashup.setSystemAmount(transactionTotal);
			theExistingCashup.setTotalAmt((float)totalAmount);
			theExistingCashup.setExcessAmount(excess);
			theExistingCashup.setShortageAmount(shorti);
			theExistingCashup.setModificationTimeStamp(new Date());
			theExistingCashup.setModifiedUserId(userFromSession.getUserId());
			theCashUpService.saveOrUpdate(theExistingCashup);
			
		}
		
		
		
		theModel.addAttribute("date", checkdate);
		theModel.addAttribute("transactionTotal", transactionTotal);
		theModel.addAttribute("total", totalAmount);
		theModel.addAttribute("excess", excess);
		theModel.addAttribute("shorti", shorti);
		theModel.addAttribute("floatingAmount", floatingAmount);
		theModel.addAttribute("totalCount", totalCount);
		
		//also add more data for more tables
		theModel.addAttribute("car_jeep_tot", car_jeep_tot);
		theModel.addAttribute("lcv_tot", lcv_tot);
		theModel.addAttribute("bus_truck_tot", bus_truck_tot);
		theModel.addAttribute("_3_axel_tot", _3_axel_tot);
		theModel.addAttribute("hcm_mav_tot", hcm_mav_tot);
		theModel.addAttribute("oversized_tot", oversized_tot);
		
		theModel.addAttribute("car_jeep_count", car_jeep_count);
		theModel.addAttribute("lcv_count", lcv_count);
		theModel.addAttribute("bus_truck_count", bus_truck_count);
		theModel.addAttribute("_3_axel_count", _3_axel_count);
		theModel.addAttribute("hcm_mav_count", hcm_mav_count);
		theModel.addAttribute("oversized_count", oversized_count);
		
		//send number of notes also
		theModel.addAttribute("d2000", d2000);
		theModel.addAttribute("d1000", d1000);
		theModel.addAttribute("d500", d500);
		theModel.addAttribute("d200", d200);
		theModel.addAttribute("d100", d100);
		theModel.addAttribute("d50", d50);
		theModel.addAttribute("d20", d20);
		theModel.addAttribute("d10", d10);
		theModel.addAttribute("d5", d5);
		theModel.addAttribute("d2", d2);
		theModel.addAttribute("d1", d1);
		
		//totals
		Integer c2000 = d2000 * 2000;
		Integer c1000 = d1000 * 1000;
		Integer c500 = d500 * 500;
		Integer c200 = d200 * 200;
		Integer c100 = d100 * 100;
		Integer c50 = d50 * 50;
		Integer c20 = d20 * 20;
		Integer c10 = d10 * 10;
		Integer c5 = d5 * 5;
		Integer c2 = d2 * 2;
		Integer c1 = d1 * 1;
		
		theModel.addAttribute("c2000", c2000);
		theModel.addAttribute("c1000", c1000);
		theModel.addAttribute("c500", c500);
		theModel.addAttribute("c200", c200);
		theModel.addAttribute("c500", c500);
		theModel.addAttribute("c100", c100);
		theModel.addAttribute("c50", c50);
		theModel.addAttribute("c20", c20);
		theModel.addAttribute("c10", c10);
		theModel.addAttribute("c5", c5);
		theModel.addAttribute("c2", c2);
		theModel.addAttribute("c1", c1);
		
		theModel.addAttribute("date", checkingDate1);
		theModel.addAttribute("laneId", laneId);
		theModel.addAttribute("shiftDesc", shiftDesc);
		theModel.addAttribute("userId", userId);
		
		theModel.addAttribute("TransacionCount", TransacionCount);
		
		
		System.out.println("-=-=-=-=>>>Diff: "+diff);
		System.out.println("excess: "+excess);
		System.out.println("short: "+shorti);
		
		return "balance_sheet";
	}
	
	@GetMapping("/getValuesForUpdate")
	public String valuesForUpdate(HttpServletRequest request, Model theModel) {
		
		String ckeckdate = request.getParameter("checkDate");
		Integer laneId = Integer.parseInt(request.getParameter("laneId"));
		Integer shiftId = Integer.parseInt(request.getParameter("shiftId"));
		
		CashupDeclaration theCashupDeclarationIfExist = theCashupDeclarationService.getDeclarationIfExist(ckeckdate, laneId, shiftId);
		
		if (theCashupDeclarationIfExist==null) return "cashupdet";
		
		List<User> operator = theUserService.getUserFromId(theCashupDeclarationIfExist.getUserId());
		
		if (operator.size()==0) return "cashupdet";
		
		theModel.addAttribute("d2000",theCashupDeclarationIfExist.getD2000());
		theModel.addAttribute("d1000", theCashupDeclarationIfExist.getD1000());
		theModel.addAttribute("d500", theCashupDeclarationIfExist.getD500());
		theModel.addAttribute("d200", theCashupDeclarationIfExist.getD200());
		theModel.addAttribute("d100", theCashupDeclarationIfExist.getD100());
		theModel.addAttribute("d50", theCashupDeclarationIfExist.getD50());
		theModel.addAttribute("d20", theCashupDeclarationIfExist.getD20());
		theModel.addAttribute("d10", theCashupDeclarationIfExist.getD10());
		theModel.addAttribute("d5", theCashupDeclarationIfExist.getD5());
		theModel.addAttribute("d2", theCashupDeclarationIfExist.getD2());
		theModel.addAttribute("d1", theCashupDeclarationIfExist.getD1());
		

			
		User theUser = operator.get(0);
		
		theModel.addAttribute("operatorId", theUser.getUserId());
		theModel.addAttribute("operatorFirstName", theUser.getUserFirstName());
		theModel.addAttribute("operatorLastName", theUser.getUserLastName());
		
		return "cashupdet";
	}
	
	@GetMapping("/getTcs")
	public String getTcs(HttpServletRequest request, HttpSession session, Model theModel) {
		
		String date = request.getParameter("date");
		String plazaId = request.getParameter("plazaId");
		String shiftId = request.getParameter("shiftId");
		if(shiftId=="") shiftId = "All";
		
		System.out.println(date+" "+plazaId+" "+shiftId+">>>>>>>>>");
		
		List<Object[]> cashupUsers = theCashUpService.getTcs(date, plazaId, shiftId); 
		
		theModel.addAttribute("cashupUsers",cashupUsers);
		
		return "tc_list_for_the_day";
	}
}

























