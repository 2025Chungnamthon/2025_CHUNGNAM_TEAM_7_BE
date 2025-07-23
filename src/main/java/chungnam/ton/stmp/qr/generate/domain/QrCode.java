package chungnam.ton.stmp.qr.generate.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="qr_code")
public class QrCode {

    @Id
    @Column(name="qr_id")
    private String qrId;

    @Column(name="qr_image_url", nullable = false)
    private String qrImageUrl;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name="duration")
    private  Integer duration;

   // @ManyToOne(fetch = FetchType.LAZY)
    @Column(name="marketId", nullable = false)
    private Long marketId;

}
