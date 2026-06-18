package com.binhlaig.pos.timecard.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TimecardScheduleRepository extends JpaRepository<TimecardSchedule, Long> {

    List<TimecardSchedule> findByShopIdAndDateBetweenOrderByDateAscStartTimeAsc(Long shopId, LocalDate from, LocalDate to);

    Optional<TimecardSchedule> findByIdAndShopId(Long id, Long shopId);

    boolean existsByShopIdAndStaffIdAndDate(Long shopId, String staffId, LocalDate date);

    boolean existsByShopIdAndStaffIdAndDateAndIdNot(Long shopId, String staffId, LocalDate date, Long id);

    @Modifying
    @Query("""
            update TimecardSchedule s
               set s.status = com.binhlaig.pos.timecard.schedule.TimecardScheduleStatus.PUBLISHED,
                   s.updatedAt = current_timestamp
             where s.shopId = :shopId
               and s.date between :from and :to
               and s.status = com.binhlaig.pos.timecard.schedule.TimecardScheduleStatus.DRAFT
            """)
    int publishDrafts(@Param("shopId") Long shopId, @Param("from") LocalDate from, @Param("to") LocalDate to);
}
