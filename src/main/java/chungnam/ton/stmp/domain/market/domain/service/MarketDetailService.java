package chungnam.ton.stmp.domain.market.domain.service;

import chungnam.ton.stmp.domain.market.domain.Facility;
import chungnam.ton.stmp.domain.market.domain.Market;
import chungnam.ton.stmp.domain.market.domain.dto.response.MarketDetailResponse;
import chungnam.ton.stmp.domain.market.domain.dto.request.SearchMarketDetailRequest;
import chungnam.ton.stmp.domain.market.domain.repository.MarketRepository;
import chungnam.ton.stmp.global.error.DefaultException;
import chungnam.ton.stmp.global.payload.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarketDetailService {
    private final MarketRepository marketRepository;

    public MarketDetailService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    public MarketDetailResponse getMarketDetailById(SearchMarketDetailRequest searchMarketDetailRequest) {
        Long id = searchMarketDetailRequest.marketId();
        return marketRepository.findById(id).map(this::convertDto).orElseThrow(() -> new DefaultException(ErrorCode.MARKET_NOT_FOUND_ERROR));
    }

    private MarketDetailResponse convertDto(Market market) {
        Facility f = market.getFacilities();
        List<String> facilityList = new ArrayList<>();
        if (f.isArcade()) facilityList.add("아케이드");
        if (f.isElevatorEscalator()) facilityList.add("엘리베이터/에스컬레이터");
        if (f.isLounge()) facilityList.add("고객휴게실");
        if (f.isShoppingCart()) facilityList.add("쇼핑카트");
        if (f.isParkingLot()) facilityList.add("시장전용 고객주차장");

        return MarketDetailResponse.builder()
                .marketName(market.getMarketName())
                .region(market.getRegion())
                .address(market.getAddress())
//                .mainProducts(market.getMainProducts())
//                .landmarks(market.getLandmarks())
                .facilityCount(facilityList.size())
                .facilities(facilityList)
                .build();
    }
}
