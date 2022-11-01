package com.brody.gestiondeconvertion.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.brody.gestiondeconvertion.exception.XecdApiException;
import com.brody.gestiondeconvertion.model.AccountInfoResponse;
import com.brody.gestiondeconvertion.model.ConvertFromResponse;
import com.brody.gestiondeconvertion.model.ConvertToResponse;
import com.brody.gestiondeconvertion.model.CurrenciesResponse;
import com.brody.gestiondeconvertion.model.HistoricRatePeriodResponse;
import com.brody.gestiondeconvertion.model.HistoricRateResponse;
import com.brody.gestiondeconvertion.model.MonthlyAverageResponse;
import com.brody.gestiondeconvertion.utils.JsonUtils;


@Service
public class XecdApiServiceImpl implements XecdApiService {
	
	
	private Logger logger = LoggerFactory.getLogger(XecdApiServiceImpl.class);

	private String accountId;
	private String apiKey;
	private String serverPrefix;  
	

	private static String accountInfoUrl = "{0}/v1/account_info";
	private static String currenciesUrl = "{0}/v1/currencies/?";
	private static String convertFromUrl = "{0}/v1/convert_from?to={1}";
	private static String convertToUrl = "{0}/v1/convert_to?from={1}";
	private static String historicRateUrl = "{0}/v1/historic_rate/?to={1}&date={2}";
	private static String historicRatePeriodUrl = "{0}/v1/historic_rate/period/?to={1}";
	private static String monthlyAverageUrl = "{0}/v1/monthly_average?to={1}";

	private JsonUtils jsonUtils;

	private final XecdHttpClient wsClient;
	
	
	

	public XecdApiServiceImpl(XecdHttpClientImpl client) {
		
		this.accountId = "your xe account id";
		this.apiKey = "your xe api key";
		this.serverPrefix = "https://xecdapi.xe.com";
		this.jsonUtils = new JsonUtils();
		this.wsClient = client;
	}

	
	@Override
	public AccountInfoResponse getAccountInfo(String accountId, String apiKey) throws URISyntaxException, IOException, XecdApiException {
		
		String accountInfoUrlStr = MessageFormat.format(accountInfoUrl, serverPrefix);
		logger.debug("Calling {}", accountInfoUrlStr);
		String responseString = wsClient.getResponse(accountInfoUrlStr, accountId, apiKey);
		return jsonUtils.fromJson(responseString, AccountInfoResponse.class);
	}

	@Override
	public AccountInfoResponse getAccountInfo() 
	{
		try {
			return getAccountInfo(accountId, apiKey);
		}catch(Exception e) {
			logger.error("EXPTION : "+e);
			return null;
		}
		
	}

	@Override
	public CurrenciesResponse getCurrencies(String accountId, String apiKey, Boolean obsolete, String language, String iso) throws XecdApiException
	{
		CurrenciesResponse response = null;

		String currenciesString = MessageFormat.format(currenciesUrl, serverPrefix);
		currenciesString += (obsolete != null) ? "obsolete=" + obsolete.toString() : "";
		currenciesString += (language != null && !language.isEmpty()) ? "&language=" + language : "";
		currenciesString += (iso != null && !iso.isEmpty()) ? "&iso=" + iso : "";

		try
		{
			logger.debug("Calling {}", currenciesString);
			String responseString = wsClient.getResponse(currenciesString, accountId, apiKey);
			response = jsonUtils.fromJson(responseString, CurrenciesResponse.class);
		}
		catch(XecdApiException e)
		{
			logger.error("Error getting currencies from XECD API", e);
			throw new XecdApiException("Error getting currencies from XECD API", e, e.getErrorResponse(), e.getHttpStatusCode());
		}
		catch(Exception e)
		{
			logger.error("Error getting currencies from XECD API", e);
			throw new XecdApiException("Error getting currencies from XECD API", e);
		}

		return response;
	}
	
	
	
	
	
	public CurrenciesResponse getCurrencies2(String accountId, String apiKey, Boolean obsolete, String language, String iso) throws URISyntaxException, IOException, XecdApiException {

		String currenciesString = MessageFormat.format(currenciesUrl, serverPrefix);
		currenciesString += (obsolete != null) ? "obsolete=" + obsolete.toString() : "";
		currenciesString += (language != null && !language.isEmpty()) ? "&language=" + language : "";
		currenciesString += (iso != null && !iso.isEmpty()) ? "&iso=" + iso : "";
		
		String responseString = wsClient.getResponse(currenciesString, accountId, apiKey);
		
		return jsonUtils.fromJson(responseString, CurrenciesResponse.class);
	}

	
	
	
	
	

