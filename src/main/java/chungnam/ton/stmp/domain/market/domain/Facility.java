package chungnam.ton.stmp.domain.market.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facility {
    @JsonProperty("아케이드 보유 여부")
    private boolean arcade;

    @JsonProperty("엘리베이터_에스컬레이터_보유여부")
    private boolean elevatorEscalator;

    @JsonProperty("고객휴게실_보유여부")
    private boolean lounge;

    @JsonProperty("쇼핑카트_보유여부")
    private boolean shoppingCart;

    @JsonProperty("시장전용 고객주차장_보유여부")
    private boolean parkingLot;}
