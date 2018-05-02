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
@Table(name="T_CONCESSION")
public class ConcessionVehicles {

	public ConcessionVehicles() {
		super();
	}

	@Column(name="VEHICLE_NUMBER")
	private String vehicleNumber;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CONCESSION_ID")
	private Integer concessionId;
	
	@Column(name="TOLL_PLAZA_ID")
	private Integer tollPlazaId;
	
	@Column(name="CONCESSION_CATEGORY")
	private String concessionCategory;
	
	@Column(name="CONCESSION_PCT")
	private Float concessionPct;
	
	@Column(name="CONCESSION_AMT")
	private Float concessionAmt;
	
	@Column(name="START_DATE", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Column(name="END_DATE", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	@Column(name="COMMENTS")
	private String comments;
	
	@Column(name="CREATE_TIMESTAMP", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTimeStamp;
	
	@Column(name="CREATE_USER_ID")
	private Integer createUserId;
	
	@Column(name="MODIFIED_USER_ID")
	private Integer modifiedUserId;
	
	@Column(name="MODIFICATION_TIMESTAMP", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp;

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Integer getRecordId() {
		return concessionId;
	}

	public void setRecordId(Integer recordId) {
		this.concessionId = recordId;
	}

	public Integer getTollPlazaId() {
		return tollPlazaId;
	}

	public void setTollPlazaId(Integer tollPlazaId) {
		this.tollPlazaId = tollPlazaId;
	}

	public String getConcessionCategory() {
		return concessionCategory;
	}

	public void setConcessionCategory(String concessionCategory) {
		this.concessionCategory = concessionCategory;
	}

	public Float getConcessionPct() {
		return concessionPct;
	}

	public void setConcessionPct(Float concessionPct) {
		this.concessionPct = concessionPct;
	}

	public Float getConcessionAmt() {
		return concessionAmt;
	}

	public void setConcessionAmt(Float concessionAmt) {
		this.concessionAmt = concessionAmt;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Integer getConcessionId() {
		return concessionId;
	}

	public void setConcessionId(Integer concessionId) {
		this.concessionId = concessionId;
	}

	@Override
	public String toString() {
		return "ConcessionVehicles [vehicleNumber=" + vehicleNumber + ", recordId=" + concessionId + ", tollPlazaId="
				+ tollPlazaId + ", concessionCategory=" + concessionCategory + ", concessionPct=" + concessionPct
				+ ", concessionAmt=" + concessionAmt + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", comments=" + comments + ", createTimeStamp=" + createTimeStamp + ", createUserId=" + createUserId
				+ ", modifiedUserId=" + modifiedUserId + ", timeStamp=" + timeStamp + "]";
	}
	
}



















