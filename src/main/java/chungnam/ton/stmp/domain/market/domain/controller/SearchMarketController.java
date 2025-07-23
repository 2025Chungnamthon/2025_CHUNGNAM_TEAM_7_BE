package chungnam.ton.stmp.domain.market.domain.controller;

import chungnam.ton.stmp.domain.market.domain.Market;
import chungnam.ton.stmp.domain.market.domain.service.SearchMarketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/markets")
public class SearchMarketController {

    private final SearchMarketService marketService;

    public SearchMarketController(SearchMarketService marketService) {
        this.marketService = marketService;
    }

    // GET/markets/search?keyword=천안역전 등
    @GetMapping("/search")
    public List<Market> searchMarkets(@RequestParam String keyword) {
        return marketService.searchMarkets(keyword);
    }
}
