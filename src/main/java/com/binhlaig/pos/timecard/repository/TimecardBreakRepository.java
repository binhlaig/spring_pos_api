package com.binhlaig.pos.timecard.repository;

import com.binhlaig.pos.timecard.entity.TimecardBreak;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimecardBreakRepository extends JpaRepository<TimecardBreak, Long> {

    Optional<TimecardBreak> findFirstByShiftIdAndEndTimeIsNullOrderByStartTimeDesc(Long shiftId);
}