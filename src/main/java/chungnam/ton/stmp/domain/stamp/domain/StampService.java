package chungnam.ton.stmp.domain.stamp.domain;

import chungnam.ton.stmp.domain.market.domain.Market;
import chungnam.ton.stmp.domain.market.domain.repository.MarketRepository;
import chungnam.ton.stmp.domain.stamp.domain.dto.StampResponseDto;
import chungnam.ton.stmp.domain.stamp.domain.repository.StampRepository;
import chungnam.ton.stmp.domain.user.domain.User;
import chungnam.ton.stmp.domain.user.domain.repository.UserRepository;
import chungnam.ton.stmp.global.error.DefaultException;
import chungnam.ton.stmp.global.payload.ErrorCode;
import chungnam.ton.stmp.domain.qr.generate.domain.QrCode;
import chungnam.ton.stmp.domain.qr.generate.domain.repository.QrCodeRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StampService {

    private final StampRepository stampRepository;
    private final UserRepository userRepository;
    private final QrCodeRepository qrCodeRepository;
    private final MarketRepository marketRepository;

    // QR 코드 스캔 및 스탬프 적립
    @Transactional
    public StampResponseDto scanStamp(Long userId, Long qrId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DefaultException(ErrorCode.USER_NOT_FOUND_ERROR));

        QrCode qrCode = qrCodeRepository.findById(qrId)
                .orElseThrow(() -> new DefaultException(ErrorCode.INVALID_OPTIONAL_ISPRESENT));

        // 만료 여부
        if (qrCode.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new DefaultException(ErrorCode.INVALID_CHECK);
        }

        // 중복 적립
        if (stampRepository.existsByUserAndQrCode(user, qrCode)) {
            throw new DefaultException(ErrorCode.INVALID_PARAMETER);
        }

        // Market 조회
        Market market = marketRepository.findById(qrCode.getMarketId())
                .orElseThrow(() -> new DefaultException(ErrorCode.MARKET_NOT_FOUND_ERROR));

        Stamp stamp = Stamp.builder()
                .user(user)
                .qrCode(qrCode)
                .scanTime(LocalDateTime.now())
                .market(market)
                .build();
        stampRepository.save(stamp);

        long total = stampRepository.countByUser(user);

        return StampResponseDto.builder()
                .qrId(stamp.getId())
                .scanTime(stamp.getScanTime())
                .marketId(market.getId())
                .marketName(market.getMarketName())
                .placeName(qrCode.getPlaceName())
                .totalStampCount((int) total)
                .build();
    }
}

