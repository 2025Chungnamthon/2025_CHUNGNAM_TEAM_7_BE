package chungnam.ton.stmp.domain.market.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class MarketDetail {

    private String marketName;
    private String region;
    private String address;
    private String imgUrl;

    private List<String> mainProducts;
    private List<String> landmarks;

    private int facilityCount;
    private List<String> facilities;

}
