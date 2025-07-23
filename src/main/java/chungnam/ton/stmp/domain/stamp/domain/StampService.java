package chungnam.ton.stmp.domain.stamp.domain;

import chungnam.ton.stmp.domain.user.domain.User;
import chungnam.ton.stmp.domain.user.domain.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StampService {

    private final StampRepository stampRepository;
    private final UserRepository userRepository;

    // QR 코드 스캔 로직 구현
    //예외) // 1. QR 코드가 유효하지 않은 경우 , // 2. 이미 스탬프가 스캔된 경우 ...
    @Transactional
    public void scanStamp(Long userId, Long qrId) {
        // QR 코드 유효성 검증
        if (!isValidQRCode(qrId)) {
            throw new IllegalArgumentException("유효하지 않은 QR 코드입니다.");
        }

        // 유저 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        // 이미 스캔된 QR 코드인지 확인
        boolean alreadyScanned = stampRepository.findByUser_Id(userId).stream()
                .anyMatch(stamp -> stamp.getQrId().equals(qrId));
        if (alreadyScanned) {
            throw new IllegalStateException("이미 스캔된 QR 코드입니다.");
        }

        // 스탬프 저장
        Stamp stamp = new Stamp();
        stamp.setQrId(qrId);
        stamp.setUser(user);
        stamp.setScanTime(LocalDateTime.now()); // 현재 시간으로 스캔 시간 설정
        stampRepository.save(stamp);
    }

//    public List<Stamp> getStampsByUser(Long userId) {
//        return stampRepository.findByUser_Id(userId);
//    }

    public List<Stamp> getStampsByUser(Long userId) {
        System.out.println(">>>>> getStampsByUser 호출됨: userId = " + userId);
        List<Stamp> result = stampRepository.findByUser_Id(userId);
        System.out.println(">>>>> 결과 개수: " + result.size());
        return result;
    }


    public List<Stamp> getStampsByStartDateBetween(String startDate, String endDate) {
        return stampRepository.findByStartDateBetween(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
    }

    private boolean isValidQRCode(Long qrId) {
        // QR 코드 유효성 검증 로직 (예: 정규식 또는 외부 서비스 호출)
        return qrId != null && qrId>0;
    }

}
