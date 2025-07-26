package chungnam.ton.stmp.domain.market.domain.controller;

import chungnam.ton.stmp.domain.market.domain.service.MarketDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/markets")
public class MarketDetailController {

    private final MarketDetailService marketDetailService;

    public MarketDetailController(MarketDetailService marketDetailService) {
        this.marketDetailService = marketDetailService;
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getMarketDetail(@RequestParam Long id) {
        return marketDetailService.getMarketDetailById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
