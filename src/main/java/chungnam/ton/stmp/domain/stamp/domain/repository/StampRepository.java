package chungnam.ton.stmp.domain.stamp.domain.repository;

import chungnam.ton.stmp.domain.stamp.domain.Stamp;
import chungnam.ton.stmp.domain.user.domain.User;
import chungnam.ton.stmp.domain.qr.generate.domain.QrCode;

import org.springframework.data.jpa.repository.JpaRepository;



public interface StampRepository extends JpaRepository<Stamp, Long> {
    long countByUser(User user);
    boolean existsByUserAndQrCode(User user, QrCode qrCode);

}
