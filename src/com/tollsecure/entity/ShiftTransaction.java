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
@Table(name="T_SHIFT_TRANSACTION")
public class ShiftTransaction {

	public ShiftTransaction() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SHIFT_TRANSACTION_ID")
	private Integer shiftTransactionId;
	
	@Column(name="TOLL_PLAZA_ID")
	private Integer tollPlazaId;
	
	@Column(name="LANE_ID")
	private Integer laneId;
	
	@Column(name="USER_ID")
	private Integer userId;
	
	@Column(name="SHIFT_ID")
	private Integer shiftId;
	
	@Column(name="PUNCH_IN_TIME", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date punchInTime;
	
	@Column(name="PUNCH_OUT_TIME", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date punchOutTime;
	
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

	public Integer getShiftTransactionId() {
		return shiftTransactionId;
	}

	public void setShiftTransactionId(Integer shiftTransactionId) {
		this.shiftTransactionId = shiftTransactionId;
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

	public Date getPunchInTime() {
		return punchInTime;
	}

	public void setPunchInTime(Date punchInTime) {
		this.punchInTime = punchInTime;
	}

	public Date getPunchOutTime() {
		return punchOutTime;
	}

	public void setPunchOutTime(Date punchOutTime) {
		this.punchOutTime = punchOutTime;
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
		return "ShiftTransaction [shiftTransactionId=" + shiftTransactionId + ", tollPlazaId=" + tollPlazaId
				+ ", laneId=" + laneId + ", userId=" + userId + ", shiftId=" + shiftId + ", punchInTime=" + punchInTime
				+ ", punchOutTime=" + punchOutTime + ", createTimeStamp=" + createTimeStamp + ", createUserId="
				+ createUserId + ", modifiedUserId=" + modifiedUserId + ", modificationTimeStamp="
				+ modificationTimeStamp + "]";
	}
	
	
}













