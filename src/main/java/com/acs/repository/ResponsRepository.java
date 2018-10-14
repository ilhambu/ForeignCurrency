/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acs.repository;

import com.acs.entity.Respon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ACS-Ilham
 */
public interface ResponsRepository extends JpaRepository<Respon, String> {

    @Query("SELECT u FROM Respon u WHERE u.rc=:rc")
    Respon findRespon(@Param("rc") String rc);
}
