/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acs.repository;

import com.acs.entity.Currency;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ACS-Ilham
 */
public interface CurrencyRepository extends JpaRepository<Currency, String>{
    
    @Query("SELECT u FROM Currency u WHERE u.inputDate BETWEEN :from AND :to")
    List<Currency> find7DaysBeforeAverage(@Param("from") Date start, @Param("to") Date end);
    
    @Query("SELECT u FROM Currency u WHERE u.currencyFrom=:from and u.currencyTo=:to and u.inputDate BETWEEN :fromDate AND :toDate")
    List<Currency> find7DaysBeforeTrackedList(@Param("fromDate") Date start, @Param("toDate") Date end, @Param("from") String from, @Param("to") String to);
    
    @Transactional
    @Modifying
    @Query("delete from Currency u where u.currencyFrom=:from and u.currencyTo=:to")
    void deleteRate(@Param("from") String from, @Param("to") String to);
}
