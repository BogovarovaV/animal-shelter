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

    @Query(value = "select max (r) from Report r where r.user.id=?1")
    Optional<Report> findLastReportByUserId(long userId);

    @Query("select max (r.sentDate) from Report r where r.user.id = ?1")
    Optional<LocalDate> getDateOfLastReportByUserId(long userId);

    @Query("select r from Report r where r.id = ?1")
    Optional<Report> findById(long id);

    @Query("select count(r) from Report r where r.clientId = ?1")
    Optional<Integer> countReportsByClientId(long id);



}
