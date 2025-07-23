package chungnam.ton.stmp.domain.market.domain.service;

import chungnam.ton.stmp.domain.market.domain.Market;
import chungnam.ton.stmp.domain.market.domain.repository.MarketRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
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
            InputStream inputStream = getClass().getResourceAsStream("/chungnam/ton/stmp/domain/market/data/market.json");
            List<Market> markets = objectMapper.readValue(inputStream, new TypeReference<List<Market>>() {});
            marketRepository.saveAll(markets);
        } catch (Exception e) {
            log.error("시장 Json 로드 실패", e);
        }
    }

    public List<Market> searchMarkets(String keyword) {
        log.info("검색 요청 keyword: {}", keyword);
        return marketRepository.findByMarketNameContainingOrRegionContaining(keyword, keyword);
    }
}