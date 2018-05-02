<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<style>
table {
    border-collapse: collapse;
}

table, th, td {
    border: 1px solid black;
}
th {
	text-align: center;
}
</style>


<table>
	<tr>
		<th rowspan="2"><span style="font-weight: bold">Vehicle Class</span></th>
		<th colspan="3"><span style="font-weight: bold">Single Journey</span></th>
		<th colspan="3"><span style="font-weight: bold">Return Journey</span></th>
		<th colspan="3"><span style="font-weight: bold">Concession</span></th>
		<th colspan="3"><span style="font-weight: bold">Pass</span></th>
		<th colspan="3"><span style="font-weight: bold">Exempted</span></th>
		<th rowspan="2"><span style="font-weight: bold">Total</span></th>
	</tr>
	
	<tr>
		<th>
		
				<c:choose>
				    <c:when test="${dir1 == 'AHMEDABAD TO UDAIPUR'}">
				       AMD - UDR
				    </c:when>
				    <c:when test="${dir1 == 'UDAIPUR TO AHMEDABAD'}">
				        UDR - AMD 
				    </c:when>
				</c:choose>		
		
		</th>
		<th>
				<c:choose>
				    <c:when test="${dir2 == 'AHMEDABAD TO UDAIPUR'}">
				       AMD - UDR
				    </c:when>
				    <c:when test="${dir2 == 'UDAIPUR TO AHMEDABAD'}">
				        UDR - AMD 
				    </c:when>
				</c:choose>
		</th>
		<th>STotal</th>
		
		<th>
			<c:choose>
				    <c:when test="${dir1 == 'AHMEDABAD TO UDAIPUR'}">
				       AMD - UDR
				    </c:when>
				    <c:when test="${dir1 == 'UDAIPUR TO AHMEDABAD'}">
				        UDR - AMD 
				    </c:when>
			</c:choose>	
		</th>
		<th>
				<c:choose>
				    <c:when test="${dir2 == 'AHMEDABAD TO UDAIPUR'}">
				       AMD - UDR
				    </c:when>
				    <c:when test="${dir2 == 'UDAIPUR TO AHMEDABAD'}">
				        UDR - AMD 
				    </c:when>
				</c:choose>
		</th>
		<th>RTotal</th>
		
		<th>
			<c:choose>
				    <c:when test="${dir1 == 'AHMEDABAD TO UDAIPUR'}">
				       AMD - UDR
				    </c:when>
				    <c:when test="${dir1 == 'UDAIPUR TO AHMEDABAD'}">
				        UDR - AMD 
				    </c:when>
			</c:choose>	
		</th>
		<th>
			<c:choose>
				    <c:when test="${dir2 == 'AHMEDABAD TO UDAIPUR'}">
				       AMD - UDR
				    </c:when>
				    <c:when test="${dir2 == 'UDAIPUR TO AHMEDABAD'}">
				        UDR - AMD 
				    </c:when>
			</c:choose>	
		</th>
		<th>CTotal</th>
		
		<th>
			<c:choose>
				    <c:when test="${dir1 == 'AHMEDABAD TO UDAIPUR'}">
				       AMD - UDR
				    </c:when>
				    <c:when test="${dir1 == 'UDAIPUR TO AHMEDABAD'}">
				        UDR - AMD 
				    </c:when>
			</c:choose>	
		</th>
		<th>
			<c:choose>
				    <c:when test="${dir2 == 'AHMEDABAD TO UDAIPUR'}">
				       AMD - UDR
				    </c:when>
				    <c:when test="${dir2 == 'UDAIPUR TO AHMEDABAD'}">
				        UDR - AMD 
				    </c:when>
			</c:choose>	
		</th>
		<th>PTotal</th>
		
		<th>
			<c:choose>
				    <c:when test="${dir1 == 'AHMEDABAD TO UDAIPUR'}">
				       AMD - UDR
				    </c:when>
				    <c:when test="${dir1 == 'UDAIPUR TO AHMEDABAD'}">
				        UDR - AMD 
				    </c:when>
			</c:choose>	
		</th>
		<th>
			<c:choose>
				    <c:when test="${dir2 == 'AHMEDABAD TO UDAIPUR'}">
				       AMD - UDR
				    </c:when>
				    <c:when test="${dir2 == 'UDAIPUR TO AHMEDABAD'}">
				        UDR - AMD 
				    </c:when>
			</c:choose>	
		</th>
		<th>ETotal</th>
	</tr>
	<br>
	<tr>
		<td>Car/ Jeep</td>
		<td>${dir1_s_car_jeep_count}<br>${dir1_s_car_jeep_tot}</td>
		<td>${dir2_s_car_jeep_count}<br>${dir2_s_car_jeep_tot}</td>
		<td><b>${s_car_jeep_count}</b><br><b>${s_car_jeep_tot}</b></td>
		
		<td>${dir1_r_car_jeep_count}<br>${dir1_r_car_jeep_tot}</td>
		<td>${dir2_r_car_jeep_count}<br>${dir2_r_car_jeep_tot}</td>
		<td><b>${r_car_jeep_count}</b><br><b>${r_car_jeep_tot}</b></td>
		
		<td>${dir1_c_car_jeep_count}<br>${dir1_c_car_jeep_tot}</td>
		<td>${dir2_c_car_jeep_count}<br>${dir2_c_car_jeep_tot}</td>
		<td><b>${c_car_jeep_count}</b><br><b>${c_car_jeep_tot}</b></td>
		
		<td>${dir1_p_car_jeep_count}<br>0.00</td>
		<td>${dir2_p_car_jeep_count}<br>0.00</td>
		<td><b>${p_car_jeep_count}</b><br><b>0.00</b></td>
		
		<td>${dir1_e_car_jeep_count}<br>0.00</td>
		<td>${dir2_e_car_jeep_count}<br>0.00</td>
		<td><b>${e_car_jeep_count}</b><br><b>0.00</b></td>
		
		<td><b>${car_jeep_count}</b><br><b>${car_jeep_tot}</b></td>
	</tr>
	
	<tr>
		<td>LCV</td>
		<td>${dir1_s_lcv_count}<br>${dir1_s_lcv_tot}</td>
		<td>${dir2_s_lcv_count}<br>${dir2_s_lcv_tot}</td>
		<td><b>${s_lcv_count}</b><br><b>${s_lcv_tot}</b></td>
		
		<td>${dir1_r_lcv_count}<br>${dir1_r_lcv_tot}</td>
		<td>${dir2_r_lcv_count}<br>${dir2_r_lcv_tot}</td>
		<td><b>${r_lcv_count}</b><br><b>${r_lcv_tot}</b></td>
		
		<td>${dir1_c_lcv_count}<br>0.00</td>
		<td>${dir2_c_lcv_count}<br>0.00</td>
		<td><b>${c_lcv_count}</b><br><b>0.00</b></td>
		
		<td>${dir1_p_lcv_count}<br>0.00</td>
		<td>${dir2_p_lcv_count}<br>0.00</td>
		<td><b>${p_lcv_count}</b><br><b>0.00</b></td>
		
		<td>${dir1_e_lcv_count}<br>0.00</td>
		<td>${dir2_e_lcv_count}<br>0.00</td>
		<td><b>${e_lcv_count}</b><br><b>0.00</b></td>
		
		<td><b>${lcv_count}</b><br><b>${lcv_tot}</b></td>
	</tr>
	
	<tr>
		<td>BUS/ TRUCK</td>
		<td>${dir1_s_bus_truck_count}<br>${dir1_s_bus_truck_tot}</td>
		<td>${dir2_s_bus_truck_count}<br>${dir2_s_bus_truck_tot}</td>
		<td><b>${s_bus_truck_count}</b><br><b>${s_bus_truck_tot}</b></td>
		
		<td>${dir1_r_bus_truck_count}<br>${dir1_r_bus_truck_tot}</td>
		<td>${dir2_r_bus_truck_count}<br>${dir2_r_bus_truck_tot}</td>
		<td><b>${r_bus_truck_count}</b><br><b>${r_bus_truck_tot}</b></td>
		
		<td>${dir1_c_bus_truck_count}<br>${dir1_c_bus_truck_tot}</td>
		<td>${dir2_c_bus_truck_count}<br>${dir2_c_bus_truck_tot}</td>
		<td><b>${c_bus_truck_count}</b><br><b>${c_bus_truck_tot}</b></td>
		
		<td>${dir1_p_bus_truck_count}<br>0.00</td>
		<td>${dir2_p_bus_truck_count}<br>0.00</td>
		<td><b>${p_bus_truck_count}</b><br><b>0.00</b></td>
		
		<td>${dir1_e_bus_truck_count}<br>0.00</td>
		<td>${dir2_e_bus_truck_count}<br>0.00</td>
		<td><b>${e_bus_truck_count}</b><br><b>0.00</b></td>
		
		<td><b>${bus_truck_count}</b><br><b>${bus_truck_tot}</b></td>
	</tr>
	
	<tr>
		<td>3 AXEL</td>
		<td>${dir1_s_3_axel_count}<br>${dir1_s_3_axel_tot}</td>
		<td>${dir2_s_3_axel_count}<br>${dir2_s_3_axel_tot}</td>
		<td><b>${s_3_axel_count}</b><br><b>${s_3_axel_tot}</b></td>
		
		<td>${dir1_r_3_axel_count}<br>${dir1_r_3_axel_tot}</td>
		<td>${dir2_r_3_axel_count}<br>${dir2_r_3_axel_tot}</td>
		<td><b>${r_3_axel_count}</b><br><b>${r_3_axel_tot}</b></td>
		
		<td>${dir1_c_3_axel_count}<br>${dir1_c_3_axel_tot}</td>
		<td>${dir2_c_3_axel_count}<br>${dir2_c_3_axel_tot}</td>
		<td><b>${c_3_axel_count}</b><br><b>${c_3_axel_tot}</b></td>
		
		<td>${dir1_p_3_axel_count}<br>0.00</td>
		<td>${dir2_p_3_axel_count}<br>0.00</td>
		<td><b>${p_3_axel_count}</b><br><b>0.00</b></td>
		
		<td>${dir1_e_3_axel_count}<br>0.00</td>
		<td>${dir2_e_3_axel_count}<br>0.00</td>
		<td><b>${e_3_axel_count}</b><br>0.00</b></td>
		
		<td><b>${_3_axel_count}</b><br><b>${_3_axel_tot}</b></td>
	</tr>
	
	<tr>
		<td>HCM/ MAV</td>
		<td>${dir1_s_hcm_mav_count}<br>${dir1_s_hcm_mav_tot}</td>
		<td>${dir2_s_hcm_mav_count}<br>${dir2_s_hcm_mav_tot}</td>
		<td><b>${s_hcm_mav_count}</b><br><b>${s_hcm_mav_tot}</b></td>
		
		<td>${dir1_r_hcm_mav_count}<br>${dir1_r_hcm_mav_tot}</td>
		<td>${dir2_r_hcm_mav_count}<br>${dir2_r_hcm_mav_tot}</td>
		<td><b>${r_hcm_mav_count}</b><br><b>${r_hcm_mav_tot}</b></td>
		
		<td>${dir1_c_hcm_mav_count}<br>${dir1_c_hcm_mav_tot}</td>
		<td>${dir2_c_hcm_mav_count}<br>${dir2_c_hcm_mav_tot}</td>
		<td><b>${c_hcm_mav_count}</b><br><b>${c_hcm_mav_tot}</b></td>
		
		<td>${dir1_p_hcm_mav_count}<br>0.00</td>
		<td>${dir2_p_hcm_mav_count}<br>0.00</td>
		<td><b>${p_hcm_mav_count}</b><br><b>0.00</b></td>
		
		<td>${dir1_e_hcm_mav_count}<br>0.00</td>
		<td>${dir2_e_hcm_mav_count}<br>0.00</td>
		<td><b>${e_hcm_mav_count}</b><br><b>0.00</b></td>
		
		<td><b>${hcm_mav_count}</b><br><b>${hcm_mav_tot}</b></td>
	</tr>
	
	<tr>
		<td>OVERSIZED</td>
		<td>${dir1_s_oversized_count}<br>${dir1_s_oversized_tot}</td>
		<td>${dir2_s_oversized_count}<br>${dir2_s_oversized_tot}</td>
		<td><b>${s_oversized_count}</b><br><b>${s_oversized_tot}</b></td>
		
		<td>${dir1_r_oversized_count}<br>${dir1_r_oversized_tot}</td>
		<td>${dir2_r_oversized_count}<br>${dir2_r_oversized_tot}</td>
		<td><b>${r_oversized_count}</b><br><b>${r_oversized_tot}</b></td>
		
		<td>${dir1_c_oversized_count}<br>${dir1_c_oversized_tot}</td>
		<td>${dir2_c_oversized_count}<br>${dir2_c_oversized_tot}</td>
		<td><b>${c_oversized_count}</b><br><b>${c_oversized_tot}</b></td>
		
		<td>${dir1_p_oversized_count}<br>0.00</td>
		<td>${dir2_p_oversized_count}<br>0.00</td>
		<td><b>${p_oversized_count}</b><br><b>0.00</b></td>
		
		<td>${dir1_e_oversized_count}<br>0.00</td>
		<td>${dir2_e_oversized_count}<br>0.00</td>
		<td><b>${e_oversized_count}</b><br><b>0.00</b></td>
		
		<td><b>${oversized_count}</b><br><b>${oversized_tot}</b></td>
	</tr>
	
	<tr>
		<td>TOTAL</td>
		<td>${dir1_s_count}<br>${dir1_s_tot}</td>
		<td>${dir2_s_count}<br>${dir2_s_tot}</td>
		<td><b>${s_count}</b><br><b>${s_tot}</b></td>
		
		<td>${dir1_r_count}<br>${dir1_r_tot}</td>
		<td>${dir2_r_count}<br>${dir2_r_tot}</td>
		<td><b>${r_count}</b><br><b>${r_tot}</b></td>
		
		<td>${dir1_c_count}<br>${dir1_c_tot}</td>
		<td>${dir2_c_count}<br>${dir2_c_tot}</td>
		<td><b>${c_count}</b><br><b>${c_tot}</b></td>
		
		<td>${dir1_p_count}<br>0.00</td>
		<td>${dir2_p_count}<br>0.00</td>
		<td><b>${p_count}</b><br><b>0.00</b></td>
		
		<td>${dir1_e_count}<br>0.00</td>
		<td>${dir2_e_count}<br>0.00</td>
		<td><b>${e_count}</b><br><b>0.00</b></td>
		
		<td><b>${final_count}</b><br><b>${final_tot}</b></td>
		<td></td>
	</tr>
	
</table>

















