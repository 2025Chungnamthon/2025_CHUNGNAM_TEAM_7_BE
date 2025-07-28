package chungnam.ton.stmp.domain.market.service;

import chungnam.ton.stmp.domain.market.domain.Facility;
import chungnam.ton.stmp.domain.market.domain.Market;
import chungnam.ton.stmp.domain.market.dto.response.MarketDetailResponse;
import chungnam.ton.stmp.domain.market.dto.request.SearchMarketDetailRequest;
import chungnam.ton.stmp.domain.market.domain.repository.MarketRepository;
import chungnam.ton.stmp.domain.stamp.domain.repository.StampRepository;
import chungnam.ton.stmp.domain.stamp.dto.reponse.StampStatusResponse;
import chungnam.ton.stmp.domain.user.domain.User;
import chungnam.ton.stmp.domain.user.domain.repository.UserRepository;
import chungnam.ton.stmp.global.error.DefaultException;
import chungnam.ton.stmp.global.payload.ErrorCode;
import chungnam.ton.stmp.global.util.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class MarketDetailService {
    private final MarketRepository marketRepository;
    private final StampRepository stampRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public MarketDetailResponse getMarketDetailById(String rawToken, SearchMarketDetailRequest searchMarketDetailRequest) {
        String token = jwtUtil.getJwt(rawToken);
        Long userId = jwtUtil.getUserIdFromToken(token);
        userRepository.findById(userId).orElseThrow(() -> new DefaultException(ErrorCode.USER_NOT_FOUND_ERROR));

        Market market = marketRepository.findById(searchMarketDetailRequest.marketId())
                .orElseThrow(() -> new DefaultException(ErrorCode.MARKET_NOT_FOUND_ERROR));

        StampStatusResponse status = getStampStatus(userId, market.getId());
        return convertDto(market, status);
    }

    private StampStatusResponse getStampStatus(Long userId, Long marketId) {
        int required = marketRepository.findById(marketId)
                .orElseThrow(() -> new DefaultException(ErrorCode.MARKET_NOT_FOUND_ERROR))
                .getStampAmount();
        int actual = stampRepository.countByUserIdAndMarketId(userId, marketId);

        return new StampStatusResponse(actual, required);
    }

    private MarketDetailResponse convertDto(Market market, StampStatusResponse s) {
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
                .imgUrl(market.getImgUrl())
                .actual(s.collected())
                .required(s.required())
                .facilityCount(facilityList.size())
                .facilities(facilityList)
                .build();
    }
}
