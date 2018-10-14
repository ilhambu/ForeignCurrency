/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acs.controller;

import com.acs.entity.Currency;
import com.acs.entity.Respon;
import com.acs.repository.CurrencyRepository;
import com.acs.repository.ResponsRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Autowired
    ResponsRepository responsRepository;

    @RequestMapping(value = "/request/currency/add_rate", method = RequestMethod.POST)
    public String addRateCurrency(@RequestBody String reqBody, HttpServletRequest req) {
        Respon responsTemp;
        JSONObject respons = new JSONObject();
        try {
            JSONObject request = new JSONObject(reqBody);
            if (!request.has("from") || !request.has("to") || !request.has("date") || !request.has("rate")) {
                responsTemp = responsRepository.findRespon("10");
                respons.put("rc", responsTemp.getRc());
                respons.put("rm", responsTemp.getRm());
                return respons.toString();
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d");
                Date date = sdf.parse(request.getString("date"));
                currencyRepository.save(new Currency(date, request.getString("from"), request.getString("to"), request.getDouble("rate")));

                responsTemp = responsRepository.findRespon("00");
                respons.put("rc", responsTemp.getRc());
                respons.put("rm", responsTemp.getRm());
                return respons.toString();
            }

        } catch (NullPointerException e) {
            responsTemp = responsRepository.findRespon("40");
            respons.put("rc", responsTemp.getRc());
            respons.put("rm", responsTemp.getRm());
            return respons.toString();
        } catch (ParseException e) {
            responsTemp = responsRepository.findRespon("20");
            respons.put("rc", responsTemp.getRc());
            respons.put("rm", responsTemp.getRm());
            return respons.toString();
        } catch (Exception e) {
            responsTemp = responsRepository.findRespon("30");
            respons.put("rc", responsTemp.getRc());
            respons.put("rm", responsTemp.getRm());
            return respons.toString();
        }
    }

    @RequestMapping(value = "/request/currency/rate_tracked", method = RequestMethod.POST)
    public String rateTracked(@RequestBody String reqBody, HttpServletRequest req) {
        Respon responsTemp;
        JSONObject respons = new JSONObject();
        try {
            JSONObject request = new JSONObject(reqBody);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d");
            Date date = sdf.parse(request.getString("date"));
            Date dateBefore = new Date(date.getTime() - 7 * 24 * 3600 * 1000);

            List<Currency> listCurrency = currencyRepository.find7DaysBeforeAverage(dateBefore, date);

            double avgRate = 0.0;
            for (Currency uSDToGBP : listCurrency) {
                avgRate = avgRate + uSDToGBP.getRate();
                System.out.println(uSDToGBP.getRate());
            }
            avgRate = avgRate / listCurrency.size();

            responsTemp = responsRepository.findRespon("00");
            respons.put("rc", responsTemp.getRc());
            respons.put("rm", responsTemp.getRm());
            respons.put("7-day avg", avgRate);
            return respons.toString();
        } catch (NullPointerException e) {
            responsTemp = responsRepository.findRespon("40");
            respons.put("rc", responsTemp.getRc());
            respons.put("rm", responsTemp.getRm());
            return respons.toString();
        } catch (ParseException e) {
            responsTemp = responsRepository.findRespon("20");
            respons.put("rc", responsTemp.getRc());
            respons.put("rm", responsTemp.getRm());
            return respons.toString();
        } catch (Exception e) {
            responsTemp = responsRepository.findRespon("30");
            respons.put("rc", responsTemp.getRc());
            respons.put("rm", responsTemp.getRm());
            return respons.toString();
        }
    }

    @RequestMapping(value = "/request/currency/rate_trend", method = RequestMethod.POST)
    public String rateTrend(@RequestBody String reqBody, HttpServletRequest req) {
        Respon responsTemp;
        JSONObject respons = new JSONObject();
        try {
            JSONObject request = new JSONObject(reqBody);

            if (!request.has("from") || !request.has("to")) {
                responsTemp = responsRepository.findRespon("10");
                respons.put("rc", responsTemp.getRc());
                respons.put("rm", responsTemp.getRm());
                return respons.toString();
            } else {
                Date date = new Date();
                Date dateBefore = new Date(date.getTime() - 7 * 24 * 3600 * 1000);
                List<Currency> listCurrency = currencyRepository.find7DaysBeforeTrend(dateBefore, date, request.getString("from"), request.getString("to"));

                if (listCurrency.size() == 0) {
                    responsTemp = responsRepository.findRespon("50");
                    respons.put("rc", responsTemp.getRc());
                    respons.put("rm", responsTemp.getRm());
                    return respons.toString();
                } else {
                    ArrayList<JSONObject> arrayData = new ArrayList<>();
                    for (Currency curr : listCurrency) {
                        SimpleDateFormat sdfTemp = new SimpleDateFormat("yyyy-MM-d");
                        String dateOfRate = sdfTemp.format(curr.getInputDate());
                        JSONObject data = new JSONObject();
                        data.put("date", dateOfRate);
                        data.put("rate", curr.getRate());
                        arrayData.add(data);
                    }

                    responsTemp = responsRepository.findRespon("00");
                    respons.put("rc", responsTemp.getRc());
                    respons.put("rm", responsTemp.getRm());
                    respons.put("data", arrayData);
                    return respons.toString();
                }
            }
        } catch (NullPointerException e) {
            responsTemp = responsRepository.findRespon("40");
            respons.put("rc", responsTemp.getRc());
            respons.put("rm", responsTemp.getRm());
            return respons.toString();
        } catch (Exception e) {
            responsTemp = responsRepository.findRespon("30");
            respons.put("rc", responsTemp.getRc());
            respons.put("rm", responsTemp.getRm());
            return respons.toString();
        }
    }

    @RequestMapping(value = "/request/currency/delete", method = RequestMethod.POST)
    public String rateDelete(@RequestBody String reqBody, HttpServletRequest req) {
        Respon responsTemp;
        JSONObject respons = new JSONObject();
        try {
            JSONObject request = new JSONObject(reqBody);

            if (!request.has("from") || !request.has("to")) {
                responsTemp = responsRepository.findRespon("10");
                respons.put("rc", responsTemp.getRc());
                respons.put("rm", responsTemp.getRm());
                return respons.toString();
            } else {
                currencyRepository.deleteRate(request.getString("from"), request.getString("to"));

                responsTemp = responsRepository.findRespon("00");
                respons.put("rc", responsTemp.getRc());
                respons.put("rm", responsTemp.getRm());
                return respons.toString();
            }

        } catch (NullPointerException e) {
            responsTemp = responsRepository.findRespon("40");
            respons.put("rc", responsTemp.getRc());
            respons.put("rm", responsTemp.getRm());
            return respons.toString();
        } catch (Exception e) {
            responsTemp = responsRepository.findRespon("30");
            respons.put("rc", responsTemp.getRc());
            respons.put("rm", responsTemp.getRm());
            return respons.toString();
        }
    }

}
