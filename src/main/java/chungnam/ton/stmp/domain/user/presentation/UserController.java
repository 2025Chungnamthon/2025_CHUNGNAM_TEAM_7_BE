package chungnam.ton.stmp.domain.user.presentation;


import chungnam.ton.stmp.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Tag(name = "유저 정보 관련 API", description = "유저의 개인정보, 서비스 관련 정보를 확인하는 API입니다.")
//@RequiredArgsConstructor
//@RequestMapping("/api/my")
//@RestController
//public class UserController {
//
//    @Operation(summary = "", description = "")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "성공"),
//            @ApiResponse(responseCode = "400", description = "실패")
//    })
//    @GetMapping("/bookmarks")
//    public ResponseCustom<?> getMyStamps(
//            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @RequestHeader("Authorization") String bearerToken
//    ) {
//    }
//}
