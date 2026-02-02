package Qpick.server.domain.product.application;

import Qpick.server.domain.product.converter.ProductConverter;
import Qpick.server.domain.product.domain.entity.Product;
import Qpick.server.domain.product.domain.repository.ProductRepository;
import Qpick.server.domain.product.dto.ProductRequestDTO;
import Qpick.server.domain.product.dto.ProductResponseDTO;
import Qpick.server.domain.product.exception.productException;
import Qpick.server.global.error.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Value("${spring.ai.gemini.api-key}")
    private String apiKey;

    @Value("${spring.ai.gemini.url}")
    private String apiUrl;

    @Override
    @Transactional
    public ProductResponseDTO.ProductRegisterDTO productRegister(ProductRequestDTO.ProductRegisterDTO request) {

        // 1. Entity 변환
        Product newProduct = ProductConverter.toEntity(request);

        // 2. DB 저장
        Product savedProduct = productRepository.save(newProduct);

        // 3. 응답 DTO 반환
        return ProductConverter.toRegisterDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO.ProductListDTO getProducts(Integer page) {

        // 1. PageRequest 생성
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 2. Repository 호출
        Page<Product> productPage = productRepository.findAll(pageRequest);

        // 3. 변환해서 반환
        return ProductConverter.toProductListDTO(productPage);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO.ProductInfoDTO getProductInfo(Long productId) {

        // 1. 상품 찾기
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new productException(ErrorStatus.PRODUCT_NOT_FOUND));

        // 2. 변환 후 반환
        return ProductConverter.toProductInfoDTO(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO.AIChatDTO productAI(Long productId, ProductRequestDTO.AIChatRequestDTO request) {

        // 1. 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new productException(ErrorStatus.PRODUCT_NOT_FOUND));

        // 2. 프롬프트 생성
        String promptText = String.format(
                "상품명: %s, 가격: %d원, 상세설명: %s. \n고객 질문: %s \n이 상품에 대해 상담원처럼 친절하게 답변해줘.",
                product.getName(), product.getPrice(), product.getDescription(), request.getQuestion()
        );

        // 3. RestClient로 Gemini API 직접 호출
        RestClient restClient = RestClient.create();

        // Gemini API Request Body 생성
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", promptText)
                        ))
                )
        );

        String aiAnswer = "죄송합니다. AI 응답을 불러오지 못했습니다.";

        try {
            // 이렇게 해야 ':generateContent'의 콜론(:)이 그대로 전송됩니다.
            String urlString = apiUrl + "?key=" + apiKey;
            java.net.URI uri = java.net.URI.create(urlString);

            // POST 요청 전송
            Map response = restClient.post()
                    .uri(uri) // 수정됨: String 대신 URI 객체 전달
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(Map.class);

            // 4. 응답 파싱
            if (response != null && response.containsKey("candidates")) {
                List candidates = (List) response.get("candidates");
                if (!candidates.isEmpty()) {
                    Map firstCandidate = (Map) candidates.get(0);
                    Map content = (Map) firstCandidate.get("content");
                    List parts = (List) content.get("parts");
                    Map firstPart = (Map) parts.get(0);
                    aiAnswer = (String) firstPart.get("text");
                }
            }
        } catch (Exception e) {
            // 에러 발생 시 로그에 상세 내용 출력
            log.error("Gemini API 호출 실패. URL: {}, Error: {}", apiUrl, e.getMessage());
            e.printStackTrace(); // 디버깅을 위해 스택트레이스 출력
            aiAnswer = "AI 서버 연결 중 오류가 발생했습니다.";
        }

        // 5. 결과 반환
        return ProductConverter.toAIChatDTO(product, request.getQuestion(), aiAnswer);
    }
}
