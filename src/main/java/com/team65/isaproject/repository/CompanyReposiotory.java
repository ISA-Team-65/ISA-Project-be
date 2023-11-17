package com.team65.isaproject.repository;

import com.team65.isaproject.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyReposiotory extends JpaRepository<Company, Integer> {
}
