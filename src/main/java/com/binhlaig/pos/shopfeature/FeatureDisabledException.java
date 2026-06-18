package com.binhlaig.pos.shopfeature;

public class FeatureDisabledException extends RuntimeException {
    public FeatureDisabledException() {
        super("This feature is disabled for your shop plan.");
    }
}
