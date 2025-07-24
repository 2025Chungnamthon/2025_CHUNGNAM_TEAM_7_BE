package chungnam.ton.stmp.domain.stamp.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StampDto {

    private Long stampId; // BaseEntity의 id
    private LocalDateTime createdAt; // BaseEntity
    private LocalDateTime updatedAt; // BaseEntity

    private Long qrId;
    private Long userId;
    private LocalDateTime scanTime;
    private LocalDate startDate;
    private LocalDate endDate;

    public StampDto(Stamp stamp) {
        this.stampId = stamp.getId();
        this.createdAt = stamp.getCreatedAt();
        this.updatedAt = stamp.getUpdatedAt();
        this.qrId = stamp.getQrId();
        this.userId = stamp.getUser() != null ? stamp.getUser().getId() : null;
        this.scanTime = stamp.getScanTime();
        this.startDate = stamp.getStartDate();
        this.endDate = stamp.getEndDate();
    }
}
