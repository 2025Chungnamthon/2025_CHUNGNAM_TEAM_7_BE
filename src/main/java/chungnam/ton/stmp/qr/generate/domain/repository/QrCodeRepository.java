package chungnam.ton.stmp.qr.generate.domain.repository;

import chungnam.ton.stmp.qr.generate.domain.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QrCodeRepository extends JpaRepository<QrCode,String> {

    //@Override
    //Optional<QrCode> findById(String qrId);
}
