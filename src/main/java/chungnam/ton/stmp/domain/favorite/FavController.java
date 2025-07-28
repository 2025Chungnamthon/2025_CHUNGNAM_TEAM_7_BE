package chungnam.ton.stmp.domain.favorite;

import chungnam.ton.stmp.domain.favorite.dto.FavRequestDto;
import chungnam.ton.stmp.domain.favorite.dto.FavResponseDto;
import chungnam.ton.stmp.global.payload.ResponseCustom;
import chungnam.ton.stmp.global.util.jwt.JwtUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class FavController {

    private final FavService favService;
    private final JwtUtil jwtUtil;

    // 즐겨찾기 추가
    @PostMapping
    public ResponseCustom<FavResponseDto> addBookmark(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody FavRequestDto request
    ) {
        String rawToken = bearerToken.replace("Bearer ", "");
        //JWT 파싱해서 userId 추출
        Long userId = jwtUtil.getUserIdFromToken(rawToken);
        FavResponseDto response = favService.addBookmark(userId, request);

        return ResponseCustom.CREATED(response);
    }

    // 즐겨찾기 삭제
    @DeleteMapping("/{marketId}")
    public ResponseCustom<Void> removeBookmark(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long marketId
    ) {
        String rawToken = bearerToken.replace("Bearer ", "");
        Long userId = jwtUtil.getUserIdFromToken(rawToken);
        favService.removeBookmark(
                userId,
                marketId
        );
        return ResponseCustom.OK();
    }

    @GetMapping
    public ResponseCustom<List<FavResponseDto>> listBookmarks(
            @RequestHeader("Authorization") String bearerToken
    ) {
        String rawToken = bearerToken.replace("Bearer ", "");
        //JWT 파싱해서 userId 추출
        Long userId = jwtUtil.getUserIdFromToken(rawToken);
        List<FavResponseDto> favorites = favService.listBookmarks(
                userId);
        return ResponseCustom.OK(favorites);
    }
}
