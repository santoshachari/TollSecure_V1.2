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
@Table(name="T_PASS")
public class Pass {
	
	public Pass() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PASS_ID")
	private Integer passId;
	
	@Column(name="TOLL_PLAZA_ID")
	private Integer tollPlazaId;
	
	@Column(name="VEHICLE_CLASS")
	private String vehicleClass;
	
	@Column(name="PASS_TYPE")
	private String passType;
	
	@Column(name="AMOUNT")
	private Float amount;
	
	@Column(name="PERIOD")
	private String period;
	
	@Column(name="CANCELLATION_CODE")
	private String cancellationCode;
	
	@Column(name="REMARKS")
	private String remarks;
	
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	@Column(name="VEHICLE_NUMBER")
	private String vehicleNumber;
	
	@Column(name="START_DATE", columnDefinition="DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveFrom;
	
	@Column(name="END_DATE", columnDefinition="DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveTo;
	
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

	@Column(name="trip_count")
	private Integer tripCount;
	
	@Column(name="trip_validity")
	private Integer tripValidity;
	
	
	public String getCancellationCode() {
		return cancellationCode;
	}

	public void setCancellationCode(String cancellationCode) {
		this.cancellationCode = cancellationCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getPassId() {
		return passId;
	}

	public void setPassId(Integer passId) {
		this.passId = passId;
	}

	public Integer getTollPlazaId() {
		return tollPlazaId;
	}

	public void setTollPlazaId(Integer tollPlazaId) {
		this.tollPlazaId = tollPlazaId;
	}

	public String getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
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

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
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

	public Integer getTripCount() {
		return tripCount;
	}

	public void setTripCount(Integer tripCount) {
		this.tripCount = tripCount;
	}

	public Integer getTripValidity() {
		return tripValidity;
	}

	public void setTripValidity(Integer tripValidity) {
		this.tripValidity = tripValidity;
	}

	@Override
	public String toString() {
		return "Pass [passId=" + passId + ", tollPlazaId=" + tollPlazaId + ", vehicleClass=" + vehicleClass
				+ ", passType=" + passType + ", amount=" + amount + ", vehicleNumber=" + vehicleNumber
				+ ", effectiveFrom=" + effectiveFrom + ", effectiveTo=" + effectiveTo + ", createTimeStamp="
				+ createTimeStamp + ", createUserId=" + createUserId + ", modifiedUserId=" + modifiedUserId
				+ ", modificationTimeStamp=" + modificationTimeStamp + "]";
	}
	
}














