package chungnam.ton.stmp.domain.gitfCard.domain;

import chungnam.ton.stmp.domain.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class GiftCard extends BaseEntity {
    private int cost;
    private String name;
    private String description;
    private String ImageName;
    private String imageUrl;
}
