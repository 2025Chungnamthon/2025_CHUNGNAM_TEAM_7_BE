package chungnam.ton.stmp.domain.favorite;

import chungnam.ton.stmp.domain.favorite.dto.FavRequestDto;
import chungnam.ton.stmp.domain.favorite.dto.FavResponseDto;
import chungnam.ton.stmp.domain.user.domain.UserPrincipal;
import chungnam.ton.stmp.global.payload.ResponseCustom;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavController {

    private final FavService favService;

    // 즐겨찾기 추가
    @PostMapping
    public ResponseCustom<FavResponseDto> addFavorite(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody FavRequestDto request
    ) {
        FavResponseDto response = favService.addFavorite(
                principal.getUser().getId(), // User ID는 UserPrincipal에서 가져옴
                request
        );

        return ResponseCustom.CREATED(response);
    }

    // 즐겨찾기 삭제
    @DeleteMapping("/{marketId}")
    public ResponseCustom<Void> removeFavorite(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long marketId
    ) {
        favService.removeFavorite(
                principal.getUser().getId(), // User ID는 UserPrincipal에서 가져옴
                marketId
        );
        return ResponseCustom.OK();
    }

    @GetMapping
    public ResponseCustom<List<FavResponseDto>> listFavorites(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        List<FavResponseDto> favorites = favService.listFavorites(
                principal.getUser().getId() // User ID는 UserPrincipal에서 가져옴
        );
        return ResponseCustom.OK(favorites);
    }


}
