package com.springboot.shoppy_fullstack_app.jpa_repository;

import com.springboot.shoppy_fullstack_app.entity.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface JpaSupportRepository extends JpaRepository<Support, Integer> {
    List<Support> findAll();
    @Query("select s from Support s where s.stype = :stype")
    List<Support> findByType(@Param("stype") String stype);
}
