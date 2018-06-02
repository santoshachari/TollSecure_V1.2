package com.tollsecure.entity;

import java.sql.Blob;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="T_TOLL_TRANSACTION")
public class TollTransaction {

	public TollTransaction() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TRANSACTION_ID")
	private Integer transactionId;
	
	@Column(name="TICKET_CODE")
	private String ticketCode;
	
	@Column(name="TOLL_PLAZA_ID")
	private Integer tollPlazaId;
	
	@Column(name="TRANSACTION_DATE")
	private Date transactionDate;
	
	@Column(name="TRANSACTION_TIMESTAMP", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionTimeStamp;
	
	@Column(name="SHIFT_TRANSACTION_ID")
	private Integer shiftTransactionId;
	
	@Column(name="VEHICLE_NUMBER")
	private String vehicleNumber;
	
	@Column(name="LANE_ID")
	private Integer laneId;
	
	@Column(name="USER_ID")
	private Integer userId;
	
	@Column(name="SHIFT_ID")
	private Integer shiftId;
	
	@Column(name="VEHICLE_CLASS_ID")
	private Integer vehicleClassId; 
	
	@Column(name="JOURNEY_TYPE")
	private String journeyType;
	
	@Column(name="CONCESSION_TYPE")
	private String concessionType;
	
	@Column(name="TOLL_AMT")
	private Float tollAmt;
	
	@Column(name="PARENT_TRANSACTION_ID")
	private Integer parentTransactionId;
	
	@Column(name="RECORD_IND")
	private String recordInd;
	
	@Column(name="COMMENTS")
	private String comments;
	
	@Column(name="CANCELLATION_CODE")
	private String cancellationCode;
	
	@Lob
    @Column(name="IMAGE_BLOB", columnDefinition="mediumblob")
    private byte[] imageBlob;
	
	@Column(name="VIDEO_BLOB")
	private byte[] videoBlob;
	
	@Column(name="PAYMENT_METHOD")
	private String paymentMethod;
	
	@Column(name="CLIENT_ID")
	private Integer clientId;
	
	@Column(name="CLIENT_IP_ADDRESS")
	private String clientIpAddress;
	
	@Column(name="CREATE_TIMESTAMP")
	private Date createTimeStamp;
	
	@Column(name="CREATE_USER_ID")
	private Integer createUserID;
	
	@Column(name="MODIFIED_USER_ID")
	private Integer modifiedUserID;

	@Column(name="MODIFICATION_TIMESTAMP")
	private Date modificationTimeStamp;
	
	@Column(name="SHIFT_DESC")
	private String shiftDescription;
	
	@Column(name="IMAGE_ADDRESS")
	private String imageAddress;

	@Column(name="PASS_ID")
	private Integer passId;
	
	@Column(name="Exempt_ID")
	private Integer exemptId;
	
	@Column(name="concession_id")
	private Integer concessionId;
	
	@Column(name="Img_Captured_YN")
	private String imageCaptured;
	
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getTicketCode() {
		return ticketCode;
	}

	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}

	public Date getTransactionTimeStamp() {
		return transactionTimeStamp;
	}

