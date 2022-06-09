package pro.sky.java.course7.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.sky.java.course7.animalshelter.model.Report;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository  {

//    @Query(value = "SELECT r FROM Report r WHERE r.userChatId=:userChatId")
//    List<Report> findByUserChatId(Long userChatId);
//
//    @Query(value = "SELECT r FROM Report r WHERE r.userChatId=:userChatId")
//    List<LocalDate> getReportsDatesByChatId(@Param("userChatId") long userChatId);
}
