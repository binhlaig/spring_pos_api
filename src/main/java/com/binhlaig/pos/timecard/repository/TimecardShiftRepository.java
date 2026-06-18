//
//
//
//
//package com.binhlaig.pos.timecard.repository;
//
//import com.binhlaig.pos.timecard.entity.TimecardShift;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//public interface TimecardShiftRepository extends JpaRepository<TimecardShift, Long> {
//
//    @Query("""
//        select distinct s
//        from TimecardShift s
//        left join fetch s.breaks
//        where s.employeeId = :employeeId
//        order by s.clockInTime desc
//    """)
//    List<TimecardShift> findByEmployeeIdWithBreaksOrderByClockInTimeDesc(
//            @Param("employeeId") Long employeeId
//    );
//
//    @Query("""
//        select distinct s
//        from TimecardShift s
//        left join fetch s.breaks
//        order by s.clockInTime desc
//    """)
//    List<TimecardShift> findAllWithBreaksOrderByClockInTimeDesc();
//
//    @Query(value = """
//        select distinct ts.*
//        from timecard_shifts ts
//        left join timecard_breaks tb on tb.shift_id = ts.id
//        inner join staff st on st.id = ts.employee_id
//        where st.shop_id = :shopId
//        order by ts.clock_in_time desc
//    """, nativeQuery = true)
//    List<TimecardShift> findByShopIdWithBreaksOrderByClockInTimeDesc(
//            @Param("shopId") Long shopId
//    );
//
//    @Query(value = """
//        select distinct ts.*
//        from timecard_shifts ts
//        left join timecard_breaks tb on tb.shift_id = ts.id
//        inner join staff st on st.id = ts.employee_id
//        where ts.employee_id = :employeeId
//          and st.shop_id = :shopId
//        order by ts.clock_in_time desc
//    """, nativeQuery = true)
//    List<TimecardShift> findByEmployeeIdAndShopIdWithBreaksOrderByClockInTimeDesc(
//            @Param("employeeId") Long employeeId,
//            @Param("shopId") Long shopId
//    );
//
//    Optional<TimecardShift> findFirstByEmployeeIdAndClockOutTimeIsNullOrderByClockInTimeDesc(Long employeeId);
//
//    boolean existsByEmployeeIdAndWorkDateAndClockOutTimeIsNull(Long employeeId, LocalDate workDate);
//}













package com.binhlaig.pos.timecard.repository;

import com.binhlaig.pos.timecard.entity.TimecardShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TimecardShiftRepository extends JpaRepository<TimecardShift, Long> {

    @Query("""
        select distinct s
        from TimecardShift s
        left join fetch s.breaks
        where s.employeeId = :employeeId
        order by s.clockInTime desc
    """)
    List<TimecardShift> findByEmployeeIdWithBreaksOrderByClockInTimeDesc(
            @Param("employeeId") Long employeeId
    );

    @Query("""
        select distinct s
        from TimecardShift s
        left join fetch s.breaks
        order by s.clockInTime desc
    """)
    List<TimecardShift> findAllWithBreaksOrderByClockInTimeDesc();

    @Query(value = """
        select distinct ts.*
        from timecard_shifts ts
        left join timecard_breaks tb on tb.shift_id = ts.id
        inner join staff st on st.id = ts.employee_id
        where st.shop_id = :shopId
        order by ts.clock_in_time desc
    """, nativeQuery = true)
    List<TimecardShift> findByShopIdWithBreaksOrderByClockInTimeDesc(
            @Param("shopId") Long shopId
    );

    @Query(value = """
        select distinct ts.*
        from timecard_shifts ts
        left join timecard_breaks tb on tb.shift_id = ts.id
        inner join staff st on st.id = ts.employee_id
        where ts.employee_id = :employeeId
          and st.shop_id = :shopId
        order by ts.clock_in_time desc
    """, nativeQuery = true)
    List<TimecardShift> findByEmployeeIdAndShopIdWithBreaksOrderByClockInTimeDesc(
            @Param("employeeId") Long employeeId,
            @Param("shopId") Long shopId
    );

    Optional<TimecardShift> findFirstByEmployeeIdAndClockOutTimeIsNullOrderByClockInTimeDesc(Long employeeId);

    Optional<TimecardShift> findByIdAndShopId(Long id, Long shopId);

    boolean existsByEmployeeIdAndWorkDateAndClockOutTimeIsNull(Long employeeId, LocalDate workDate);
}





