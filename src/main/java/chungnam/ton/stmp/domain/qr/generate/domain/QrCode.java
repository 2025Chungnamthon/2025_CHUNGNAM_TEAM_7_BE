package chungnam.ton.stmp.domain.qr.generate.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import chungnam.ton.stmp.domain.market.domain.Market;


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

    @Column(name="marketId", nullable = false)
    private Long marketId;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "market_id", nullable = false)
    //private Market market;


    @Column(name = "place_Name", nullable = false)
    private String placeName;
}
