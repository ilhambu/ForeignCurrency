/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acs.caster.repository;

import com.acs.caster.entity.USDToGBP;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ACS-Ilham
 */
public interface CurrencyRepository extends JpaRepository<USDToGBP, String>{
    
}
