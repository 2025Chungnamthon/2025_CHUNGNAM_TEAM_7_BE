package chungnam.ton.stmp.domain.market.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "시장의 상제정보 조회용 response DTO입니다.")
public record SearchMarketRequest(
        String keyword
) {
}
