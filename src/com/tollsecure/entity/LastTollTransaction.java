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
@Table(name="t_last_toll_transaction")
public class LastTollTransaction {

	public LastTollTransaction() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="last_transaction_id")
	private Integer lastTransactionId;
	
	@Column(name="lane_code")
	private String laneCode;
	
	@Column(name="transaction_id")
	private Integer transactionId;
	
	@Column(name="create_timestamp", columnDefinition="TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTimeStamp;

	public Integer getLastTransactionId() {
		return lastTransactionId;
	}

	public void setLastTransactionId(Integer lastTransactionId) {
		this.lastTransactionId = lastTransactionId;
	}

	public String getLaneCode() {
		return laneCode;
	}

	public void setLaneCode(String laneCode) {
		this.laneCode = laneCode;
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
		return "LastTollTransaction [lastTransactionId=" + lastTransactionId + ", laneCode=" + laneCode
				+ ", transactionId=" + transactionId + ", createTimeStamp=" + createTimeStamp + "]";
	}
	
}
