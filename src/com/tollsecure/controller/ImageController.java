package com.tollsecure.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tollsecure.entity.TollTransaction;
import com.tollsecure.service.TollTransactionService;

@Controller
@RequestMapping("/myImage")
public class ImageController {


//need to inject the TollTransaction Service 
@Autowired
private TollTransactionService tollTransactionService;

@RequestMapping(value = "/imageDisplay")
  public void showImage(@RequestParam("code") String code, HttpServletResponse response,HttpServletRequest request) 
          throws ServletException, IOException{


	//get transaction from transaction code
    TollTransaction theTollTransaction = tollTransactionService.getTollTransactionFromCode(code);        
    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
    response.getOutputStream().write(theTollTransaction.getImageBlob());


    response.getOutputStream().close();
	
	}
}
