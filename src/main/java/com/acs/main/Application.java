/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acs.main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 *
 * @author ACS-Ilham
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.acs.caster.repository")
@EntityScan(basePackages = "com.acs.caster.entity")
@ComponentScan(basePackages = "com.acs.caster.controller")
public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SpringApplication.run(Application.class, args);
        
    }
    
}
