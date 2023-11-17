package com.team65.isaproject.repository;

import com.team65.isaproject.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    @Query("SELECT c FROM Company c WHERE LOWER(c.companyName) LIKE LOWER(CONCAT(:prefix, '%'))")
    List<Company> findByCompanyNameStartingWithIgnoreCase(@Param("prefix") String prefix);
}
