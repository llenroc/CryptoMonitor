package com.kieral.cryptomon.service.exchange;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.kieral.cryptomon.model.accounting.TradingFeeType;
import com.kieral.cryptomon.model.general.Currency;
import com.kieral.cryptomon.model.general.CurrencyPair;
import com.kieral.cryptomon.model.general.ApiRequest;

public abstract class ServiceExchangeProperties {

	public enum SubscriptionMode {
		POLLING,
		STREAMING
	}
	
    protected String exchange;
    protected boolean enabled;
    protected String pushApi;
    protected String snapshotApi;
    protected String tradingApi;
    protected SubscriptionMode subscriptionMode;
    protected long pollingInterval;
    protected int maxTransPerSecond;
    protected int maxLevels;
    protected boolean skipHearbeats;
    protected boolean snapshotBaseline;
    protected boolean snapshotBaselineSequence;
    protected String apiKeyLoc;
    protected String apiSecretLoc;
    protected String apiPassphraseLoc;
    protected List<CurrencyPairProperties> currencyPairs;

	private final AtomicBoolean initialised = new AtomicBoolean(false);
	private final List<CurrencyPair> pairs = new ArrayList<CurrencyPair>();

    public ServiceExchangeProperties() {
    }
    
	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPushApi() {
		return pushApi;
	}

	public void setPushApi(String pushApi) {
		this.pushApi = pushApi;
	}

	public String getSnapshotApi() {
		return snapshotApi;
	}

	public void setSnapshotApi(String snapshotApi) {
		this.snapshotApi = snapshotApi;
	}

	public String getTradingApi() {
		return tradingApi;
	}

	public void setTradingApi(String tradingApi) {
		this.tradingApi = tradingApi;
	}

	public SubscriptionMode getSubscriptionMode() {
		return subscriptionMode;
	}

	public void setSubscriptionMode(SubscriptionMode subscriptionMode) {
		this.subscriptionMode = subscriptionMode;
	}

	public long getPollingInterval() {
		return pollingInterval;
	}

	public void setPollingInterval(long pollingInterval) {
		this.pollingInterval = pollingInterval;
	}

	public int getMaxTransPerSecond() {
		return maxTransPerSecond;
	}

	public void setMaxTransPerSecond(int maxTransPerSecond) {
		this.maxTransPerSecond = maxTransPerSecond;
	}

	public int getMaxLevels() {
		return maxLevels;
	}

	public void setMaxLevels(int maxLevels) {
		this.maxLevels = maxLevels;
	}

	public boolean isSkipHearbeats() {
		return skipHearbeats;
	}

	public void setSkipHearbeats(boolean skipHearbeats) {
		this.skipHearbeats = skipHearbeats;
	}

	public boolean isSnapshotBaseline() {
		return snapshotBaseline;
	}

	public void setSnapshotBaseline(boolean snapshotBaseline) {
		this.snapshotBaseline = snapshotBaseline;
	}

	public boolean isSnapshotBaselineSequence() {
		return snapshotBaselineSequence;
	}

	public void setSnapshotBaselineSequence(boolean snapshotBaselineSequence) {
		this.snapshotBaselineSequence = snapshotBaselineSequence;
	}

	public String getApiKeyLoc() {
		return apiKeyLoc;
	}

	public void setApiKeyLoc(String apiKeyLoc) {
		this.apiKeyLoc = apiKeyLoc;
	}

	public String getApiSecretLoc() {
		return apiSecretLoc;
	}

	public void setApiSecretLoc(String apiSecretLoc) {
		this.apiSecretLoc = apiSecretLoc;
	}

	public String getApiPassphraseLoc() {
		return apiPassphraseLoc;
	}

	public void setApiPassphraseLoc(String apiPassphraseLoc) {
		this.apiPassphraseLoc = apiPassphraseLoc;
	}

	public List<CurrencyPairProperties> getCurrencyPairs() {
		return currencyPairs;
	}

	public void setCurrencyPairs(List<CurrencyPairProperties> currencyPairs) {
		this.currencyPairs = currencyPairs;
	}

	protected abstract String[] splitPair(String topicStr);
	
	public CurrencyPair getPair(CurrencyPairProperties currencyPair) {
		if (currencyPair == null)
			throw new IllegalArgumentException("currencyPair can not be null");
		String topic = currencyPair.getPair();
		String[] pairs = splitPair(currencyPair.getPair());
		Currency baseCurrency = Currency.valueOf(pairs[0].trim());
		Currency quotedCurrency = Currency.valueOf(pairs[1].trim());
		// TODO: add trading fees and currency
		return new CurrencyPair(pairs[0].trim() + pairs[1].trim(), baseCurrency, quotedCurrency, topic.trim(),
				currencyPair.getPriceScale(), currencyPair.getTradingFee() != null ? new BigDecimal(currencyPair.getTradingFee()) : BigDecimal.ZERO, 
				getTradingFeeType());
	}

	public List<CurrencyPair> getPairs() {
		if (currencyPairs == null)
			return null;
		if (initialised.compareAndSet(false, true)) {
			currencyPairs.forEach(pair -> {
				pairs.add(getPair(pair));
			});
		}
		return pairs;
	}

	public abstract ApiRequest getOrderBookSnapshotQuery(String currencyPairSymbol);

	public abstract TradingFeeType getTradingFeeType();

	public abstract ApiRequest getAccountsQuery();

	@Override
	public String toString() {
		return "ServiceExchangeProperties [exchange=" + exchange + ", enabled=" + enabled + ", pushApi=" + pushApi
				+ ", snapshotApi=" + snapshotApi + ", tradingApi=" + tradingApi + ", subscriptionMode="
				+ subscriptionMode + ", pollingInterval=" + pollingInterval + ", maxTransPerSecond=" + maxTransPerSecond
				+ ", maxLevels=" + maxLevels + ", skipHearbeats=" + skipHearbeats + ", snapshotBaseline="
				+ snapshotBaseline + ", snapshotBaselineSequence=" + snapshotBaselineSequence + ", apiKeyLoc="
				+ apiKeyLoc + ", apiSecretLoc=" + apiSecretLoc + ", apiPassphraseLoc=" + apiPassphraseLoc
				+ ", currencyPairs=" + currencyPairs + ", pairs=" + pairs + "]";
	}

	
}
