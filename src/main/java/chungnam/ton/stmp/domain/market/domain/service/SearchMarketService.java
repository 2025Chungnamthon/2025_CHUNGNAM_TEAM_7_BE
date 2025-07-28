package chungnam.ton.stmp.domain.market.domain.service;

import chungnam.ton.stmp.domain.market.domain.Market;
import chungnam.ton.stmp.domain.market.domain.dto.request.SearchMarketRequest;
import chungnam.ton.stmp.domain.market.domain.dto.response.MarketResponse;
import chungnam.ton.stmp.domain.market.domain.repository.MarketRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class SearchMarketService {

    private final MarketRepository marketRepository;
    private final ObjectMapper objectMapper;

    public SearchMarketService(MarketRepository marketRepository, ObjectMapper objectMapper) {
        this.marketRepository = marketRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        try {
            if(marketRepository.count() == 0) {
                log.info("market.json 초기 데이터 로딩");

                ClassPathResource resource = new ClassPathResource("market/data/market.json");
                InputStream inputStream = resource.getInputStream();

                List<Market> markets = objectMapper.readValue(inputStream, new TypeReference<List<Market>>() {});
                marketRepository.saveAll(markets);
                
                log.info("market.json 데이터 저장 완료");
            } else {
                log.info("이미 마켓 데이터 존재하여 초기화 생략");
            }
        } catch (Exception e) {
            log.error("시장 Json 로드 실패", e);
        }
    }

    public List<MarketResponse> searchMarkets(SearchMarketRequest searchMarketRequest) {
        String keyword = searchMarketRequest.keyword();
        log.info("검색 요청: {}", keyword);
        List<MarketResponse> responses = marketRepository.findByMarketNameContainingOrRegionContaining(keyword, keyword).stream().map(MarketResponse::fromEntity).toList();
        return responses;
    }
}