package chungnam.ton.stmp.domain.qr.generate.domain.repository;

import chungnam.ton.stmp.domain.market.domain.Market;
import chungnam.ton.stmp.domain.qr.generate.domain.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QrCodeRepository extends JpaRepository<QrCode,Long> {
    Optional<QrCode> findByMarket_IdAndPlaceName(Long marketId, String placeName);
}
    //@Override
    //Optional<QrCode> findById(String qrId);

