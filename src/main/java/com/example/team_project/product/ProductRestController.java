package com.example.team_project.product;

import com.example.team_project._core.utils.ApiUtils;
import com.example.team_project.product.ProductRequest.ProductUpdateReqDTO;
import com.example.team_project.user.User;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductService productService;
    private final HttpSession session;

    // 상품 목록보기
    @GetMapping("/products")
    public ResponseEntity<?> productList() {
        List<ProductResponse.ProductListRespDTO> responseDTO = productService.findAll();
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    // 상품상세보기
    @GetMapping("/products/{id}")
    public ResponseEntity<?> productDetail(@PathVariable Integer id) {
        ProductResponse.ProductDetailRespDTO responseDTO = productService.FindById(id);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    // 상품 등록
    @PostMapping("/products/write")
    public ResponseEntity<?> writeProduct(@RequestBody ProductRequest.ProductWriteReqDTO productWriteReqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        ProductResponse.ProductWriteRespDTO responseDTO = productService.saveProductWithProductPics(productWriteReqDTO,
                sessionUser);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    // 상품 수정
    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id,
            @RequestBody ProductUpdateReqDTO productUpdateReqDTO) {
        ProductResponse.ProductUpdateRespDTO responseDTO = productService.updateProductWithProductPics(id,
                productUpdateReqDTO);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));

    }

    // 상품 삭제
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> DeleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().body(ApiUtils.success("상품 삭제 완료"));
    }

    // 상품 검색
    @GetMapping("/products/search")
    public ResponseEntity<?> searchProducts(@RequestParam("keyword") String keyword) {
        List<ProductResponse.ProductSearchRespDTO> responseDTO = productService.searchProductsByKeyword(keyword);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    // 상품 북마크
    @PostMapping("/products/bookmark")
    public ResponseEntity<?> bookmarkProducts(@RequestBody ProductRequest.ProductBookMarkReqDTO productBookMarkReqDTO) {
        System.out.println("dto 값 잘 들어오지? : " + productBookMarkReqDTO);
        ProductResponse.ProductBookMarkRespDTO responseDTO = productService.bookmarkProducts(productBookMarkReqDTO);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    // 북마크삭제
    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<?> ProductDelete(@PathVariable Integer id) {
        productService.DeleteBookmarkProducts(id);
        return ResponseEntity.ok().body(ApiUtils.success("북마크삭제 완료"));
    }

}
