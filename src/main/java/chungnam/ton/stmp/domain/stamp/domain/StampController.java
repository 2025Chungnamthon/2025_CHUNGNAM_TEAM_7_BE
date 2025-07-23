package chungnam.ton.stmp.domain.stamp.domain;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stamps")
@RequiredArgsConstructor
public class StampController {

    private final StampService stampService;

    // QR 코드 스캔 및 스탬프 적립
    @PostMapping("/scan")
    public ResponseEntity<String> scanStamp(@RequestParam Long userId, @RequestParam Long qrId) {
        try {
            stampService.scanStamp(userId, qrId);
            return ResponseEntity.ok("스탬프가 성공적으로 적립되었습니다.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 특정 유저의 스탬프 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Stamp>> getStampsByUser(@PathVariable Long userId) {
        List<Stamp> stamps = stampService.getStampsByUser(userId);
        return ResponseEntity.ok(stamps);
    }

    // 특정 기간 동안의 스탬프 조회
    @GetMapping("/StartDateBetween")
    public ResponseEntity<List<Stamp>> getStampsByStartDateBetween(@RequestParam String startDate, @RequestParam String endDate) {
        List<Stamp> stamps = stampService.getStampsByStartDateBetween(startDate, endDate);
        return ResponseEntity.ok(stamps);
    }

}
