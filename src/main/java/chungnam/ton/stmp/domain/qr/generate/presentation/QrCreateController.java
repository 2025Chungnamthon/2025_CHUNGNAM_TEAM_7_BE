package chungnam.ton.stmp.domain.qr.generate.presentation;

import chungnam.ton.stmp.domain.qr.generate.domain.QrCode;
import chungnam.ton.stmp.domain.qr.generate.dto.request.QrCreateRequest;
import chungnam.ton.stmp.domain.qr.generate.dto.response.QrCreateResponse;
import chungnam.ton.stmp.domain.qr.generate.service.QrCodeService;

import chungnam.ton.stmp.domain.qr.generate.service.QrGenerateResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.google.zxing.WriterException;
import java.io.IOException;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/qr")
public class QrCreateController {

    private final QrCodeService qrCodeService;

    public QrCreateController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @PostMapping("/generate")
    @ResponseBody
    public QrCreateResponse generateQrCode(@RequestBody QrCreateRequest request) throws WriterException, IOException {
        QrGenerateResult result = qrCodeService.createQr(request.getMarketId());
        QrCode qrCode = result.getQrCode();
        String base64Image = result.getBase64Image();

        return QrCreateResponse.builder()
                .base64QrImage(base64Image)
                .expiredAt(qrCode.getExpiredAt())
                .build();
    }
    @GetMapping("/page")
    public String qrPage(Model model) throws WriterException, IOException {
        Long marketId = 1L;  // 고정 마켓
        QrGenerateResult result = qrCodeService.createQr(marketId);

        model.addAttribute("qrImageBase64", result.getBase64Image());
        model.addAttribute("expiredAt", result.getQrCode().getExpiredAt().truncatedTo(ChronoUnit.SECONDS).toString());
        model.addAttribute("durationMinutes", result.getQrCode().getDuration());


        return "qr/page";
    }
}