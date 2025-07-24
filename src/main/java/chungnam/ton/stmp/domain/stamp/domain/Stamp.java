package chungnam.ton.stmp.domain.stamp.domain;

import chungnam.ton.stmp.domain.common.BaseEntity;
import chungnam.ton.stmp.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Stamp extends BaseEntity {

    @Column(name = "qr_id")
    private Long qrId;  // QR 코드 ID를 직접 저장, 임시


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;      // FK: user_id

    @Column(name = "scan_time")
    private LocalDateTime scanTime;


    private LocalDate startDate;
    private LocalDate endDate;


}