	@Override
	public CurrenciesResponse getCurrencies(Boolean obsolete, String language, String iso) throws XecdApiException
	{
		return getCurrencies(accountId, apiKey, obsolete, language, iso);
	}

	@Override
	public CurrenciesResponse getCurrencies(String accountId, String apiKey, Boolean obsolete, String language) throws XecdApiException
	{
		return getCurrencies(accountId, apiKey, obsolete, language, null);
	}

	@Override
	public CurrenciesResponse getCurrencies(Boolean obsolete, String language) throws XecdApiException
	{
		return getCurrencies(obsolete, language, null);
	}


	@Override
	public ConvertFromResponse convertFrom(String accountId, String apiKey, String from, String to, Double amount, Boolean obsolete, Boolean inverse) throws XecdApiException
	{
		ConvertFromResponse response = null;

		String convertFromString = MessageFormat.format(convertFromUrl, serverPrefix, to);

		convertFromString += (from != null && !from.isEmpty()) ? "&from=" + from : "";
		convertFromString += (amount != null && amount != 0) ? "&amount=" + amount : "";
		convertFromString += (obsolete != null) ? "&obsolete=" + obsolete.toString() : "";
		convertFromString += (inverse != null) ? "&inverse=" + inverse.toString() : "";

		try
		{
			logger.debug("Calling {}", convertFromString);

			String responseString = wsClient.getResponse(convertFromString, accountId, apiKey);
			response = jsonUtils.fromJson(responseString, ConvertFromResponse.class);
		}
		catch(XecdApiException e)
		{
			logger.error("Error getting response from XECD API", e);
			throw new XecdApiException("Error getting response from XECD API", e, e.getErrorResponse(), e.getHttpStatusCode());
		}
		catch(Exception e)
		{
			logger.error("Error getting response from XECD API", e);
			throw new XecdApiException("Error getting response from XECD API", e);
		}
		return response;
	}
	
	public ConvertFromResponse convertFrom2(String accountId, String apiKey, String from, String to, Double amount, Boolean obsolete, Boolean inverse){
		try {
			String convertFromString = MessageFormat.format(convertFromUrl, serverPrefix, to);

			convertFromString += (from != null && !from.isEmpty()) ? "&from=" + from : "";
			convertFromString += (amount != null && amount != 0) ? "&amount=" + amount : "";
			convertFromString += (obsolete != null) ? "&obsolete=" + obsolete.toString() : "";
			convertFromString += (inverse != null) ? "&inverse=" + inverse.toString() : "";
			
			String responseString = wsClient.getResponse(convertFromString, accountId, apiKey);
			return jsonUtils.fromJson(responseString, ConvertFromResponse.class);
		}catch(Exception e) {
			logger.error("EXCEPTION : "+e);
			return null;
		}

	
	}

	@Override
	public ConvertFromResponse convertFrom(String from, String to, Double amount, Boolean obsolete, Boolean inverse) throws XecdApiException
	{
		return convertFrom2(accountId, apiKey, from, to, amount, obsolete, inverse);
	}

	@Override
	public ConvertFromResponse convertFrom(String accountId, String apiKey, String from, String to, Double amount, Boolean obsolete) throws XecdApiException
	{
		return convertFrom(accountId, apiKey, from, to, amount, obsolete, null);
	}

	@Override
	public ConvertFromResponse convertFrom(String from, String to, Double amount, Boolean obsolete) throws XecdApiException
	{
		return convertFrom(from, to, amount, obsolete, null);
	}

	@Override
	public ConvertToResponse convertTo(String accountId, String apiKey, String to, String from, Double amount, Boolean obsolete, Boolean inverse) throws XecdApiException
	{
		ConvertToResponse response = null;

		String convertToString = MessageFormat.format(convertToUrl, serverPrefix, from);

		convertToString += (to != null && !to.isEmpty()) ? "&to=" + to : "";
		convertToString += (amount != null && amount != 0) ? "&amount=" + amount : "";
		convertToString += (obsolete != null) ? "&obsolete=" + obsolete.toString() : "";
		convertToString += (inverse != null) ? "&inverse=" + inverse.toString() : "";

		try
		{
			logger.debug("Calling {}", convertToString);

			String responseString = wsClient.getResponse(convertToString, accountId, apiKey);
			response = jsonUtils.fromJson(responseString, ConvertToResponse.class);
		}
		catch(XecdApiException e)
		{
			logger.error("Error getting response from XECD API", e);
			throw new XecdApiException("Error getting response from XECD API", e, e.getErrorResponse(), e.getHttpStatusCode());
		}
		catch(Exception e)
		{
			logger.error("Error getting response from XECD API", e);
			throw new XecdApiException("Error getting response from XECD API", e);
		}
		return response;
	}

