//
//package com.binhlaig.pos.modules.product;
//
//import com.binhlaig.pos.modules.product.dto.ProductResponse;
//import jakarta.validation.constraints.NotBlank;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/products")
//public class ProductController {
//
//    private final ProductService service;
//
//    // ✅ list all
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<ProductResponse> list(@RequestParam(value = "q", required = false) String q) {
//        return service.list(q);
//    }
//
//    // ✅ create
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ProductResponse create(
//            @RequestParam("sku") @NotBlank String sku,
//            @RequestParam("product_name") @NotBlank String productName,
//            @RequestParam("product_price") BigDecimal productPrice,
//
//            @RequestParam(value = "product_quantity_amount", required = false) BigDecimal qty,
//            @RequestParam(value = "barcode", required = false) String barcode,
//            @RequestParam(value = "category", required = false) String category,
//            @RequestParam(value = "product_type", required = false) ProductType productType,
//            @RequestParam(value = "note", required = false) String note,
//            @RequestParam(value = "product_discount", required = false) BigDecimal discount,
//
//            @RequestPart(value = "image", required = false) MultipartFile image
//    ) throws Exception {
//        if (qty == null) qty = BigDecimal.ZERO;
//        if (discount == null) discount = BigDecimal.ZERO;
//
//        return service.create(
//                sku.trim(), productName.trim(), productPrice, qty,
//                barcode, category, productType, discount, note, image
//        );
//    }
//
//    // ✅ update (multipart/form-data) : id နဲ့ update
//    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ProductResponse update(
//            @PathVariable Long id,
//
//            @RequestParam(value = "sku", required = false) String sku,
//            @RequestParam(value = "product_name", required = false) String productName,
//            @RequestParam(value = "product_price", required = false) BigDecimal productPrice,
//
//            @RequestParam(value = "product_quantity_amount", required = false) BigDecimal qty,
//            @RequestParam(value = "barcode", required = false) String barcode,
//            @RequestParam(value = "category", required = false) String category,
//            @RequestParam(value = "product_type", required = false) ProductType productType,
//            @RequestParam(value = "note", required = false) String note,
//            @RequestParam(value = "product_discount", required = false) BigDecimal discount,
//
//            @RequestPart(value = "image", required = false) MultipartFile image
//    ) throws Exception {
//
//        return service.update(
//                id,
//                sku, productName, productPrice, qty,
//                barcode, category, productType, discount, note,
//                image
//        );
//    }
//
//    // ✅ delete
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) throws Exception {
//        service.delete(id);
//    }
//
//}



package com.binhlaig.pos.modules.product;

import com.binhlaig.pos.modules.product.dto.ProductResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductResponse> list(@RequestParam(value = "q", required = false) String q) {
        return service.list(q);
    }

    // ✅ NEW: get by id (edit/view page needs this)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponse create(
            @RequestParam("sku") @NotBlank String sku,
            @RequestParam("product_name") @NotBlank String productName,
            @RequestParam("product_price") BigDecimal productPrice,
            @RequestParam(value = "product_quantity_amount", required = false) BigDecimal qty,
            @RequestParam(value = "barcode", required = false) String barcode,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "product_type", required = false) ProductType productType,
            @RequestParam(value = "note", required = false) String note,
            @RequestParam(value = "product_discount", required = false) BigDecimal discount,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {
        if (qty == null) qty = BigDecimal.ZERO;
        if (discount == null) discount = BigDecimal.ZERO;

        return service.create(
                sku.trim(), productName.trim(), productPrice, qty,
                barcode, category, productType, discount, note, image
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponse update(
            @PathVariable Long id,
            @RequestParam(value = "sku", required = false) String sku,
            @RequestParam(value = "product_name", required = false) String productName,
            @RequestParam(value = "product_price", required = false) BigDecimal productPrice,
            @RequestParam(value = "product_quantity_amount", required = false) BigDecimal qty,
            @RequestParam(value = "barcode", required = false) String barcode,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "product_type", required = false) ProductType productType,
            @RequestParam(value = "note", required = false) String note,
            @RequestParam(value = "product_discount", required = false) BigDecimal discount,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {

        return service.update(
                id,
                sku, productName, productPrice, qty,
                barcode, category, productType, discount, note,
                image
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        service.delete(id);
    }
}
