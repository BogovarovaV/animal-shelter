package pro.sky.java.course7.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.java.course7.animalshelter.model.Report;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

   @Query("select r from Report r where r.user.id = ?1")
   Optional<List<Report>> findByUserId(long userId);

    @Query(value = "SELECT MAX (r) FROM Report r WHERE r.user.id=:userId")
    Optional<Report> findLastReportByUserId(long userId);

    @Query(value = "SELECT MAX (sentDate) FROM Report ORDER BY CURRENT_TIMESTAMP DESC")
    Optional<LocalDate> getDateOfLastReportByUserId(long userId);

  @Query("select r from Report r where r.id = ?1")
  Optional<Report> findById(long id);



}
