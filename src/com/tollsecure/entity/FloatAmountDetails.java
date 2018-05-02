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
@Table(name="T_FLOAT_AMOUNT_DETAILS")
public class FloatAmountDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="FLOAT_DETAIL_ID")
	private Integer floatDetailId;
	
	@Column(name="TOLL_PLAZA_ID")
	private Integer tollPlazaId;
	
	@Column(name="SHIFT_ID")
	private Integer shiftId;
	
	@Column(name="LANE_ID")
	private Integer laneId;
	
	@Column(name="USER_ID")
	private Integer userId;
	
	@Column(name="T_DATE", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date tDate;
	
	@Column(name="FLOAT_AMOUNT")
	private Float floatAmount;
	
	@Column(name="ACCOUNTANT_ID")
	private Integer accountantId;
	
	@Column(name="STATUS")
	private Character status;
	
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

	public Integer getFloatDetailId() {
		return floatDetailId;
	}

	public void setFloatDetailId(Integer floatDetailId) {
		this.floatDetailId = floatDetailId;
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

	public Integer getShiftId() {
		return shiftId;
	}

	public void setShiftId(Integer shiftId) {
		this.shiftId = shiftId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date gettDate() {
		return tDate;
	}

	public void settDate(Date tDate) {
		this.tDate = tDate;
	}

	public Float getFloatAmount() {
		return floatAmount;
	}

	public void setFloatAmount(Float floatAmount) {
		this.floatAmount = floatAmount;
	}

	public Integer getAccountantId() {
		return accountantId;
	}

	public void setAccountantId(Integer accountantId) {
		this.accountantId = accountantId;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
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
		return "FloatAmountDetails [floatDetailId=" + floatDetailId + ", tollPlazaId=" + tollPlazaId + ", laneId="
				+ laneId + ", userId=" + userId + ", tDate=" + tDate + ", floatAmount=" + floatAmount
				+ ", accountantId=" + accountantId + ", status=" + status + ", createTimeStamp=" + createTimeStamp
				+ ", createUserId=" + createUserId + ", modifiedUserId=" + modifiedUserId + ", modificationTimeStamp="
				+ modificationTimeStamp + "]";
	}
		
}

