	@Override
	public ConvertToResponse convertTo(String to, String from, Double amount, Boolean obsolete, Boolean inverse) throws XecdApiException
	{
		return convertTo(accountId, apiKey, to, from, amount, obsolete, inverse);
	}

	@Override
	public ConvertToResponse convertTo(String accountId, String apiKey, String to, String from, Double amount, Boolean obsolete) throws XecdApiException
	{
		return convertTo(accountId, apiKey, to, from, amount, obsolete, null);
	}

	@Override
	public ConvertToResponse convertTo(String to, String from, Double amount, Boolean obsolete) throws XecdApiException
	{
		return convertTo( to, from, amount, obsolete, null);
	}

	@Override
	public HistoricRateResponse historicRate(String accountId, String apiKey, String from, String to, String date, String time, Double amount, Boolean obsolete, Boolean inverse) throws XecdApiException
	{
		HistoricRateResponse response = null;

		String historicRateString = MessageFormat.format(historicRateUrl, serverPrefix, to, date);

		historicRateString += (from != null && !from.isEmpty()) ? "&from=" + from : "";
		historicRateString += (time != null && !time.isEmpty()) ? "&time=" + time : "";
		historicRateString += (amount != null && amount != 0) ? "&amount=" + amount : "";
		historicRateString += (obsolete != null) ? "&obsolete=" + obsolete.toString() : "";
		historicRateString += (inverse != null) ? "&inverse=" + inverse.toString() : "";

		try
		{
			logger.debug("Calling {}", historicRateString);
			String responseString = wsClient.getResponse(historicRateString, accountId, apiKey);
			response = jsonUtils.fromJson(responseString, HistoricRateResponse.class);
		}
		catch(XecdApiException e)
		{
			logger.error("Error getting historic rates from XECD API", e);
			throw new XecdApiException("Error getting historic rates from XECD API", e, e.getErrorResponse(), e.getHttpStatusCode());
		}
		catch(Exception e)
		{
			logger.error("Error getting historic rates from XECD API", e);
			throw new XecdApiException("Error getting historic rates from XECD API", e);
		}


		return response;
	}

	@Override
	public HistoricRateResponse historicRate(String from, String to, String date, String time, Double amount, Boolean obsolete, Boolean inverse) throws XecdApiException
	{
		return historicRate(accountId, apiKey, from, to, date, time, amount, obsolete, inverse);
	}

	@Override
	public HistoricRateResponse historicRate(String accountId, String apiKey, String from, String to, String date, String time, Double amount, Boolean obsolete) throws XecdApiException
	{
		return historicRate(accountId, apiKey, from, to, date, time, amount, obsolete, null);
	}

	@Override
	public HistoricRateResponse historicRate(String from, String to, String date, String time, Double amount, Boolean obsolete) throws XecdApiException
	{
		return historicRate(from, to, date, time, amount, obsolete, null);
	}

	@Override
	public HistoricRatePeriodResponse historicRatePeriod(String accountId, String apiKey, String from, String to, Double amount, String start, String end, String interval, Integer page, Integer perPage, Boolean obsolete, Boolean inverse) throws XecdApiException
	{
		HistoricRatePeriodResponse response = null;

		String historicRatePeriodString = MessageFormat.format(historicRatePeriodUrl, serverPrefix, to);

		historicRatePeriodString += (from != null && !from.isEmpty()) ? "&from=" + from : "";
		historicRatePeriodString += (amount != null && amount != 0) ? "&amount=" + amount : "";
		historicRatePeriodString += (start != null && !end.isEmpty()) ? "&start_timestamp=" + start : "";
		historicRatePeriodString += (end != null && !end.isEmpty()) ? "&end_timestamp=" + end : "";
		historicRatePeriodString += (interval != null && !interval.isEmpty()) ? "&interval=" + interval : "";
		historicRatePeriodString += (page != null && page != 0) ? "&page=" + page : "";
		historicRatePeriodString += (perPage != null && perPage != 0) ? "&per_page=" + perPage : "";
		historicRatePeriodString += (obsolete != null) ? "&obsolete=" + obsolete.toString() : "";
		historicRatePeriodString += (inverse != null) ? "&inverse=" + inverse.toString() : "";

		try
		{
			logger.debug("Calling {}", historicRatePeriodString);
			String responseString = wsClient.getResponse(historicRatePeriodString, accountId, apiKey);
			response = jsonUtils.fromJson(responseString, HistoricRatePeriodResponse.class);
		}
		catch(XecdApiException e)
		{
			logger.error("Error getting historic rates from XECD API", e);
			throw new XecdApiException("Error getting historic rates from XECD API", e, e.getErrorResponse(), e.getHttpStatusCode());
		}
		catch(Exception e)
		{
			logger.error("Error getting historic rates from XECD API", e);
			throw new XecdApiException("Error getting historic rates from XECD API", e);
		}

		return response;
	}

