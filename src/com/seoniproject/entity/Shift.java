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
@Table(name="T_SHIFT")
public class Shift {

	public Shift() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SHIFT_ID")
	private Integer shiftId;
	
	@Column(name="TOLL_PLAZA_ID")
	private Integer tollPlazaId;
	
	@Column(name="SHIFT_DESC")
	private String shiftDesc;
	
	@Column(name="START_TIME")
	@Temporal(TemporalType.TIME)
	private Date startTime;
	
	@Column(name="END_TIME")
	@Temporal(TemporalType.TIME)
	private Date endTime;
	
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

	@Column(name="STATUS_FLAG")
	private String statusFlag;
	
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

	public String getShiftDesc() {
		return shiftDesc;
	}

	public void setShiftDesc(String shiftDesc) {
		this.shiftDesc = shiftDesc;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(Date currentTimeStamp) {
		this.createTimeStamp = currentTimeStamp;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer currentUserId) {
		this.createUserId = currentUserId;
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

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	@Override
	public String toString() {
		return "Shift [shiftId=" + shiftId + ", tollPlazaId=" + tollPlazaId + ", shiftDesc=" + shiftDesc
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", createTimeStamp=" + createTimeStamp
				+ ", createUserId=" + createUserId + ", modifiedUserId=" + modifiedUserId + ", modificationTimeStamp="
				+ modificationTimeStamp + ", statusFlag=" + statusFlag + "]";
	}
	
}
