package chungnam.ton.stmp.domain.market.domain.service;

import chungnam.ton.stmp.domain.market.domain.Facility;
import chungnam.ton.stmp.domain.market.domain.Market;
import chungnam.ton.stmp.domain.market.domain.dto.MarketDetail;
import chungnam.ton.stmp.domain.market.domain.repository.MarketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MarketDetailService {
    private final MarketRepository marketRepository;

    public MarketDetailService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    public Optional<MarketDetail> getMarketDetailById(Long id) {
        return marketRepository.findById(id).map(this::convertDto);
    }

    private MarketDetail convertDto(Market market) {
        Facility f = market.getFacilities();
        List<String> facilityList = new ArrayList<>();
        if (f.isArcade()) facilityList.add("아케이드");
        if (f.isElevatorEscalator()) facilityList.add("엘리베이터/에스컬레이터");
        if (f.isLounge()) facilityList.add("고객휴게실");
        if (f.isShoppingCart()) facilityList.add("쇼핑카트");
        if (f.isParkingLot()) facilityList.add("시장전용 고객주차장");

        return MarketDetail.builder()
                .marketName(market.getMarketName())
                .region(market.getRegion())
                .address(market.getAddress())
                .mainProducts(market.getMainProducts())
                .landmarks(market.getLandmarks())
                .facilityCount(facilityList.size())
                .facilities(facilityList)
                .build();
    }
}
