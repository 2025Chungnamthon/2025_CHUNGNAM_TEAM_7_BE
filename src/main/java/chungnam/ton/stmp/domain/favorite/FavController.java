package chungnam.ton.stmp.domain.favorite;

import chungnam.ton.stmp.domain.favorite.dto.FavRequestDto;
import chungnam.ton.stmp.domain.favorite.dto.FavResponseDto;
import chungnam.ton.stmp.domain.user.domain.UserPrincipal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<FavResponseDto> addFavorite(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody FavRequestDto request
    ) {
        FavResponseDto response = favService.addFavorite(
                principal.getUser().getId(), // User ID는 UserPrincipal에서 가져옴
                request
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 즐겨찾기 삭제
    @DeleteMapping("/{marketId}")
    public ResponseEntity<Void> removeFavorite(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long marketId
    ) {
        favService.removeFavorite(
                principal.getUser().getId(), // User ID는 UserPrincipal에서 가져옴
                marketId
        );
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FavResponseDto>> listFavorites(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        List<FavResponseDto> favorites = favService.listFavorites(
                principal.getUser().getId() // User ID는 UserPrincipal에서 가져옴
        );
        return ResponseEntity.ok(favorites);
    }


}
