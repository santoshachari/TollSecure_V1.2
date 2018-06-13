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
@Table(name="T_AVC")
public class AVC {

	public AVC() {
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="avc_id")
	private Integer avcId;
	
	@Column(name="avc_txn_number")
	private Integer avcTxnNumber;
	
	@Column(name="avc_date", columnDefinition="DATE")
	@Temporal(TemporalType.DATE)
	private Date avcDate;
	
	@Column(name="avc_time", columnDefinition="TIME")
	@Temporal(TemporalType.TIME)
	private Date avcTime;
	
	@Column(name="avc_direction")
	private String avcDirection;
	
	@Column(name="avc_axle_count")
	private Integer avcAxleCount;
	
	@Column(name="avc_wheel_base")
	private Integer avcWheelBase;
	
	@Column(name="avc_height_sensor_1")
	private String avcHeightSensor1;
	
	@Column(name="avc_height_sensor_2")
	private String avcHeightSensor2;
	
	@Column(name="avc_height_sensor_3")
	private String avcHeightSensor3;
	
	@Column(name="avc_height_sensor_4")
	private String avcHeightSensor4;
	
	@Column(name="avc_vehicle_class")
	private String avcVehicleClass;
	
	@Column(name="avc_error_status")
	private Integer avcErrorStatus;
	
	@Column(name="avc_sensor_alignment_status")
	private Integer avcSensorAlignmentStatus;
	
	@Column(name="transaction_id")
	private Integer transactionId;
	
	@Column(name="create_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTimeStamp;

	public Integer getAvcId() {
		return avcId;
	}

	public void setAvcId(Integer avcId) {
		this.avcId = avcId;
	}

	public Integer getAvcTxnNumber() {
		return avcTxnNumber;
	}

	public void setAvcTxnNumber(Integer avcTxnNumber) {
		this.avcTxnNumber = avcTxnNumber;
	}

	public Date getAvcDate() {
		return avcDate;
	}

	public void setAvcDate(Date avcDate) {
		this.avcDate = avcDate;
	}

	public Date getAvcTime() {
		return avcTime;
	}

	public void setAvcTime(Date avcTime) {
		this.avcTime = avcTime;
	}

	public String getAvcDirection() {
		return avcDirection;
	}

	public void setAvcDirection(String avcDirection) {
		this.avcDirection = avcDirection;
	}

	public Integer getAvcAxleCount() {
		return avcAxleCount;
	}

	public void setAvcAxleCount(Integer avcAxleCount) {
		this.avcAxleCount = avcAxleCount;
	}

	public Integer getAvcWheelBase() {
		return avcWheelBase;
	}

	public void setAvcWheelBase(Integer avcWheelBase) {
		this.avcWheelBase = avcWheelBase;
	}

	public String getAvcHeightSensor1() {
		return avcHeightSensor1;
	}

	public void setAvcHeightSensor1(String avcHeightSensor1) {
		this.avcHeightSensor1 = avcHeightSensor1;
	}

	public String getAvcHeightSensor2() {
		return avcHeightSensor2;
	}

	public void setAvcHeightSensor2(String avcHeightSensor2) {
		this.avcHeightSensor2 = avcHeightSensor2;
	}

	public String getAvcHeightSensor3() {
		return avcHeightSensor3;
	}

	public void setAvcHeightSensor3(String avcHeightSensor3) {
		this.avcHeightSensor3 = avcHeightSensor3;
	}

	public String getAvcHeightSensor4() {
		return avcHeightSensor4;
	}

	public void setAvcHeightSensor4(String avcHeightSensor4) {
		this.avcHeightSensor4 = avcHeightSensor4;
	}

	public String getAvcVehicleClass() {
		return avcVehicleClass;
	}

	public void setAvcVehicleClass(String avcVehicleClass) {
		this.avcVehicleClass = avcVehicleClass;
	}

	public Integer getAvcErrorStatus() {
		return avcErrorStatus;
	}

	public void setAvcErrorStatus(Integer avcErrorStatus) {
		this.avcErrorStatus = avcErrorStatus;
	}

	public Integer getAvcSensorAlignmentStatus() {
		return avcSensorAlignmentStatus;
	}

	public void setAvcSensorAlignmentStatus(Integer avcSensorAlignmentStatus) {
		this.avcSensorAlignmentStatus = avcSensorAlignmentStatus;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Date getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(Date createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}

	@Override
	public String toString() {
		return "AVC [avcId=" + avcId + ", avcTxnNumber=" + avcTxnNumber + ", avcDate=" + avcDate + ", avcTime="
				+ avcTime + ", avcDirection=" + avcDirection + ", avcAxleCount=" + avcAxleCount + ", avcWheelBase="
				+ avcWheelBase + ", avcHeightSensor1=" + avcHeightSensor1 + ", avcHeightSensor2=" + avcHeightSensor2
				+ ", avcHeightSensor3=" + avcHeightSensor3 + ", avcHeightSensor4=" + avcHeightSensor4
				+ ", avcVehicleClass=" + avcVehicleClass + ", avcErrorStatus=" + avcErrorStatus
				+ ", avcSensorAlignmentStatus=" + avcSensorAlignmentStatus + ", transactionId=" + transactionId
				+ ", createTimeStamp=" + createTimeStamp + "]";
	}
	
}




















