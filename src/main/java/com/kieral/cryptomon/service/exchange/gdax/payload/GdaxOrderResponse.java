package com.kieral.cryptomon.service.exchange.gdax.payload;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kieral.cryptomon.model.general.Side;
import com.kieral.cryptomon.model.trading.OrderStatus;
import com.kieral.cryptomon.service.exchange.gdax.GdaxServiceConfig;
import com.kieral.cryptomon.service.rest.OrderResponse;
import com.kieral.cryptomon.service.util.TradingUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GdaxOrderResponse implements OrderResponse {

	private String id;
	private BigDecimal size;
	private String productId;
	private String side;
	private String stp;
	private BigDecimal funds;
	private BigDecimal specifiedFunds;
	private String type;
	private String createdAt;
	private String doneAt;
	private String doneReason;
	private BigDecimal fillFees;
	private BigDecimal filledSize;
	private BigDecimal executedValue;
	private String status;
	private boolean settled;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getSize() {
		return size;
	}

	public void setSize(BigDecimal size) {
		this.size = size;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getStp() {
		return stp;
	}

	public void setStp(String stp) {
		this.stp = stp;
	}

	public BigDecimal getFunds() {
		return funds;
	}

	public void setFunds(BigDecimal funds) {
		this.funds = funds;
	}

	public BigDecimal getSpecifiedFunds() {
		return specifiedFunds;
	}

	public void setSpecifiedFunds(BigDecimal specifiedFunds) {
		this.specifiedFunds = specifiedFunds;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getDoneAt() {
		return doneAt;
	}

	public void setDoneAt(String doneAt) {
		this.doneAt = doneAt;
	}

	public String getDoneReason() {
		return doneReason;
	}

	public void setDoneReason(String doneReason) {
		this.doneReason = doneReason;
	}

	public BigDecimal getFillFees() {
		return fillFees;
	}

	public void setFillFees(BigDecimal fillFees) {
		this.fillFees = fillFees;
	}

	public BigDecimal getFilledSize() {
		return filledSize;
	}

	public void setFilledSize(BigDecimal filledSize) {
		this.filledSize = filledSize;
	}

	public BigDecimal getExecutedValue() {
		return executedValue;
	}

	public void setExecutedValue(BigDecimal executedValue) {
		this.executedValue = executedValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isSettled() {
		return settled;
	}

	public void setSettled(boolean settled) {
		this.settled = settled;
	}

	@Override
	public String toString() {
		return "GdaxOrderResponse [id=" + id + ", size=" + size + ", productId=" + productId + ", side=" + side
				+ ", stp=" + stp + ", funds=" + funds + ", specifiedFunds=" + specifiedFunds + ", type=" + type
				+ ", createdAt=" + createdAt + ", doneAt=" + doneAt + ", doneReason=" + doneReason + ", fillFees="
				+ fillFees + ", filledSize=" + filledSize + ", executedValue=" + executedValue + ", status=" + status
				+ ", settled=" + settled + "]";
	}

	@Override
	public String getOrderId() {
		return id;
	}

	@Override
	public BigDecimal getQuantity() {
		return size;
	}

	@Override
	public BigDecimal getQuantityRemaining() {
		return filledSize;
	}

	@Override
	public BigDecimal getPrice() {
		return null;
	}

	@Override
	public boolean isOpen() {
		return "done".equals(status) || "settled".equals(status);
	}

	@Override
	public boolean isClosing() {
		return false;
	}

	@Override
	public boolean isSuccess() {
		return id != null;
	}

	@Override
	public OrderStatus getOrderStatus() {
		return TradingUtils.getOrderStatus(this); 
	}

	@Override
	public Side getSide() {
		return side == null ? null : side.toUpperCase().contains("BUY") ? Side.BID : Side.ASK;
	}

	@Override
	public long getCreatedTime() {
		try {
			return LocalDateTime.parse(createdAt, GdaxServiceConfig.dateTimeFormatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		} catch (Exception e) {}
		return System.currentTimeMillis();
	}

	@Override
	public long getClosedTime() {
		if (doneAt != null) {
			try {
				return LocalDateTime.parse(doneAt, GdaxServiceConfig.dateTimeFormatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			} catch (Exception e) {}
			return System.currentTimeMillis();
		}
		return 0;
	}

}