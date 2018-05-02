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
@Table(name="T_TOLL_CONFIG")
public class TollConfig {
	
	public TollConfig() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="VEHICLE_CLASS_ID")
	private Integer vehicleClassId;
	
	@Column(name="VEHICLE_CLASS")
	private String vehicleClass;
	
	@Column(name="TOLL_PLAZA_ID")
	private Integer tollPlazaId;
	
	@Column(name="JOURNEY_TYPE")
	private String journeyType;
	
	@Column(name="TOLL_AMT")
	private Float tollAmt;
	
	@Column(name="EFFECTIVE_FROM", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveFrom;
	
	@Column(name="EFFECTIVE_TO", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveTo;

	@Column(name="CREATE_USER_ID")
	private Integer createUserId;
	
	@Column(name="CREATE_TIMESTAMP", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTimeStamp;
	
	@Column(name="MODIFIED_USER_ID")
	private Integer modifiedUserId;
	
	@Column(name="MODIFICATION_TIMESTAMP", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modificationTimeStamp;
	
	@Column(name="STATUS_FLAG")
	private String statusFlag;
	
	
	public Integer getVehicleClassId() {
		return vehicleClassId;
	}

	public void setVehicleClassId(Integer vehicleClassId) {
		this.vehicleClassId = vehicleClassId;
	}

	public String getJourneyType() {
		return journeyType;
	}

	public void setJourneyType(String journeyType) {
		this.journeyType = journeyType;
	}

	public Float getTollAmt() {
		return tollAmt;
	}

	public void setTollAmt(Float tollAmt) {
		this.tollAmt = tollAmt;
	}

	public String getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	public Integer getTollPlazaId() {
		return tollPlazaId;
	}

	public void setTollPlazaId(Integer tollPlazaId) {
		this.tollPlazaId = tollPlazaId;
	}

	public Date getEffectiveFrom() {
		return effectiveFrom;
	}

	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public Date getEffectiveTo() {
		return effectiveTo;
	}

	public void setEffectiveTo(Date effectiveTo) {
		this.effectiveTo = effectiveTo;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(Date createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
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
		return "TollConfig [vehicleClassId=" + vehicleClassId + ", vehicleClass=" + vehicleClass + ", tollPlazaId="
				+ tollPlazaId + ", journeyType=" + journeyType + ", tollAmt=" + tollAmt + ", effectiveFrom="
				+ effectiveFrom + ", effectiveTo=" + effectiveTo + ", createUserId=" + createUserId
				+ ", createTimeStamp=" + createTimeStamp + ", modifiedUserId=" + modifiedUserId
				+ ", modificationTimeStamp=" + modificationTimeStamp + ", statusFlag=" + statusFlag + "]";
	}
	
	
}
