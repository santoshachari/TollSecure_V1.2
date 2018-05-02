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
import com.seoniproject.entity.TollTransaction;
import com.seoniproject.entity.User;
import com.seoniproject.service.LaneService;
import com.seoniproject.service.ReportService;
import com.seoniproject.service.ShiftService;
import com.seoniproject.service.TollConfigService;
import com.seoniproject.service.UserService;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	private LaneService theLaneService;
	
	@Autowired
	private ShiftService theShiftService;
	
	@Autowired
	private UserService theUserService;
	
	@Autowired
	private ReportService theReportService;
	
	@Autowired 
	private TollConfigService theTollConfigService;
	
	@RequestMapping("/consolidatedTrafficAndRevenue")
	public String getConsolidated(Model theModel, HttpSession session) {

		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		

		//get all the lanes, shifts, TC for tollPlaza 1
		List<Lane> allLanes = theLaneService.getAllLanes(1);
		List<Shift> allShifts = theShiftService.getAllShifts(1);
		List<User> allOperators = theUserService.getAllOperators(1);

		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		theModel.addAttribute("allLanes", allLanes);
		theModel.addAttribute("allShifts", allShifts);
		theModel.addAttribute("allOperators", allOperators);
		theModel.addAttribute("today", sdf.format(today));
		
		return "consolidated_traffic_revenue";
	}
	
	@RequestMapping("/generateConsolidated")
	public String generateConsolidated(HttpServletRequest request, Model theModel) {
		
		//get all the parameters
		String shiftId = request.getParameter("shiftId");
		String userId = request.getParameter("userId");
		String laneId = request.getParameter("laneId");
		String to = request.getParameter("to");
		String from = request.getParameter("from");
		
		//System.out.println(shiftId+" "+laneId+" "+userId+" to: "+to+" from: "+from);
		//here we have 8 types 3 each for 2 vars 3 for 1 var 1 for no vars and 1 for all 3
		
		//get the 2 distinct directions from lanes
		List<Lane> laneDirections = theLaneService.getDistinctLaneDirections(1);
		String dir1 = laneDirections.get(0).getLaneDirection();
		String dir2 = laneDirections.get(1).getLaneDirection();
		
		System.out.println("Lane directions are as follows: "+dir1+" "+dir2);
		
		List<TollTransaction> tollTransactions = null;
		//all three are absent (mostly)
		if ((shiftId==null || shiftId.equals("")) && (userId==null || userId.equals("")) && (laneId==null || laneId.equals(""))) {
			System.out.println("-=-=-=>>>>>all nulls");
			tollTransactions = theReportService.getTollTransactionsFrom(from, to);	
		} else if ((shiftId==null || shiftId.equals("")) && (userId==null || userId.equals(""))) {
			System.out.println("-=-=-=>>>>>laneID not null");
			tollTransactions = theReportService.getTollTransactionsFromLaneId(from, to, laneId);
		} else if ((shiftId==null || shiftId.equals("")) && (laneId==null || laneId.equals(""))) {
			System.out.println("-=-=-=>>>>>userId not null");
			tollTransactions = theReportService.getTollTransactionsFromUserId(from, to, userId);
		} else if ((userId==null || userId.equals("")) && (laneId==null || laneId.equals(""))) {
			System.out.println("-=-=-=>>>>>shiftId not null");
			tollTransactions = theReportService.getTollTransactionsFromshiftId(from, to, shiftId);
		} else if ((shiftId==null || shiftId.equals(""))) {
			System.out.println("-=-=-=>>>>>shift id is nulls");
			tollTransactions = theReportService.getTollTransactionsFromLaneIdUserId(from, to, laneId, userId);
		} else if ((laneId==null || laneId.equals(""))) {
			System.out.println("-=-=-=>>>>>lane id is nulls");
			tollTransactions = theReportService.getTollTransactionsFromShiftIdUserId(from, to, shiftId, userId);
		} else if ((userId==null || userId.equals(""))) {
			System.out.println("-=-=-=>>>>>user id is nulls");
			tollTransactions = theReportService.getTollTransactionsFromShiftIdLaneId(from, to, shiftId, laneId);
		} else {
			System.out.println("-=-=-=>>>>>all not nulls");
			tollTransactions = theReportService.getTollTransactionsFromShiftIdLaneIdUserId(from, to, shiftId, laneId, userId);
		}
		
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
		 
		//setting all counts and totals to 0
		Integer dir1_s_car_jeep_count = 0;
		Integer dir1_s_lcv_count = 0;
		Integer dir1_s_bus_truck_count = 0;
		Integer dir1_s_3_axel_count = 0;
		Integer dir1_s_hcm_mav_count = 0;
		Integer dir1_s_oversized_count = 0;
		
		Integer dir2_s_car_jeep_count = 0;
		Integer dir2_s_lcv_count = 0;
		Integer dir2_s_bus_truck_count = 0;
		Integer dir2_s_3_axel_count = 0;
		Integer dir2_s_hcm_mav_count = 0;
		Integer dir2_s_oversized_count = 0;
		
		Integer dir1_r_car_jeep_count = 0;
		Integer dir1_r_lcv_count = 0;
		Integer dir1_r_bus_truck_count = 0;
		Integer dir1_r_3_axel_count = 0;
		Integer dir1_r_hcm_mav_count = 0;
		Integer dir1_r_oversized_count = 0;
		
		Integer dir2_r_car_jeep_count = 0;
		Integer dir2_r_lcv_count = 0;
		Integer dir2_r_bus_truck_count = 0;
		Integer dir2_r_3_axel_count = 0;
		Integer dir2_r_hcm_mav_count = 0;
		Integer dir2_r_oversized_count = 0;
		
		//setting all totals to 0
		Float dir1_s_car_jeep_tot = (float) 0;
		Float dir1_s_lcv_tot = (float)0;
		Float dir1_s_bus_truck_tot = (float)0;
		Float dir1_s_3_axel_tot = (float)0;
		Float dir1_s_hcm_mav_tot = (float)0;
		Float dir1_s_oversized_tot = (float)0;
		
		Float dir2_s_car_jeep_tot = (float) 0;
		Float dir2_s_lcv_tot = (float)0;
		Float dir2_s_bus_truck_tot = (float)0;
		Float dir2_s_3_axel_tot = (float)0;
		Float dir2_s_hcm_mav_tot = (float)0;
		Float dir2_s_oversized_tot = (float)0;
		
		Float dir1_r_car_jeep_tot = (float)0;
		Float dir1_r_lcv_tot = (float)0;
		Float dir1_r_bus_truck_tot = (float)0;
		Float dir1_r_3_axel_tot = (float)0;
		Float dir1_r_hcm_mav_tot = (float)0;
		Float dir1_r_oversized_tot = (float)0;
		
		Float dir2_r_car_jeep_tot = (float)0;
		Float dir2_r_lcv_tot = (float)0;
		Float dir2_r_bus_truck_tot = (float)0;
		Float dir2_r_3_axel_tot = (float)0;
		Float dir2_r_hcm_mav_tot = (float)0;
		Float dir2_r_oversized_tot = (float)0;
		
		//for concession types
		Integer dir1_c_car_jeep_count = 0;
		Integer dir1_c_lcv_count = 0;
		Integer dir1_c_bus_truck_count = 0;
		Integer dir1_c_3_axel_count = 0;
		Integer dir1_c_hcm_mav_count = 0;
		Integer dir1_c_oversized_count = 0;
		
		Integer dir2_c_car_jeep_count = 0;
		Integer dir2_c_lcv_count = 0;
		Integer dir2_c_bus_truck_count = 0;
		Integer dir2_c_3_axel_count = 0;
		Integer dir2_c_hcm_mav_count = 0;
		Integer dir2_c_oversized_count = 0;
		
		Float dir1_c_car_jeep_tot = (float) 0;
		Float dir1_c_lcv_tot = (float)0;
		Float dir1_c_bus_truck_tot = (float)0;
		Float dir1_c_3_axel_tot = (float)0;
		Float dir1_c_hcm_mav_tot = (float)0;
		Float dir1_c_oversized_tot = (float)0;

		Float dir2_c_car_jeep_tot = (float) 0;
		Float dir2_c_lcv_tot = (float)0;
		Float dir2_c_bus_truck_tot = (float)0;
		Float dir2_c_3_axel_tot = (float)0;
		Float dir2_c_hcm_mav_tot = (float)0;
		Float dir2_c_oversized_tot = (float)0;
		
		//for pass vehicles (we need only count for them)
		Integer dir1_p_car_jeep_count = 0;
		Integer dir1_p_lcv_count = 0;
		Integer dir1_p_bus_truck_count = 0;
		Integer dir1_p_3_axel_count = 0;
		Integer dir1_p_hcm_mav_count = 0;
		Integer dir1_p_oversized_count = 0;
		
		Integer dir2_p_car_jeep_count = 0;
		Integer dir2_p_lcv_count = 0;
		Integer dir2_p_bus_truck_count = 0;
		Integer dir2_p_3_axel_count = 0;
		Integer dir2_p_hcm_mav_count = 0;
		Integer dir2_p_oversized_count = 0;
		
		//for exempt vehicles (these also have only count)
		Integer dir1_e_car_jeep_count = 0;
		Integer dir1_e_lcv_count = 0;
		Integer dir1_e_bus_truck_count = 0;
		Integer dir1_e_3_axel_count = 0;
		Integer dir1_e_hcm_mav_count = 0;
		Integer dir1_e_oversized_count = 0;
		
		Integer dir2_e_car_jeep_count = 0;
		Integer dir2_e_lcv_count = 0;
		Integer dir2_e_bus_truck_count = 0;
		Integer dir2_e_3_axel_count = 0;
		Integer dir2_e_hcm_mav_count = 0;
		Integer dir2_e_oversized_count = 0;	
		
		
		//getting totals and counts
		//in 
		if (tollTransactions != null) {
			for (TollTransaction t : tollTransactions) {
				
				///get the direction of the lane
				List<Lane> lane = theLaneService.getLaneFromLaneId(t.getLaneId());
				String ourDirection = lane.get(0).getLaneDirection();
				
				System.out.println("Our direction: "+ourDirection);
				
				//for dir1
				if (ourDirection.equals(dir1)) {
					// for s and r types type 
					if (t.getVehicleClassId().equals(s_car_jeep_id) && t.getTollAmt().equals(s_car_jeep)) {
						dir1_s_car_jeep_tot = dir1_s_car_jeep_tot + t.getTollAmt();
						dir1_s_car_jeep_count = dir1_s_car_jeep_count+1;
					} else if (t.getVehicleClassId().equals(r_car_jeep_id) && t.getTollAmt().equals(r_car_jeep)) {
						dir1_r_car_jeep_tot = dir1_r_car_jeep_tot + t.getTollAmt();
						dir1_r_car_jeep_count = dir1_r_car_jeep_count+1;
					} else if (t.getVehicleClassId().equals(s_lcv_id) && t.getTollAmt().equals(s_lcv)) {
						dir1_s_lcv_tot = dir1_s_lcv_tot + t.getTollAmt();
						dir1_s_lcv_count = dir1_s_lcv_count+1;
					} else if (t.getVehicleClassId().equals(r_lcv_id) && t.getTollAmt().equals(r_lcv)) {
						dir1_r_lcv_tot = dir1_r_lcv_tot + t.getTollAmt();
						dir1_r_lcv_count = dir1_r_lcv_count+1;
					} else if (t.getVehicleClassId().equals(s_bus_truck_id) && t.getTollAmt().equals(s_bus_truck)) {
						dir1_s_bus_truck_tot = dir1_s_bus_truck_tot + t.getTollAmt();
						dir1_s_bus_truck_count = dir1_s_bus_truck_count+1;
					} else if (t.getVehicleClassId().equals(r_bus_truck_id) && t.getTollAmt().equals(r_bus_truck)) {
						dir1_r_bus_truck_tot = dir1_r_bus_truck_tot + t.getTollAmt();
						dir1_r_bus_truck_count = dir1_r_bus_truck_count+1;
					} else if (t.getVehicleClassId().equals(s_3_axel_id) && t.getTollAmt().equals(s_3_axel)) {
						dir1_s_3_axel_tot = dir1_s_3_axel_tot + t.getTollAmt();
						dir1_s_3_axel_count = dir1_s_3_axel_count+1;
					} else if (t.getVehicleClassId().equals(r_3_axel_id) && t.getTollAmt().equals(r_3_axel)) {
						dir1_r_3_axel_tot = dir1_r_3_axel_tot + t.getTollAmt();
						dir1_r_3_axel_count = dir1_r_3_axel_count+1;
					} else if (t.getVehicleClassId().equals(s_hcm_mav_id) && t.getTollAmt().equals(s_hcm_mav)) {
						dir1_s_hcm_mav_tot = dir1_s_hcm_mav_tot + t.getTollAmt();
						dir1_s_hcm_mav_count = dir1_s_hcm_mav_count+1;
					} else if (t.getVehicleClassId().equals(r_hcm_mav_id) && t.getTollAmt().equals(r_hcm_mav)) {
						dir1_r_hcm_mav_tot = dir1_r_hcm_mav_tot + t.getTollAmt();
						dir1_r_hcm_mav_count = dir1_r_hcm_mav_count+1;
					} else if (t.getVehicleClassId().equals(s_oversized_id) && t.getTollAmt().equals(s_oversized)) {
						dir1_s_oversized_tot = dir1_s_oversized_tot + t.getTollAmt();
						dir1_s_oversized_count = dir1_s_oversized_count+1;
					} else if (t.getVehicleClassId().equals(r_oversized_id) && t.getTollAmt().equals(r_oversized)) {
						dir1_r_oversized_tot = dir1_r_oversized_tot + t.getTollAmt();
						dir1_r_oversized_count = dir1_r_oversized_count+1;
					} 
					
					//for concession vehicles 
					else if (t.getVehicleClassId().equals(s_car_jeep_id) && !t.getTollAmt().equals(s_car_jeep)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir1_e_car_jeep_count = dir1_e_car_jeep_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir1_p_car_jeep_count = dir1_p_car_jeep_count + 1;
						} else {
							dir1_c_car_jeep_tot = dir1_c_car_jeep_tot + t.getTollAmt();
							dir1_c_car_jeep_count = dir1_c_car_jeep_count+1;
						}
					} else if (t.getVehicleClassId().equals(r_car_jeep_id) && !t.getTollAmt().equals(r_car_jeep)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir1_e_car_jeep_count = dir1_e_car_jeep_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir1_p_car_jeep_count = dir1_p_car_jeep_count + 1;
						} else {
							dir1_c_car_jeep_tot = dir1_c_car_jeep_tot + t.getTollAmt();
							dir1_c_car_jeep_count = dir1_c_car_jeep_count+1;
						}
					} else if (t.getVehicleClassId().equals(s_lcv_id) && !t.getTollAmt().equals(s_lcv)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir1_e_lcv_count = dir1_e_lcv_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir1_p_lcv_count = dir1_p_lcv_count + 1;
						} else {
							dir1_c_lcv_tot = dir1_c_lcv_tot + t.getTollAmt();
							dir1_c_lcv_count = dir1_c_lcv_count+1;
						}
					} else if (t.getVehicleClassId().equals(r_lcv_id) && !t.getTollAmt().equals(r_lcv)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir1_e_lcv_count = dir1_e_lcv_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir1_p_lcv_count = dir1_p_lcv_count + 1;
						} else {
							dir1_c_lcv_tot = dir1_c_lcv_tot + t.getTollAmt();
							dir1_c_lcv_count = dir1_c_lcv_count+1;
						}
					} else if (t.getVehicleClassId().equals(s_bus_truck_id) && !t.getTollAmt().equals(s_bus_truck)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir1_e_bus_truck_count = dir1_e_bus_truck_count+1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir1_p_bus_truck_count = dir1_p_bus_truck_count + 1;
						} else {
							dir1_c_bus_truck_tot = dir1_c_bus_truck_tot + t.getTollAmt();
							dir1_c_bus_truck_count = dir1_c_bus_truck_count+1;
						}
					} else if (t.getVehicleClassId().equals(r_bus_truck_id) && !t.getTollAmt().equals(r_bus_truck)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir1_e_bus_truck_count = dir1_e_bus_truck_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir1_p_bus_truck_count = dir1_p_bus_truck_count + 1;
						} else {
							dir1_c_bus_truck_tot = dir1_c_bus_truck_tot + t.getTollAmt();
							dir1_c_bus_truck_count = dir1_c_bus_truck_count+1;
						}
					} else if (t.getVehicleClassId().equals(s_3_axel_id) && !t.getTollAmt().equals(s_3_axel)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir1_e_3_axel_count = dir1_e_3_axel_count+1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir1_p_3_axel_count = dir1_p_3_axel_count + 1;
						} else {
							dir1_c_3_axel_tot = dir1_c_3_axel_tot + t.getTollAmt();
							dir1_c_3_axel_count = dir1_c_3_axel_count+1;
						}
					} else if (t.getVehicleClassId().equals(r_3_axel_id) && !t.getTollAmt().equals(r_3_axel)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir1_e_3_axel_count = dir1_e_3_axel_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir1_p_3_axel_count = dir1_p_3_axel_count + 1;
						} else {
							dir1_c_3_axel_tot = dir1_c_3_axel_tot + t.getTollAmt();
							dir1_c_3_axel_count = dir1_c_3_axel_count+1;
						}
					} else if (t.getVehicleClassId().equals(s_hcm_mav_id) && !t.getTollAmt().equals(s_hcm_mav)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir1_e_hcm_mav_count = dir1_e_hcm_mav_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir1_p_hcm_mav_count = dir1_p_hcm_mav_count + 1;
						} else {
							dir1_c_hcm_mav_tot = dir1_c_hcm_mav_tot + t.getTollAmt();
							dir1_c_hcm_mav_count = dir1_c_hcm_mav_count+1;
						}
					} else if (t.getVehicleClassId().equals(r_hcm_mav_id) && !t.getTollAmt().equals(r_hcm_mav)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir1_e_hcm_mav_count = dir1_e_hcm_mav_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir1_p_hcm_mav_count = dir1_p_hcm_mav_count + 1;
						} else {
							dir1_c_hcm_mav_tot = dir1_c_hcm_mav_tot + t.getTollAmt();
							dir1_c_hcm_mav_count = dir1_c_hcm_mav_count+1;
						}
					} else if (t.getVehicleClassId().equals(s_oversized_id) && !t.getTollAmt().equals(s_oversized)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir1_e_oversized_count = dir1_e_oversized_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir1_p_oversized_count = dir1_p_oversized_count + 1;
						} else {
							dir1_c_oversized_tot = dir1_c_oversized_tot + t.getTollAmt();
							dir1_c_oversized_count = dir1_c_oversized_count+1;
						}
					} else if (t.getVehicleClassId().equals(r_oversized_id) && !t.getTollAmt().equals(r_oversized)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir1_e_oversized_count = dir1_e_oversized_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir1_p_oversized_count = dir1_p_oversized_count + 1;
						} else {
							dir1_c_oversized_tot = dir1_c_oversized_tot + t.getTollAmt();
							dir1_c_oversized_count = dir1_c_oversized_count+1;
						}
					} 
				} 
				
				
				
				//other direction
				else {
					// for s and r types type 
					if (t.getVehicleClassId().equals(s_car_jeep_id) && t.getTollAmt().equals(s_car_jeep)) {
						dir2_s_car_jeep_tot = dir2_s_car_jeep_tot + t.getTollAmt();
						dir2_s_car_jeep_count = dir2_s_car_jeep_count+1;
					} else if (t.getVehicleClassId().equals(r_car_jeep_id) && t.getTollAmt().equals(r_car_jeep)) {
						dir2_r_car_jeep_tot = dir2_r_car_jeep_tot + t.getTollAmt();
						dir2_r_car_jeep_count = dir2_r_car_jeep_count+1;
					} else if (t.getVehicleClassId().equals(s_lcv_id) && t.getTollAmt().equals(s_lcv)) {
						dir2_s_lcv_tot = dir2_s_lcv_tot + t.getTollAmt();
						dir2_s_lcv_count = dir2_s_lcv_count+1;
					} else if (t.getVehicleClassId().equals(r_lcv_id) && t.getTollAmt().equals(r_lcv)) {
						dir2_r_lcv_tot = dir2_r_lcv_tot + t.getTollAmt();
						dir2_r_lcv_count = dir2_r_lcv_count+1;
					} else if (t.getVehicleClassId().equals(s_bus_truck_id) && t.getTollAmt().equals(s_bus_truck)) {
						dir2_s_bus_truck_tot = dir2_s_bus_truck_tot + t.getTollAmt();
						dir2_s_bus_truck_count = dir2_s_bus_truck_count+1;
					} else if (t.getVehicleClassId().equals(r_bus_truck_id) && t.getTollAmt().equals(r_bus_truck)) {
						dir2_r_bus_truck_tot = dir2_r_bus_truck_tot + t.getTollAmt();
						dir2_r_bus_truck_count = dir2_r_bus_truck_count+1;
					} else if (t.getVehicleClassId().equals(s_3_axel_id) && t.getTollAmt().equals(s_3_axel)) {
						dir2_s_3_axel_tot = dir2_s_3_axel_tot + t.getTollAmt();
						dir2_s_3_axel_count = dir2_s_3_axel_count+1;
					} else if (t.getVehicleClassId().equals(r_3_axel_id) && t.getTollAmt().equals(r_3_axel)) {
						dir2_r_3_axel_tot = dir2_r_3_axel_tot + t.getTollAmt();
						dir2_r_3_axel_count = dir2_r_3_axel_count+1;
					} else if (t.getVehicleClassId().equals(s_hcm_mav_id) && t.getTollAmt().equals(s_hcm_mav)) {
						dir2_s_hcm_mav_tot = dir2_s_hcm_mav_tot + t.getTollAmt();
						dir2_s_hcm_mav_count = dir2_s_hcm_mav_count+1;
					} else if (t.getVehicleClassId().equals(r_hcm_mav_id) && t.getTollAmt().equals(r_hcm_mav)) {
						dir2_r_hcm_mav_tot = dir2_r_hcm_mav_tot + t.getTollAmt();
						dir2_r_hcm_mav_count = dir2_r_hcm_mav_count+1;
					} else if (t.getVehicleClassId().equals(s_oversized_id) && t.getTollAmt().equals(s_oversized)) {
						dir2_s_oversized_tot = dir2_s_oversized_tot + t.getTollAmt();
						dir2_s_oversized_count = dir2_s_oversized_count+1;
					} else if (t.getVehicleClassId().equals(r_oversized_id) && t.getTollAmt().equals(r_oversized)) {
						dir2_r_oversized_tot = dir2_r_oversized_tot + t.getTollAmt();
						dir2_r_oversized_count = dir2_r_oversized_count+1;
					} 

					//for concession vehicles 
					else if (t.getVehicleClassId().equals(s_car_jeep_id) && !t.getTollAmt().equals(s_car_jeep)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir2_e_car_jeep_count = dir2_e_car_jeep_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir2_p_car_jeep_count = dir2_p_car_jeep_count + 1;
						} else {
							dir2_c_car_jeep_tot = dir2_c_car_jeep_tot + t.getTollAmt();
							dir2_c_car_jeep_count = dir2_c_car_jeep_count+1;
						}
					} else if (t.getVehicleClassId().equals(r_car_jeep_id) && !t.getTollAmt().equals(r_car_jeep)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir2_e_car_jeep_count = dir2_e_car_jeep_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir2_p_car_jeep_count = dir2_p_car_jeep_count + 1;
						} else {
							dir2_c_car_jeep_tot = dir2_c_car_jeep_tot + t.getTollAmt();
							dir2_c_car_jeep_count = dir2_c_car_jeep_count+1;
						}
					} else if (t.getVehicleClassId().equals(s_lcv_id) && !t.getTollAmt().equals(s_lcv)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir2_e_lcv_count = dir2_e_lcv_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir2_p_lcv_count = dir2_p_lcv_count + 1;
						} else {
							dir2_c_lcv_tot = dir2_c_lcv_tot + t.getTollAmt();
							dir2_c_lcv_count = dir2_c_lcv_count+1;
						}
					} else if (t.getVehicleClassId().equals(r_lcv_id) && !t.getTollAmt().equals(r_lcv)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir2_e_lcv_count = dir2_e_lcv_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir2_p_lcv_count = dir2_p_lcv_count + 1;
						} else {
							dir2_c_lcv_tot = dir2_c_lcv_tot + t.getTollAmt();
							dir2_c_lcv_count = dir2_c_lcv_count+1;
						}
					} else if (t.getVehicleClassId().equals(s_bus_truck_id) && !t.getTollAmt().equals(s_bus_truck)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir2_e_bus_truck_count = dir2_e_bus_truck_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir2_p_bus_truck_count = dir2_p_bus_truck_count + 1;
						} else {
							dir2_c_bus_truck_tot = dir2_c_bus_truck_tot + t.getTollAmt();
							dir2_c_bus_truck_count = dir2_c_bus_truck_count+1;
						}
					} else if (t.getVehicleClassId().equals(r_bus_truck_id) && !t.getTollAmt().equals(r_bus_truck)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir2_e_bus_truck_count = dir2_e_bus_truck_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir2_p_bus_truck_count = dir2_p_bus_truck_count + 1;
						} else {
							dir2_c_bus_truck_tot = dir2_c_bus_truck_tot + t.getTollAmt();
							dir2_c_bus_truck_count = dir2_c_bus_truck_count+1;
						}
					} else if (t.getVehicleClassId().equals(s_3_axel_id) && !t.getTollAmt().equals(s_3_axel)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir2_e_3_axel_count = dir2_e_3_axel_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir2_p_3_axel_count = dir2_p_3_axel_count + 1;
						} else {
							dir2_c_3_axel_tot = dir2_c_3_axel_tot + t.getTollAmt();
							dir2_c_3_axel_count = dir2_c_3_axel_count+1;
						}
					} else if (t.getVehicleClassId().equals(r_3_axel_id) && !t.getTollAmt().equals(r_3_axel)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir2_e_3_axel_count = dir2_e_3_axel_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir2_p_3_axel_count = dir2_p_3_axel_count + 1;
						} else {
							dir2_c_3_axel_tot = dir2_c_3_axel_tot + t.getTollAmt();
							dir2_c_3_axel_count = dir2_c_3_axel_count+1;
						}
					} else if (t.getVehicleClassId().equals(s_hcm_mav_id) && !t.getTollAmt().equals(s_hcm_mav)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir2_e_hcm_mav_count = dir2_e_hcm_mav_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir2_p_hcm_mav_count = dir2_p_hcm_mav_count + 1;
						} else {
							dir2_c_hcm_mav_tot = dir2_c_hcm_mav_tot + t.getTollAmt();
							dir2_c_hcm_mav_count = dir2_c_hcm_mav_count+1;
						}
					} else if (t.getVehicleClassId().equals(r_hcm_mav_id) && !t.getTollAmt().equals(r_hcm_mav)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir2_e_hcm_mav_count = dir2_e_hcm_mav_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir2_p_hcm_mav_count = dir2_p_hcm_mav_count + 1;
						} else {
							dir2_c_hcm_mav_tot = dir2_c_hcm_mav_tot + t.getTollAmt();
							dir2_c_hcm_mav_count = dir2_c_hcm_mav_count+1;
						}
					} else if (t.getVehicleClassId().equals(s_oversized_id) && !t.getTollAmt().equals(s_oversized)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir2_e_oversized_count = dir2_e_oversized_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir2_p_oversized_count = dir2_p_oversized_count + 1;
						} else {
							dir2_c_oversized_tot = dir2_c_oversized_tot + t.getTollAmt();
							dir2_c_oversized_count = dir2_c_oversized_count+1;
						}
					} else if (t.getVehicleClassId().equals(r_oversized_id) && !t.getTollAmt().equals(r_oversized)) {
						if (t.getConcessionType().equals("Exempted")) {
							dir2_e_oversized_count = dir2_e_oversized_count + 1;
						} else if (t.getConcessionType().equals("Pass")) {
							dir2_p_oversized_count = dir2_p_oversized_count + 1;
						} else {
							dir2_c_oversized_tot = dir2_c_oversized_tot + t.getTollAmt();
							dir2_c_oversized_count = dir2_c_oversized_count+1;
						}
					} 
					
				}
				
				
			}
		}
		
		//for car jeep
		theModel.addAttribute("dir1_s_car_jeep_tot", dir1_s_car_jeep_tot);
		theModel.addAttribute("dir2_s_car_jeep_tot", dir2_s_car_jeep_tot);
		Float s_car_jeep_tot = dir1_s_car_jeep_tot + dir2_s_car_jeep_tot;
		theModel.addAttribute("s_car_jeep_tot", s_car_jeep_tot);
		
		theModel.addAttribute("dir1_s_car_jeep_count", dir1_s_car_jeep_count);
		theModel.addAttribute("dir2_s_car_jeep_count", dir2_s_car_jeep_count);
		Integer s_car_jeep_count = dir1_s_car_jeep_count + dir2_s_car_jeep_count;
		theModel.addAttribute("s_car_jeep_count", s_car_jeep_count);
		
		//r
		theModel.addAttribute("dir1_r_car_jeep_tot", dir1_r_car_jeep_tot);
		theModel.addAttribute("dir2_r_car_jeep_tot", dir2_r_car_jeep_tot);
		Float r_car_jeep_tot = dir1_r_car_jeep_tot + dir2_r_car_jeep_tot;
		theModel.addAttribute("r_car_jeep_tot", r_car_jeep_tot);
		
		theModel.addAttribute("dir1_r_car_jeep_count", dir1_r_car_jeep_count);
		theModel.addAttribute("dir2_r_car_jeep_count", dir2_r_car_jeep_count);
		Integer r_car_jeep_count = dir1_r_car_jeep_count + dir2_r_car_jeep_count;
		theModel.addAttribute("r_car_jeep_count", r_car_jeep_count);
		
		//c
		theModel.addAttribute("dir1_c_car_jeep_tot", dir1_c_car_jeep_tot);
		theModel.addAttribute("dir2_c_car_jeep_tot", dir2_c_car_jeep_tot);
		Float c_car_jeep_tot = dir1_c_car_jeep_tot + dir2_c_car_jeep_tot;
		theModel.addAttribute("c_car_jeep_tot", c_car_jeep_tot);
		
		theModel.addAttribute("dir1_c_car_jeep_count", dir1_c_car_jeep_count);
		theModel.addAttribute("dir2_c_car_jeep_count", dir2_c_car_jeep_count);
		Integer c_car_jeep_count = dir1_c_car_jeep_count + dir2_c_car_jeep_count;
		theModel.addAttribute("c_car_jeep_count", c_car_jeep_count);
		
		//e
		theModel.addAttribute("dir1_e_car_jeep_count",dir1_e_car_jeep_count );
		theModel.addAttribute("dir2_e_car_jeep_count",dir2_e_car_jeep_count );
		Integer e_car_jeep_count = dir1_e_car_jeep_count + dir2_e_car_jeep_count;
		theModel.addAttribute("e_car_jeep_count", e_car_jeep_count);
		
		//p
		theModel.addAttribute("dir1_p_car_jeep_count",dir1_p_car_jeep_count );
		theModel.addAttribute("dir2_p_car_jeep_count",dir2_p_car_jeep_count );
		Integer p_car_jeep_count = dir1_p_car_jeep_count + dir2_p_car_jeep_count;
		theModel.addAttribute("p_car_jeep_count", p_car_jeep_count);
		
		//row tot
		Float row_car_jeep_tot = s_car_jeep_tot + r_car_jeep_tot + c_car_jeep_tot;
		Integer row_car_jeep_count = s_car_jeep_count + r_car_jeep_count + c_car_jeep_count + e_car_jeep_count + p_car_jeep_count;
		theModel.addAttribute("row_car_jeep_tot", row_car_jeep_tot);
		theModel.addAttribute("row_car_jeep_count", row_car_jeep_count);
		
		//for lcv
		theModel.addAttribute("dir1_s_lcv_tot", dir1_s_lcv_tot);
		theModel.addAttribute("dir2_s_lcv_tot", dir2_s_lcv_tot);
		Float s_lcv_tot = dir1_s_lcv_tot+dir2_s_lcv_tot;
		theModel.addAttribute("s_lcv_tot", s_lcv_tot);
		
		theModel.addAttribute("dir1_s_lcv_count", dir1_s_lcv_count);
		theModel.addAttribute("dir2_s_lcv_count", dir2_s_lcv_count);
		Integer s_lcv_count = dir1_s_lcv_count+dir2_s_lcv_count;
		theModel.addAttribute("s_lcv_count", s_lcv_count);
		
		//r
		theModel.addAttribute("dir1_r_lcv_tot", dir1_r_lcv_tot);
		theModel.addAttribute("dir2_r_lcv_tot", dir2_r_lcv_tot);
		Float r_lcv_tot = dir1_r_lcv_tot+dir2_r_lcv_tot;
		theModel.addAttribute("r_lcv_tot", r_lcv_tot);
		
		theModel.addAttribute("dir1_r_lcv_count", dir1_r_lcv_count);
		theModel.addAttribute("dir2_r_lcv_count", dir2_r_lcv_count);
		Integer r_lcv_count = dir1_r_lcv_count+dir2_r_lcv_count;
		theModel.addAttribute("r_lcv_count", r_lcv_count);
		
		//c
		theModel.addAttribute("dir1_c_lcv_tot", dir1_c_lcv_tot);
		theModel.addAttribute("dir2_c_lcv_tot", dir2_c_lcv_tot);
		Float c_lcv_tot = dir1_c_lcv_tot+dir2_c_lcv_tot;
		theModel.addAttribute("c_lcv_tot", c_lcv_tot);
		
		theModel.addAttribute("dir1_c_lcv_count", dir1_c_lcv_count);
		theModel.addAttribute("dir2_c_lcv_count", dir2_c_lcv_count);
		Integer c_lcv_count = dir1_c_lcv_count+dir2_c_lcv_count;
		theModel.addAttribute("c_lcv_count", c_lcv_count);
		
		//e
		theModel.addAttribute("dir1_e_lcv_count", dir1_e_lcv_count);
		theModel.addAttribute("dir2_e_lcv_count", dir2_e_lcv_count);
		Integer e_lcv_count = dir1_e_lcv_count+dir2_e_lcv_count;
		theModel.addAttribute("e_lcv_count", e_lcv_count);
		
		//p
		theModel.addAttribute("dir1_p_lcv_count", dir1_p_lcv_count);
		theModel.addAttribute("dir2_p_lcv_count", dir2_p_lcv_count);
		Integer p_lcv_count = dir1_p_lcv_count+dir2_p_lcv_count;
		theModel.addAttribute("p_lcv_count", p_lcv_count);
		
		//row 
		Float row_lcv_tot = s_lcv_tot + r_lcv_tot + c_lcv_tot;
		Integer row_lcv_count = s_lcv_count + r_lcv_count + c_lcv_count + e_lcv_count + p_lcv_count;
		theModel.addAttribute("row_lcv_tot", row_lcv_tot);
		theModel.addAttribute("row_lcv_count", row_lcv_count);
		
		//bus_truck
		theModel.addAttribute("dir1_s_bus_truck_tot", dir1_s_bus_truck_tot);
		theModel.addAttribute("dir2_s_bus_truck_tot", dir2_s_bus_truck_tot);
		Float s_bus_truck_tot = dir1_s_bus_truck_tot + dir2_s_bus_truck_tot;
		theModel.addAttribute("s_bus_truck_tot", s_bus_truck_tot);
		
		theModel.addAttribute("dir1_s_bus_truck_count", dir1_s_bus_truck_count);
		theModel.addAttribute("dir2_s_bus_truck_count", dir2_s_bus_truck_count);
		Integer s_bus_truck_count = dir1_s_bus_truck_count + dir2_s_bus_truck_count;
		theModel.addAttribute("s_bus_truck_count", s_bus_truck_count);
		
		//r
		theModel.addAttribute("dir1_r_bus_truck_tot", dir1_r_bus_truck_tot);
		theModel.addAttribute("dir2_r_bus_truck_tot", dir2_r_bus_truck_tot);
		Float r_bus_truck_tot = dir1_r_bus_truck_tot + dir2_r_bus_truck_tot;
		theModel.addAttribute("r_bus_truck_tot", r_bus_truck_tot);
		
		theModel.addAttribute("dir1_r_bus_truck_count", dir1_r_bus_truck_count);
		theModel.addAttribute("dir2_r_bus_truck_count", dir2_r_bus_truck_count);
		Integer r_bus_truck_count = dir1_r_bus_truck_count + dir2_r_bus_truck_count;
		theModel.addAttribute("r_bus_truck_count", r_bus_truck_count);
		
		//c
		theModel.addAttribute("dir1_c_bus_truck_tot", dir1_c_bus_truck_tot);
		theModel.addAttribute("dir2_c_bus_truck_tot", dir2_c_bus_truck_tot);
		Float c_bus_truck_tot = dir1_c_bus_truck_tot + dir2_c_bus_truck_tot;
		theModel.addAttribute("c_bus_truck_tot", c_bus_truck_tot);
		
		theModel.addAttribute("dir1_c_bus_truck_count", dir1_c_bus_truck_count);
		theModel.addAttribute("dir2_c_bus_truck_count", dir2_c_bus_truck_count);
		Integer c_bus_truck_count = dir1_c_bus_truck_count + dir2_c_bus_truck_count;
		theModel.addAttribute("c_bus_truck_count", c_bus_truck_count);
		
		//p
		theModel.addAttribute("dir1_p_bus_truck_count", dir1_p_bus_truck_count);
		theModel.addAttribute("dir2_p_bus_truck_count", dir2_p_bus_truck_count);
		Integer p_bus_truck_count = dir1_p_bus_truck_count + dir2_p_bus_truck_count;
		theModel.addAttribute("p_bus_truck_count", p_bus_truck_count);
		
		//e
		theModel.addAttribute("dir1_e_bus_truck_count", dir1_e_bus_truck_count);
		theModel.addAttribute("dir2_e_bus_truck_count", dir2_e_bus_truck_count);
		Integer e_bus_truck_count = dir1_e_bus_truck_count + dir2_e_bus_truck_count;
		theModel.addAttribute("e_bus_truck_count", e_bus_truck_count);
		
		//row
		Float row_bus_truck_tot = s_bus_truck_tot + r_bus_truck_tot + c_bus_truck_tot;
		Integer row_bus_truck_count = s_bus_truck_count + r_bus_truck_count + c_bus_truck_count + p_bus_truck_count + e_bus_truck_count;
		theModel.addAttribute("row_bus_truck_tot", row_bus_truck_tot);
		theModel.addAttribute("row_bus_truck_count", row_bus_truck_count);
		
		//3 axel
		theModel.addAttribute("dir1_s_3_axel_tot", dir1_s_3_axel_tot);
		theModel.addAttribute("dir2_s_3_axel_tot", dir2_s_3_axel_tot);
		Float s_3_axel_tot = dir1_s_3_axel_tot + dir2_s_3_axel_tot;
		theModel.addAttribute("s_3_axel_tot", s_3_axel_tot);
		
		theModel.addAttribute("dir1_s_3_axel_count", dir1_s_3_axel_count);
		theModel.addAttribute("dir2_s_3_axel_count", dir2_s_3_axel_count);
		Integer s_3_axel_count = dir1_s_3_axel_count + dir2_s_3_axel_count;
		theModel.addAttribute("s_3_axel_count", s_3_axel_count);
		
		//r
		theModel.addAttribute("dir1_r_3_axel_tot", dir1_r_3_axel_tot);
		theModel.addAttribute("dir2_r_3_axel_tot", dir2_r_3_axel_tot);
		Float r_3_axel_tot = dir1_r_3_axel_tot + dir2_r_3_axel_tot;
		theModel.addAttribute("r_3_axel_tot", r_3_axel_tot);
		
		theModel.addAttribute("dir1_r_3_axel_count", dir1_r_3_axel_count);
		theModel.addAttribute("dir2_r_3_axel_count", dir2_r_3_axel_count);
		Integer r_3_axel_count = dir1_r_3_axel_count + dir2_r_3_axel_count;
		theModel.addAttribute("r_3_axel_count", r_3_axel_count);
		
		//c
		theModel.addAttribute("dir1_c_3_axel_tot", dir1_c_3_axel_tot);
		theModel.addAttribute("dir2_c_3_axel_tot", dir2_c_3_axel_tot);
		Float c_3_axel_tot = dir1_c_3_axel_tot + dir2_c_3_axel_tot;
		theModel.addAttribute("c_3_axel_tot", c_3_axel_tot);
		
		theModel.addAttribute("dir1_c_3_axel_count", dir1_c_3_axel_count);
		theModel.addAttribute("dir2_c_3_axel_count", dir2_c_3_axel_count);
		Integer c_3_axel_count = dir1_c_3_axel_count + dir2_c_3_axel_count;
		theModel.addAttribute("c_3_axel_count", c_3_axel_count);
		
		//p
		theModel.addAttribute("dir1_p_3_axel_count", dir1_p_3_axel_count);
		theModel.addAttribute("dir2_p_3_axel_count", dir2_p_3_axel_count);
		Integer p_3_axel_count = dir1_p_3_axel_count + dir2_p_3_axel_count;
		theModel.addAttribute("p_3_axel_count", p_3_axel_count);
		
		//e
		theModel.addAttribute("dir1_e_3_axel_count", dir1_e_3_axel_count);
		theModel.addAttribute("dir2_e_3_axel_count", dir2_e_3_axel_count);
		Integer e_3_axel_count = dir1_e_3_axel_count + dir2_e_3_axel_count;
		theModel.addAttribute("e_3_axel_count", e_3_axel_count);
		
		//row
		Float row_3_axel_tot = s_3_axel_tot + r_3_axel_tot + c_3_axel_tot;
		Integer row_3_axel_count = s_3_axel_count + r_3_axel_count + c_3_axel_count + p_3_axel_count + e_3_axel_count;
		theModel.addAttribute("row_3_axel_tot", row_3_axel_tot);
		theModel.addAttribute("row_3_axel_count", row_3_axel_count);
		
		//hcm mav
		theModel.addAttribute("dir1_s_hcm_mav_tot", dir1_s_hcm_mav_tot);
		theModel.addAttribute("dir2_s_hcm_mav_tot", dir2_s_hcm_mav_tot);
		Float s_hcm_mav_tot = dir1_s_hcm_mav_tot + dir2_s_hcm_mav_tot;
		theModel.addAttribute("s_hcm_mav_tot", s_hcm_mav_tot);
		
		theModel.addAttribute("dir1_s_hcm_mav_count", dir1_s_hcm_mav_count);
		theModel.addAttribute("dir2_s_hcm_mav_count", dir2_s_hcm_mav_count);
		Integer s_hcm_mav_count = dir1_s_hcm_mav_count + dir2_s_hcm_mav_count;
		theModel.addAttribute("s_hcm_mav_count", s_hcm_mav_count);
		
		//r
		theModel.addAttribute("dir1_r_hcm_mav_tot", dir1_r_hcm_mav_tot);
		theModel.addAttribute("dir2_r_hcm_mav_tot", dir2_r_hcm_mav_tot);
		Float r_hcm_mav_tot = dir1_r_hcm_mav_tot + dir2_r_hcm_mav_tot;
		theModel.addAttribute("r_hcm_mav_tot", r_hcm_mav_tot);
		
		theModel.addAttribute("dir1_r_hcm_mav_count", dir1_r_hcm_mav_count);
		theModel.addAttribute("dir2_r_hcm_mav_count", dir2_r_hcm_mav_count);
		Integer r_hcm_mav_count = dir1_r_hcm_mav_count + dir2_r_hcm_mav_count;
		theModel.addAttribute("r_hcm_mav_count", r_hcm_mav_count);
		
		//c
		theModel.addAttribute("dir1_c_hcm_mav_tot", dir1_c_hcm_mav_tot);
		theModel.addAttribute("dir2_c_hcm_mav_tot", dir2_c_hcm_mav_tot);
		Float c_hcm_mav_tot = dir1_c_hcm_mav_tot + dir2_c_hcm_mav_tot;
		theModel.addAttribute("c_hcm_mav_tot", c_hcm_mav_tot);
		
		theModel.addAttribute("dir1_c_hcm_mav_count", dir1_c_hcm_mav_count);
		theModel.addAttribute("dir2_c_hcm_mav_count", dir2_c_hcm_mav_count);
		Integer c_hcm_mav_count = dir1_c_hcm_mav_count + dir2_c_hcm_mav_count;
		theModel.addAttribute("c_hcm_mav_count", c_hcm_mav_count);
		
		//p
		theModel.addAttribute("dir1_p_hcm_mav_count", dir1_p_hcm_mav_count);
		theModel.addAttribute("dir2_p_hcm_mav_count", dir2_p_hcm_mav_count);
		Integer p_hcm_mav_count = dir1_p_hcm_mav_count + dir2_p_hcm_mav_count;
		theModel.addAttribute("p_hcm_mav_count", p_hcm_mav_count);
		
		//e
		theModel.addAttribute("dir1_e_hcm_mav_count", dir1_e_hcm_mav_count);
		theModel.addAttribute("dir2_e_hcm_mav_count", dir2_e_hcm_mav_count);
		Integer e_hcm_mav_count = dir1_e_hcm_mav_count + dir2_e_hcm_mav_count;
		theModel.addAttribute("e_hcm_mav_count", e_hcm_mav_count);
		
		//row hcm_mav
		Float row_hcm_mav_tot = s_hcm_mav_tot + r_hcm_mav_tot + c_hcm_mav_tot;
		Integer row_hcm_mav_count = s_hcm_mav_count + r_hcm_mav_count + c_hcm_mav_count + p_hcm_mav_count + e_hcm_mav_count;
		theModel.addAttribute("row_hcm_mav_tot", row_hcm_mav_tot);
		theModel.addAttribute("row_hcm_mav_count", row_hcm_mav_count);
		
		//oversized
		theModel.addAttribute("dir1_s_oversized_tot", dir1_s_oversized_tot);
		theModel.addAttribute("dir2_s_oversized_tot", dir2_s_oversized_tot);
		Float s_oversized_tot = dir1_s_oversized_tot + dir2_s_oversized_tot;
		theModel.addAttribute("s_oversized_tot", s_oversized_tot);

		theModel.addAttribute("dir1_s_oversized_count", dir1_s_oversized_count);
		theModel.addAttribute("dir2_s_oversized_count", dir2_s_oversized_count);
		Integer s_oversized_count = dir1_s_oversized_count + dir2_s_oversized_count;
		theModel.addAttribute("s_oversized_count", s_oversized_count);
		
		//r
		theModel.addAttribute("dir1_r_oversized_tot", dir1_r_oversized_tot);
		theModel.addAttribute("dir2_r_oversized_tot", dir2_r_oversized_tot);
		Float r_oversized_tot = dir1_r_oversized_tot + dir2_r_oversized_tot;
		theModel.addAttribute("r_oversized_tot", r_oversized_tot);
		
		theModel.addAttribute("dir1_r_oversized_count", dir1_r_oversized_count);
		theModel.addAttribute("dir2_r_oversized_count", dir2_r_oversized_count);
		Integer r_oversized_count = dir1_r_oversized_count + dir2_r_oversized_count;
		theModel.addAttribute("r_oversized_count", r_oversized_count);
		
		//c
		theModel.addAttribute("dir1_c_oversized_tot", dir1_c_oversized_tot);
		theModel.addAttribute("dir2_c_oversized_tot", dir2_c_oversized_tot);
		Float c_oversized_tot = dir1_c_oversized_tot + dir2_c_oversized_tot;
		theModel.addAttribute("c_oversized_tot", c_oversized_tot);
		
		theModel.addAttribute("dir1_c_oversized_count", dir1_c_oversized_count);
		theModel.addAttribute("dir2_c_oversized_count", dir2_c_oversized_count);
		Integer c_oversized_count = dir1_c_oversized_count + dir2_c_oversized_count;
		theModel.addAttribute("c_oversized_count", c_oversized_count);
		
		//p
		theModel.addAttribute("dir1_p_oversized_count", dir1_p_oversized_count);
		theModel.addAttribute("dir2_p_oversized_count", dir2_p_oversized_count);
		Integer p_oversized_count = dir1_p_oversized_count + dir2_p_oversized_count;
		theModel.addAttribute("p_oversized_count", p_oversized_count);
		
		//e
		theModel.addAttribute("dir1_e_oversized_count", dir1_e_oversized_count);
		theModel.addAttribute("dir2_e_oversized_count", dir2_e_oversized_count);
		Integer e_oversized_count = dir1_e_oversized_count + dir2_e_oversized_count;
		theModel.addAttribute("e_oversized_count", e_oversized_count);
		
		//row oversized
		Float row_oversized_tot = s_oversized_tot + r_hcm_mav_tot + c_oversized_tot;
		Integer row_oversized_count = s_oversized_count + r_oversized_count + c_oversized_count + p_oversized_count + e_oversized_count;
		theModel.addAttribute("row_hcm_mav_tot", row_oversized_tot);
		theModel.addAttribute("row_hcm_mav_count", row_oversized_count);
		
		//column totals
		Float dir1_s_tot = dir1_s_car_jeep_tot + dir1_s_lcv_tot + dir1_s_bus_truck_tot + dir1_s_3_axel_tot + dir1_s_hcm_mav_tot + dir1_s_oversized_tot; 
		Integer dir1_s_count = dir1_s_car_jeep_count + dir1_s_lcv_count + dir1_s_bus_truck_count + dir1_s_3_axel_count + dir1_s_hcm_mav_count + dir1_s_oversized_count;
		
		Float dir2_s_tot = dir2_s_car_jeep_tot + dir2_s_lcv_tot + dir2_s_bus_truck_tot + dir2_s_3_axel_tot + dir2_s_hcm_mav_tot + dir2_s_oversized_tot; 
		Integer dir2_s_count = dir2_s_car_jeep_count + dir2_s_lcv_count + dir2_s_bus_truck_count + dir2_s_3_axel_count + dir2_s_hcm_mav_count + dir2_s_oversized_count;
		
		Float s_tot = s_car_jeep_tot + s_lcv_tot + s_bus_truck_tot + s_3_axel_tot + s_hcm_mav_tot + s_oversized_tot;
		Integer s_count = s_car_jeep_count + s_lcv_count + s_bus_truck_count + s_3_axel_count + s_hcm_mav_count + s_oversized_count;
		
		//r
		Float dir1_r_tot = dir1_r_car_jeep_tot + dir1_r_lcv_tot + dir1_r_bus_truck_tot + dir1_r_3_axel_tot + dir1_r_hcm_mav_tot + dir1_r_oversized_tot; 
		Integer dir1_r_count = dir1_r_car_jeep_count + dir1_r_lcv_count + dir1_r_bus_truck_count + dir1_r_3_axel_count + dir1_r_hcm_mav_count + dir1_r_oversized_count;
		
		Float dir2_r_tot = dir2_r_car_jeep_tot + dir2_r_lcv_tot + dir2_r_bus_truck_tot + dir2_r_3_axel_tot + dir2_r_hcm_mav_tot + dir2_r_oversized_tot; 
		Integer dir2_r_count = dir2_r_car_jeep_count + dir2_r_lcv_count + dir2_r_bus_truck_count + dir2_r_3_axel_count + dir2_r_hcm_mav_count + dir2_r_oversized_count;
		
		Float r_tot = r_car_jeep_tot + r_lcv_tot + r_bus_truck_tot + r_3_axel_tot + r_hcm_mav_tot + r_oversized_tot;
		Integer r_count = r_car_jeep_count + r_lcv_count + r_bus_truck_count + r_3_axel_count + r_hcm_mav_count + r_oversized_count;
		
		//r
		Float dir1_c_tot = dir1_c_car_jeep_tot + dir1_c_lcv_tot + dir1_c_bus_truck_tot + dir1_c_3_axel_tot + dir1_c_hcm_mav_tot + dir1_c_oversized_tot; 
		Integer dir1_c_count = dir1_c_car_jeep_count + dir1_c_lcv_count + dir1_c_bus_truck_count + dir1_c_3_axel_count + dir1_c_hcm_mav_count + dir1_c_oversized_count;
		
		Float dir2_c_tot = dir2_c_car_jeep_tot + dir2_c_lcv_tot + dir2_c_bus_truck_tot + dir2_c_3_axel_tot + dir2_c_hcm_mav_tot + dir2_c_oversized_tot; 
		Integer dir2_c_count = dir2_c_car_jeep_count + dir2_c_lcv_count + dir2_c_bus_truck_count + dir2_c_3_axel_count + dir2_c_hcm_mav_count + dir2_c_oversized_count;
		
		Float c_tot = c_car_jeep_tot + c_lcv_tot + c_bus_truck_tot + c_3_axel_tot + c_hcm_mav_tot + c_oversized_tot;
		Integer c_count = c_car_jeep_count + c_lcv_count + c_bus_truck_count + c_3_axel_count + c_hcm_mav_count + c_oversized_count;
			
		//p
		Integer dir1_p_count = dir1_p_car_jeep_count + dir1_p_lcv_count + dir1_p_bus_truck_count + dir1_p_3_axel_count + dir1_p_hcm_mav_count + dir1_p_oversized_count;
		Integer dir2_p_count = dir2_p_car_jeep_count + dir2_p_lcv_count + dir2_p_bus_truck_count + dir2_p_3_axel_count + dir2_p_hcm_mav_count + dir2_p_oversized_count;
		Integer p_count = p_car_jeep_count + p_lcv_count + p_bus_truck_count + p_3_axel_count + p_hcm_mav_count + p_oversized_count;
		
		//e
		Integer dir1_e_count = dir1_e_car_jeep_count + dir1_e_lcv_count + dir1_e_bus_truck_count + dir1_e_3_axel_count + dir1_e_hcm_mav_count + dir1_e_oversized_count;
		Integer dir2_e_count = dir2_e_car_jeep_count + dir2_e_lcv_count + dir2_e_bus_truck_count + dir2_e_3_axel_count + dir2_e_hcm_mav_count + dir2_e_oversized_count;
		Integer e_count = e_car_jeep_count + e_lcv_count + e_bus_truck_count + e_3_axel_count + e_hcm_mav_count + e_oversized_count;
		
		theModel.addAttribute("dir1_s_tot", dir1_s_tot);
		theModel.addAttribute("dir1_r_tot", dir1_r_tot);
		theModel.addAttribute("dir1_c_tot", dir1_c_tot);
		
		theModel.addAttribute("dir2_s_tot", dir2_s_tot);
		theModel.addAttribute("dir2_r_tot", dir2_r_tot);
		theModel.addAttribute("dir2_c_tot", dir2_c_tot);
		
		theModel.addAttribute("dir1_s_count", dir1_s_count);
		theModel.addAttribute("dir1_r_count", dir1_r_count);
		theModel.addAttribute("dir1_c_count", dir1_c_count);
		theModel.addAttribute("dir1_p_count", dir1_p_count);
		theModel.addAttribute("dir1_e_count", dir1_e_count);
		
		theModel.addAttribute("dir2_s_count", dir2_s_count);
		theModel.addAttribute("dir2_r_count", dir2_r_count);
		theModel.addAttribute("dir2_c_count", dir2_c_count);
		theModel.addAttribute("dir2_p_count", dir2_p_count);
		theModel.addAttribute("dir2_e_count", dir2_e_count);
		
		theModel.addAttribute("s_tot", s_tot);
		theModel.addAttribute("s_count", s_count);
		
		theModel.addAttribute("r_tot", r_tot);
		theModel.addAttribute("r_count", r_count);
		
		theModel.addAttribute("c_tot", c_tot);
		theModel.addAttribute("c_count", c_count);
		
		theModel.addAttribute("p_count", p_count);
		theModel.addAttribute("e_count", e_count);
		
		//row totals
		Integer car_jeep_count = s_car_jeep_count + r_car_jeep_count + c_car_jeep_count + p_car_jeep_count + e_car_jeep_count;
		Float car_jeep_tot = s_car_jeep_tot + r_car_jeep_tot + c_car_jeep_tot;
		
		Integer lcv_count = s_lcv_count + r_lcv_count + c_lcv_count + p_lcv_count + e_lcv_count;
		Float lcv_tot = s_lcv_tot + r_lcv_tot + c_lcv_tot;
		
		Integer bus_truck_count = s_bus_truck_count + r_bus_truck_count + c_bus_truck_count + p_bus_truck_count + e_bus_truck_count;
		Float bus_truck_tot = s_bus_truck_tot + r_bus_truck_tot + c_bus_truck_tot;
		
		Integer _3_axel_count = s_3_axel_count + r_3_axel_count + c_3_axel_count + p_3_axel_count + e_3_axel_count;
		Float _3_axel_tot = s_3_axel_tot + r_3_axel_tot + c_3_axel_tot;
		
		Integer hcm_mav_count = s_hcm_mav_count + r_hcm_mav_count + c_hcm_mav_count + p_hcm_mav_count + e_hcm_mav_count;
		Float hcm_mav_tot = s_hcm_mav_tot + r_hcm_mav_tot + c_hcm_mav_tot;
		
		Integer oversized_count = s_oversized_count + r_oversized_count + c_oversized_count + p_oversized_count + e_oversized_count;
		Float oversized_tot = s_oversized_tot + r_oversized_tot + c_oversized_tot;
		
		theModel.addAttribute("car_jeep_count", car_jeep_count);
		theModel.addAttribute("car_jeep_tot", car_jeep_tot);
		
		theModel.addAttribute("lcv_count", lcv_count);
		theModel.addAttribute("lcv_tot", lcv_tot);
		
		theModel.addAttribute("bus_truck_count", bus_truck_count);
		theModel.addAttribute("bus_truck_tot", bus_truck_tot);
		
		theModel.addAttribute("_3_axel_count", _3_axel_count);
		theModel.addAttribute("_3_axel_tot", _3_axel_tot);
		
		theModel.addAttribute("hcm_mav_count", hcm_mav_count);
		theModel.addAttribute("hcm_mav_tot", hcm_mav_tot);
		
		theModel.addAttribute("oversized_count", oversized_count);
		theModel.addAttribute("oversized_tot", oversized_tot);
		
		Float final_tot = car_jeep_tot + lcv_tot + bus_truck_tot + _3_axel_tot + hcm_mav_tot + oversized_tot;
		Integer final_count = car_jeep_count + lcv_count + bus_truck_count + _3_axel_count + hcm_mav_count + oversized_count;
		
		theModel.addAttribute("final_tot", final_tot);
		theModel.addAttribute("final_count", final_count);
		theModel.addAttribute("dir1", dir1);
		theModel.addAttribute("dir2", dir2);
		return "consolidated_report_table";
	}
}



















