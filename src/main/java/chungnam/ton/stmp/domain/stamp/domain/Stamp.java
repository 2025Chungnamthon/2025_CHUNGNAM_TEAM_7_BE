package chungnam.ton.stmp.domain.stamp.domain;

import chungnam.ton.stmp.domain.common.BaseEntity;
import chungnam.ton.stmp.domain.market.domain.Market;
import chungnam.ton.stmp.domain.user.domain.User;
import chungnam.ton.stmp.domain.qr.generate.domain.QrCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Stamp extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qr_id", nullable = false)
    private QrCode qrCode; // FK: qr_id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;      // FK: user_id

    @Column(name = "scan_time", nullable = false)
    private LocalDateTime scanTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", nullable = false, columnDefinition = "BIGINT")
    private Market market; // FK: market_id


    @Builder(toBuilder = true)
    public Stamp(QrCode qrCode, User user, LocalDateTime scanTime, Market market) {
        this.qrCode = qrCode;
        this.user = user;
        this.scanTime = scanTime;
        this.market = market;
    }
}