package chungnam.ton.stmp.domain.stamp.domain.repository;

import chungnam.ton.stmp.domain.stamp.domain.Stamp;
import chungnam.ton.stmp.domain.user.domain.User;
import chungnam.ton.stmp.qr.generate.domain.QrCode;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StampRepository extends JpaRepository<Stamp, Long> {

    // 유저별 스탬프 조회
    List<Stamp> findAllByUser(User user);
    List<Stamp> findAllByScanTimeBetween(LocalDateTime from, LocalDateTime to);

    boolean existsByUserAndQrCode(User user, QrCode qrCode);


}
