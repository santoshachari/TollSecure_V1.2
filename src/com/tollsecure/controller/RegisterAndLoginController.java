package com.tollsecure.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tollsecure.entity.Cashup;
import com.tollsecure.entity.FloatAmountDetails;
import com.tollsecure.entity.Lane;
import com.tollsecure.entity.Shift;
import com.tollsecure.entity.ShiftTransaction;
import com.tollsecure.entity.TollPlaza;
import com.tollsecure.entity.User;
import com.tollsecure.service.CashupService;
import com.tollsecure.service.FloatAmountDetailsService;
import com.tollsecure.service.LaneService;
import com.tollsecure.service.RestService;
import com.tollsecure.service.ShiftService;
import com.tollsecure.service.ShiftTransactionService;
import com.tollsecure.service.TollPlazaService;
import com.tollsecure.service.UserService;


@Controller
@RequestMapping("/index")
public class RegisterAndLoginController {
	
	@Autowired
	private UserService theUserService;
	
	@Autowired 
	private LaneService theLaneService;
	
	@Autowired
	private TollPlazaService theTollPlazaService;
	
	@Autowired
	private ShiftTransactionService theShiftTransactionService;
	
	@Autowired 
	private ShiftService theShiftService;
	
	@Autowired
	private FloatAmountDetailsService theFloatAmountDetailsService;
	
	@Autowired
	private RestService theRestService;
	
	//static final string for encryption and decryption
	public static final String encrStr = "\\\"pqKM:,./<>?QRSTUVrsjntuvwZfgCDEBJFGHIhiNOLPo1klm482390-=!@]}{|;'abc#$%^&*()_+[de5xyzAWXY67";
	
	@RequestMapping("/registerUser")
	public String getRegistrationPage(Model theModel, HttpServletRequest request, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		//get all the tollPlazas available
		List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
		
		//if the request is coming from tollPlaza page
		String plazaId = request.getParameter("tollPlazaId");
		
		if (plazaId != null) theModel.addAttribute("plazaId", plazaId);
		
		//let us say a single tollPlaza is not yet added
		if (allTollPlazas.size()==0 && (plazaId==null || plazaId.equals(""))) {
			theModel.addAttribute("error", "Please add a Toll Plaza first before adding an Employee");
			return "redirect:/tollPlaza/showFormForAdd";
		}
		
		theModel.addAttribute("allTollPlazas", allTollPlazas);
		
		String passwordError = request.getParameter("message");
		if (passwordError != null) theModel.addAttribute("message", passwordError);
		
		if (userFromSession.getUserRole().equals("Admin"))
			return "register";
		else
			return "register1";
	}

	@RequestMapping("/saveUser")
	public String saveUser(HttpServletRequest request, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/"; 
		
		//declaring a user object
		User theUser = new User();
		
		//getting parameters
		String firstName=request.getParameter("userFirstName");
		String lastName=request.getParameter("userLastName");
		String mobileNumber1=request.getParameter("userMobileNumber");
		Long mobileNumber=Long.parseLong(mobileNumber1);
		String password = request.getParameter("userPassword");
		String newPassword = request.getParameter("newPassword");
		String userRole=request.getParameter("userRole");
		Integer plazaId = null;
		//if(userRole.equals("Operator")) { //previously we only used to save this for an operator
			String plazaId1 = request.getParameter("plazaId");
			plazaId = Integer.parseInt(plazaId1);
		//}
		
		String dateJoined1 = request.getParameter("dateJoined");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date dateJoined = null;		
		try {
			dateJoined = df.parse(dateJoined1);
		} catch (Exception e) {
		    System.out.println(e+"-------------------------------------------------------");
		}
		
		//encrypt password before entering into db
		String encrPassword = "";
		int key = 3;
		for (int i=0; i<password.length(); i++) {
			if (Character.isWhitespace(password.charAt(i))) {
				encrPassword = encrPassword + " ";
			} else {
				int charpos = encrStr.indexOf(password.charAt(i));
				int keyval = (charpos+key)%92;
				char replaceval = encrStr.charAt(keyval);
				encrPassword = encrPassword + replaceval;
			}
		}
				
		//setting the values to user
		theUser.setTollPlazaId(plazaId);
		theUser.setUserFirstName(firstName);
		theUser.setUserLastName(lastName);
		theUser.setUserMobileNumber(mobileNumber);
		theUser.setUserPassword(encrPassword);
		theUser.setUserRole(userRole);
		theUser.setDateJoined(dateJoined);
		
		//check whether user exist
		List<User> userExist = theUserService.checkUserPasswordForUpdate(theUser);
		if (userExist.isEmpty()) {
			//just insert
			//this is commented for testing encrypting
			//in this case check if other user has same user first and last names
			List<User> userExist1 = theUserService.checkUser(theUser);
			
			if (userExist1.isEmpty()) {
				//newly added active field
				theUser.setStatusFlag("ACTIVE");
				theUser.setCreateTimeStamp(new Date());
				theUser.setCreateUserId(userFromSession.getUserId());
				theUserService.saveUser(theUser);
				theModel.addAttribute("message", "<span style='color:#00FFFF'>Successfully added "+firstName+" "+lastName+"</span>");
			}	else {
				theModel.addAttribute("message", "<span style='color:red'>User "+firstName+" "+lastName+" already exist</span>");
			}
		} else {
			//update user
			//encrypt new password
			if (!newPassword.equals("")) { //new password is added
				encrPassword = "";
				key = 3;
				for (int i=0; i<newPassword.length(); i++) {
					if (Character.isWhitespace(newPassword.charAt(i))) {
						encrPassword = encrPassword + " ";
					} else {
						int charpos = encrStr.indexOf(newPassword.charAt(i));
						int keyval = (charpos+key)%92;
						char replaceval = encrStr.charAt(keyval);
						encrPassword = encrPassword + replaceval;
					}
				}
				System.out.println("Encr password: "+ encrPassword);
				theUser.setUserPassword(encrPassword);
			}
			theUser.setUserId(userExist.get(0).getUserId());
			
			//newly added setting status flag
			String status = request.getParameter("status");
			if(status!=null && status.equals("DEACTIVE"))theUser.setStatusFlag("DEACTIVE");
			else theUser.setStatusFlag("ACTIVE");
			theUser.setCreateTimeStamp(userExist.get(0).getCreateTimeStamp());
			theUser.setCreateUserId(userExist.get(0).getCreateUserId());
			theUser.setModificationTimeStamp(new Date());
			theUser.setModifiedUserId(userFromSession.getUserId());
			theUserService.saveUser(theUser);
			theModel.addAttribute("message", "<span style='color:#00FFFF'>Successfully updated "+firstName+" "+lastName+"</span>");
		}
		
		//2 lines below are commented for testing encrypting
		return "redirect:/index/registerUser";
		
	}
	
