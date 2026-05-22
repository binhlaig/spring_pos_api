//
////
//package com.binhlaig.pos.auth.dto;
//
//import com.binhlaig.pos.auth.Role;
//
//public record RegisterMultipartRequest(
//        String username,
//        String password,
//        Role role,
//        Long shopId,
//        String shopCode
//) {
//}



package com.binhlaig.pos.auth.dto;

import com.binhlaig.pos.auth.Role;

public record RegisterMultipartRequest(
        String username,
        String password,
        Role role,
        Long shopId,
        String shopCode,
        String shopName,
        String address
) {
}