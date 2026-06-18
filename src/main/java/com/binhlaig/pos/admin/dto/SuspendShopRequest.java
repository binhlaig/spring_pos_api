package com.binhlaig.pos.admin.dto;

public record SuspendShopRequest(
        String reason,
        String suspendedReason
) {
}
