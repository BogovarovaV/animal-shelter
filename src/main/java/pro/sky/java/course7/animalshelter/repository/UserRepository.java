package pro.sky.java.course7.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.model.User;

import java.time.LocalDate;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.id = ?1")
    User findUserById(long id);

    @Query("select u from User u where u.chatId = ?1")
    User findUserByChatId(long chatId);

    @Query("select u from User u where u.status = ?1")
    List<User> findAllAdopters(User.UserStatus status);

    @Query("select u from User u where u.status = ?1 and u.endTrialDate = ?2")
    List<User> findAdoptersWithEndOfTrial(User.UserStatus status, LocalDate endTrialDate);

    @Query("select u from User u inner join Report as r on r.id = (select r2.id from Report as r2 where r2.status = ?1 and r2.sentDate = ?2)")
    List<User> findAdoptersByReportStatusAndSentDate(Report.ReportStatus reportStatus, LocalDate sentDate);

    @Query("select u from User u inner join Report as r on u.status = ?1 and r.id = (select r2.id from Report as r2 where r2.sentDate = ?2)")
    List<User> findAdoptersByStatusAndReportDate(User.UserStatus status, LocalDate sentDate);

    @Query("select u from User u inner join Report as r on u.status =?1 and u.extendedEndTrialDate is not null and current_date > u.endTrialDate")
    List<User>findAdoptersByStatusAndExtendedTrial (User.UserStatus status);

}
