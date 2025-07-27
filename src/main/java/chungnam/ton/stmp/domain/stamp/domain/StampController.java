package chungnam.ton.stmp.domain.stamp.domain;

import chungnam.ton.stmp.domain.stamp.domain.dto.StampInfoDto;
import chungnam.ton.stmp.domain.stamp.domain.dto.StampRequestDto;
import chungnam.ton.stmp.domain.stamp.domain.dto.StampResponseDto;
import chungnam.ton.stmp.domain.user.domain.UserPrincipal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/scan")
    public ResponseEntity<StampResponseDto> scanStamp(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody StampRequestDto request
    ) {
        Long userId = principal.getUser().getId();
        String qrId   = request.getQrId();
        String placeName = request.getPlaceName();
        Long marketId = request.getMarketId();


        StampResponseDto response =
                stampService.scanStamp(userId, qrId, placeName, marketId);

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<StampInfoDto>> getMyStamps(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        Long userId = principal.getUser().getId();
        List<StampInfoDto> stamps = stampService.getStampsByUser(userId);
        return ResponseEntity.ok(stamps);
    }

    @GetMapping("/period")
    public ResponseEntity<List<StampInfoDto>> getStampsByPeriod(
            @RequestParam("from")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        List<StampInfoDto> stamps = stampService.getStampsByPeriod(from, to);
        return ResponseEntity.ok(stamps);
    }

}
