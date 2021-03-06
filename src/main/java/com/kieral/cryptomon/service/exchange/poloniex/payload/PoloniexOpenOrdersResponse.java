package com.kieral.cryptomon.service.exchange.poloniex.payload;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kieral.cryptomon.service.rest.OrdersResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PoloniexOpenOrdersResponse extends ArrayList<PoloniexOpenOrderResponse> implements OrdersResponse<PoloniexOpenOrderResponse> {

	private static final long serialVersionUID = -291446473018656424L;

	@Override
	public String toString() {
		return "PoloniexOrdersResponse [orders()=" + super.toString() + "]";
	}

	@Override
	public boolean isSuccess() {
		return true;
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public List<PoloniexOpenOrderResponse> getOrderResponses() {
		return this;
	}

}
