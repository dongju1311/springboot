package com.springboot.shoppy_fullstack_app.controller;

import com.springboot.shoppy_fullstack_app.dto.KakaoPay;
import com.springboot.shoppy_fullstack_app.dto.KakaoApproveResponse;
import com.springboot.shoppy_fullstack_app.dto.KakaoReadyResponse;
import com.springboot.shoppy_fullstack_app.service.KakaoPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;

    @Autowired
    public KakaoPayController(KakaoPayService kakaoPayService) {
        this.kakaoPayService = kakaoPayService;
    }

    /**
     * 결제 준비 요청 (프론트에서 호출)
     * 카카오페이 결제 ready API 호출
     */
    @PostMapping("/kakao/ready")
    public KakaoReadyResponse paymentKakao(@RequestBody  KakaoPay kakaoPay) {
        String orderId = kakaoPay.getOrderId();
        String userId = kakaoPay.getUserId();
        String itemName = kakaoPay.getItemName();
        String qty = kakaoPay.getQty();
        String totalAmount = kakaoPay.getTotalAmount();

        String TEMP_TID = null;
        System.out.println(orderId + userId + itemName + qty + totalAmount);
        KakaoReadyResponse response = kakaoPayService.kakaoPayReady(kakaoPay);
        System.out.println("Kakao Pay Ready --> " + response);
        if (response != null) {
            TEMP_TID = response.getTid(); // 발급받은 TID 저장
            System.out.println("TID 발급 성공: " + TEMP_TID + ". 사용자에게 QR 코드를 제시하고 승인을 기다립니다.");
        } else {
            System.out.println("결제 준비 실패.");
        }

        return response;
    }

    @GetMapping("/qr/success")
    public ResponseEntity<Void> success( @RequestParam String orderId, @RequestParam("pg_token") String pgToken) {

        // 1. tid, userId 조회
        String tid = kakaoPayService.findByTid(orderId);
        String userId = kakaoPayService.findByUserId(orderId);

        // 2. 조회된 tid를 Approve 서비스에 넘겨 최종 승인 요청
        KakaoApproveResponse approve = kakaoPayService.approve(tid, userId, orderId, pgToken);
        System.out.println("Kakao Approve Success --> " + approve);

        // 3. 결제 완료 처리 (DB 상태 업데이트 등)
        URI redirect = URI.create("http://localhost:3000/payResult?orderId=" + orderId + "&status=success");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirect);

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }


    /**
     * ✅ 3️⃣ 결제 취소 콜백
     */
    @GetMapping("/qr/cancel")
    public ResponseEntity<?> cancel(@RequestParam String orderId) {
        return ResponseEntity.ok(Map.of("status", "CANCEL", "orderId", orderId));
    }

    /**
     * ✅ 4️⃣ 결제 실패 콜백
     */
    @GetMapping("/qr/fail")
    public ResponseEntity<?> fail(@RequestParam String orderId) {
        return ResponseEntity.ok(Map.of("status", "FAIL", "orderId", orderId));
    }
}
