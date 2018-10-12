/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acs.controller;

import com.acs.entity.USDToGBP;
import com.acs.repository.CurrencyRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    
    @RequestMapping(value = "/request/usdtogbp/addrate", method = RequestMethod.POST)
    public String addRateUSDToGBP(@RequestBody String reqBody, HttpServletRequest req) {
        try {
            JSONObject request = new JSONObject(reqBody);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d");
            Date date = sdf.parse(request.getString("date"));
            System.out.println(date.toString());
            
            
            currencyRepository.save(new USDToGBP(date, request.getString("from"), request.getString("to"), request.getDouble("rate")));
            
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