	@Override
	public HistoricRatePeriodResponse historicRatePeriod(String from, String to, Double amount, String start, String end, String interval, Integer page, Integer perPage, Boolean obsolete, Boolean inverse) throws XecdApiException
	{
		return historicRatePeriod(accountId, apiKey, from, to, amount, start, end, interval, page, perPage, obsolete, inverse);
	}

	@Override
	public HistoricRatePeriodResponse historicRatePeriod(String accountId, String apiKey, String from, String to, Double amount, String start, String end, String interval, Integer page, Integer perPage, Boolean obsolete) throws XecdApiException
	{
		return historicRatePeriod(accountId, apiKey, from, to, amount, start, end, interval, page, perPage, obsolete, null);
	}

	@Override
	public HistoricRatePeriodResponse historicRatePeriod(String from, String to, Double amount, String start, String end, String interval, Integer page, Integer perPage, Boolean obsolete) throws XecdApiException
	{
		return historicRatePeriod(from, to, amount, start, end, interval, page, perPage, obsolete, null);
	}

	@Override
	public MonthlyAverageResponse monthlyAverage(String accountId, String apiKey, String from, String to, Double amount, Integer year, Integer month, Boolean obsolete, Boolean inverse) throws XecdApiException
	{
		MonthlyAverageResponse response = null;

		String monthlyAverageString = MessageFormat.format(monthlyAverageUrl, serverPrefix, to);
		monthlyAverageString += (from != null && !from.isEmpty()) ? "&from=" + from : "";
		monthlyAverageString += (amount != null && amount != 0) ? "&amount=" + amount : "";
		monthlyAverageString += (year != null) ? "&year=" + year : "";
		monthlyAverageString += (month != null) ? "&month=" + month : "";
		monthlyAverageString += (obsolete != null) ? "&obsolete=" + obsolete.toString() : "";
		monthlyAverageString += (inverse != null) ? "&inverse=" + inverse.toString() : "";

		try
		{
			logger.debug("Calling {}", monthlyAverageString);
			String responseString = wsClient.getResponse(monthlyAverageString, accountId, apiKey);
			response = jsonUtils.fromJson(responseString, MonthlyAverageResponse.class);
		}
		catch(XecdApiException e)
		{
			logger.error("Error getting currencies from XECD API", e);
			throw new XecdApiException("Error getting monthly average from XECD API", e, e.getErrorResponse(), e.getHttpStatusCode());
		}
		catch(Exception e)
		{
			logger.error("Error getting currencies from XECD API", e);
			throw new XecdApiException("Error getting monthly average from XECD API", e);
		}

		return response;
	}

	@Override
	public MonthlyAverageResponse monthlyAverage(String from, String to, Double amount, Integer year, Integer month, Boolean obsolete, Boolean inverse) throws XecdApiException
	{
		return monthlyAverage(accountId, apiKey, from, to, amount, year, month, obsolete, inverse);
	}

	@Override
	public MonthlyAverageResponse monthlyAverage(String from, String to, Double amount, Integer year, Integer month, Boolean obsolete) throws XecdApiException
	{
		return monthlyAverage(from, to, amount, year, month, obsolete, null);
	}

	@Override
	public MonthlyAverageResponse monthlyAverage(String accountId, String apiKey, String from, String to, Double amount, Integer year, Integer month, Boolean obsolete) throws XecdApiException
	{
		return monthlyAverage(accountId, apiKey, from, to, amount, year, month, obsolete, null);
	}

}
