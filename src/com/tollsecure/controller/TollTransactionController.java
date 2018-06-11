package com.tollsecure.controller;
//for barcode image
import java.awt.image.BufferedImage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tollsecure.entity.Cashup;
import com.tollsecure.entity.ConcessionVehicles;
import com.tollsecure.entity.Exempt;
import com.tollsecure.entity.FloatAmountDetails;
import com.tollsecure.entity.Lane;
import com.tollsecure.entity.Pass;
import com.tollsecure.entity.Shift;
import com.tollsecure.entity.ShiftTransaction;
import com.tollsecure.entity.TollConfig;
import com.tollsecure.entity.TollPlaza;
import com.tollsecure.entity.TollTransaction;
import com.tollsecure.entity.User;
import com.tollsecure.entity.VehicleTracking;
import com.tollsecure.service.CashupService;
import com.tollsecure.service.ConcessionVehiclesService;
import com.tollsecure.service.ExemptService;
import com.tollsecure.service.FloatAmountDetailsService;
import com.tollsecure.service.LaneService;
import com.tollsecure.service.PasService;
import com.tollsecure.service.PassService;
import com.tollsecure.service.ShiftService;
import com.tollsecure.service.TollConfigService;
import com.tollsecure.service.TollPlazaService;
import com.tollsecure.service.TollTransactionService;
import com.tollsecure.service.UserService;
import com.tollsecure.service.VehicleTrackingService;



@Controller
@RequestMapping("/tollTransaction")
public class TollTransactionController {

	//need to inject the TollTransaction Service 
	@Autowired
	private TollTransactionService tollTransactionService;
	
	@Autowired
	private ShiftService theShiftService;
	
	@Autowired
	private TollConfigService tollConfigService;
	
	@Autowired
	private ConcessionVehiclesService theConcessionVehiclesService;
	
	@Autowired
	private LaneService theLaneService;
	
	@Autowired
	private TollPlazaService theTollPlazaService;

	@Autowired
	private UserService theUserService;
	
	@Autowired
	private VehicleTrackingService theVehicleTrackingService;
	
	@Autowired
	private PassService thePassService;
	
	@Autowired
	private PasService thePasService;
	
	@Autowired
	private ExemptService theExemptService;
	
	@Autowired
	private FloatAmountDetailsService theFloatAmountDetailsService;
	
	@Autowired
	private CashupService theCashupService;
	
	//Bar-code image generator
	public void createImage(String image_name,String myString)  {
		try {
		Code128Bean code128 = new Code128Bean();
		code128.setHeight(15f);
		code128.setModuleWidth(0.3);
		code128.setQuietZone(10);
		code128.doQuietZone(true);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BitmapCanvasProvider canvas = new BitmapCanvasProvider(baos, "image/x-png", 300, BufferedImage.TYPE_BYTE_BINARY, false, 0);
		code128.generateBarcode(canvas, myString);
		canvas.finish();
		//write to png file "C:\\Users\\MIRITPC\\Desktop\\jas\\New folder\\" /home/anjan/Downloads/Eclipse_Workspaces/
		FileOutputStream fos = new FileOutputStream("C:\\TollSecure\\barCodes\\"+image_name);
		fos.write(baos.toByteArray());
		fos.flush();
		fos.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(">>>>>>>>>>>Barcode image file not generated due to: "+e);
		}
	}
	
	
	//gives the list of toll entries till f=date in a tabular form
	@GetMapping("/tollEntriesList")
	public String showTollEntries(Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		//getting session attribute
		//User userFromSession =  (User) session.getAttribute("userFromSession");
		
		//get the TollTransaction s from Service 
		List<TollTransaction> theTollTransactions =
				tollTransactionService.getTollTransactions();
		
		List<TollConfig> theTollConfigs =
				tollConfigService.getTollConfigs();
		
		//add the TollTransaction to the model
		theModel.addAttribute("tollTransactions", theTollTransactions);
		theModel.addAttribute("tollConfigs", theTollConfigs);
		
		return "toll_entries_list";
		
	}

