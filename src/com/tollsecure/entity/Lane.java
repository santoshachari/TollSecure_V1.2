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
@Table(name="T_LANE")
public class Lane {

	public Lane() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="LANE_ID")
	private Integer laneId;
	
	@Column(name="TOLL_PLAZA_ID")
	private Integer tollPlazaId;
	
	@Column(name="LANE_DIRECTION")
	private String laneDirection;
	
	@Column(name="LANE_CODE")
	private String laneCode;
	
	@Column(name="STATUS_FLAG")
	private String statusFlag;
	
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
	
	@Column(name="USER_ID")
	private Integer userId;
	
	public Integer getLaneId() {
		return laneId;
	}

	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}

	public Integer getTollPlazaId() {
		return tollPlazaId;
	}

	public void setTollPlazaId(Integer tollPlazaId) {
		this.tollPlazaId = tollPlazaId;
	}

	public String getLaneDirection() {
		return laneDirection;
	}

	public void setLaneDirection(String laneDirection) {
		this.laneDirection = laneDirection;
	}

	public String getLaneCode() {
		return laneCode;
	}

	public void setLaneCode(String laneCode) {
		this.laneCode = laneCode;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Lane [laneId=" + laneId + ", tollPlazaId=" + tollPlazaId + ", laneDirection=" + laneDirection
				+ ", createTimeStamp=" + createTimeStamp + ", createUserId=" + createUserId + ", modifiedUserId="
				+ modifiedUserId + ", modificationTimeStamp=" + modificationTimeStamp + ", userId=" + userId + "]";
	}

}
