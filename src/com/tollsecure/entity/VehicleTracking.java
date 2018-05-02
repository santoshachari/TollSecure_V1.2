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
@Table(name="T_VEHICLE_TRACKING")
public class VehicleTracking {

	public VehicleTracking() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TRACKING_ID")
	Integer trackingId;
	
	@Column(name="VEHICLE_NUMBER")
	String vehicleNumber;
	
	@Column(name="LANE_DIRECTION")
	String laneDirection;
	
	@Column(name="VEHICLE_CLASS_ID")
	Integer vehicleClassId;

	public Integer getVehicleClassId() {
		return vehicleClassId;
	}

	public void setVehicleClassId(Integer vehicleClassId) {
		this.vehicleClassId = vehicleClassId;
	}
	
	@Column(name="JOURNEY_TYPE")
	private String journeyType;

	public String getJourneyType() {
		return journeyType;
	}

	public void setJourneyType(String journeyType) {
		this.journeyType = journeyType;
	}

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

	public Integer getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(Integer trackingId) {
		this.trackingId = trackingId;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getLaneDirection() {
		return laneDirection;
	}

	public void setLaneDirection(String laneDirection) {
		this.laneDirection = laneDirection;
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
		return "VehicleTracking [trackingId=" + trackingId + ", vehicleNumber=" + vehicleNumber + ", laneDirection="
				+ laneDirection + ", createTimeStamp=" + createTimeStamp + ", createUserId=" + createUserId
				+ ", modifiedUserId=" + modifiedUserId + ", modificationTimeStamp=" + modificationTimeStamp + "]";
	}
	
	
}