	//this is done when someone want to come back to home not from login page but other pages
	@RequestMapping("/h0me")
	public String backToHome(HttpSession session, Model theModel, HttpServletRequest  request) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		//if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		//put user in user object
		User myUser = (User) session.getAttribute("userFromSession");
		
		if (myUser.getUserRole().equals("Admin")) {
			String tabNumber = request.getParameter("tabNumber");
			theModel.addAttribute("tabNumber", tabNumber);
			return "admin_login_success";
		} 
		
		if (myUser.getUserRole().equals("Supervisor")) {
			String tabNumber = request.getParameter("tabNumber");
			theModel.addAttribute("tabNumber", tabNumber);
			return "sup_login_success";
		} 
		
		//user can be operator so handle here
		List<Lane> lanes = theLaneService.getAllLanes(myUser.getTollPlazaId());
		theModel.addAttribute("lanes", lanes);
		return "redirect:/index/home";
	}
	
	@RequestMapping("/loginUser")
	public String loginPage(Model theModel, HttpSession session, HttpServletRequest  request) {
		
		//if already logged in no need to relog in
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession!=null) return "redirect:../index/h0me";
		
		//getting ip address //for future use
		String ipAddr = request.getRemoteAddr();
		System.out.println(ipAddr+"=>>>>>====");
		
		//set user in the model
		User theUser = new User();
		
		//add user attribute to the model
		theModel.addAttribute("user",theUser);
		
		String shiftNotFoundError = request.getParameter("shiftNotFoundError");
		if (shiftNotFoundError != null) theModel.addAttribute("shiftNotFoundError", shiftNotFoundError);
		
		String error = request.getParameter("error");
		if (error != null) theModel.addAttribute("error", error);
		
		return "user_login";
	}
	
	//@ModelAttribute("customer") Customer theCustomer
	
	@RequestMapping("/home")
	public String showHomePage(@ModelAttribute("user") User theUser, Model theModel, HttpSession session, HttpServletRequest  request) {	
		System.out.println(">>>>>>After build problem ");
		
		//testing restful service
		//String vehicleType = theRestService.getVehicleType();
		//System.out.println("...>>>>>> "+vehicleType.toString());
		
		//if user is already logged in
		User userFromSession = (User) session.getAttribute("userFromSession");

		if(userFromSession!=null) {
			if(userFromSession.getUserRole().equals("Admin")) {
				System.out.println("-=-=-=-=Came into admin");
				return "admin_login_success";
			} else if(userFromSession.getUserRole().equals("Operator")) {
				System.out.println("-=-=-=-=Came into operatior");
				//check whether tollPplaza associated with that user exist
				List<TollPlaza> tollPlazas = theTollPlazaService.getTollPlaza(userFromSession.getTollPlazaId());
				TollPlaza tollPlaza = null;
				if (tollPlazas.size() == 0) {
					theModel.addAttribute("error", "<span class='error_show'>Toll Plaza that the user associated with does not exist</span>");
					return "redirect:/index/loginUser";
				} else {
					//now give the tolPlaza a value
					tollPlaza = tollPlazas.get(0);
				}
				
				
				String ipAddr = request.getRemoteAddr();
				System.out.println(ipAddr+"=>>>>>====");
				
				//for lane 2
				//192.168.0.100
				System.out.println("-=-=-=>>>Ippp: "+ipAddr);
				if (ipAddr.equals("192.168.0.100")) {
					//get the lane which has L2 lane code
					Lane L2 = theLaneService.getLaneFromLaneCode("L2");
					Integer tpId = tollPlaza.getTollPlazaId();
					Integer lId = L2.getLaneId();
					return "redirect:/tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else if (ipAddr.equals("192.168.0.101")) {
					//get the lane which has L2 lane code
					Lane L2 = theLaneService.getLaneFromLaneCode("L3");
					Integer tpId = tollPlaza.getTollPlazaId();
					Integer lId = L2.getLaneId();
					return "redirect:/tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				}  else if (ipAddr.equals("192.168.0.103")) {
					//get the lane which has L2 lane code
					Lane L2 = theLaneService.getLaneFromLaneCode("L4");
					Integer tpId = tollPlaza.getTollPlazaId();
					Integer lId = L2.getLaneId();
					return "redirect:/tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				}  else if (ipAddr.equals("192.168.0.102")) {
					//get the lane which has L2 lane code
					Lane L2 = theLaneService.getLaneFromLaneCode("L5");
					Integer tpId = tollPlaza.getTollPlazaId();
					Integer lId = L2.getLaneId();
					return "redirect:/tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				}  else if (ipAddr.equals("192.168.0.104")) {
					//get the lane which has L2 lane code
					Lane L2 = theLaneService.getLaneFromLaneCode("L6");
					Integer tpId = tollPlaza.getTollPlazaId();
					Integer lId = L2.getLaneId();
					return "redirect:/tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				}  else if (ipAddr.equals("192.168.0.105")) {
					//get the lane which has L2 lane code
					Lane L2 = theLaneService.getLaneFromLaneCode("L7");
					Integer tpId = tollPlaza.getTollPlazaId();
					Integer lId = L2.getLaneId();
					return "redirect:/tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else if (ipAddr.equals("127.0.0.1")) {
					//get the lane which has L2 lane code
					Lane L2 = theLaneService.getLaneFromLaneCode("L5");
					Integer tpId = tollPlaza.getTollPlazaId();
					Integer lId = L2.getLaneId();
					return "redirect:/tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else if (ipAddr.equals("192.168.0.106")) {
					//get the lane which has L2 lane code
					Lane L2 = theLaneService.getLaneFromLaneCode("L1");
					Integer tpId = tollPlaza.getTollPlazaId();
					Integer lId = L2.getLaneId();
					return "redirect:/tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else if (ipAddr.equals("192.168.0.107")) {
					//get the lane which has L2 lane code
					Lane L2 = theLaneService.getLaneFromLaneCode("L8");
					Integer tpId = tollPlaza.getTollPlazaId();
					Integer lId = L2.getLaneId();
					return "redirect:/tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else if (ipAddr.equals("10.0.2.27")) {
					//get the lane which has L2 lane code
					Lane L2 = theLaneService.getLaneFromLaneCode("L1");
					Integer tpId = tollPlaza.getTollPlazaId();
					Integer lId = L2.getLaneId();
					return "redirect:/tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else {
					//List<Lane> lanes = theLaneService.getAllLanes(userFromSession.getTollPlazaId());
					//theModel.addAttribute("lanes", lanes);
					//return "operator_login_success";
					
					//get the lane which has L2 lane code
					Lane L2 = theLaneService.getLaneFromLaneCode("L5");
					Integer tpId = tollPlaza.getTollPlazaId();
					Integer lId = L2.getLaneId();
					return "redirect:/tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				}
				
				//theModel.addAttribute("error","<span class='error_show'>This computer is not configured for Operator Module</span>");
				//return "user_login";
				
				//List<Lane> lanes = theLaneService.getAllLanes(userFromSession.getTollPlazaId());
				//theModel.addAttribute("lanes", lanes);
				//return "operator_login_success";
			} else if(userFromSession.getUserRole().equals("Supervisor")) {
				//check whether tollPplaza associated with that user exist
				List<TollPlaza> tollPlazas = theTollPlazaService.getTollPlaza(userFromSession.getTollPlazaId());
				if (tollPlazas.size() == 0) {
					theModel.addAttribute("error", "<span class='error_show'>Toll Plaza that the user associated with does not exist</span>");
					return "redirect:/index/loginUser";
				}
				//List<Lane> lanes = theLaneService.getAllLanes(userFromSession.getTollPlazaId());
				//theModel.addAttribute("lanes", lanes);
				return "sup_login_success";
			}
		}
		
		//encrypt password and reset to user
		//code for encryption
		String password=theUser.getUserPassword();
		String encrPassword = "";
		if(password==null) return "redirect:/";
		int key = 3;
		for (int i=0; i<password.length(); i++) {
			if (Character.isWhitespace(password.charAt(i))) {
				encrPassword = encrPassword + " ";
			} else {
				int charpos = encrStr.indexOf(password.charAt(i));
				int keyval = (charpos+key)%92;
				char replaceval = encrStr.charAt(keyval);
				encrPassword = encrPassword + replaceval;
			}
		}
		
		//change his password to encrypted one
		theUser.setUserPassword(encrPassword);
		
		//check whether customer exist
		List<User> users = theUserService.checkUserPassword(theUser);
		
		if(users.isEmpty()) {
			System.out.println("user list is empty-------------------");
			theModel.addAttribute("error","<span class='error_show'>UserName Password didn't match</span>");
			
			
			//may be from hold mode also so if it is from those computers send that result
			String ipAdr = request.getRemoteAddr();
			if (ipAdr.equals("192.168.0.100") || ipAdr.equals("192.168.0.101") || ipAdr.equals("192.168.0.102") || ipAdr.equals("192.168.0.103") || ipAdr.equals("192.168.0.104") 
					|| ipAdr.equals("192.168.0.105") || ipAdr.equals("192.168.0.106") || ipAdr.equals("192.168.0.107") || ipAdr.equals("10.0.2.27")) {
				theModel.addAttribute("error","<span class='error_show'>User Id and Password did not match</span>");
				return "hold_page";
			}
			
			return "user_login";
		}
		
		//as mySql is not case sensitive in columns also check in java
		/*User userFromDb = users.get(0);
		if (!userFromDb.getUserFirstName().equals(theUser.getUserFirstName()) || !userFromDb.getUserLastName().equals(theUser.getUserLastName()) || !userFromDb.getUserPassword().equals(theUser.getUserPassword())) {
			System.out.println("Cases does not match------------------- ");
			theModel.addAttribute("error","<span class='error_show'>UserName Password didn't match</span>");
			return "user_login";
		}*/
		
		//add session attribute
		session.setAttribute("userFromSession", users.get(0));

		//put user in user object
		User myUser = users.get(0);
		
		
		
		//before entering into login success page enter details into shiftTransactionTable
		ShiftTransaction shiftTransaction = new ShiftTransaction();
		
		//getting current shift
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String sCertDate = dateFormat.format(new Date());
		
		String error = "";
		
		//it returns shiftDesc_shift_id
		Integer plazaId = myUser.getTollPlazaId();
		if (plazaId == null) {
			List<TollPlaza> allTollPlazas = theTollPlazaService.getAllTollPlazas();
			if (allTollPlazas.size()==0 && !myUser.getUserRole().equals("Admin")) {
				theModel.addAttribute("error","<br><span class='error_show'>set toll Plazas first</span>");
				return "user_login";
			}
			if (allTollPlazas.size() != 0) plazaId = allTollPlazas.get(0).getTollPlazaId();
		}
		
		Shift currentShift = null;
		
		if(plazaId != null) currentShift = theShiftService.getShift(sCertDate, plazaId);
		
		if (currentShift==null && !myUser.getUserRole().equals("Admin")) {
			theModel.addAttribute("error","<br><span class='error_show'>Set Shifts first</span>");
			return "user_login";
		}
		
		shiftTransaction.setUserId(myUser.getUserId());
		if (currentShift!=null) shiftTransaction.setShiftId(currentShift.getShiftId());
		shiftTransaction.setTollPlazaId(plazaId);
		//we can not set laneId now but can do it later while adding ip addresses for now we can add while logging out
		//shiftTransaction.setLaneId(laneId);
		
		System.out.println("line: 325:-=-=>>>"+currentShift);
		
		if (currentShift!=null) {
			System.out.println("line: 328:-=-=-=>>came into if");
			shiftTransaction.setPunchInTime(new Date()); //punch out time will be set while logging out
			session.setAttribute("punchInTime", shiftTransaction.getPunchInTime());
			shiftTransaction.setCreateTimeStamp(new Date());
			shiftTransaction.setCreateUserId(myUser.getUserId());
			
			
		}
			
		//finally render the page according to user type
		if (myUser.getUserRole().equals("Admin")) {
			System.out.println("-=-=-=-=Came into admin");
			
			theShiftTransactionService.saveOrUpdate(shiftTransaction);
			
			//soon after saving shift transaction get the shift transaction for use
			ShiftTransaction theShiftTransaction = theShiftTransactionService.getShiftTransactionFromUserAndPunchInTime(myUser.getUserId(), (Date) session.getAttribute("punchInTime"));
			
			session.setAttribute("shiftTransactionFromSession", theShiftTransaction);
			
			System.out.println("-=-=-=>>>>line: 340 session has shiftTransactionFromSession: "+theShiftTransaction);
			
			return "admin_login_success";
		} else if(myUser.getUserRole().equals("Operator")){
			System.out.println("-=-=-=-=Came into operator");
			//check whether tollPplaza associated with that user exist
			List<TollPlaza> tollPlazas = null;
			TollPlaza tollPlaza = null;
			if (myUser != null) {
				tollPlazas = theTollPlazaService.getTollPlaza(myUser.getTollPlazaId());
			}
			if (tollPlazas.size()==0) {
				session.removeAttribute("userFromSession");
				session.invalidate();
				theModel.addAttribute("error", "<span class='error_show'>Toll Plaza that the user associated with does not exist</span>");
				return "redirect:/index/loginUser";
			} else {
				tollPlaza = tollPlazas.get(0);
			}
			//user can be operator so handle here
			
			//List<Lane> lanes = theLaneService.getAllLanes(myUser.getTollPlazaId());
			//theModel.addAttribute("lanes", lanes);
			
			//check the ip address and redirect to the required page
			//getting ip address //for future use
			String ipAddr = request.getRemoteAddr();
			System.out.println(ipAddr+"=>>>>>====");
			
			//for allowing only assigned operator---> New use case
			
			//get the date
			Date checkDate1 = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String checkDate = sdf.format(checkDate1);
			//currentShift

			
			//for lane 2
			//
			System.out.println("-=-=-=>>>Ippp: "+ipAddr);
			// 10.0.2.27 
			if (ipAddr.equals("192.168.0.100")) {
				//get the lane which has L2 lane code
				Lane L2 = theLaneService.getLaneFromLaneCode("L2", tollPlaza.getTollPlazaId().toString());
				Integer tpId = tollPlaza.getTollPlazaId();
				Integer lId = L2.getLaneId();

				//before going to tollTransaction Screen check whether same user is assigned to the lane
				List<FloatAmountDetails> floatAmountDetail = theFloatAmountDetailsService.getSameDetailIfExist(checkDate.toString(), tollPlaza.getTollPlazaId().toString(), lId.toString(), currentShift.getShiftId().toString());
				System.out.println(">>>>>>>>>>>>"+floatAmountDetail+"Anjan Anjan Anjan Anjan Anjan -=============================================================================================================================");
				if (floatAmountDetail.size() != 0 && floatAmountDetail.get(0).getUserId().equals(myUser.getUserId())) {
					shiftTransaction.setLaneId(lId);
					
					//once check whether user is in wait mode so instead of saving new one we can update old shift transaction
					ShiftTransaction existingOne = theShiftTransactionService.getShiftTransactionForLaneDateShift(L2.getLaneId(), checkDate, currentShift.getShiftId());
					
					if (existingOne != null) {
						session.setAttribute("punchInTime", existingOne.getPunchInTime());
						existingOne.setPunchOutTime(null);
						existingOne.setLaneId(lId);
						shiftTransaction = existingOne;
					}
					theShiftTransactionService.saveOrUpdate(shiftTransaction);
					//soon after saving shift transaction get the shift transaction for use
					ShiftTransaction theShiftTransaction = theShiftTransactionService.getShiftTransactionFromUserAndPunchInTime(myUser.getUserId(), (Date) session.getAttribute("punchInTime"));
					
					session.setAttribute("shiftTransactionFromSession", theShiftTransaction);
					
					System.out.println("-=-=-=>>>>session has shiftTransactionFromSession: "+theShiftTransaction);
					
					return "redirect:/tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else {
					session.removeAttribute("userFromSession");
					session.invalidate();
					if (floatAmountDetail.size() == 0) theModel.addAttribute("error", "<span class='error_show'>No user was assigned to the lane</span>");
					else theModel.addAttribute("error", "<span class='error_show'>Another user was assigned to the lane</span>");
					return "redirect:/index/loginUser";
				}// 10.0.2.27
			} else if (ipAddr.equals("192.168.0.101")) {
				//get the lane which has L2 lane code
				Lane L2 = theLaneService.getLaneFromLaneCode("L3", tollPlaza.getTollPlazaId().toString());
				Integer tpId = tollPlaza.getTollPlazaId();
				Integer lId = L2.getLaneId();
				
				//before going to tollTransaction Screen check whether same user is assigned to the lane
				List<FloatAmountDetails> floatAmountDetail = theFloatAmountDetailsService.getSameDetailIfExist(checkDate.toString(), tollPlaza.getTollPlazaId().toString(), lId.toString(), currentShift.getShiftId().toString());
				
				if (floatAmountDetail.size() != 0 && floatAmountDetail.get(0).getUserId().equals(myUser.getUserId())) {
					shiftTransaction.setLaneId(lId);
					
					//once check whether user is in wait mode so instead of saving new one we can update old shift transaction
					ShiftTransaction existingOne = theShiftTransactionService.getShiftTransactionForLaneDateShift(L2.getLaneId(), checkDate, currentShift.getShiftId());
					
					if (existingOne != null) {
						session.setAttribute("punchInTime", existingOne.getPunchInTime());
						existingOne.setPunchOutTime(null);
						existingOne.setLaneId(lId);
						shiftTransaction = existingOne;
					}
					theShiftTransactionService.saveOrUpdate(shiftTransaction);
					//soon after saving shift transaction get the shift transaction for use
					ShiftTransaction theShiftTransaction = theShiftTransactionService.getShiftTransactionFromUserAndPunchInTime(myUser.getUserId(), (Date) session.getAttribute("punchInTime"));
					
					session.setAttribute("shiftTransactionFromSession", theShiftTransaction);
					
					System.out.println("-=-=-=>>>>session has shiftTransactionFromSession: "+theShiftTransaction);
					
					return "redirect:/tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else {
					session.removeAttribute("userFromSession");
					session.invalidate();
					if (floatAmountDetail.size() == 0) theModel.addAttribute("error", "<span class='error_show'>No user was assigned to the lane</span>");
					else theModel.addAttribute("error", "<span class='error_show'>Another user was assigned to the lane</span>");
					return "redirect:/index/loginUser";
				}
				//  10.0.2.27
			}  else if (ipAddr.equals("192.168.0.103")) {
				//get the lane which has L2 lane code
				Lane L2 = theLaneService.getLaneFromLaneCode("L4", tollPlaza.getTollPlazaId().toString());
				Integer tpId = tollPlaza.getTollPlazaId();
				Integer lId = L2.getLaneId();
				
				//before going to tollTransaction Screen check whether same user is assigned to the lane
				List<FloatAmountDetails> floatAmountDetail = theFloatAmountDetailsService.getSameDetailIfExist(checkDate.toString(), tollPlaza.getTollPlazaId().toString(), lId.toString(), currentShift.getShiftId().toString());
				
				if (floatAmountDetail.size() != 0 && floatAmountDetail.get(0).getUserId().equals(myUser.getUserId())) {
					shiftTransaction.setLaneId(lId);
					
					//once check whether user is in wait mode so instead of saving new one we can update old shift transaction
					ShiftTransaction existingOne = theShiftTransactionService.getShiftTransactionForLaneDateShift(L2.getLaneId(), checkDate, currentShift.getShiftId());
					
					if (existingOne != null) {
						session.setAttribute("punchInTime", existingOne.getPunchInTime());
						existingOne.setPunchOutTime(null);
						existingOne.setLaneId(lId);
						shiftTransaction = existingOne;
					}
					
					theShiftTransactionService.saveOrUpdate(shiftTransaction);
					//soon after saving shift transaction get the shift transaction for use
					ShiftTransaction theShiftTransaction = theShiftTransactionService.getShiftTransactionFromUserAndPunchInTime(myUser.getUserId(), (Date) session.getAttribute("punchInTime"));
					
					session.setAttribute("shiftTransactionFromSession", theShiftTransaction);
					
					System.out.println("-=-=-=>>>>session has shiftTransactionFromSession: "+theShiftTransaction);
					
					return "redirect:/tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else {
					session.removeAttribute("userFromSession");
					session.invalidate();
					if (floatAmountDetail.size() == 0) theModel.addAttribute("error", "<span class='error_show'>No user was assigned to the lane</span>");
					else theModel.addAttribute("error", "<span class='error_show'>Another user was assigned to the lane</span>");
					return "redirect:/index/loginUser";
				}
				// 10.0.2.27
			}  else if (ipAddr.equals("192.168.0.102")) {
				//get the lane which has L2 lane code
				Lane L2 = theLaneService.getLaneFromLaneCode("L5", tollPlaza.getTollPlazaId().toString());
				Integer tpId = tollPlaza.getTollPlazaId();
				Integer lId = L2.getLaneId();
				
				//before going to tollTransaction Screen check whether same user is assigned to the lane
				List<FloatAmountDetails> floatAmountDetail = theFloatAmountDetailsService.getSameDetailIfExist(checkDate.toString(), tollPlaza.getTollPlazaId().toString(), lId.toString(), currentShift.getShiftId().toString());
				
				if (floatAmountDetail.size() != 0 && floatAmountDetail.get(0).getUserId().equals(myUser.getUserId())) {
					shiftTransaction.setLaneId(lId);
					
					//once check whether user is in wait mode so instead of saving new one we can update old shift transaction
					ShiftTransaction existingOne = theShiftTransactionService.getShiftTransactionForLaneDateShift(L2.getLaneId(), checkDate, currentShift.getShiftId());
					
					if (existingOne != null) {
						session.setAttribute("punchInTime", existingOne.getPunchInTime());
						existingOne.setPunchOutTime(null);
						existingOne.setLaneId(lId);
						shiftTransaction = existingOne;
					}
					
					theShiftTransactionService.saveOrUpdate(shiftTransaction);
					//soon after saving shift transaction get the shift transaction for use
					ShiftTransaction theShiftTransaction = theShiftTransactionService.getShiftTransactionFromUserAndPunchInTime(myUser.getUserId(), (Date) session.getAttribute("punchInTime"));
					
					session.setAttribute("shiftTransactionFromSession", theShiftTransaction);
					
					System.out.println("-=-=-=>>>>session has shiftTransactionFromSession: "+theShiftTransaction);
					
					return "redirect://tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else {
					session.removeAttribute("userFromSession");
					session.invalidate();
					if (floatAmountDetail.size() == 0) theModel.addAttribute("error", "<span class='error_show'>No user was assigned to the lane</span>");
					else theModel.addAttribute("error", "<span class='error_show'>Another user was assigned to the lane</span>");
					return "redirect:/index/loginUser";
				}
				//10.0.2.27
			}  else if (ipAddr.equals("192.168.0.104")) {
				//get the lane which has L2 lane code
				Lane L2 = theLaneService.getLaneFromLaneCode("L6", tollPlaza.getTollPlazaId().toString());
				Integer tpId = tollPlaza.getTollPlazaId();
				Integer lId = L2.getLaneId();
				
				//before going to tollTransaction Screen check whether same user is assigned to the lane
				List<FloatAmountDetails> floatAmountDetail = theFloatAmountDetailsService.getSameDetailIfExist(checkDate.toString(), tollPlaza.getTollPlazaId().toString(), lId.toString(), currentShift.getShiftId().toString());
				
				if (floatAmountDetail.size() != 0 && floatAmountDetail.get(0).getUserId().equals(myUser.getUserId())) {
					shiftTransaction.setLaneId(lId);
					
					//once check whether user is in wait mode so instead of saving new one we can update old shift transaction
					ShiftTransaction existingOne = theShiftTransactionService.getShiftTransactionForLaneDateShift(L2.getLaneId(), checkDate, currentShift.getShiftId());
					
					if (existingOne != null) {
						session.setAttribute("punchInTime", existingOne.getPunchInTime());
						existingOne.setPunchOutTime(null);
						existingOne.setLaneId(lId);
						shiftTransaction = existingOne;
					}
					
					theShiftTransactionService.saveOrUpdate(shiftTransaction);
					//soon after saving shift transaction get the shift transaction for use
					ShiftTransaction theShiftTransaction = theShiftTransactionService.getShiftTransactionFromUserAndPunchInTime(myUser.getUserId(), (Date) session.getAttribute("punchInTime"));
					
					session.setAttribute("shiftTransactionFromSession", theShiftTransaction);
					
					System.out.println("-=-=-=>>>>session has shiftTransactionFromSession: "+theShiftTransaction);
					
					return "redirect:/tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else {
					session.removeAttribute("userFromSession");
					session.invalidate();
					System.out.println(floatAmountDetail.size()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					if (floatAmountDetail.size() == 0) theModel.addAttribute("error", "<span class='error_show'>No user was assigned to the lane</span>");
					else theModel.addAttribute("error", "<span class='error_show'>Another user was assigned to the lane</span>");
					return "redirect:/index/loginUser";
				}
				//10.0.2.27
			}  else if (ipAddr.equals("192.168.0.105")) {
				//get the lane which has L2 lane code
				Lane L2 = theLaneService.getLaneFromLaneCode("L7", tollPlaza.getTollPlazaId().toString());
				Integer tpId = tollPlaza.getTollPlazaId();
				Integer lId = L2.getLaneId();
				
				//before going to tollTransaction Screen check whether same user is assigned to the lane
				List<FloatAmountDetails> floatAmountDetail = theFloatAmountDetailsService.getSameDetailIfExist(checkDate.toString(), tollPlaza.getTollPlazaId().toString(), lId.toString(), currentShift.getShiftId().toString());
				
				if (floatAmountDetail.size() != 0 && floatAmountDetail.get(0).getUserId().equals(myUser.getUserId())) {
					shiftTransaction.setLaneId(lId);
					
					//once check whether user is in wait mode so instead of saving new one we can update old shift transaction
					ShiftTransaction existingOne = theShiftTransactionService.getShiftTransactionForLaneDateShift(L2.getLaneId(), checkDate, currentShift.getShiftId());
					
					if (existingOne != null) {
						session.setAttribute("punchInTime", existingOne.getPunchInTime());
						existingOne.setPunchOutTime(null);
						existingOne.setLaneId(lId);
						shiftTransaction = existingOne;
					}
					
					theShiftTransactionService.saveOrUpdate(shiftTransaction);
					//soon after saving shift transaction get the shift transaction for use
					ShiftTransaction theShiftTransaction = theShiftTransactionService.getShiftTransactionFromUserAndPunchInTime(myUser.getUserId(), (Date) session.getAttribute("punchInTime"));
					
					session.setAttribute("shiftTransactionFromSession", theShiftTransaction);
					
					System.out.println("-=-=-=>>>>session has shiftTransactionFromSession: "+theShiftTransaction);
					
					return "redirect://tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else {
					session.removeAttribute("userFromSession");
					session.invalidate();
					if (floatAmountDetail.size() == 0) theModel.addAttribute("error", "<span class='error_show'>No user was assigned to the lane</span>");
					else theModel.addAttribute("error", "<span class='error_show'>Another user was assigned to the lane</span>");
					return "redirect:/index/loginUser";
				}
				
			} else if (ipAddr.equals("127.0.0.1")) {
				//previously we used a home page for operator also
				/*List<Lane> lanes = theLaneService.getAllLanes(myUser.getTollPlazaId());
				theModel.addAttribute("lanes", lanes);
				return "operator_login_success";*/
				
				//get the lane which has L5 lane code, we gave lane 5 for localhost
				Lane L2 = theLaneService.getLaneFromLaneCode("L5", tollPlaza.getTollPlazaId().toString());
				Integer tpId = tollPlaza.getTollPlazaId();
				Integer lId = L2.getLaneId();
				
				//before going to tollTransaction Screen check whether same user is assigned to the lane
				List<FloatAmountDetails> floatAmountDetail = theFloatAmountDetailsService.getSameDetailIfExist(checkDate.toString(), tollPlaza.getTollPlazaId().toString(), lId.toString(), currentShift.getShiftId().toString());
				
				if (floatAmountDetail.size() != 0 && floatAmountDetail.get(0).getUserId().equals(myUser.getUserId())) {
					shiftTransaction.setLaneId(lId);
					
					//once check whether user is in wait mode so instead of saving new one we can update old shift transaction
					ShiftTransaction existingOne = theShiftTransactionService.getShiftTransactionForLaneDateShift(L2.getLaneId(), checkDate, currentShift.getShiftId());
					
					if (existingOne != null) {
						session.setAttribute("punchInTime", existingOne.getPunchInTime());
						existingOne.setPunchOutTime(null);
						existingOne.setLaneId(lId);
						shiftTransaction = existingOne;
					}
					
					theShiftTransactionService.saveOrUpdate(shiftTransaction);
					//soon after saving shift transaction get the shift transaction for use
					ShiftTransaction theShiftTransaction = theShiftTransactionService.getShiftTransactionFromUserAndPunchInTime(myUser.getUserId(), (Date) session.getAttribute("punchInTime"));
					
					session.setAttribute("shiftTransactionFromSession", theShiftTransaction);
					
					System.out.println("-=-=-=>>>>session has shiftTransactionFromSession: "+theShiftTransaction);
					
					return "redirect://tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else {
					session.removeAttribute("userFromSession");
					session.invalidate();
					if (floatAmountDetail.size() == 0) theModel.addAttribute("error", "<span class='error_show'>No user was assigned to the lane</span>");
					else theModel.addAttribute("error", "<span class='error_show'>Another user was assigned to the lane</span>");
					return "redirect:/index/loginUser";
				}
			} //   
			else if (ipAddr.equals("192.168.0.106")) {
				//get the lane which has L2 lane code
				Lane L2 = theLaneService.getLaneFromLaneCode("L1", tollPlaza.getTollPlazaId().toString());
				Integer tpId = tollPlaza.getTollPlazaId();
				Integer lId = L2.getLaneId();
				
				//before going to tollTransaction Screen check whether same user is assigned to the lane
				List<FloatAmountDetails> floatAmountDetail = theFloatAmountDetailsService.getSameDetailIfExist(checkDate.toString(), tollPlaza.getTollPlazaId().toString(), lId.toString(), currentShift.getShiftId().toString());
				
				if (floatAmountDetail.size() != 0 && floatAmountDetail.get(0).getUserId().equals(myUser.getUserId())) {
					shiftTransaction.setLaneId(lId);
					
					//once check whether user is in wait mode so instead of saving new one we can update old shift transaction
					ShiftTransaction existingOne = theShiftTransactionService.getShiftTransactionForLaneDateShift(L2.getLaneId(), checkDate, currentShift.getShiftId());
					
					if (existingOne != null) {
						session.setAttribute("punchInTime", existingOne.getPunchInTime());
						existingOne.setPunchOutTime(null);
						existingOne.setLaneId(lId);
						shiftTransaction = existingOne;
					}
					
					theShiftTransactionService.saveOrUpdate(shiftTransaction);
					//soon after saving shift transaction get the shift transaction for use
					ShiftTransaction theShiftTransaction = theShiftTransactionService.getShiftTransactionFromUserAndPunchInTime(myUser.getUserId(), (Date) session.getAttribute("punchInTime"));
					
					session.setAttribute("shiftTransactionFromSession", theShiftTransaction);
					
					System.out.println("-=-=-=>>>>session has shiftTransactionFromSession: "+theShiftTransaction);
					
					return "redirect://tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else {
					session.removeAttribute("userFromSession");
					session.invalidate();
					if (floatAmountDetail.size() == 0) theModel.addAttribute("error", "<span class='error_show'>No user was assigned to the lane</span>");
					else theModel.addAttribute("error", "<span class='error_show'>Another user was assigned to the lane</span>");
					return "redirect:/index/loginUser";
				}
				//
			} else if (ipAddr.equals("192.168.0.107")) {
				//get the lane which has L2 lane code
				Lane L2 = theLaneService.getLaneFromLaneCode("L8", tollPlaza.getTollPlazaId().toString());
				Integer tpId = tollPlaza.getTollPlazaId();
				Integer lId = L2.getLaneId();
				

				//before going to tollTransaction Screen check whether same user is assigned to the lane
				List<FloatAmountDetails> floatAmountDetail = theFloatAmountDetailsService.getSameDetailIfExist(checkDate.toString(), tollPlaza.getTollPlazaId().toString(), lId.toString(), currentShift.getShiftId().toString());
				
				if (floatAmountDetail.size() != 0 && floatAmountDetail.get(0).getUserId().equals(myUser.getUserId())) {
					shiftTransaction.setLaneId(lId);
					
					//once check whether user is in wait mode so instead of saving new one we can update old shift transaction
					ShiftTransaction existingOne = theShiftTransactionService.getShiftTransactionForLaneDateShift(L2.getLaneId(), checkDate, currentShift.getShiftId());
					
					if (existingOne != null) {
						session.setAttribute("punchInTime", existingOne.getPunchInTime());
						existingOne.setPunchOutTime(null);
						existingOne.setLaneId(lId);
						shiftTransaction = existingOne;
					}
					
					theShiftTransactionService.saveOrUpdate(shiftTransaction);
					//soon after saving shift transaction get the shift transaction for use
					ShiftTransaction theShiftTransaction = theShiftTransactionService.getShiftTransactionFromUserAndPunchInTime(myUser.getUserId(), (Date) session.getAttribute("punchInTime"));
					
					session.setAttribute("shiftTransactionFromSession", theShiftTransaction);
					
					System.out.println("-=-=-=>>>>session has shiftTransactionFromSession: "+theShiftTransaction);
					
					return "redirect://tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else {
					session.removeAttribute("userFromSession");
					session.invalidate();
					if (floatAmountDetail.size() == 0) theModel.addAttribute("error", "<span class='error_show'>No user was assigned to the lane</span>");
					else theModel.addAttribute("error", "<span class='error_show'>Another user was assigned to the lane</span>");
					return "redirect:/index/loginUser";
				}
				
			} else if (ipAddr.equals("10.0.2.27")) {
				//let it be lane 1
				
				//get the lane which has L2 lane code
				Lane L2 = theLaneService.getLaneFromLaneCode("L1", tollPlaza.getTollPlazaId().toString());
				Integer tpId = tollPlaza.getTollPlazaId();
				Integer lId = L2.getLaneId();
				

				//before going to tollTransaction Screen check whether same user is assigned to the lane
				List<FloatAmountDetails> floatAmountDetail = theFloatAmountDetailsService.getSameDetailIfExist(checkDate.toString(), tollPlaza.getTollPlazaId().toString(), lId.toString(), currentShift.getShiftId().toString());
				
				if (floatAmountDetail.size() != 0 && floatAmountDetail.get(0).getUserId().equals(myUser.getUserId())) {
					shiftTransaction.setLaneId(lId);
					
					//once check whether user is in wait mode so instead of saving new one we can update old shift transaction
					ShiftTransaction existingOne = theShiftTransactionService.getShiftTransactionForLaneDateShift(L2.getLaneId(), checkDate, currentShift.getShiftId());
					System.out.println("Existing one: "+existingOne);
					if (existingOne != null) {
						session.setAttribute("punchInTime", existingOne.getPunchInTime());
						existingOne.setPunchOutTime(null);
						existingOne.setLaneId(lId);
						shiftTransaction = existingOne;
					}
					
					theShiftTransactionService.saveOrUpdate(shiftTransaction);
					//soon after saving shift transaction get the shift transaction for use
					ShiftTransaction theShiftTransaction = theShiftTransactionService.getShiftTransactionFromUserAndPunchInTime(myUser.getUserId(), (Date) session.getAttribute("punchInTime"));
					
					session.setAttribute("shiftTransactionFromSession", theShiftTransaction);
					
					System.out.println("-=-=-=>>>>session has shiftTransactionFromSession: "+theShiftTransaction);
					
					return "redirect://tollTransaction/tollTransactionForm?plazaId="+tpId+"&laneId="+lId;
				} else {
					session.removeAttribute("userFromSession");
					session.invalidate();
					if (floatAmountDetail.size() == 0) theModel.addAttribute("error", "<span class='error_show'>No user was assigned to the lane</span>");
					else theModel.addAttribute("error", "<span class='error_show'>Another user was assigned to the lane</span>");
					return "redirect:/index/loginUser";
				}
				
				
			} else {
				List<Lane> lanes = theLaneService.getAllLanes(myUser.getTollPlazaId());
				theModel.addAttribute("lanes", lanes);
				
				Lane L2 = theLaneService.getLaneFromLaneCode("L5", tollPlaza.getTollPlazaId().toString());
				Integer lId = L2.getLaneId();

				//before going to tollTransaction Screen check whether same user is assigned to the lane
				List<FloatAmountDetails> floatAmountDetail = theFloatAmountDetailsService.getSameDetailIfExist(checkDate.toString(), tollPlaza.getTollPlazaId().toString(), lId.toString(), currentShift.getShiftId().toString());
				System.out.println(">>>>>>>>>>>>"+floatAmountDetail+"Anjan Anjan Anjan Anjan Anjan -=============================================================================================================================");
				
				//return "operator_login_success";
				
				
				
				
				
				if (floatAmountDetail.size() != 0 && floatAmountDetail.get(0).getUserId().equals(myUser.getUserId())) {
					shiftTransaction.setLaneId(lId);
					
					//once check whether user is in wait mode so instead of saving new one we can update old shift transaction
					ShiftTransaction existingOne = theShiftTransactionService.getShiftTransactionForLaneDateShift(L2.getLaneId(), checkDate, currentShift.getShiftId());
					System.out.println("Existing one: "+existingOne);
					if (existingOne != null) {
						session.setAttribute("punchInTime", existingOne.getPunchInTime());
						existingOne.setPunchOutTime(null);
						existingOne.setLaneId(lId);
						shiftTransaction = existingOne;
					}
					
					theShiftTransactionService.saveOrUpdate(shiftTransaction);
					//soon after saving shift transaction get the shift transaction for use
					ShiftTransaction theShiftTransaction = theShiftTransactionService.getShiftTransactionFromUserAndPunchInTime(myUser.getUserId(), (Date) session.getAttribute("punchInTime"));
					
					session.setAttribute("shiftTransactionFromSession", theShiftTransaction);
					
					System.out.println("-=-=-=>>>>session has shiftTransactionFromSession: "+theShiftTransaction);
					
					return "redirect://tollTransaction/tollTransactionForm?plazaId=1&laneId="+lId;
				} else {
					/*System.out.println("Condition2: "+(floatAmountDetail.get(0).getUserId()==myUser.getUserId()));
					System.out.println("Condidion2 Modified: "+(floatAmountDetail.get(0).getUserId().equals(myUser.getUserId())));
					System.out.println("Condition1: "+(floatAmountDetail.size() != 0));
					System.out.println("The user in the session: "+myUser);*/
					
					session.removeAttribute("userFromSession");
					session.invalidate();
					if (floatAmountDetail.size() == 0) theModel.addAttribute("error", "<span class='error_show'>No user was assigned to the lane</span>");
					else theModel.addAttribute("error", "<span class='error_show'>Another user was assigned to the lane</span>");
					return "redirect:/index/loginUser";
				}
				
				
				
				
				
				
			}
			
			//theModel.addAttribute("error","<span class='error_show'>This computer is not configured for Operator Module</span>");
			//return "user_login";
			
			//List<Lane> lanes = theLaneService.getAllLanes(myUser.getTollPlazaId());
			//theModel.addAttribute("lanes", lanes);
			//return "operator_login_success";
		} else if (myUser.getUserRole().equals("Supervisor")) {
			//check whether tollPplaza associated with that user exist
			List<TollPlaza> tollPlaza = null;
			if (myUser != null) {
				tollPlaza = theTollPlazaService.getTollPlaza(myUser.getTollPlazaId());
			}
			if (tollPlaza.size()==0) {
				session.removeAttribute("userFromSession");
				session.invalidate();
				theModel.addAttribute("error", "<span class='error_show'>Toll Plaza that the user associated with does not exist</span>");
				return "redirect:/index/loginUser";
			}
			
			theShiftTransactionService.saveOrUpdate(shiftTransaction);
			
			//soon after saving shift transaction get the shift transaction for use
			ShiftTransaction theShiftTransaction = theShiftTransactionService.getShiftTransactionFromUserAndPunchInTime(myUser.getUserId(), (Date) session.getAttribute("punchInTime"));
			
			session.setAttribute("shiftTransactionFromSession", theShiftTransaction);
			
			System.out.println("-=-=-=>>>>line: 340 session has shiftTransactionFromSession: "+theShiftTransaction);
			
			return "sup_login_success";
		}

		theModel.addAttribute("error","<span class='error_show'>Emo telidamma</span>");
		return "user_login";
	}
	
	@GetMapping("/addOperator")
	public String addOperator(@RequestParam("plazaId") Integer plazaId, Model theModel, HttpSession session) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "redirect:/";
		
		//create model attribute to bind form data
		User theUser = new User(); 
				
		theModel.addAttribute("user", theUser);
		theModel.addAttribute("plazaId", plazaId);
			
		return "register";
	}
	
	@GetMapping("/logout")
	public String logout(Model theModel, HttpSession session, HttpServletRequest request) {
		
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if(userFromSession==null) return "redirect:/";
		//if(!userFromSession.getUserRole().equals("Admin")) return "redirect:/";
		
		//if the user is operator get all his lanes and release them before logging out
		User theUser = (User) session.getAttribute("userFromSession");
		
		//set punchOut time for Admin/ operator
		Date punchInTime = (Date)session.getAttribute("punchInTime");
		
		//System.out.println("Punchintime-=-=-=-=->>>>>>>>>>: "+punchInTime);
		
		//now get the shift Transaction to update
		ShiftTransaction shiftTransaction = null;
		//query modified --> theShiftTransactionService.getShiftTransactionFromUserAndPunchInTime(theUser.getUserId(), punchInTime);
		if (punchInTime != null)  shiftTransaction = //theShiftTransactionService.getShiftTransactionFromUserAndPunchInTime(theUser.getUserId(), punchInTime);
				(ShiftTransaction) session.getAttribute("shiftTransactionFromSession");
				
		System.out.println("-=-=>>>line: 609: "+shiftTransaction);
	
		if (shiftTransaction == null) { //may be shiftTransaction is not set
			//simply log off for now
		} else {
			shiftTransaction.setPunchOutTime(new Date());
		}
		
		if (theUser.getUserRole().equals("Operator")) {

			//check if he is using any other lane if so release it 
			//get any lane which is associated with this user
			
			//List<Lane> theOtherLanes = (List<Lane>) theLaneService.getLaneAssociatedWithUser(theUser.getUserId());
			//if (theOtherLanes.size()>0) {
				//release all other lanes
				//for (int i=0; i<theOtherLanes.size(); i++) {
					//we are adding lane here remove this line when you assign shifts through IP address
					
					/*shiftTransaction.setLaneId(theOtherLanes.get(i).getLaneId());
					
					theOtherLanes.get(i).setUserId(null);
					theLaneService.saveLane(theOtherLanes.get(i));*/
				//}
			//}
		}
		
		System.out.println("Shift Transaction-=-=======>>>>>>"+shiftTransaction);
		
		if (shiftTransaction != null) {
			shiftTransaction.setModificationTimeStamp(new Date());
			shiftTransaction.setModifiedUserId(theUser.getUserId());
			
			//update shift transaction
			System.out.println("-=-=======>>>>>>"+shiftTransaction);
			theShiftTransactionService.saveOrUpdate(shiftTransaction);
		}
		
		//once again chck if session still exist as he may have pressed sign out at the time of shift end time
		HttpSession session1 = request.getSession(false); 
		if (session1 != null) {
			session.removeAttribute("userFromSession");
			session.invalidate();
		}
		
		
		String shiftNotFoundError = request.getParameter("shiftNotFoundError");
		if (shiftNotFoundError != null) theModel.addAttribute("shiftNotFoundError", shiftNotFoundError);
		
		return "redirect:/";
	}
	
	@GetMapping("/getEmployeeForUpdate")
	public String getEmployeeForUpdate(Model theModel, HttpSession session, HttpServletRequest request) {
		//getting values
		String userFirstName = request.getParameter("firstName");
		String userLastName = request.getParameter("lastName");
		String userPassword = request.getParameter("password");
		
		String encrPassword = "";
		int key = 3;
		for (int i=0; i<userPassword.length(); i++) {
			if (Character.isWhitespace(userPassword.charAt(i))) {
				encrPassword = encrPassword + " ";
			} else {
				int charpos = encrStr.indexOf(userPassword.charAt(i));
				int keyval = (charpos+key)%92;
				char replaceval = encrStr.charAt(keyval);
				encrPassword = encrPassword + replaceval;
			}
		}
		
		User theUser = new User();
		theUser.setUserFirstName(userFirstName);
		theUser.setUserLastName(userLastName);
		theUser.setUserPassword(encrPassword);
		
		List<User> userIfExist = theUserService.checkUserPasswordForUpdate(theUser);
		
		if (userIfExist.isEmpty()) {
			return "employeeDetailsForUpdate";
		}
		
		theModel.addAttribute("user", userIfExist.get(0));
		return "employeeDetailsForUpdate";
	}


	@GetMapping("/hold")
	public String holdMode(Model theModel, HttpSession session, HttpServletRequest request) {
		
		//this is only for the operator
		//handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		//we do not want to redirect on refresh
		//if(userFromSession==null) return "redirect:/";
		
		ShiftTransaction shiftTransaction = null;
		
		if  (userFromSession!=null) {
			//get the shift transaction and update the punch out time as he may wait till logout also
			shiftTransaction = (ShiftTransaction) session.getAttribute("shiftTransactionFromSession");
		
			//set the punch out time
			shiftTransaction.setPunchOutTime(new Date());
		
			//update the shift transaction
			theShiftTransactionService.saveOrUpdate(shiftTransaction);
		}
	
		//invalidate the session
		HttpSession session1 = request.getSession(false); 
		if (session1 != null) {
			session.removeAttribute("userFromSession");
			session.invalidate();
		}
		
		return "hold_page";
	}

	@RequestMapping("/mobilehome")
	public String getMobileHomePage() {
		return "admin_login_success_mob";
	}
	
	@RequestMapping("/supmobilehome")
	public String getMobileSupHomePage() {
		return "sup_login_success_mob";
	}
	
	@RequestMapping("/adminLoggedIn")
	public String checkAdminLoggedIn(HttpSession session) {
		//handling session
		//User userFromSession = (User) session.getAttribute("userFromSession");
		//System.out.println(userFromSession);
		//if(userFromSession==null) return "blank_page1";
		//if(!userFromSession.getUserRole().equals("Admin")) return "blank_page1";
		
		System.out.println("hi all");
		
		return "blank_page";
	}
	
	@RequestMapping("/loggedIn")
	public String checkLoggedIn(HttpSession session) {
		//handling session
		//User userFromSession = (User) session.getAttribute("userFromSession");
		//if(userFromSession==null) return "blank_page1";
		//if(!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor")) return "blank_page1";

		System.out.println("hi all");
		
		return "blank_page";
	}
}















