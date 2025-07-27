package chungnam.ton.stmp.domain.stamp.domain;

import chungnam.ton.stmp.domain.common.BaseEntity;
import chungnam.ton.stmp.domain.user.domain.User;
import chungnam.ton.stmp.domain.qr.generate.domain.QrCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Entity
public class Stamp extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qr_id", nullable = false)
    private QrCode qrCode; // FK: qr_id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;      // FK: user_id

    @Column(name = "scan_time", nullable = false)
    private LocalDateTime scanTime;



    public static Stamp create(User user, QrCode qrCode) {
        Stamp stamp = new Stamp();
        stamp.user = user;
        stamp.qrCode = qrCode;
        stamp.scanTime = LocalDateTime.now();
        return stamp;
    }
}
