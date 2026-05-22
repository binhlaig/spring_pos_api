package com.binhlaig.pos.modules.product;

import com.binhlaig.pos.modules.product.dto.ProductResponse;
import com.binhlaig.pos.storage.FileStorageService;
import com.binhlaig.pos.user.User;
import com.binhlaig.pos.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;
    private final FileStorageService storage;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    // ─────────────────────────────────────────────────────────────
    // OLD list - keep for compatibility
    // ─────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductResponse> list(String q) {
        if (q != null && !q.trim().isEmpty()) {
            return repo.findByProductNameContainingIgnoreCase(q.trim())
                    .stream()
                    .map(ProductResponse::from)
                    .toList();
        }

        return repo.findAll()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    // ─────────────────────────────────────────────────────────────
    // NEW listMine - current user / shop products only
    // ─────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductResponse> listMine(
            String q,
            Long createdByUserId,
            Long shopId,
            String shopCode,
            String authorization
    ) {
        User currentUser = getCurrentUserOrNull();

        Long finalUserId = createdByUserId;
        Long finalShopId = shopId;
        String finalShopCode = cleanNullable(shopCode);

        /*
         * Controller/Frontend က userId မပို့နိုင်ရင်
         * SecurityContext ထဲက username နဲ့ users table ကိုရှာပြီး
         * real users.id / shop_id / shop_code ကိုသုံးမယ်။
         */
        if (currentUser != null) {
            if (finalUserId == null) {
                finalUserId = currentUser.getId();
            }

            if (finalShopId == null) {
                finalShopId = currentUser.getShopId();
            }

            if (finalShopCode == null) {
                finalShopCode = currentUser.getShopCode();
            }
        }

        List<Product> products;

        boolean hasSearch = q != null && !q.trim().isEmpty();
        String keyword = hasSearch ? q.trim() : null;

        if (finalUserId != null) {
            products = hasSearch
                    ? repo.findByCreatedByUserIdAndProductNameContainingIgnoreCase(finalUserId, keyword)
                    : repo.findByCreatedByUserId(finalUserId);

        } else if (finalShopId != null) {
            products = hasSearch
                    ? repo.findByShopIdAndProductNameContainingIgnoreCase(finalShopId, keyword)
                    : repo.findByShopId(finalShopId);

        } else if (finalShopCode != null && !finalShopCode.isBlank()) {
            products = hasSearch
                    ? repo.findByShopCodeAndProductNameContainingIgnoreCase(finalShopCode, keyword)
                    : repo.findByShopCode(finalShopCode);

        } else {
            /*
             * Security အတွက် user/shop မသိရင် empty list ပြန်ပေးတာက ပိုကောင်းပါတယ်။
             * အရင်လို repo.findAll() ပြန်ပေးရင် user အကုန် product မြင်နိုင်ပါတယ်။
             */
            products = List.of();
        }

        return products.stream()
                .map(ProductResponse::from)
                .toList();
    }

    // ─────────────────────────────────────────────────────────────
    // CREATE with owner info
    // ─────────────────────────────────────────────────────────────
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
            MultipartFile image,

            Long createdByUserId,
            String createdByUsername,
            String createdByName,
            String createdByRole,
            Long shopId,
            String shopCode,
            String createdBy,
            String authorization
    ) throws Exception {

        String finalSku = cleanRequired(sku, "SKU");
        String finalProductName = cleanRequired(productName, "Product name");

        if (repo.existsBySku(finalSku)) {
            throw new IllegalArgumentException("SKU already exists");
        }

        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            imagePath = storage.saveProductImage(image);
        }

        /*
         * အရေးကြီး:
         * frontend ကပို့တဲ့ createdByUserId ကို မယုံဘဲ
         * backend login user ကနေ real numeric users.id ကိုယူမယ်။
         */
        User currentUser = getCurrentUserOrNull();

        Long finalCreatedByUserId = createdByUserId;
        String finalCreatedByUsername = cleanNullable(createdByUsername);
        String finalCreatedByName = cleanNullable(createdByName);
        String finalCreatedByRole = cleanNullable(createdByRole);
        Long finalShopId = shopId;
        String finalShopCode = cleanNullable(shopCode);

        if (currentUser != null) {
            finalCreatedByUserId = currentUser.getId();
            finalCreatedByUsername = currentUser.getUsername();
            finalCreatedByName = currentUser.getUsername();
            finalShopId = currentUser.getShopId();
            finalShopCode = currentUser.getShopCode();
        }

        /*
         * User entity ထဲမှာ getRoles() မရှိလို့ currentUser.getRoles() မသုံးပါ။
         * frontend က role ပို့လာရင်သုံးမယ်။
         * မပို့လာရင် SecurityContext authorities ထဲကယူမယ်။
         * မရရင် USER default ထားမယ်။
         */
        finalCreatedByRole = cleanNullable(finalCreatedByRole);

        if (finalCreatedByRole == null) {
            finalCreatedByRole = getCurrentRoleOrDefault();
        }

        String finalCreatedByJson = buildCreatedByJson(
                createdBy,
                finalCreatedByUserId,
                finalCreatedByUsername,
                finalCreatedByName,
                finalCreatedByRole,
                finalShopId,
                finalShopCode
        );

        Product p = Product.builder()
                .sku(finalSku)
                .name(finalProductName)
                .productName(finalProductName)
                .productPrice(productPrice == null ? BigDecimal.ZERO : productPrice)
                .productQuantityAmount(productQuantityAmount == null ? BigDecimal.ZERO : productQuantityAmount)
                .barcode(cleanNullable(barcode))
                .category(cleanNullable(category))
                .productType(productType == null ? ProductType.OTHER : productType)
                .productDiscount(productDiscount == null ? BigDecimal.ZERO : productDiscount)
                .note(cleanNullable(note))
                .imagePath(imagePath)

                // owner fields
                .createdByUserId(finalCreatedByUserId)
                .createdByUsername(finalCreatedByUsername)
                .createdByName(finalCreatedByName)
                .createdByRole(finalCreatedByRole)
                .shopId(finalShopId)
                .shopCode(finalShopCode)
                .createdBy(finalCreatedByJson)
                .build();

        Product saved = repo.save(p);
        return ProductResponse.from(saved);
    }

    // ─────────────────────────────────────────────────────────────
    // OLD create - keep for old Controller compatibility
    // ─────────────────────────────────────────────────────────────
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
        return create(
                sku,
                productName,
                productPrice,
                productQuantityAmount,
                barcode,
                category,
                productType,
                productDiscount,
                note,
                image,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    // ─────────────────────────────────────────────────────────────
    // UPDATE with optional owner info
    // ─────────────────────────────────────────────────────────────
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
            MultipartFile image,

            Long createdByUserId,
            String createdByUsername,
            Long shopId,
            String shopCode,
            String authorization
    ) throws Exception {

        Product p = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        if (sku != null && !sku.trim().isEmpty()) {
            String newSku = sku.trim();

            if (!newSku.equals(p.getSku()) && repo.existsBySku(newSku)) {
                throw new IllegalArgumentException("SKU already exists");
            }

            p.setSku(newSku);
        }

        if (productName != null && !productName.trim().isEmpty()) {
            String cleanName = productName.trim();
            p.setProductName(cleanName);
            p.setName(cleanName);
        }

        if (productPrice != null) {
            p.setProductPrice(productPrice);
        }

        if (productQuantityAmount != null) {
            p.setProductQuantityAmount(productQuantityAmount);
        }

        if (barcode != null) {
            p.setBarcode(cleanNullable(barcode));
        }

        if (category != null) {
            p.setCategory(cleanNullable(category));
        }

        if (productType != null) {
            p.setProductType(productType);
        }

        if (productDiscount != null) {
            p.setProductDiscount(productDiscount);
        }

        if (note != null) {
            p.setNote(cleanNullable(note));
        }

        /*
         * update မှာ owner info ပို့လာမှ update လုပ်မယ်။
         * မပို့ရင် မူလ owner မပျက်စေဘူး။
         */
        if (createdByUserId != null) {
            p.setCreatedByUserId(createdByUserId);
        }

        if (createdByUsername != null) {
            p.setCreatedByUsername(cleanNullable(createdByUsername));
        }

        if (shopId != null) {
            p.setShopId(shopId);
        }

        if (shopCode != null) {
            p.setShopCode(cleanNullable(shopCode));
        }

        if (image != null && !image.isEmpty()) {
            String imagePath = storage.saveProductImage(image);
            p.setImagePath(imagePath);
        }

        Product saved = repo.save(p);
        return ProductResponse.from(saved);
    }

    // ─────────────────────────────────────────────────────────────
    // OLD update - keep for old Controller compatibility
    // ─────────────────────────────────────────────────────────────
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
        return update(
                id,
                sku,
                productName,
                productPrice,
                productQuantityAmount,
                barcode,
                category,
                productType,
                productDiscount,
                note,
                image,
                null,
                null,
                null,
                null,
                null
        );
    }

    // ─────────────────────────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────────────────────────
    @Transactional
    public void delete(Long id) throws Exception {
        Product p = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        // FileStorageService မှာ delete method ရှိရင် ဖွင့်သုံးနိုင်ပါတယ်။
        // storage.delete(p.getImagePath());

        repo.delete(p);
    }

    // ─────────────────────────────────────────────────────────────
    // GET BY ID
    // ─────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        return ProductResponse.from(product);
    }

    // ─────────────────────────────────────────────────────────────
    // CURRENT USER
    // ─────────────────────────────────────────────────────────────
    private User getCurrentUserOrNull() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getName() == null || auth.getName().isBlank()) {
            return null;
        }

        String username = auth.getName();

        return userRepository.findByUsername(username).orElse(null);
    }

    private String getCurrentRoleOrDefault() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getAuthorities() == null || auth.getAuthorities().isEmpty()) {
            return "USER";
        }

        return auth.getAuthorities()
                .stream()
                .findFirst()
                .map(authority -> authority.getAuthority())
                .orElse("USER");
    }

    // ─────────────────────────────────────────────────────────────
    // JSON creator info
    // ─────────────────────────────────────────────────────────────
    private String buildCreatedByJson(
            String fallbackCreatedBy,
            Long userId,
            String username,
            String name,
            String role,
            Long shopId,
            String shopCode
    ) {
        /*
         * frontend ကပို့တဲ့ JSON ရှိပြီး backend current user မတွေ့ရင် fallback သုံးမယ်။
         */
        if (userId == null && fallbackCreatedBy != null && !fallbackCreatedBy.trim().isEmpty()) {
            return fallbackCreatedBy.trim();
        }

        try {
            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("id", userId);
            payload.put("userId", userId);
            payload.put("username", username);
            payload.put("name", name);
            payload.put("role", role);
            payload.put("shopId", shopId);
            payload.put("shopCode", shopCode);

            return objectMapper.writeValueAsString(payload);
        } catch (Exception e) {
            return "{}";
        }
    }

    // ─────────────────────────────────────────────────────────────
    // HELPERS
    // ─────────────────────────────────────────────────────────────
    private String cleanRequired(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }

        return value.trim();
    }

    private String cleanNullable(String value) {
        if (value == null) return null;

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}