//
//package com.binhlaig.pos.modules.product;
//
//import com.binhlaig.pos.modules.product.dto.ProductResponse;
//import com.binhlaig.pos.storage.FileStorageService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ProductService {
//
//    private final ProductRepository repo;
//    private final FileStorageService storage;
//
//    // ✅ NEW: GET all products (မူလ code မဖျက်)
//    @Transactional(readOnly = true)
//    public List<ProductResponse> list(String q) {
//        return repo.findAll()
//                .stream()
//                .map(ProductResponse::from)
//                .toList();
//    }
//
//    // 🔵 မူလ create method 그대로
//    @Transactional
//    public ProductResponse create(
//            String sku,
//            String productName,
//            BigDecimal productPrice,
//            BigDecimal productQuantityAmount,
//            String barcode,
//            String category,
//            ProductType productType,
//            BigDecimal productDiscount,
//            String note,
//            MultipartFile image
//    ) throws Exception {
//
//        if (repo.existsBySku(sku)) {
//            throw new IllegalArgumentException("SKU already exists");
//        }
//
//        String imagePath = storage.saveProductImage(image);
//
//        Product p = Product.builder()
//                .sku(sku)
//                .productName(productName)
//                .productPrice(productPrice)
//                .productQuantityAmount(productQuantityAmount == null ? BigDecimal.ZERO : productQuantityAmount)
//                .barcode(barcode)
//                .category(category)
//                .productType(productType)
//                .productDiscount(productDiscount == null ? BigDecimal.ZERO : productDiscount)
//                .note(note)
//                .imagePath(imagePath)
//                .build();
//
//        repo.save(p);
//        return ProductResponse.from(p);
//    }
//}



package com.binhlaig.pos.modules.product;

import com.binhlaig.pos.modules.product.dto.ProductResponse;
import com.binhlaig.pos.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;
    private final FileStorageService storage;

    // ✅ list (မူလ code မပျက်)
    @Transactional(readOnly = true)
    public List<ProductResponse> list(String q) {
        // search မလိုသေးရင် findAll() နဲ့ပဲ OK
        return repo.findAll().stream().map(ProductResponse::from).toList();
    }

    // ✅ create (မူလ code 그대로)
    @Transactional
    public ProductResponse create(
            String sku,
            String productName,
            BigDecimal productPrice,
            BigDecimal productQuantityAmount,
            String barcode,
            String category,
            ProductType productType,
            BigDecimal productDiscount,
            String note,
            MultipartFile image
    ) throws Exception {

        if (repo.existsBySku(sku)) {
            throw new IllegalArgumentException("SKU already exists");
        }

        String imagePath = storage.saveProductImage(image);

        Product p = Product.builder()
                .sku(sku)
                .productName(productName)
                .productPrice(productPrice)
                .productQuantityAmount(productQuantityAmount == null ? BigDecimal.ZERO : productQuantityAmount)
                .barcode(barcode)
                .category(category)
                .productType(productType)
                .productDiscount(productDiscount == null ? BigDecimal.ZERO : productDiscount)
                .note(note)
                .imagePath(imagePath)
                .build();

        repo.save(p);
        return ProductResponse.from(p);
    }

    // ✅ update: null ဖြစ်တဲ့ field ကိုမပြောင်းဘူး (partial update)
    @Transactional
    public ProductResponse update(
            Long id,
            String sku,
            String productName,
            BigDecimal productPrice,
            BigDecimal productQuantityAmount,
            String barcode,
            String category,
            ProductType productType,
            BigDecimal productDiscount,
            String note,
            MultipartFile image
    ) throws Exception {

        Product p = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        // sku update (unique check)
        if (sku != null && !sku.trim().isEmpty()) {
            String newSku = sku.trim();
            if (!newSku.equals(p.getSku()) && repo.existsBySku(newSku)) {
                throw new IllegalArgumentException("SKU already exists");
            }
            p.setSku(newSku);
        }

        if (productName != null && !productName.trim().isEmpty()) {
            p.setProductName(productName.trim());
        }

        if (productPrice != null) {
            p.setProductPrice(productPrice);
        }

        if (productQuantityAmount != null) {
            p.setProductQuantityAmount(productQuantityAmount);
        }

        // barcode/category/note allow set null? (မင်းလိုချင်တာနဲ့ကိုက်အောင်)
        if (barcode != null) p.setBarcode(barcode.trim().isEmpty() ? null : barcode.trim());
        if (category != null) p.setCategory(category.trim().isEmpty() ? null : category.trim());
        if (productType != null) p.setProductType(productType);
        if (productDiscount != null) p.setProductDiscount(productDiscount);
        if (note != null) p.setNote(note.trim().isEmpty() ? null : note.trim());

        // image update (အသစ်တင်မှသာ save)
        if (image != null && !image.isEmpty()) {
            String imagePath = storage.saveProductImage(image);
            p.setImagePath(imagePath);
        }

        repo.save(p);
        return ProductResponse.from(p);
    }

    // ✅ delete
    @Transactional
    public void delete(Long id) throws Exception {
        Product p = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        // image ဖိုင်လည်း ဖျက်ချင်ရင် FileStorageService မှာ delete method ထည့်နိုင်
        // storage.delete(p.getImagePath());

        repo.delete(p);
    }

    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
        return ProductResponse.from(product);
    }


}
