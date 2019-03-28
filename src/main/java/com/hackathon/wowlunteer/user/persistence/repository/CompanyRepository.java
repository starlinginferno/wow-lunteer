package com.hackathon.wowlunteer.user.persistence.repository;

import com.hackathon.wowlunteer.user.persistence.model.Company;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CompanyRepository extends UserBaseRepository<Company> {
}