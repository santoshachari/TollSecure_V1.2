package com.tollsecure.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tollsecure.entity.AVC;
import com.tollsecure.entity.Lane;
import com.tollsecure.entity.LastTollTransaction;
import com.tollsecure.service.AVCService;
import com.tollsecure.service.LaneService;
import com.tollsecure.service.LastTollTransactionService;

@Controller
@RequestMapping("/AVC")
public class AVCController {
	
	@Autowired
	private AVCService theAVCService;
	
	@Autowired
	private LaneService theLaneService;
	
	@Autowired
	private LastTollTransactionService theLastTollTransactionService;

	@RequestMapping("/insertAVCDetails")
	public String declarationPage(HttpServletRequest request, Model theModel) {
		
		//We are not checking session as the program is calling this method
		String laneCode = request.getParameter("laneCode");
		String msg1 = request.getParameter("msg1");
		String msg2 = request.getParameter("msg2");
		
		//get the last toll transaction from last toll transaction table
		LastTollTransaction theLastTollTransaction = theLastTollTransactionService.getLastTollTransactionForLane(laneCode);
		
		if (theLastTollTransaction!=null) {
			//check if AVC has this transaction
			boolean alreadyExist = theAVCService.checkForTransactionIdIfExists(theLastTollTransaction.getTransactionId());
			
			//if the transaction id already exist in AVC table do nothing
			if (alreadyExist) {
				theModel.addAttribute("msg", "false, as the last toll transaction in last toll transaction table already added to AVC");
				return "avc_result";
			} else {
				
				//split the message 1 at commas
				String[] arr1 = msg1.split(",");
				
				
				AVC theAVC = new AVC();
				theAVC.setAvcTxnNumber(Integer.parseInt(arr1[14]));
				theAVC.setAvcDirection(arr1[4]);
				theAVC.setAvcAxleCount(Integer.parseInt(arr1[5]));
				theAVC.setAvcWheelBase(Integer.parseInt(arr1[6]));
				theAVC.setAvcHeightSensor1(arr1[7]);
				theAVC.setAvcHeightSensor2(arr1[8]);
				theAVC.setAvcHeightSensor3(arr1[9]);
				theAVC.setAvcHeightSensor4(arr1[10]);
				theAVC.setAvcVehicleClass(arr1[11]);
				theAVC.setAvcErrorStatus(Integer.parseInt(arr1[12]));
				theAVC.setTransactionId(theLastTollTransaction.getTransactionId());
				theAVC.setCreateTimeStamp(new Date());
				
				
				//if msg2 not empty
				if (msg2!=null && !msg2.equals("")) {
					String[] arr2 = msg2.split(",");
					theAVC.setAvcSensorAlignmentStatus(Integer.parseInt(arr2[2]));
				}
				
				theAVCService.save(theAVC);
				
				theModel.addAttribute("msg", "Successfully saved in AVC table");
				return "avc_result";
			}
			
		} else {
			theModel.addAttribute("msg", "false, as this lane has got no last toll transactions in last toll transaction table ");
			return "avc_result";
		}
		
	}
}
