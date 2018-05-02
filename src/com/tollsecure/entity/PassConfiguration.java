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
@Table(name="T_PASS_CONFIGURATION")
public class PassConfiguration {

	public PassConfiguration() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PASS_CONFIGURATION_ID")
	private Integer passConfigurationId;
	
	@Column(name="TOLL_PLAZA_ID")
	private Integer tollPlazaId;
	
	@Column(name="VEHICLE_CLASS_ID")
	private Integer vehicleClassId;
	
	@Column(name="PASS_TYPE")
	private String passType;
	
	@Column(name="AMT")
	private Float amount;
	
	@Column(name="VEHICLE_CLASS")
	private String vehicleClass;
	
	@Column(name="EFFECTIVE_FROM", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveFrom;
	
	@Column(name="EFFECTIVE_TO", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveTo;
	
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

	public Integer getPassConfigurationId() {
		return passConfigurationId;
	}

	public void setPassConfigurationId(Integer passConfigurationId) {
		this.passConfigurationId = passConfigurationId;
	}

	public Integer getTollPlazaId() {
		return tollPlazaId;
	}

	public void setTollPlazaId(Integer tollPlazaId) {
		this.tollPlazaId = tollPlazaId;
	}

	public Integer getVehicleClassId() {
		return vehicleClassId;
	}

	public void setVehicleClassId(Integer vehicleClassId) {
		this.vehicleClassId = vehicleClassId;
	}

	public String getPassType() {
		return passType;
	}

	public void setPassType(String passType) {
		this.passType = passType;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
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
	
	public String getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	@Override
	public String toString() {
		return "PassConfiguration [passConfigurationId=" + passConfigurationId + ", tollPlazaId=" + tollPlazaId
				+ ", vehicleClassId=" + vehicleClassId + ", passType=" + passType + ", amount=" + amount
				+ ", vehicleClass=" + vehicleClass + ", effectiveFrom=" + effectiveFrom + ", effectiveTo=" + effectiveTo
				+ ", statusFlag=" + statusFlag + ", createTimeStamp=" + createTimeStamp + ", createUserId="
				+ createUserId + ", modifiedUserId=" + modifiedUserId + ", modificationTimeStamp="
				+ modificationTimeStamp + "]";
	}
		
}





