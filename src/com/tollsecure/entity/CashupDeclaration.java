package com.tollsecure.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="T_CASHUP_DECLARATION")
public class CashupDeclaration {

	public CashupDeclaration() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="DECLARATION_ID")
	private Integer declarationId;
	
	@Column(name="USER_ID")
	private Integer userId;
	
	@Column(name="TOLL_PLAZA_ID")
	private Integer tollPlazaId;
	
	@Column(name="SHIFT_ID")
	private Integer shiftId;
	
	@Column(name="LANE_ID")
	private Integer laneId;
	
	@Column(name="D_2000")
	private Integer d2000;
	
	@Column(name="D_1000")
	private Integer d1000;
	
	@Column(name="D_500")
	private Integer d500;
	
	@Column(name="D_200")
	private Integer d200;
		
	@Column(name="D_100")
	private Integer d100;
	
	@Column(name="D_50")
	private Integer d50;
	
	@Column(name="D_20")
	private Integer d20;
	
	@Column(name="D_10")
	private Integer d10;
	
	@Column(name="D_5")
	private Integer d5;
	
	@Column(name="D_2")
	private Integer d2;
	
	@Column(name="D_1")
	private Integer d1;
	
	@Column(name="TOTAL_COUNT")
	private Integer totalCount;
	
	@Column(name="TOTAL_AMOUNT")
	private Integer totalAmount;
	
	@Column(name="CHECK_DATE", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkDate;
	
	@Column(name="ACCOUNTANT_ID")
	private Integer accountantId;
	
	@Column(name="CREATE_TIMESTAMP", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTimeStamp;
	
	@Column(name="CREATE_USER_ID")
	private Integer createUserID;
	
	@Column(name="MODIFIED_USER_ID")
	private Integer modifiedUserId;
	
	@Column(name="MODIFICATION_TIMESTAMP", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modificationTimeStamp;
	
	@Column(name="SHIFT_DESC")
	private String shiftDesc;

	public Integer getDeclarationId() {
		return declarationId;
	}

	public void setDeclarationId(Integer declarationId) {
		this.declarationId = declarationId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTollPlazaId() {
		return tollPlazaId;
	}

	public void setTollPlazaId(Integer tollPlazaId) {
		this.tollPlazaId = tollPlazaId;
	}

	public Integer getShiftId() {
		return shiftId;
	}

	public void setShiftId(Integer shiftId) {
		this.shiftId = shiftId;
	}

	public Integer getLaneId() {
		return laneId;
	}

	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}

	public Integer getD2000() {
		return d2000;
	}

	public void setD2000(Integer d2000) {
		this.d2000 = d2000;
	}

	public Integer getD1000() {
		return d1000;
	}

	public void setD1000(Integer d1000) {
		this.d1000 = d1000;
	}

	public Integer getD500() {
		return d500;
	}

	public void setD500(Integer d500) {
		this.d500 = d500;
	}

	public Integer getD200() {
		return d200;
	}

	public void setD200(Integer d200) {
		this.d200 = d200;
	}

	public Integer getD100() {
		return d100;
	}

	public void setD100(Integer d100) {
		this.d100 = d100;
	}

	public Integer getD50() {
		return d50;
	}

	public void setD50(Integer d50) {
		this.d50 = d50;
	}

	public Integer getD20() {
		return d20;
	}

	public void setD20(Integer d20) {
		this.d20 = d20;
	}

	public Integer getD10() {
		return d10;
	}

	public void setD10(Integer d10) {
		this.d10 = d10;
	}

	public Integer getD5() {
		return d5;
	}

	public void setD5(Integer d5) {
		this.d5 = d5;
	}

	public Integer getD2() {
		return d2;
	}

	public void setD2(Integer d2) {
		this.d2 = d2;
	}

	public Integer getD1() {
		return d1;
	}

	public void setD1(Integer d1) {
		this.d1 = d1;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Integer getAccountantId() {
		return accountantId;
	}

	public void setAccountantId(Integer accountantId) {
		this.accountantId = accountantId;
	}

	public Date getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(Date createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}

	public Integer getCreateUserID() {
		return createUserID;
	}

	public void setCreateUserID(Integer createUserID) {
		this.createUserID = createUserID;
	}

	public Integer getModifiedUserId() {
		return modifiedUserId;
	}

	public void setModifiedUserId(Integer modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public Date getModificationTimeStamp() {
		return modificationTimeStamp;
	}

	public void setModificationTimeStamp(Date modificationTimeStamp) {
		this.modificationTimeStamp = modificationTimeStamp;
	}

	public String getShiftDesc() {
		return shiftDesc;
	}

	public void setShiftDesc(String shiftDesc) {
		this.shiftDesc = shiftDesc;
	}

	@Override
	public String toString() {
		return "CashupDeclaration [declarationId=" + declarationId + ", userId=" + userId + ", tollPlazaId="
				+ tollPlazaId + ", shiftId=" + shiftId + ", laneId=" + laneId + ", d2000=" + d2000 + ", d500=" + d500
				+ ", d200=" + d200 + ", d100=" + d100 + ", d50=" + d50 + ", d20=" + d20 + ", d10=" + d10 + ", d5=" + d5
				+ ", d2=" + d2 + ", d1=" + d1 + ", totalCount=" + totalCount + ", totalAmount=" + totalAmount
				+ ", checkDate=" + checkDate + ", accountantId=" + accountantId + ", createTimeStamp=" + createTimeStamp
				+ ", createUserID=" + createUserID + ", modifiedUserId=" + modifiedUserId + ", modificationTimeStamp="
				+ modificationTimeStamp + ", shiftDesc=" + shiftDesc + "]";
	}


	
}



























