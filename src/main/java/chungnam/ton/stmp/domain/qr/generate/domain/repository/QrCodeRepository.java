package chungnam.ton.stmp.domain.qr.generate.domain.repository;

import chungnam.ton.stmp.domain.qr.generate.domain.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrCodeRepository extends JpaRepository<QrCode,String> {

    //@Override
    //Optional<QrCode> findById(String qrId);
}
