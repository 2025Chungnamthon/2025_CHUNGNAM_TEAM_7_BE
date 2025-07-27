package chungnam.ton.stmp.domain.favorite;

import chungnam.ton.stmp.domain.favorite.dto.FavRequestDto;
import chungnam.ton.stmp.domain.favorite.dto.FavResponseDto;
import chungnam.ton.stmp.domain.market.domain.Market;
import chungnam.ton.stmp.domain.market.domain.repository.MarketRepository;
import chungnam.ton.stmp.domain.user.domain.User;
import chungnam.ton.stmp.domain.user.domain.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavService {

    private final FavRepository favRepo;
    private final UserRepository userRepo;
    private final MarketRepository marketRepo;

    // 즐겨찾기 추가
    @Transactional
    public FavResponseDto addFavorite(Long userId, FavRequestDto req) {
        // 1) User/Market 존재 검사
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Market market = marketRepo.findById(req.getMarketId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 마켓입니다."));

        // 2) 중복 체크
        favRepo.findByUserAndMarket(user, market).ifPresent(f -> {
            throw new IllegalStateException("이미 즐겨찾기가 추가된 마켓입니다.");
        });

        // 3) 저장
        Fav favo = Fav.builder()
                .user(user)
                .market(market)
                .build();
        Fav saved = favRepo.save(favo);

        // 4) DTO 반환
        return FavResponseDto.builder()
                .favId(saved.getId())
                .marketId(saved.getMarket().getId())
                .marketName(saved.getMarket().getMarketName())
                .build();
    }

    // 즐겨찾기 삭제
    @Transactional
    public void removeFavorite(Long userId, Long marketId) {
        User user = userRepo.getReferenceById(userId);
        Market market = marketRepo.getReferenceById(marketId);
        // deleteByUserAndMarket 내부에서 해당 레코드 없으면 조용히 넘어갑니다.
        favRepo.deleteByUserAndMarket(user, market);
    }

    // 즐겨찾기 목록 조회
    @Transactional(readOnly = true)
    public List<FavResponseDto> listFavorites(Long userId) {
        User user = userRepo.getReferenceById(userId);
        return favRepo.findAllByUser(user).stream()
                .map(f -> FavResponseDto.builder()
                        .favId(f.getId())
                        .marketId(f.getMarket().getId())
                        .marketName(f.getMarket().getMarketName())
                        .build()
                )
                .collect(Collectors.toList());
    }

}