	@GetMapping("/tollTransactionForm")
	public String showTollForm(@RequestParam("plazaId") Integer plazaId,@RequestParam("laneId") Integer laneId, Model theModel, HttpServletRequest  request, HttpSession session) {
		
		/**
		 * testing time taken
		 */
		//System.out.println(">>>>>>>>>Entered /tollTransactionForm method: "+new Date()+" >>>>>>>>>>>>>>>>>>");
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		//if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		//if user is operator just check if other user is using the lane and if not insert user_id into lane similarly remove when he logs out
		User theUser = (User) session.getAttribute("userFromSession");
		if (theUser.getUserRole().equals("Operator")) {
			
			//get all lanes for this Plaza
			List<Lane> theLane = theLaneService.getLaneFromLaneId(laneId); //this brings only one lane at index 0 which the user wants to get in
			
			//check if user_id field is filled with other user
			if (theLane.get(0).getUserId() == null || theLane.get(0).getUserId() == theUser.getUserId()) {
				
				//check if he is using any other lane if so release it and assign new one
				//get any lane which is associated with this user
				List<Lane> theOtherLanes = (List<Lane>) theLaneService.getLaneAssociatedWithUser(theUser.getUserId());
				
				/*if (theOtherLanes.size()>0) {
					//release all other lanes
					for (int i=0; i<theOtherLanes.size(); i++) {
						theOtherLanes.get(i).setUserId(null);
						theLaneService.saveLane(theOtherLanes.get(i));
					}
				}*/
				
				//now assign the user to the lane
				//theLane.get(0).setUserId(theUser.getUserId());
				//theLaneService.saveLane(theLane.get(0));
				
			} 
			
			else {
				//theModel.addAttribute("alreadyAssignedError", "Lane is filled");
				//return "redirect:/index/h0me";
			}
		}
		
		
		//getting ip address //for future use
		String ipAddr = request.getRemoteAddr();
		//System.out.println(ipAddr+"=>>>>>====");
		

		
		//create model attribute to bind the form data
		TollTransaction theTollTransaction = new TollTransaction();
		
		//for getting vehicle class list
		List<TollConfig> theTollConfigs = tollConfigService.getTollConfigs();
		
		//getting all unique journey types for inserting in journey type field
		List<String> uniqueJourneyTypes = tollConfigService.getUniqueJourneyTypes();
		
		//getting all the unique vehicle classes for drop down
		Map<String, String> uniqueVehicleClasses = tollConfigService.getUniqueVehicleClasses();
				
		//getting current shift
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String sCertDate = dateFormat.format(new Date());
		
		/**
		 * testing time taken
		 */
		//System.out.println(">>>>>>>>>Got the current shift: Time: "+new Date()+" >>>>>>>>>>>>>");
		
		
		//it returns shiftDesc_shift_id
		Shift currentShift = theShiftService.getShift(sCertDate, plazaId);
		
		if(currentShift == null) {
			theModel.addAttribute("shiftNotFoundError", "shifts not configured");
			return "redirect:/index/logout";
		}
		
		//manage logging off from front end also by sending when to log off
			String shiftEndTime = null;
		if (currentShift != null) {
			Date shiftStartTime1 = currentShift.getStartTime();
			Date shiftEndTime1 = currentShift.getEndTime();
			Date currentTime1 = new Date();
			String currentTime = dateFormat.format(currentTime1);
			

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (shiftStartTime1.before(shiftEndTime1)) { //normal shift
				Date today1 = new Date();
				String today = sdf.format(today1);

				shiftEndTime = today+" "+dateFormat.format(shiftEndTime1);
			} else { //day change shift
				//check whether current time is greater of lesser than start time
				SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
				Date cTime = null;
				try {
					cTime = stf.parse(currentTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (cTime.after(shiftStartTime1)) { //same day
					Date tomorrow1 = new Date(new Date().getTime()+(24*60*60*1000));
					String tomorrow = sdf.format(tomorrow1);
					shiftEndTime = tomorrow+" "+dateFormat.format(shiftEndTime1);
				} else { //next day
					Date yesterday1 = new Date(new Date().getTime());
					String todayle = sdf.format(yesterday1);
					shiftEndTime = todayle+" "+dateFormat.format(shiftEndTime1);
				}
				 
			}
		}
		
		theModel.addAttribute("shiftEndTime", shiftEndTime);
		
		/**
		 * testing time taken
		 */
		//System.out.println(">>>>>>>>> Calculated shift end time: "+new Date()+" >>>>>>>>>>>>>>>");
		
		//also check whether there is less then half an hour for shift end
		SimpleDateFormat forCheck = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date EndTimeForCheck = null;
		try {
			EndTimeForCheck = forCheck.parse(shiftEndTime);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		long remainingTime = (EndTimeForCheck.getTime() -  new Date().getTime())/1000;
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>remainingTime: "+remainingTime);
		
		//setting shift transaction id -- this is cancelled and is only used to check shift end
		String previousShiftDesc = (String) session.getAttribute("shiftDescFromSession");
		if (previousShiftDesc!=null && !previousShiftDesc.equals(currentShift.getShiftDesc())) {//shift changed
			//when ever shift changes he should definitely login again	
			return "redirect:/index/logout";
		} /*else if (previousShiftDesc == null) { //first tollTransaction
			//here we also should check if session is ended in middle in that case we should 
			//keep exact value
			//bring the list of elements where laneId and shift are same
			List<TollTransaction> tollTransacList = tollTransactionService.getTollTransactionsForCurrentShift(laneId, currentShift, new Date());
			if (tollTransacList != null && tollTransacList.size() != 0) {
				session.setAttribute("shiftTransactionId", tollTransacList.size()+1);
				System.out.println("shift transaction Id set to: "+tollTransacList.size()+" plus 1");
			}
			else session.setAttribute("shiftTransactionId", 1);
		} else if (previousShiftDesc.equals(currentShift.getShiftDesc())) {
			Integer previousShiftTransactionId = (Integer) session.getAttribute("shiftTransactionId");
			session.setAttribute("shiftTransactionId", previousShiftTransactionId+1);
		}
		*/
		
		session.setAttribute("shiftDescFromSession", currentShift.getShiftDesc());
		session.setAttribute("shiftIdFromSession", currentShift.getShiftId());
		
		//Also check whether the user who logged in is assigned to the same lane
		//get the user assigned to the lane, shift and date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<FloatAmountDetails> assignedUserDetails = theFloatAmountDetailsService.getSameDetailIfExist(sdf.format(new Date()), plazaId.toString(), laneId.toString(), currentShift.getShiftId().toString());
		
		
		if (assignedUserDetails.size()>0) { //some user is assigned go get his id
			Integer assignedUserId = assignedUserDetails.get(0).getUserId();
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>assignedUserId: "+assignedUserId+", currentUserID: "+theUser.getUserId());
			
			if (!assignedUserId.equals(theUser.getUserId())) { 
				//some other user logged into lane so log him out
				return "redirect:/index/logout";
			}
		} else {
			//user is using a lane where nobody is assigned
			return "redirect:/index/logout";
		}
		
		/**
		 * testing time taken
		 */
		//System.out.println(">>>>>>>>>>> Checked the asigned user to lane: "+new Date()+" >>>>>>>>>>>>>>>>>>>");
		
		//get the last transaction for current laneId and currentShift 
		TollTransaction lastTollTransaction = tollTransactionService.getLastTollTransaction(laneId, currentShift);

		//get last Transaction vehicleClass
		String lastTransactionVehicleClass = null;
		
		try {
			lastTransactionVehicleClass = tollConfigService.getLastTrollTransactionVehicleClass(lastTollTransaction.getVehicleClassId());
		} catch (Exception e) {
			
		}
		
		
		
		/**
		 * testing time taken
		 */
		//System.out.println(">>>>>>>>>>>>>> Set the last toll transaction: "+new Date()+" >>>>>>>>>>>>");
		
		//before saving check with end date of shift and if it exceeds do not save and redirect to logout
		
		
		theModel.addAttribute("remainingTime", remainingTime);
		theModel.addAttribute("shift", currentShift);
		theModel.addAttribute("tollTransaction",theTollTransaction);
		theModel.addAttribute("tollConfigs", theTollConfigs);
		theModel.addAttribute("uniqueJourneyTypes", uniqueJourneyTypes);
		theModel.addAttribute("uniqueVehicleClasses", uniqueVehicleClasses);
		theModel.addAttribute("lastTransaction", lastTollTransaction);
		theModel.addAttribute("lastTransactionVehicleClass",lastTransactionVehicleClass);
		
		System.out.println("-=-=-=-=-==>>>Last Toll Transaction: "+lastTollTransaction);
		
		//get lane and tollPlaza and send for use
		List<Lane> lane = theLaneService.getLaneFromLaneId(laneId);
		List<TollPlaza> tollPlaza = theTollPlazaService.getTollPlaza(plazaId);
		
		if(lane.size()!=0) theModel.addAttribute("laneName", lane.get(0).getLaneCode());
		if (tollPlaza.size()!=0) theModel.addAttribute("plazaName", tollPlaza.get(0).getTollPlazaName());
		theModel.addAttribute("laneId", laneId);
		theModel.addAttribute("plazaId", plazaId);
		
		//also send the cam streaming address
		String camStreamAddr = null;
		if (ipAddr.equals("10.0.2.27")) {
			camStreamAddr="ws://10.0.2.27:8081/cam/realmonitor";
		} else if (lane.get(0).getLaneCode().equals("L2")) {
			camStreamAddr="ws://192.168.0.2:8081/cam/Lane2";
		} else if (lane.get(0).getLaneCode().equals("L3")) {
			camStreamAddr="ws://192.168.0.2:8081/cam/Lane3";
		} else if (lane.get(0).getLaneCode().equals("L4")) {
			camStreamAddr="ws://192.168.0.2:8081/cam/L4";
		} else if (lane.get(0).getLaneCode().equals("L5")) {
			camStreamAddr="ws://192.168.0.2:8081/cam/L5";
		} else if (lane.get(0).getLaneCode().equals("L6")) {
			camStreamAddr="ws://192.168.0.2:8081/cam/L6";
		} else if (lane.get(0).getLaneCode().equals("L7")) {
			camStreamAddr="ws://192.168.0.2:8081/cam/L7";
		} else if (lane.get(0).getLaneCode().equals("L8")) {
			camStreamAddr="ws://192.168.0.2:8081/cam/L8";
		} else if (lane.get(0).getLaneCode().equals("L1")) {
			camStreamAddr="ws://192.168.0.2:8081/cam/L1";
		} else {
			camStreamAddr="ws://10.0.2.27:8081/cam/realmonitor";
		}
		
		theModel.addAttribute("camAddress", camStreamAddr);
		return "toll_transaction_form";
	}
	
	@GetMapping("/getRemainingTime")
	public String getRemainingTime(Model theModel, HttpSession session) {
		
		//getting current shift
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String sCertDate = dateFormat.format(new Date());
		
		//it returns shiftDesc_shift_id
		Shift currentShift = theShiftService.getShift(sCertDate, 1);
		
		//if(currentShift == null) {
		//	theModel.addAttribute("shiftNotFoundError", "shifts not configured");
		//	return "redirect:/index/logout";
		//}
		
		//manage logging off from front end also by sending when to log off
		String shiftEndTime = null;
		if (currentShift != null) {
			Date shiftStartTime1 = currentShift.getStartTime();
			Date shiftEndTime1 = currentShift.getEndTime();
			Date currentTime1 = new Date();
			String currentTime = dateFormat.format(currentTime1);
			

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (shiftStartTime1.before(shiftEndTime1)) { //normal shift
				Date today1 = new Date();
				String today = sdf.format(today1);

				shiftEndTime = today+" "+dateFormat.format(shiftEndTime1);
			} else { //day change shift
				//check whether current time is greater of lesser than start time
				SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
				Date cTime = null;
				try {
					cTime = stf.parse(currentTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (cTime.after(shiftStartTime1)) { //same day
					Date tomorrow1 = new Date(new Date().getTime()+(24*60*60*1000));
					String tomorrow = sdf.format(tomorrow1);
					shiftEndTime = tomorrow+" "+dateFormat.format(shiftEndTime1);
				} else { //next day
					Date yesterday1 = new Date(new Date().getTime());
					String todayle = sdf.format(yesterday1);
					shiftEndTime = todayle+" "+dateFormat.format(shiftEndTime1);
				}
				 
			}
		}
		
		theModel.addAttribute("shiftEndTime", shiftEndTime);
		
		//also check whether there is less then half an hour for shift end
		SimpleDateFormat forCheck = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date EndTimeForCheck = null;
		try {
			EndTimeForCheck = forCheck.parse(shiftEndTime);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		long remainingTime = (EndTimeForCheck.getTime() -  new Date().getTime())/1000;
		
		
		theModel.addAttribute("remainingTime", remainingTime);
		return "remaining_time";
	}
	
	@GetMapping("/saveTollTransaction")
	public String saveTollTransaction (HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "blank_page1";
		//if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		//first handle shift
		String previousShiftDesc = (String) session.getAttribute("shiftDescFromSession");
		
		//getting current shift
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String sCertDate = dateFormat.format(new Date());
				
		//it returns shiftDesc_shift_id
		Shift currentShift = theShiftService.getShift(sCertDate, 1); //2nd parameter is plaza id will be modified later
		
		//this happens if the request comes after shift ends
		if (previousShiftDesc!=null && !previousShiftDesc.equals(currentShift.getShiftDesc())) {//shift changed
			//when ever shift changes he should definitely login again	

			System.out.println("previousShiftDesc: "+previousShiftDesc+" currentShift: "+currentShift+">>>>>>>>>>>>>>");
			return "blank_page";
		}
		
		
		//declaring a TollTransaction object
		TollTransaction theTollTransaction = new TollTransaction();
		
		//Getting photo
		
		
		//getting parameters
		String ticketCode = request.getParameter("ticketCode");
		System.out.println("-=-=-=-============>>>>Ticket Code: "+ticketCode);

		//convert into upper case as we may need to search the vehicle id
		String vnum = request.getParameter("vehicleNumber").toUpperCase();
		SimpleDateFormat stf1 = new SimpleDateFormat("HH:mm:ss");
		String timeStamp = stf1.format(new Date());
		

		Float tollAmnt = Float.parseFloat(request.getParameter("tollAmt"));
		String cType = request.getParameter("concessionType");
		String jType = request.getParameter("journeyType");
		if (jType==null) jType = "S";
		String vehicleClassId = request.getParameter("vcid");
		String paymentMethod = request.getParameter("paymentMethod");
		//we are fixing those values
		String concessionPercent = request.getParameter("concession");
		String laneId1 = request.getParameter("laneId");
		
		//mostly javascript is not working for getting the ticket code
		if (ticketCode==null) {
			if(laneId1!=null) {
				List<Lane> lanee = theLaneService.getLaneFromLaneId(Integer.parseInt(laneId1)); 
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
				
				String da = sdf.format(new Date());
				String ti = stf.format(new Date());
				
				String[] strs1 = da.split("-");
				String[] strs2 = ti.split(":");

				ticketCode = lanee.get(0).getLaneCode()+strs1[0].substring(2, 4)+strs1[1]+strs1[2];
				ticketCode = ticketCode+strs2[0]+strs2[1]+strs2[2];
			}
		}
		
		Integer laneId = Integer.parseInt(laneId1);
		String plazaId1 = request.getParameter("plazaId");
		Integer plazaId = Integer.parseInt(plazaId1);
		String shiftDesc = (String)request.getSession().getAttribute("shiftDescFromSession");
		Integer shiftId = (Integer) request.getSession().getAttribute("shiftIdFromSession");
		User currentUser =  (User) request.getSession().getAttribute("userFromSession");
		Integer shiftTransactionId = (Integer) request.getSession().getAttribute("shiftTransactionId");
		//System.out.println("-=-=-=-======>>>>shift transaction id: "+shiftTransactionId);
		
		//Also check whether the user who logged in is assigned to the same lane
		//get the user assigned to the lane, shift and date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<FloatAmountDetails> assignedUserDetails = theFloatAmountDetailsService.getSameDetailIfExist(sdf.format(new Date()), plazaId1, laneId1, shiftId.toString());
		
		if (assignedUserDetails.size()>0) { //some user is assigned go get his id
			Integer assignedUserId = assignedUserDetails.get(0).getUserId();
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>assignedUserId: "+assignedUserId+", currentUserID: "+currentUser.getUserId());
			
			if (!assignedUserId.equals(currentUser.getUserId())) { 
				//some other user logged into lane so log him out
				return "blank_page";
			}
		} else {
			//user is using a lane where nobody is assigned
			return "blank_page";
		}
		
		
		//checking for errors
		//for vehicle number
		Pattern p = Pattern.compile("^[a-zA-Z]{2}[0-9]{1,2}[a-zA-Z]{1,3}[0-9]{4}$");
		Matcher m = p.matcher(vnum);
		

		String errors = "";
		if(!m.matches()) {
			errors = errors+"Invalid Vehicle Number";
		}
		if(cType=="") {
			errors = errors+"\nInvalid Concession Type";
		} 
		
		if (errors != "") {
			theModel.addAttribute("laneId", laneId);
			theModel.addAttribute("plazaId", plazaId);
			theModel.addAttribute("errors", errors);
			return "toll_transaction_form";
		}
				
		//setting data into object
		theTollTransaction.setTicketCode(ticketCode);
		theTollTransaction.setVehicleNumber(vnum);
		theTollTransaction.setTollAmt(tollAmnt);
		theTollTransaction.setTransactionTimeStamp(new Date());
		theTollTransaction.setConcessionType(cType);
		theTollTransaction.setJourneyType(jType);
		theTollTransaction.setVehicleClassId(Integer.parseInt(vehicleClassId));
		theTollTransaction.setPaymentMethod(paymentMethod);
		theTollTransaction.setLaneId(laneId);
		theTollTransaction.setTollPlazaId(plazaId);

		
		//also save the image
		//create a folder for storing images
		String filename = null;
		BufferedWriter bw = null;
		String today = null;
		FileWriter fw = null;
		        
		today = new SimpleDateFormat("yyyyMMdd").format(new Date());
		        
		//also add lane and shift to today
		today = today+shiftDesc+""+ticketCode.substring(0,2);
		       
		//File file = new File(request.getSession().getServletContext().getRealPath("/")+"WebContent/resources/vehicleImages/"+today);
		//File file = new File("/home/anjan/test/"+today);
		//File file = new File("newFolder");
		//System.out.println("=-=-=>>>"+request.getSession().getServletContext().getRealPath("/")+"WebContent/resources/vehicleImages/"+today);
		//File file = new File("/opt/apache-tomcat-8.0.45/webapps/SeoniProject/resources/vehicleImages/"+today);
		
		//toggle between these two
		//File file = new File("C:\\Users\\Admin\\Pictures\\vehicleImages\\"+today);
		
		File file = new File("C:\\TollSecure\\vehicleImages\\"+today);
		
		try {
			file.mkdir();
		    System.out.println(file.getAbsolutePath() +" in the try==============================");
		} catch (Exception e){
		   	System.out.println(e+"-=-=-=-=----->>>>");
		}
		        
		if (!file.exists()) {
		    if (file.mkdir()) {
		        System.out.println("Directory is created!---------------------------------");
		    } else {
		        System.out.println("Failed to create directory!----------------------------");
		    }
		}
		        
		//filename = request.getSession().getServletContext().getRealPath("")+"WebContent/resources/vehicleImages/"+today+"/"+vnum+timeStamp+".jpeg";
		//filename = "/home/anjan/Downloads/Eclipse_Workspaces/Chad_Course/SeoniProject/WebContent/resources/vehicleImages/"+today+"/"+vnum+timeStamp+".jpeg";
		        
		//String filePath = request.getSession().getServletContext().getRealPath("/")+"WebContent/resources/vehicleImages/"+today+"/"+vnum+timeStamp+".jpeg";
		//calling curl from java
		//String command = "curl -o "+ filename+" --user admin:123 --request GET http://10.0.0.25/cgi-bin/snapshot.cgi?channel=1";
		        
		//String command = "curl -o /home/anjan/Downloads/Eclipse_Workspaces/Chad_Course/SeoniProject/WebContent/resources/vehicleImages"+today+"/"+vnum+""+timeStamp+".jpeg --user admin:123 --request GET http://10.0.0.25/cgi-bin/snapshot.cgi?channel=1";
		       
		//String command = "curl -o /home/anjan/Downloads/Eclipse_Workspaces/Chad_Course/SeoniProject/WebContent/resources/vehicleImages/"+today+"/"+vnum+""+timeStamp+".jpeg  --user admin:123 --request GET http://10.0.0.25/cgi-bin/snapshot.cgi?channel=1";
		        
		//String command = "curl -o /opt/apache-tomcat-8.0.45/webapps/SeoniProject/resources/vehicleImages/"+today+"/"+ticketCode+".jpeg  --user admin:123 --request GET http://10.0.0.25/cgi-bin/snapshot.cgi?channel=1";
		
		String command = null;
		
		Integer vcid = Integer.parseInt(vehicleClassId);
		String vehicleClass = null;
		if (vcid != null) vehicleClass = tollConfigService.getLastTrollTransactionVehicleClass(vcid);
		String ext = null;
		if (vehicleClass != null) {
			if (vehicleClass.equals("CAR/ JEEP")) {
				ext = "cj";
			} else if (vehicleClass.equals("BUS/ TRUCK")) {
				ext = "bt";
			} else if (vehicleClass.equals("LCV")) {
				ext = "lc";
			} else if (vehicleClass.equals("3 AXEL")) {
				ext = "3a";
			} else if (vehicleClass.equals("HCM/ MAV")) {
				ext = "hm";
			} else if (vehicleClass.equals("OVERSIZED")) {
				ext = "os";
			}
		}
		
		String imageAddress = null;
		if (vehicleClass==null) {
			imageAddress = today+"/"+ticketCode+".jpeg";
		}
		else {
			imageAddress = today+"/"+ticketCode+"_"+ext+".jpeg";
			ticketCode = ticketCode+"_"+ext;
		}

		theTollTransaction.setImageAddress(imageAddress);
		
		//To know whether Image has been captured
		theTollTransaction.setImageCaptured("Y");
		
		//give the camera address based on the lane
		if (ticketCode.substring(0,2).equals("L2")) {
			command = "curl -o C:\\TollSecure\\vehicleImages\\"+today+"\\"+ticketCode+".jpeg  --user admin2:admin2 --request GET http://192.168.0.150/cgi-bin/snapshot.cgi?channel=1";
		} else if (ticketCode.substring(0,2).equals("L3")) {
			command = "curl -o C:\\TollSecure\\vehicleImages\\"+today+"\\"+ticketCode+".jpeg  --user admin2:admin2 --request GET http://192.168.0.151/cgi-bin/snapshot.cgi?channel=1";
		} else if (ticketCode.substring(0,2).equals("L4")) {
			command = "curl -o C:\\TollSecure\\vehicleImages\\"+today+"\\"+ticketCode+".jpeg  --user admin:admin123 --request GET http://192.168.0.122/cgi-bin/snapshot.cgi?channel=1";
		} else if (ticketCode.substring(0,2).equals("L5")) {
			command = "curl -o C:\\TollSecure\\vehicleImages\\"+today+"\\"+ticketCode+".jpeg  --user admin:admin123 --request GET http://192.168.0.121/cgi-bin/snapshot.cgi?channel=1";
		} else if (ticketCode.substring(0,2).equals("L6")) {
			command = "curl -o C:\\TollSecure\\vehicleImages\\"+today+"\\"+ticketCode+".jpeg  --user admin:admin123 --request GET http://192.168.0.113/cgi-bin/snapshot.cgi?channel=1";
		} else if (ticketCode.substring(0,2).equals("L7")) {
			command = "curl -o C:\\TollSecure\\vehicleImages\\"+today+"\\"+ticketCode+".jpeg  --user admin:admin123 --request GET http://192.168.0.112/cgi-bin/snapshot.cgi?channel=1";
		} 
		
		//use this for local computer
		//command = "curl -o C:\\TollSecure\\vehicleImages\\"+today+"\\"+ticketCode+".jpeg  --user admin:123 --request GET http://10.0.2.25/cgi-bin/snapshot.cgi?channel=1";
//		command = "curl -o C:\\TollSecure\\vehicleImages\\"+today+"\\"+ticketCode+".jpeg  --user anjan:anjan123 --request GET http://10.0.0.89/cgi-bin/snapshot.cgi?channel=2";

		//Bar code generator code below
		//keep it before saving because it stops saving in case of exception
		createImage(vnum+".png", vnum);
		File barcodeFile = new File("C:\\TollSecure\\barCodes\\"+vnum+".png");
		System.out.println(">>>>>>>>Barcode File length: "+barcodeFile.length());
		System.out.println(">>>>>>>>Barcode File length: ==0"+(barcodeFile.length()==0));
		if (!barcodeFile.exists() || barcodeFile.length()==0)
			try {
				TimeUnit.SECONDS.sleep(2);
				System.out.println(">>>>Waiting for 2 seconds");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println(">>>>exception at Waiting for 2 seconds");
			}
		if (!barcodeFile.exists() || barcodeFile.length()==0)
			try {
				TimeUnit.SECONDS.sleep(2);
				System.out.println(">>>>Waiting for 2 seconds");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println(">>>>exception at Waiting for 2 seconds");
			}
		if (!barcodeFile.exists() || barcodeFile.length()==0)
			try {
				TimeUnit.SECONDS.sleep(2);
				System.out.println(">>>>Waiting for 2 seconds");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println(">>>>exception at Waiting for 2 seconds");
			}
		
		
		/**
		 * This is commented in case of testing if there is no camera installed especially in Linux
		 */
		if (!ticketCode.substring(0,2).equals("L1") && !ticketCode.substring(0,2).equals("L8")) { //lame 1 and lane 8 do not have cameras
			try {
				File imageFile =new File("C:\\TollSecure\\vehicleImages\\"+today+"\\"+ticketCode+".jpeg");
				//only try 3 times
				int count=0;
				do {
					//if file already exists delete it
					if(imageFile.exists()) {
						if(imageFile.delete()) System.out.println("Image File Deleted as the size is less than minimum>>>>>>>>");
						else System.out.println("Failed to delete the corrupted Image file>>>>>>>>>>>>>>>>>");
					}
					Process child = Runtime.getRuntime().exec(command);
					System.out.println(command+"===Command executed: ===============");
					//wait for 2 seconds
					TimeUnit.SECONDS.sleep(2);
					imageFile =new File("C:\\TollSecure\\vehicleImages\\"+today+"\\"+ticketCode+".jpeg");
					System.out.println("ImageFileLength=============="+imageFile.length());
					count++; //third time we should break the loop even if the photo is not clicked
					if (count>=3) break;
				} while(imageFile.length()< 100); //if the image size is less than 100 bytes (minimum size of jpeg is 125 bytes)
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("stack trace: =======================");
				System.out.println(command+"===Command not executed: ===============");
				e.printStackTrace();
				System.out.println(e+"-=-=-=-=>>>");
				//To know whether Image has been captured
				theTollTransaction.setImageCaptured("N");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			//To know whether Image has been captured
			theTollTransaction.setImageCaptured("N");
		}
		
		/**
		 * camera code ends here
		 */
		
		
		//Image saving test
		/**
		 * https://dzone.com/articles/how-load-or-save-image-using
		 */
		//File imageFile = new File("/home/anjan/Downloads/lunch.jpg");
		File imageFile =new File("C:\\TollSecure\\vehicleImages\\"+today+"\\"+ticketCode+".jpeg");
		byte[] bImageFile = new byte[(int) imageFile.length()];
		//FileInputStream fileInputStream = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(imageFile);
            fileInputStream.read(bImageFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	//try {
				//fileInputStream.close();
			//} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			//}
        }
		theTollTransaction.setImageBlob(bImageFile);
		//this is added for memory leakage problem
		//bImageFile = null;
		//System.gc();
		
		//adding shiftId and shiftDesc to transaction
		theTollTransaction.setShiftDescription(shiftDesc);
		theTollTransaction.setShiftId(shiftId);
		ShiftTransaction shiftTransaction = (ShiftTransaction) session.getAttribute("shiftTransactionFromSession");
		theTollTransaction.setShiftTransactionId(shiftTransaction.getShiftTransactionId());
		
		theTollTransaction.setCreateTimeStamp(new Date());
		theTollTransaction.setCreateUserID(currentUser.getUserId());

		
		
		if (concessionPercent !=null && !concessionPercent.equals("null")) {
			//get last record and insert into T_CONCESSION_VEHICLES
			//take concession percent from concessionPercent string
			TollTransaction lastTollTransaction = theTollTransaction;
			ConcessionVehicles theConcessionVehicle = new ConcessionVehicles();
			theConcessionVehicle.setVehicleNumber(lastTollTransaction.getVehicleNumber());
			theConcessionVehicle.setConcessionCategory(lastTollTransaction.getConcessionType());
			theConcessionVehicle.setTollPlazaId(plazaId);
			//Float cpct = Float.parseFloat(concessionPercent.substring(0,concessionPercent.length()));
			//theConcessionVehicle.setConcessionPct(cpct);
			//if concession amount is how much is deducted
			//Float camt = (lastTollTransaction.getTollAmt()/(1-(cpct/100))) - lastTollTransaction.getTollAmt();
			//if it is amount in toll entries just keep it lastTollTransaction.getTollAmt()
			//theConcessionVehicle.setConcessionAmt(camt);
			theConcessionVehicle.setCreateTimeStamp(new Date());
			theConcessionVehicle.setCreateUserId(lastTollTransaction.getCreateUserID());
			
			theConcessionVehiclesService.saveVehicle(theConcessionVehicle);
			
			//get the last concession vehicle to keep it in the tollTransaction table
			ConcessionVehicles concessionVehicle = theConcessionVehiclesService.getLastRecord(vnum);
			
			System.out.println(">>>>>>>>>>LastConcessionVehicle: "+concessionVehicle);
			
			//set the concession id to tollTransaction
			theTollTransaction.setConcessionId(concessionVehicle.getConcessionId());
		}
		
		//return "redirect:/tollTransaction/tollEntriesList";
		//return "redirect:/tollTransaction/tollTransactionForm?laneId="+laneId+"&&plazaId="+plazaId;
		
		
		//also if it is pass type and non local and if its trip validity and trip count are not nulls 
		//update it
		if (theTollTransaction.getConcessionType().equals("Pass")) {
			//get the pass for that vehicle number
			Pass thePass = thePassService.lastPassForTheVehicle(theTollTransaction.getVehicleNumber());
			
			//set the pass_id in the toll transaction table
			theTollTransaction.setPassId(thePass.getPassId());
			
			if (thePass != null && thePass.getPassType().equals("MONTHLY") && thePass.getTripValidity()!=null && thePass.getTripCount()!=null) {
				thePass.setTripCount((thePass.getTripCount()+1));
				thePasService.saveOrUpdate(thePass);
			}
		}
		
		//get the exempt type
		String exemptType = request.getParameter("exemptType");
		if (!exemptType.equals("")) {
			
			//insert vehicle details into exempt table
			Exempt exempt = new Exempt();
			exempt.setTollPlazaId(plazaId);
			exempt.setCreateTimeStamp(new Date());
			exempt.setCreateUserId(userFromSession.getUserId());
			exempt.setExemptType(exemptType);
			exempt.setVehicleNumber(theTollTransaction.getVehicleNumber());
			
			//save the exempt
			theExemptService.saveOrUpdate(exempt);
			
			//get the last saved exempt for the vehicle number
			Exempt lastExempt = theExemptService.getLastExempt(theTollTransaction.getVehicleNumber());
			theTollTransaction.setExemptId(lastExempt.getExemptId());
		}
		
		
		//save the tollTransaction using our service
		tollTransactionService.saveTollTransaction(theTollTransaction);
		
		
		//now get the inserted transaction for receipt printing
		//get the last transaction of the lane
//		TollTransaction savedOne = tollTransactionService.getTheSavedOne(vnum, laneId);
		TollTransaction savedOne = theTollTransaction;
		
		//get lane from lane id
		List<Lane> lane = theLaneService.getLaneFromLaneId(savedOne.getLaneId());
		String laneCode = null;
		if (lane.size()>0)laneCode = lane.get(0).getLaneCode();
		
		//also save it in vehicle tracking table
		VehicleTracking vehicleTracking = theVehicleTrackingService.getVehicleTrack(vnum);
		if (vehicleTracking!=null) {
			//update it
			vehicleTracking.setJourneyType(jType);
			System.out.println("-=-=-=-=====>>>jType-=-=-=====>>>"+jType+" jType");
			vehicleTracking.setLaneDirection(lane.get(0).getLaneDirection());
			vehicleTracking.setModificationTimeStamp(new Date());
			vehicleTracking.setModifiedUserId(userFromSession.getUserId());
			theVehicleTrackingService.saveVehicleTrack(vehicleTracking);
		} else {
			//insert it
			VehicleTracking newVehicleTracking = new VehicleTracking();
			newVehicleTracking.setJourneyType(jType);
			System.out.println("-=-=-=-=====>>>jType-=-=-=====>>>"+jType+" jType");
			newVehicleTracking.setVehicleNumber(vnum);
			newVehicleTracking.setLaneDirection(lane.get(0).getLaneDirection());
			newVehicleTracking.setCreateTimeStamp(new Date());
			newVehicleTracking.setCreateUserId(userFromSession.getUserId());
			newVehicleTracking.setVehicleClassId(Integer.parseInt(vehicleClassId));
			theVehicleTrackingService.saveVehicleTrack(newVehicleTracking);
		}
		
		//get vehicle class
		//String vehicleClass = tollConfigService.getLastTrollTransactionVehicleClass(savedOne.getVehicleClassId());
		
		///go to the receipt
		theModel.addAttribute("savedOne", savedOne);
		theModel.addAttribute("laneCode", laneCode);
		theModel.addAttribute("vehicleClass", vehicleClass);
		
		//bar code image address
		theModel.addAttribute("barcodeImage", "/barCodes/"+vnum+".png");
		
		return "receipt";
	}
	
	@GetMapping("/checkTheShiftEnded")
	public String checkTheShiftEnded(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//first handle shift
		String previousShiftDesc = (String) session.getAttribute("shiftDescFromSession");
		
		//getting current shift
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String sCertDate = dateFormat.format(new Date());
				
		//it returns shiftDesc_shift_id
		Shift currentShift = theShiftService.getShift(sCertDate, 1); //2nd parameter is plaza id will be modified later
		
		//this happens if the request comes after shift ends
		if (previousShiftDesc!=null && previousShiftDesc.equals(currentShift.getShiftDesc())) {//shift changed
			//when ever shift changes he should definitely login again	

			return "blank_page1";
		} else {
			return "shift_end_page";
		}
	}
	
	@GetMapping("/rate")
	public String getTollAmounts(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "blank_page1";
		//if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		String vClass = request.getParameter("vehicle");
		String jType = request.getParameter("jType");
		Integer plazaId = Integer.parseInt(request.getParameter("plazaId"));
	
		HashMap<String, String> rateFromDb = tollConfigService.getRate(vClass, jType, plazaId, new Date());
		
		
		System.out.println("==-=-=-=-=->>>rate from db: "+rateFromDb);
		if(rateFromDb==null) {
			theModel.addAttribute("rateFromDb", null);
		} else {
			theModel.addAttribute("rateFromDb", rateFromDb);
		}

		return "rate";
	}
	
	//this is for rate in tollPlaza configuration
	@GetMapping("/rate1")
	public String getTollAmounts1(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		//if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		String vClass = request.getParameter("vehicle");
		String jType = request.getParameter("jType");
		Integer plazaId = Integer.parseInt(request.getParameter("plazaId"));
		String startDate = request.getParameter("sDate");
		String endDate = request.getParameter("eDate");
	
		HashMap<String, String> rateFromDb = tollConfigService.getRate(vClass, jType, plazaId, startDate, endDate);
		
		
		System.out.println("==-=-=-=-=->>>rate from db: "+rateFromDb);
		if(rateFromDb==null) {
			theModel.addAttribute("rateFromDb", null);
		} else {
			theModel.addAttribute("rateFromDb", rateFromDb);
		}

		return "rate";
	}
	
	//this is used for cashup but later this is useless because of the advent of floatAmountDescription table
	@GetMapping("/userId") 
	public String getUserId(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		//handling session
		//User userFromSession = (User) session.getAttribute("userFromSession");
		if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		String plazaId = request.getParameter("plazaId");
		String laneId = request.getParameter("laneId");
		String checkDate = request.getParameter("checkDate");
		String shiftDesc = request.getParameter("shiftDesc");
		
		//get the shift using plazaId shift desc
		Shift theShift = theShiftService.getShiftFromDesc(plazaId, shiftDesc);
		
		if (theShift==null) {
			theModel.addAttribute("shiftError", "Shift invalid for that date");
			return "redirect:/cashup/declareCash";
		}
		
		//get the start and end times
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat hr = new SimpleDateFormat("HH");
		
		Date stTime = theShift.getStartTime();
		Date edTime = theShift.getEndTime();
		
		Integer stHr = Integer.parseInt(hr.format(stTime));
		Integer edHr = Integer.parseInt(hr.format(edTime));
		
		String stDateTime = checkDate+" "+stf.format(stTime);
		String edDateTime = null;
		if (stHr>edHr) {
			Date checkDate1=null;
			try {
				checkDate1 = sdf.parse(checkDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Date checkNextDate1 = new Date(checkDate1.getTime()+(1000 * 60 * 60 * 24));
			String checkNextDate = sdf.format(checkNextDate1);
			edDateTime = checkNextDate+" "+stf.format(edTime);
		} else {
			edDateTime = checkDate+" "+stf.format(edTime);
		}
		
		//we know checking date and shift start time and end time and lane id
		//get a random user id and name from shift
		HashMap<String, String> userIdAndUserFNLNames = tollTransactionService.getUserIdAndFNLNames(stDateTime, edDateTime, laneId); 
		
		theModel.addAttribute("userIdAndUserFNLNames",userIdAndUserFNLNames);
		
		//get the lane 
		return "user";
	}
	/* Important note:
	 * in case of manual entries we are not storing shiftTransactionid (if we want we can do it later)
	 * */
	@GetMapping("/manualEntries")
	public String manualEntryTransactionForm(Model theModel, HttpSession session, HttpServletRequest request) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		//send all tollPlazas and lanes
		List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
		
		if (allTollPlazas.size()==0) {
			theModel.addAttribute("error","Please add a TollPlaza first");
			return "redirect:/tollPlaza/showFormForAdd";
		}
		
		List<Lane> allLanes = new ArrayList<Lane>();
		List<Shift> allShifts = new ArrayList<Shift>();
		
		if (allTollPlazas.size()!=0) {
			allLanes = theLaneService.getAllLanes(allTollPlazas.get(0).getTollPlazaId());
			allShifts = theShiftService.getAllShifts(allTollPlazas.get(0).getTollPlazaId());
		}

		String message = request.getParameter("message");
		
		theModel.addAttribute("allTollPlazas", allTollPlazas);
		theModel.addAttribute("allLanes", allLanes);
		theModel.addAttribute("allShifts", allShifts);
		theModel.addAttribute("message", message);
		
		return "manual_transaction_entry";
	}
	
	
	@PostMapping("/saveManualTransaction")
	public String savingManuakEntry(HttpServletRequest request, Model theModel, HttpSession session) {

		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		//getting all the parameters
		String tollPlazaId1 = request.getParameter("plazaId");
		Integer tollPlazaId = Integer.parseInt(tollPlazaId1);
		Integer laneId = Integer.parseInt(request.getParameter("laneId")); 
		String transactionDate1 = request.getParameter("entryDate");
		Date transactionTimeStamp = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			transactionTimeStamp = sdf.parse(transactionDate1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//shiftTransactionId -- left it as i don't know what it is
		String vehicleNumber = request.getParameter("vehicleNumber").toUpperCase(); 
		User theUser = (User) request.getSession().getAttribute("userFromSession");
		Integer userId = theUser.getUserId();
		String shiftId1 = request.getParameter("shiftId");
		Integer shiftId = null;
		String shiftDesc = null;
		if (shiftId1 != null) {
			shiftId = Integer.parseInt(shiftId1);
			Shift shift = theShiftService.getShiftFromId(shiftId);
			shiftDesc = shift.getShiftDesc();
		}
		Integer vehicleClassId = Integer.parseInt(request.getParameter("vcid"));
		String journeyType = request.getParameter("journeyType");
		String concessionType = request.getParameter("concessionType");
		Float tollAmt = Float.parseFloat(request.getParameter("tollAmt"));
		String paymentMethod = request.getParameter("paymentMethod");
		String concessionPercent = request.getParameter("concession");
		
		//checking for errors
		//for vehicle number
		Pattern p = Pattern.compile("^[a-zA-Z]{2}[0-9]{1,2}[a-zA-Z]{1,3}[0-9]{4}$");
		Matcher m = p.matcher(vehicleNumber);
			
		String errors = "";
		if(!m.matches()) {
			errors = errors+"Invalid Vehicle Number";
		}
		
		if(concessionType=="") {
			errors = errors+"\nInvalid Concession Type";
		} 
		
		if (errors != "") {
			theModel.addAttribute("laneId", laneId);
			theModel.addAttribute("plazaId", tollPlazaId);
			theModel.addAttribute("errors", errors);
			return "manual_transaction_entry";
		}
		
		
		//set all values to tollTransaction
		
		//declaring a TollTransaction object
		TollTransaction theTollTransaction = new TollTransaction();
		theTollTransaction.setTollPlazaId(tollPlazaId);
		theTollTransaction.setTransactionDate(transactionTimeStamp);
		theTollTransaction.setTransactionTimeStamp(transactionTimeStamp);
		theTollTransaction.setVehicleNumber(vehicleNumber);
		theTollTransaction.setLaneId(laneId);
		theTollTransaction.setCreateUserID(userId); 
		theTollTransaction.setShiftId(shiftId);
		theTollTransaction.setShiftDescription(shiftDesc);
		theTollTransaction.setCreateTimeStamp(new Date());
		theTollTransaction.setVehicleClassId(vehicleClassId);
		theTollTransaction.setJourneyType(journeyType);
		theTollTransaction.setConcessionType(concessionType);
		theTollTransaction.setTollAmt(tollAmt);
		theTollTransaction.setPaymentMethod(paymentMethod);
		
		//save the tollTransaction using our service
		tollTransactionService.saveTollTransaction(theTollTransaction);
				
		//if there is some concession percentage then save in the concession table also
		if (concessionPercent != "") {
			
			
			//get last record and insert into T_CONCESSION_VEHICLES
			//take concession percent from concessionPercent string
			TollTransaction lastTollTransaction = theTollTransaction;
			ConcessionVehicles theConcessionVehicle = new ConcessionVehicles();
			theConcessionVehicle.setVehicleNumber(lastTollTransaction.getVehicleNumber());
			theConcessionVehicle.setConcessionCategory(lastTollTransaction.getConcessionType());
			theConcessionVehicle.setTollPlazaId(tollPlazaId);
			Float cpct = Float.parseFloat(concessionPercent.substring(0,concessionPercent.length()));
			theConcessionVehicle.setConcessionPct(cpct);
			//if concession amount is how much is deducted
			Float camt = (lastTollTransaction.getTollAmt()/(1-(cpct/100))) - lastTollTransaction.getTollAmt();
			//if it is amount in toll entries just keep it lastTollTransaction.getTollAmt()
			theConcessionVehicle.setConcessionAmt(camt);
			theConcessionVehicle.setCreateTimeStamp(new Date());
			theConcessionVehicle.setCreateUserId(lastTollTransaction.getCreateUserID());
			
			theConcessionVehiclesService.saveVehicle(theConcessionVehicle);
		}
		
		
		//get lane from lane id
		List<Lane> lane = theLaneService.getLaneFromLaneId(laneId);
		
		//also save it in vehicle tracking table
		VehicleTracking vehicleTracking = theVehicleTrackingService.getVehicleTrack(vehicleNumber);
		if (vehicleTracking!=null) {
			//update it
			vehicleTracking.setJourneyType(journeyType);
			vehicleTracking.setLaneDirection(lane.get(0).getLaneDirection());
			vehicleTracking.setModificationTimeStamp(new Date());
			vehicleTracking.setModifiedUserId(userFromSession.getUserId());
			theVehicleTrackingService.saveVehicleTrack(vehicleTracking);
		} else {
			//insert it
			VehicleTracking newVehicleTracking = new VehicleTracking();
			newVehicleTracking.setJourneyType(journeyType);
			newVehicleTracking.setVehicleNumber(vehicleNumber);
			newVehicleTracking.setLaneDirection(lane.get(0).getLaneDirection());
			newVehicleTracking.setCreateTimeStamp(new Date());
			newVehicleTracking.setCreateUserId(userFromSession.getUserId());
			newVehicleTracking.setVehicleClassId(vehicleClassId);
			theVehicleTrackingService.saveVehicleTrack(newVehicleTracking);
		}
		
		//return "redirect:/tollTransaction/tollEntriesList";
		theModel.addAttribute("message", "Transaction Saved");
		return "redirect:/tollTransaction/manualEntries?laneId="+laneId+"&&plazaId="+tollPlazaId;
	}
	
	@RequestMapping("/cancelTicket")
	public String cancelTicket(HttpSession session, HttpServletRequest request, Model theModel) {
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		String error = request.getParameter("error");
		theModel.addAttribute("error", error);

		String message = request.getParameter("message");
		theModel.addAttribute("message", message);
		
		//if it is coming from vehicle search
		String transactionId = request.getParameter("transactionId");
		if (transactionId!=null) {
			TollTransaction tollTransaction = tollTransactionService.getTollTransactionFromId(transactionId);

			//get the lane code for the laneId
			List<Lane> lane = theLaneService.getLaneFromLaneId(tollTransaction.getLaneId()); 
			
			//get the user name from userId
			List<User> user = theUserService.getUserFromId(tollTransaction.getCreateUserID());
			
			
			
			if (tollTransaction!=null) {
				theModel.addAttribute("tollTransaction", tollTransaction);
				theModel.addAttribute("lane", lane.get(0));
				theModel.addAttribute("user", user.get(0));
			}
		}
		
		return "cancel_transaction";
	}
	
	@GetMapping("/searchTicket")
	public String searchTicket(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
				
		
		String ticketNumber = request.getParameter("ticketNo");
		TollTransaction ticket = tollTransactionService.searchTicket(ticketNumber);
		
		if (ticket==null) return "ticket";
		
		//get the lane code for the laneId
		List<Lane> lane = theLaneService.getLaneFromLaneId(ticket.getLaneId()); 
		
		//get the user name from userId
		List<User> user = theUserService.getUserFromId(ticket.getCreateUserID());
		
		theModel.addAttribute("ticket", ticket);
		theModel.addAttribute("lane", lane.get(0));
		theModel.addAttribute("user", user.get(0));
		
		return "ticket";
	}
	
	@GetMapping("/getVclassAndReturn")
	public String getVclassAndReturn(HttpServletRequest request, Model theModel) {
		String vnumber = request.getParameter("vnumber");
		String laneId = request.getParameter("laneId"); 
				
		VehicleTracking vehicleTrack = theVehicleTrackingService.getVehicleTrack(vnumber);
		
		
		if (vehicleTrack==null) return "automate";
		
		//get vehicle class from vehicle class id
		String vehicleClass = tollConfigService.getLastTrollTransactionVehicleClass(vehicleTrack.getVehicleClassId());
		
		//also check but not update return
		boolean vehicleExist = theVehicleTrackingService.checkButNotUpdateReturn(vnumber, laneId);
		
		//get the vehicle class id for the given vehicle class and Plaza (we can get Plaza from lane)
		Integer vehicleClassId = tollConfigService.getVehicleClassId(vehicleClass, laneId);
		
		//now set all the values and get it on to a jsp file
		theModel.addAttribute("vehicleClass", vehicleClass);
		theModel.addAttribute("vehicleClassId", vehicleClassId);
		theModel.addAttribute("vehicleExist", vehicleExist);
		return "automate";
	}
	
	@GetMapping("/returnCheck")
	public String returnCheck(HttpServletRequest request, Model theModel, HttpSession session) {
		
		User userFromSession = (User) session.getAttribute("userFromSession");
		String vehicleNumber = request.getParameter("vehicleNumber");
		String laneId1 = request.getParameter("laneId");
		Integer laneId = Integer.parseInt(laneId1);
				
		boolean vehicleExist = //tollTransactionService.getReturnStatus(vehicleNumber);
					theVehicleTrackingService.getReturnStatus(vehicleNumber, laneId, userFromSession.getUserId());
		
		theModel.addAttribute("vehicleExist", vehicleExist);
		
		return "vehicle_return_status";
	}
	
	@GetMapping("/cancelTicket1")
	public String cancelTicket1(HttpServletRequest request, Model theModel, HttpSession session) {
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/";
		
		String ticketNumber = request.getParameter("tno");
		String comments = request.getParameter("comments");
		
		//get that ticket and modify it
		TollTransaction ticket = tollTransactionService.searchTicket(ticketNumber);
		
		System.out.println("ticketNumber: =-======>"+ticketNumber);
		
		//check whether it is already cancelled
		if (ticket.getCancellationCode() != null && ticket.getCancellationCode().equals("CANCELLED")) {
			theModel.addAttribute("error", "Ticket was already cancelled");
			return "redirect:/tollTransaction/cancelTicket";
		} else {
			ticket.setCancellationCode("CANCELLED");
			ticket.setModificationTimeStamp(new Date());
			ticket.setModifiedUserID(userFromSession.getUserId());
			ticket.setComments(comments);
			tollTransactionService.saveTollTransaction(ticket);
		}
		
		theModel.addAttribute("message", "Ticket Cancelled successfully");
		return "redirect:/tollTransaction/cancelTicket";
	}
	
	@RequestMapping("vehicleSearch")
	public String searchAVehicle(Model theModel, HttpSession session) {
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		//get all toll plazas and add last toll plaza
		List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
		List<Lane>  allLanes = null;
		List<Shift> allShifts = null;
		List<User> allOperators = null;
		Map<String, String> allUniqueVehicleClasses = null;
		if (!allTollPlazas.isEmpty()) {
			TollPlaza lastOne = allTollPlazas.get(allTollPlazas.size()-1);
			//get all the lanes, shifts, TC for tollPlaza 1
			allLanes = theLaneService.getAllLanes(lastOne.getTollPlazaId());
			allShifts = theShiftService.getAllShifts(lastOne.getTollPlazaId());
			allOperators = theUserService.getAllOperators(lastOne.getTollPlazaId());
			allUniqueVehicleClasses = tollConfigService.getUniqueVehicleClasses();
		}
		
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				
		theModel.addAttribute("allLanes", allLanes);
		theModel.addAttribute("allShifts", allShifts);
		theModel.addAttribute("allOperators", allOperators);
		theModel.addAttribute("today", sdf.format(today));
		theModel.addAttribute("uniqueVehicleClasses", allUniqueVehicleClasses);
		
		return "vehicle_search";
	}
	
	@RequestMapping("/getVehicleTranactions")
	public String getVehicleTransactions(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "blank_page1";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "blank_page1"; 
		
		String laneId = request.getParameter("laneId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String date = request.getParameter("from");
		String vnumber = request.getParameter("vnumber");
		String vehicleClass = request.getParameter("vahicleClass");
				
		System.out.println("Lane: |"+laneId+"| startTime: "+startTime+" date: "+date+" end time: "+endTime+" vnum: |"+vnumber+"|"+vehicleClass+"|");
		
		
		//bring the transactions 
		List<Object[]> tollTransactions = tollTransactionService.getTransactionsOfAVehicle(laneId, startTime, endTime, date, vnumber, vehicleClass);
		
		//theModel.addAttribute("tollTransactions", tollTransactions);
		
		/*for (Object[] o: tollTransactions) {
			for (Object os : o) {
				System.out.println("Testing: "+os);
			}
		}*/
		
		//for remembering the query
		theModel.addAttribute("hdate", date);
		theModel.addAttribute("hstime", startTime);
		theModel.addAttribute("hetime", endTime);
		theModel.addAttribute("hlane", laneId);
		theModel.addAttribute("hvnumber", vnumber);
		theModel.addAttribute("hvclass", vehicleClass);
		
		theModel.addAttribute("tollTransactions", tollTransactions);
		return "vehicle_transactions";
	}
	
	@RequestMapping("/getVehicleTranactionsForMobile") 
	public String getVehicleTransactionsForMobile(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "blank_page1";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "blank_page1";
				
		String laneId = request.getParameter("laneId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String date = request.getParameter("from");
		String vnumber = request.getParameter("vnumber");
		String vehicleClass = request.getParameter("vahicleClass");
				
		System.out.println("Lane: |"+laneId+"| startTime: "+startTime+" date: "+date+" end time: "+endTime+" vnum: |"+vnumber+"|"+vehicleClass+"|");
		
		
		//bring the transactions 
		List<Object[]> tollTransactions = tollTransactionService.getTransactionsOfAVehicle(laneId, startTime, endTime, date, vnumber, vehicleClass);
		
		//theModel.addAttribute("tollTransactions", tollTransactions);
		
		/*for (Object[] o: tollTransactions) {
			for (Object os : o) {
				System.out.println("Testing: "+os);
			}
		}*/
		
		//for remembering the query
		theModel.addAttribute("hdate", date);
		theModel.addAttribute("hstime", startTime);
		theModel.addAttribute("hetime", endTime);
		theModel.addAttribute("hlane", laneId);
		theModel.addAttribute("hvnumber", vnumber);
		theModel.addAttribute("hvclass", vehicleClass);
		
		theModel.addAttribute("tollTransactions", tollTransactions);
		return "vehicle_transactions_for_mobile";
	}
	
	@GetMapping("/transactionDetails")
	public String getTransactionDetails(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		String transactionId = request.getParameter("transactionId");
		
		//get the query Parameters for prev next functionality
		String hdate = request.getParameter("hdate");
		String hstime = request.getParameter("hstime");
		String hetime = request.getParameter("hetime");
		String hlane = request.getParameter("hlane");
		String hvnumber = request.getParameter("hvnumber");
		String hvclass = request.getParameter("hvclass");
		
		System.out.println(hdate+"|"+"|"+hstime+"|"+hetime+"|"+hlane+"|"+hvnumber+"|"+"|"+hvclass+"|");
 		
		//bring all the transactions for hiding 
		List<Object[]> tollTransactions = tollTransactionService.getTransactionsOfAVehicle(hlane, hstime, hetime, hdate, hvnumber, hvclass);
		theModel.addAttribute("tollTransactions", tollTransactions);
		
		//this is the main transaction to be shown
		List<Object[]> transaction_full_details = tollTransactionService.getTransactionDetails(transactionId);
		
		
		
		if(!transaction_full_details.isEmpty()) theModel.addAttribute("transaction", transaction_full_details.get(0));
		
		return "vehicle_details";
	}
	
	//this is for updating the Img_Captured_YN column through API if the photo does not exist
	@GetMapping("/updateTollTransactionImageCaptured")
	public String update_TollTransction_imgCaptured(HttpServletRequest request, Model theModel) {
		String transactionId = request.getParameter("TransactionId");
		String imgCaptured = request.getParameter("imgCaptured");
		
		if  (transactionId==null || imgCaptured==null) {
			theModel.addAttribute("result", "Values incorrect.");
			return "isUpdated";
		}
		
		if (!imgCaptured.equals("Y") && !imgCaptured.equals("N")) {
			theModel.addAttribute("result", "Not done. Value for Image Captured should be 'Y' or 'N'.");
		}
		
		TollTransaction theTollTransaction = tollTransactionService.getTollTransactionFromId(transactionId);
		
		if (theTollTransaction==null) { ///TollTransaction does not exist
			theModel.addAttribute("result", "Not done. Transaction not found.");
		} else {//update the transaction
			theTollTransaction.setImageCaptured(imgCaptured);
			tollTransactionService.saveTollTransaction(theTollTransaction);
			theModel.addAttribute("result", "Done");
		}
		
		return "isUpdated";
	}
	
	@GetMapping("/checkIfCashUpIsDone")
	public String checkIfCashUpIsDone (HttpServletRequest request, Model theModel,HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "blank_page1";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "blank_page1"; 
		
		//get the start and end time with dates
		String transactionId = request.getParameter("transactionId");
		String transactionCode = request.getParameter("transactionCode");
		TollTransaction tollTransaction = null;
		
		//if transactionId is empty then it is coming from search button of cancel transaction page
		if(transactionId == null || transactionId.equals("")) {
			tollTransaction = tollTransactionService.getTollTransactionFromCode(transactionCode);
		} else {
			//get the transaction
			tollTransaction = tollTransactionService.getTollTransactionFromId(transactionId);
		}
		
		
		
		//get the id of the cashup record for the given transaction
		if (tollTransaction != null) {
			Cashup cashup = theCashupService.getCashUpIfExists(tollTransaction.getTransactionTimeStamp().toString().split(" ")[0], tollTransaction.getLaneId(), tollTransaction.getShiftId());
			
			if (cashup == null) { //cash up not done
				return "true";
			} else {
				theModel.addAttribute("err", "<p style='color: red'>Sorry! Cashup is done for this Transaction.</p>");
				return "false";
			}
		} else { //we cannot cancel it as it does not exist (we wont enter here normally
			theModel.addAttribute("err", "<p style='color: red'>Sorry! Ticket doesnot exist.</p>");
			return "false";
		}
	
	}
}















