package chungnam.ton.stmp.domain.qr.generate.domain;

import chungnam.ton.stmp.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import chungnam.ton.stmp.domain.market.domain.Market;


import java.time.LocalDateTime;
import java.util.Base64;

@Getter
@Setter
@Entity
@Table(name="qr_code")
public class QrCode extends BaseEntity {

    @Column(name="qr_image_url", nullable = false)
    private String qrImageUrl;

    @Column(name="expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name="duration")
    private Integer duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", nullable = false)
    private Market market;

    @Column(name = "place_name", nullable = false)
    private String placeName;
}
