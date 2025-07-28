package chungnam.ton.stmp.domain.stamp.domain;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface StampRepository extends JpaRepository<Stamp, Long> {

    // 유저별 스탬프 조회
    List<Stamp> findByUser_Id(Long userId);
    List<Stamp> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    // 스탬프 상태 조회
//    List<Stamp> findByStatus(@Param("status") String status);


}
