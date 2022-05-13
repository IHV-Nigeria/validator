package com.ihvn.validator.repositories;

import com.ihvn.validator.entities.ErrorReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorReportRepository extends JpaRepository<ErrorReport, Long> {
}
