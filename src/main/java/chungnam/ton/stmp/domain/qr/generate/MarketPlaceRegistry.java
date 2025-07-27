package chungnam.ton.stmp.domain.qr.generate;

import java.util.*;

public class MarketPlaceRegistry {

    // 공통 장소 리스트를 'marketId'라는 문자열 key로 통일하여 저장
    private static final Map<String, List<String>> PLACE_MAP = new HashMap<>();

    static {
        // 모든 marketId에 공통으로 적용할 장소들
        PLACE_MAP.put("marketId", Arrays.asList("입구", "중앙통로", "후문"));
    }

    /**
     * 주어진 marketId에 대해 사용 가능한 장소 리스트를 반환합니다.
     * 현재는 공통 장소 리스트를 반환합니다.
     */
    public static List<String> getPlaces(Long marketId) {
        return PLACE_MAP.getOrDefault("marketId", Collections.emptyList());
    }

    /**
     * 주어진 marketId와 장소 이름이 유효한지 검사합니다.
     * 현재는 모든 marketId에 대해 공통 장소 리스트만 유효합니다.
     */
    public static boolean isValidPlace(Long marketId, String placeName) {
        return PLACE_MAP.getOrDefault("marketId", Collections.emptyList()).contains(placeName);
    }
}
