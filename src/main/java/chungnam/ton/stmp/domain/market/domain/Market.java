package chungnam.ton.stmp.domain.market.domain;

import chungnam.ton.stmp.domain.common.BaseEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Market extends BaseEntity {

    private String marketName;
    private String region;
    private String address;
    private String imgUrl;
    private int stampAmount;

    @Embedded
    private Facility facilities;
}