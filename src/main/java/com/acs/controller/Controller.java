/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acs.controller;

import com.acs.entity.Currency;
import com.acs.repository.CurrencyRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ACS-Ilham
 */
@RestController
public class Controller {
    
    @Autowired
    CurrencyRepository currencyRepository;
    
    String respon = new String();
    
    @RequestMapping(value = "/request/currency/add_rate", method = RequestMethod.POST)
    public String addRateUSDToGBP(@RequestBody String reqBody, HttpServletRequest req) {
        try {
            JSONObject request = new JSONObject(reqBody);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d");
            Date date = sdf.parse(request.getString("date"));         
            currencyRepository.save(new Currency(date, request.getString("from"), request.getString("to"), request.getDouble("rate")));
            
            return respon = "Success";
        } catch (NullPointerException e) {
            return respon = "Missing Parameter";
        } catch (ParseException e) {
            return respon = "Format Tanggal Tidak Sesuai";
        } catch (Exception e) {
            return respon = "Internal Server Error";
        } 
    }
    
    @RequestMapping(value = "/request/currency/rate_tracked", method = RequestMethod.POST)
    public String rateTracked(@RequestBody String reqBody, HttpServletRequest req) {
        try {
            JSONObject request = new JSONObject(reqBody);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d");
            Date date = sdf.parse(request.getString("date"));
            Date dateBefore = new Date(date.getTime() - 7 * 24 * 3600 * 1000);
            
            List<Currency> listCurrency = currencyRepository.find7DaysBefore(dateBefore, date);
            
            double avgRate = 0.0;
            for (Currency uSDToGBP : listCurrency) {
                avgRate = avgRate + uSDToGBP.getRate();
            }
            avgRate = avgRate / listCurrency.size();
                                    
            return respon = "Success" + " " + avgRate;
        } catch (NullPointerException e) {
            return respon = "Missing Parameter";
        } catch (ParseException e) {
            return respon = "Format Tanggal Tidak Sesuai";
        } catch (Exception e) {
            return respon = "Internal Server Error";
        } 
    }
    
    @RequestMapping(value = "/request/currency/rate_trend", method = RequestMethod.POST)
    public String rateTrend(@RequestBody String reqBody, HttpServletRequest req) {
        try {
            JSONObject request = new JSONObject(reqBody);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d");
            Date date = sdf.parse(request.getString("date"));
            Date dateBefore = new Date(date.getTime() - 7 * 24 * 3600 * 1000);
            
            List<Currency> listCurrency = currencyRepository.find7DaysBefore(dateBefore, date);
            
            for (Currency uSDToGBP : listCurrency) {
                SimpleDateFormat sdfTemp  = new SimpleDateFormat("yyyy-MM-d");
                String dateOfRate = sdfTemp.format(uSDToGBP.getInputDate());
                System.out.println(dateOfRate + " " + uSDToGBP.getRate());
            }
                                    
            return respon = "Success";
        } catch (NullPointerException e) {
            return respon = "Missing Parameter";
        } catch (ParseException e) {
            return respon = "Format Tanggal Tidak Sesuai";
        } catch (Exception e) {
            return respon = "Internal Server Error";
        } 
    }
    
}