	public void setTransactionTimeStamp(Date transactionTimeStamp) {
		this.transactionTimeStamp = transactionTimeStamp;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Float getTollAmt() {
		return tollAmt;
	}

	public void setTollAmt(Float tollAmt) {
		this.tollAmt = tollAmt;
	}

	public String getConcessionType() {
		return concessionType;
	}

	public void setConcessionType(String concessionType) {
		this.concessionType = concessionType;
	}

	public Integer getVehicleClassId() {
		return vehicleClassId;
	}

	public void setVehicleClassId(Integer vehicleClassId) {
		this.vehicleClassId = vehicleClassId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public String getJourneyType() {
		return journeyType;
	}

	public void setJourneyType(String journeyType) {
		this.journeyType = journeyType;
	}

	public Integer getShiftId() {
		return shiftId;
	}

	public void setShiftId(Integer shiftId) {
		this.shiftId = shiftId;
	}

	public String getShiftDescription() {
		return shiftDescription;
	}

	public void setShiftDescription(String shiftDescription) {
		this.shiftDescription = shiftDescription;
	}

	public Integer getTollPlazaId() {
		return tollPlazaId;
	}

	public void setTollPlazaId(Integer tollPlazaId) {
		this.tollPlazaId = tollPlazaId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Integer getShiftTransactionId() {
		return shiftTransactionId;
	}

	public void setShiftTransactionId(Integer shiftTransactionId) {
		this.shiftTransactionId = shiftTransactionId;
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

	public Integer getParentTransactionId() {
		return parentTransactionId;
	}

	public void setParentTransactionId(Integer parentTransactionId) {
		this.parentTransactionId = parentTransactionId;
	}

	public String getRecordInd() {
		return recordInd;
	}

	public void setRecordInd(String recordInd) {
		this.recordInd = recordInd;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCancellationCode() {
		return cancellationCode;
	}

	public void setCancellationCode(String cancellationCode) {
		this.cancellationCode = cancellationCode;
	}

	public byte[] getImageBlob() {
		return imageBlob;
	}

	public void setImageBlob(byte[] imageBlob) {
		this.imageBlob = imageBlob;
	}

	public byte[] getVideoBlob() {
		return videoBlob;
	}

	public void setVideoBlob(byte[] videoBlob) {
		this.videoBlob = videoBlob;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getClientIpAddress() {
		return clientIpAddress;
	}

	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
	}

	public Date getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(Date createTimeStamo) {
		this.createTimeStamp = createTimeStamo;
	}

	public Integer getCreateUserID() {
		return createUserID;
	}

	public void setCreateUserID(Integer createUserID) {
		this.createUserID = createUserID;
	}

	public Integer getModifiedUserID() {
		return modifiedUserID;
	}

	public void setModifiedUserID(Integer modifiedUserID) {
		this.modifiedUserID = modifiedUserID;
	}

	public Date getModificationTimeStamp() {
		return modificationTimeStamp;
	}

	public void setModificationTimeStamp(Date modificationTimeStamp) {
		this.modificationTimeStamp = modificationTimeStamp;
	}

	public String getImageAddress() {
		return imageAddress;
	}

	public void setImageAddress(String imageAddress) {
		this.imageAddress = imageAddress;
	}

	public Integer getPassId() {
		return passId;
	}

	public void setPassId(Integer passId) {
		this.passId = passId;
	}

	public Integer getExemptId() {
		return exemptId;
	}

	public void setExemptId(Integer exemptId) {
		this.exemptId = exemptId;
	}

	public Integer getConcessionId() {
		return concessionId;
	}

	public void setConcessionId(Integer concessionId) {
		this.concessionId = concessionId;
	}

	public String getImageCaptured() {
		return imageCaptured;
	}

	public void setImageCaptured(String imageCaptured) {
		this.imageCaptured = imageCaptured;
	}

	@Override
	public String toString() {
		return "TollTransaction [transactionId=" + transactionId + ", ticketCode=" + ticketCode + ", tollPlazaId="
				+ tollPlazaId + ", transactionDate=" + transactionDate + ", transactionTimeStamp="
				+ transactionTimeStamp + ", shiftTransactionId=" + shiftTransactionId + ", vehicleNumber="
				+ vehicleNumber + ", laneId=" + laneId + ", userId=" + userId + ", shiftId=" + shiftId
				+ ", vehicleClassId=" + vehicleClassId + ", journeyType=" + journeyType + ", concessionType="
				+ concessionType + ", tollAmt=" + tollAmt + ", parentTransactionId=" + parentTransactionId
				+ ", recordInd=" + recordInd + ", comments=" + comments + ", cancellationCode=" + cancellationCode
				+ ", imageBlob=" + Arrays.toString(imageBlob) + ", videoBlob=" + Arrays.toString(videoBlob)
				+ ", paymentMethod=" + paymentMethod + ", clientId=" + clientId + ", clientIpAddress=" + clientIpAddress
				+ ", createTimeStamp=" + createTimeStamp + ", createUserID=" + createUserID + ", modifiedUserID="
				+ modifiedUserID + ", modificationTimeStamp=" + modificationTimeStamp + ", shiftDescription="
				+ shiftDescription + ", imageAddress=" + imageAddress + ", passId=" + passId + ", exemptId=" + exemptId
				+ ", concessionId=" + concessionId + ", imageCaptured=" + imageCaptured + "]";
	}


}
