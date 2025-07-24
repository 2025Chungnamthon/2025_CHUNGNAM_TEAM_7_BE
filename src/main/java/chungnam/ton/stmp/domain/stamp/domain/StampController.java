package chungnam.ton.stmp.domain.stamp.domain;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<String> scanStamp(@RequestBody StampDto request) {
        try {
            stampService.scanStamp(request.getUserId(), request.getQrId());
            return ResponseEntity.ok("스탬프가 성공적으로 적립되었습니다.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StampDto>> getStampsByUser(@PathVariable Long userId) {
        List<StampDto> response = stampService.getStampsByUser(userId).stream()
                .map(StampDto::new) // Stamp 객체를 StampDto로 변환
                .toList();
        return ResponseEntity.ok(response);
    }


    // 특정 기간 동안의 스탬프 조회
    @GetMapping("/StartDateBetween")
    public ResponseEntity<List<StampDto>> getStampsByStartDateBetween(@RequestParam String startDate, @RequestParam String endDate) {
        List<StampDto> response = stampService.getStampsByStartDateBetween(startDate, endDate).stream()
                .map(StampDto::new) // Stamp 객체를 StampDto로 변환
                .toList();
        return ResponseEntity.ok(response);
    }

}
