<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="yt.item5.CountryCode"%>
<%@page import="yt.item5.factory.CountryMapFactory"%>
<%@page import="com.google.gson.Gson"%>

<%@page import="java.util.Map"%>
<%
	final String COUNTRY_CODE_FILE = "countryCodeToFullName";
	Map<CountryCode, String> countryCodeMap = (new CountryMapFactory()).createCountryMap(COUNTRY_CODE_FILE);
	String json = new Gson().toJson(countryCodeMap);
	response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(json);

	
%>