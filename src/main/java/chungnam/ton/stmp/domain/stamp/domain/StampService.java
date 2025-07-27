package chungnam.ton.stmp.domain.stamp.domain;

import chungnam.ton.stmp.domain.market.domain.Market;
import chungnam.ton.stmp.domain.market.domain.repository.MarketRepository;
import chungnam.ton.stmp.domain.stamp.domain.dto.StampInfoDto;
import chungnam.ton.stmp.domain.stamp.domain.dto.StampResponseDto;
import chungnam.ton.stmp.domain.stamp.domain.repository.StampRepository;
import chungnam.ton.stmp.domain.user.domain.User;
import chungnam.ton.stmp.domain.user.domain.repository.UserRepository;
import chungnam.ton.stmp.global.error.DefaultException;
import chungnam.ton.stmp.global.payload.ErrorCode;
import chungnam.ton.stmp.domain.qr.generate.domain.QrCode;
import chungnam.ton.stmp.domain.qr.generate.domain.repository.QrCodeRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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
    public StampResponseDto scanStamp(Long userId, String qrId, String placeName, Long marketId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DefaultException(ErrorCode.USER_NOT_FOUND_ERROR));

        QrCode qrCode = qrCodeRepository.findById(qrId)
                .orElseThrow(() -> new DefaultException(ErrorCode.INVALID_OPTIONAL_ISPRESENT));


        // 만료 여부 체크
        if (qrCode.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new DefaultException(ErrorCode.INVALID_CHECK);
        }

        // 중복 적립 방지
        if (stampRepository.existsByUserAndQrCode(user, qrCode)) {
            throw new DefaultException(ErrorCode.INVALID_PARAMETER);
        }

        // Market 조회
        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new DefaultException(ErrorCode.MARKET_NOT_FOUND_ERROR));

        Stamp stamp = Stamp.create(user, qrCode);
        stampRepository.save(stamp);

        return new StampResponseDto(
                "스탬프가 적립되었습니다.",
                stamp.getScanTime(),
                market.getId(),
                market.getMarketName(),
                qrCode.getPlaceName()
        );
    }

    // 유저별 스탬프 조회
    @Transactional(readOnly = true)
    public List<StampInfoDto> getStampsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DefaultException(ErrorCode.USER_NOT_FOUND_ERROR));

        return stampRepository.findAllByUser(user).stream()
                .map(s -> new StampInfoDto(
                        s.getQrCode().getQrId(),
                        s.getScanTime(),
                        s.getQrCode().getMarketId(),
                        s.getQrCode().getPlaceName()
                ))
                .collect(Collectors.toList());
    }

    // 특정 기간 동안의 스탬프 조회
    @Transactional(readOnly = true)
    public List<StampInfoDto> getStampsByPeriod(LocalDateTime from,
            LocalDateTime to) {
        return stampRepository.findAllByScanTimeBetween(from, to).stream()
                .map(s -> new StampInfoDto(
                        s.getQrCode().getQrId(),
                        s.getScanTime(),
                        s.getQrCode().getMarketId(),
                        s.getQrCode().getPlaceName()
                ))
                .collect(Collectors.toList());
    }

}
