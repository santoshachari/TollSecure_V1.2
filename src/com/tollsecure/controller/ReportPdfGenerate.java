package com.tollsecure.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tollsecure.entity.User;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
//import db.SqlConnect;

@Controller
@RequestMapping("/jasperReports")
public class ReportPdfGenerate {// tends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReportPdfGenerate() {
		super();
	}

	@GetMapping("/doGet")
	protected String doGet(HttpServletRequest request, HttpServletResponse response, Model theModel,
			HttpSession session) throws ServletException, IOException {

		// handling session
		User userFromSession = (User) session.getAttribute("userFromSession");
		if (userFromSession == null)
			return "blank_page1";
		if (!userFromSession.getUserRole().equals("Admin") && !userFromSession.getUserRole().equals("Supervisor"))
			return "blank_page1";

		ServletOutputStream servletOutputStream = response.getOutputStream();
		Connection conn = null;

		JasperReport jasperReport;
		JasperPrint jasperPrint;
		JasperDesign jasperDesign;
		// SqlConnect scon = new SqlConnect();

		String action = "";
		String tollPlazaId = "";
		String userId = "";
		Date StartDate = null;
		Date EndDate = null;
		String laneId = "";
		String shiftId = "";
		String OutputType = "";
		String Status = "";
		String filePath = "";
		String pLogo = "";
		String cLogo = "";
		String tsLogo = "";
		String jType = "";

		try {
			//
			// conn = scon.getConn();

			// String dbUrl = props.getProperty("jdbc.url");
			String dbUrl = "jdbc:mysql://localhost:3306/seoni_db";
			// String dbDriver = props.getProperty("jdbc.driver");
			String dbDriver = "com.mysql.jdbc.Driver";
			// String dbUname = props.getProperty("db.username");
			String dbUname = "seoni_user";
			// String dbPwd = props.getProperty("db.password");
			String dbPwd = "seoni_password";

			// Load the JDBC driver
			Class.forName(dbDriver);
			// Get the connection
			conn = DriverManager.getConnection(dbUrl, dbUname, dbPwd);
			Map<String, Object> parameters = new HashMap<String, Object>();

			if (request.getParameterMap().containsKey("tollPlazaId")) {
				tollPlazaId = request.getParameter("tollPlazaId");
			}
			//filePath = "/home/anjan/Desktop/jasperIntegration";

			 filePath = "C:\\TollSecure\\JasperReports";

			 pLogo = filePath + "\\nhai.jpg";
			 cLogo = filePath + "\\rupee.png";
			 tsLogo = filePath + "\\TS_Logo_hdpi.png";
			//pLogo = filePath + "/nhai.jpg";
			//cLogo = filePath + "/rupee.png";
			//tsLogo = filePath + "/TS_Logo_hdpi.png";

			if (request.getParameterMap().containsKey("action")) {
				action = request.getParameter("action");
			}
			if (request.getParameterMap().containsKey("UserId")) {
				userId = request.getParameter("UserId");
			}
			if (request.getParameterMap().containsKey("OutputType")) {
				OutputType = request.getParameter("OutputType");
				// System.out.println("OutputType=====================>"+OutputType);
			}
			if (request.getParameterMap().containsKey("Status")) {
				Status = request.getParameter("Status");
				// System.out.println("Status=====================>"+Status);
			}
			if ((action.equals("TollIntegration")) || (action.equals("Schedule Status Report"))) {
				userId = request.getParameter("P_tuser");
				parameters.put("P_tuser", userId);
			}

			if ((action.equals("LoginLogoutReport")) || (action.equals("ConsolidatedTrafficandRevenueReport")) || (action.equals("ConsolidatedTrafficandRevenueReportMobile"))
					|| (action.equals("ConsolidatedTrafficReport")) || (action.equals("DatewiseRevenueReport"))
					|| action.equals("DatewiseTrafficReport")) {

				laneId = request.getParameter("laneId");
				if (laneId.equals("") || laneId == null)
					laneId = "All";
				shiftId = request.getParameter("shiftId");
				if (shiftId.equals("") || shiftId == null)
					shiftId = "All";
				userId = request.getParameter("userId");
				if (userId.equals("") || userId == null)
					userId = "All";

				System.out.println("laneId: " + laneId + "shiftId: " + shiftId + "P_tuser: " + userId + ".>>>>");
				parameters.put("P_SHIFT", shiftId);
				parameters.put("P_LANE", laneId);
				parameters.put("P_USER_ID", userId);

			}

			else if (action.equals("DatewiseLanewiseRevenueReport") || action.equals("DatewiseLanewiseTrafficReport")) { // for
																															// future
																															// use
				// action = "DatewiseLanewiseRevenueReport";
			}

			else if (action.equals("ShortExcessRevenueReport") || action.equals("CashupSummaryReport")
					|| action.equals("MonthlyPassDetailsReport") || action.equals("JourneyTypeClassificationReport")) {
				shiftId = request.getParameter("shiftId");
				if (shiftId.equals("") || shiftId == null)
					shiftId = "All";
				userId = request.getParameter("userId");
				if (userId.equals("") || userId == null)
					userId = "All";

				System.out.println("shiftId: " + shiftId + "P_tuser: " + userId + "action: " + action + ".>>>>");
				parameters.put("P_SHIFT", shiftId);
				parameters.put("P_USER_ID", userId);
			}

			else if (action.equals("LanewiseTrafficReport") || action.equals("LanewiseRevenueReport")) {
				jType = request.getParameter("jType");
				if (jType.equals("") || jType == null)
					jType = "All";
				shiftId = request.getParameter("shiftId");
				if (shiftId.equals("") || shiftId == null)
					shiftId = "All";

				System.out.println("jType: " + jType + "shiftId: " + shiftId + "==>>>>>>>>");
				parameters.put("P_SHIFT", shiftId);
				parameters.put("P_JOURNEY_TYPE", jType);
			} else if (action.equals("CancellationticketstxnReport") || action.equals("ExemptedTrafficReport")) {
				laneId = request.getParameter("laneId");
				if (laneId.equals("") || laneId == null)
					laneId = "All";
				shiftId = request.getParameter("shiftId");
				if (shiftId.equals("") || shiftId == null)
					shiftId = "All";

				parameters.put("P_SHIFT", shiftId);
				parameters.put("P_LANE", laneId);
				System.out.println("Lane: "+laneId+" Shift: "+shiftId);
			} else if (action.equals("MonthlyPassRevenueReport")) {
				shiftId = request.getParameter("shiftId");
				if (shiftId.equals("") || shiftId == null)
					shiftId = "All";

				System.out.println("shiftId: " + shiftId + "use id: " + userFromSession.getUserId() + "==>>>>>>>>");
				parameters.put("P_SHIFT", shiftId);
				parameters.put("P_USER_ID", userFromSession.getUserId());
			}

			String StartDate1 = request.getParameter("from");
			String EndDate1 = request.getParameter("to");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			StartDate = sdf.parse(StartDate1);
			if (EndDate1 != null)
				EndDate = sdf.parse(EndDate1);
			if (action.equals("CashupSummaryReport"))
				EndDate = StartDate; // change this in future accordingly
			
			parameters.put("P_TOLLPLAZA_ID", tollPlazaId);
			parameters.put("P_LOGO", pLogo);
			parameters.put("P_CURRLOGO", cLogo);
			parameters.put("P_TSLOGO", tsLogo);
			parameters.put("P_FROM_DATE", StartDate);
			parameters.put("P_TO_DATE", EndDate);
			/*
			 * parameters.put("P_PROD_LOGO", pLogo); parameters.put("P_COMP_LOGO", cLogo);
			 * 
			 * parameters.put("D_FM_DT", tempStartDate); parameters.put("D_TO_DT",
			 * tempEndDate);
			 */
			System.out.println("from: " + StartDate + " to: " + EndDate + "PlazaID" + tollPlazaId + "=====>>>>>>>>");
			// jasperDesign = JRXmlLoader.load(filePath + "\\" + action + ".jrxml");
			jasperDesign = JRXmlLoader.load(filePath + "/" + action + ".jrxml");
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

			System.out.println("ReportPdfGenerate doGet inside==========================>" + action + " " + userId);
			// response.setContentType("application/pdf");
			// JasperExportManager.exportReportToPdfStream(jasperPrint,
			// servletOutputStream);

			// JasperExportManager.exportReportToPdfFile(jasperPrint, filePath + "\\" +
			// action + ".pdf");
			JasperExportManager.exportReportToPdfFile(jasperPrint, filePath + "/" + action + ".pdf");
			
			JasperExportManager.exportReportToHtmlFile(jasperPrint, filePath + "/" + action + ".html");
			}
		/*
		 * if(OutputType.equals("pdf")) {
		 * 
		 * response.setContentType("application/pdf");
		 * JasperExportManager.exportReportToPdfStream(jasperPrint,
		 * servletOutputStream); } else {
		 * 
		 * ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		 * JRXlsExporter exporterXls = new JRXlsExporter ();
		 * exporterXls.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		 * exporterXls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, false);
		 * exporterXls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);
		 * exporterXls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
		 * false); exporterXls.setParameter(JRXlsExporterParameter.
		 * IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
		 * exporterXls.setParameter(JRXlsExporterParameter.
		 * IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
		 * exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
		 * exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
		 * System.currentTimeMillis() + "test" + ".xls");
		 * exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
		 * byteArrayOutputStream); exporterXls.exportReport();
		 * 
		 * response.setContentType("application/vnd.ms-excel");
		 * response.setHeader("Content-Disposition", "attachment;filename=" +
		 * System.currentTimeMillis() + ".xls");
		 * servletOutputStream.write(byteArrayOutputStream.toByteArray()); }
		 * servletOutputStream.flush(); servletOutputStream.close(); }
		 */
		catch (JRException e) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
			response.getOutputStream().print(stringWriter.toString());
		} catch (Exception e) {
			System.out.println("Exception in ReportPdfGenerate==========>" + e);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ignored) {
				}
			}
		}

		// return "jasper_report_result";
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
