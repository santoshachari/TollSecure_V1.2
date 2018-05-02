package com.seoniproject.entity;

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
@Table(name="T_CASHUP")
public class Cashup {
	
	public Cashup() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CASHUP_ID")
	private Integer cashUpId;
	
	@Column(name="USER_ID")
	private Integer userId;
	
	@Column(name="SHIFT_ID")
	private Integer shiftId;
	
	@Column(name="TOLL_PLAZA_ID")
	private Integer tollPlazaId;
	
	@Column(name="LANE_ID")
	private Integer laneId;
	
	@Column(name="ACCOUNTANT_ID")
	private Integer accountantId;
	
	@Column(name="CHECK_DATE", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkDate;
	
	@Column(name="TOTAL_AMOUNT")
	private Float totalAmt;
	
	@Column(name="SYSTEM_AMOUNT")
	private Float systemAmount;
	
	@Column(name="SHORTAGE_AMOUNT")
	private Float shortageAmount;
	
	@Column(name="EXCESS_AMOUNT")
	private Float excessAmount;
	
	@Column(name="CREATE_TIMESTAMP", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTimeStamp;
	
	@Column(name="CREATE_USER_ID")
	private Integer createUserId;
	
	@Column(name="MODIFIED_USER_ID")
	private Integer modifiedUserId;
	
	@Column(name="MODIFICATION_TIMESTAMP", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modificationTimeStamp;

	public Integer getCashUpId() {
		return cashUpId;
	}

	public void setCashUpId(Integer cashUpId) {
		this.cashUpId = cashUpId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getShiftId() {
		return shiftId;
	}

	public void setShiftId(Integer shiftId) {
		this.shiftId = shiftId;
	}

	public Integer getTollPlazaId() {
		return tollPlazaId;
	}

	public void setTollPlazaId(Integer tollPlazaId) {
		this.tollPlazaId = tollPlazaId;
	}

	public Integer getLaneId() {
		return laneId;
	}

	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}

	public Integer getAccountantId() {
		return accountantId;
	}

	public void setAccountantId(Integer accountantId) {
		this.accountantId = accountantId;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Float getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Float totalAmt) {
		this.totalAmt = totalAmt;
	}

	public Float getSystemAmount() {
		return systemAmount;
	}

	public void setSystemAmount(Float systemAmount) {
		this.systemAmount = systemAmount;
	}

	public Float getShortageAmount() {
		return shortageAmount;
	}

	public void setShortageAmount(Float shortageAmount) {
		this.shortageAmount = shortageAmount;
	}

	public Float getExcessAmount() {
		return excessAmount;
	}

	public void setExcessAmount(Float excessAmount) {
		this.excessAmount = excessAmount;
	}

	public Date getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(Date createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
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

	@Override
	public String toString() {
		return "Cashup [cashUpId=" + cashUpId + ", userId=" + userId + ", shiftId=" + shiftId + ", tollPlazaId="
				+ tollPlazaId + ", accountantId=" + accountantId + ", checkDate=" + checkDate + ", totalAmt=" + totalAmt
				+ ", systemAmount=" + systemAmount + ", shortageAmount=" + shortageAmount + ", excessAmount="
				+ excessAmount + ", createTimeStamp=" + createTimeStamp + ", createUserId=" + createUserId
				+ ", modifiedUserId=" + modifiedUserId + ", modificationTimeStamp=" + modificationTimeStamp + "]";
	}
	
}



















