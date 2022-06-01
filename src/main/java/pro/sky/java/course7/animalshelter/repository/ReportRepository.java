package pro.sky.java.course7.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.java.course7.animalshelter.model.Report;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findReportByUserId(Long userId);
}
