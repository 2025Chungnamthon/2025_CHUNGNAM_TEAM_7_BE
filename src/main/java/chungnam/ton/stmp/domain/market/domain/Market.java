package chungnam.ton.stmp.domain.market.domain;

import chungnam.ton.stmp.domain.common.BaseEntity;
import chungnam.ton.stmp.domain.favorite.Fav;
import jakarta.persistence.*;
import java.util.ArrayList;
import lombok.*;

import java.util.List;

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

    @ElementCollection
    private List<String> mainProducts;

    @ElementCollection
    private List<String> landmarks;

    @Embedded
    private Facility facilities;

    @OneToMany(mappedBy = "market", cascade = CascadeType.REMOVE)
    private List<Fav> favoritedBy = new ArrayList<>();
}