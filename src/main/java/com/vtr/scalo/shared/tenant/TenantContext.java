package com.vtr.scalo.shared.tenant;


import java.util.UUID;

public final class TenantContext {

    private static final ThreadLocal<UUID> CURRENT_TENANT = new ThreadLocal<>();

    private TenantContext() {
    }

    public static void setCurrentTenant(UUID tenantId) {
        CURRENT_TENANT.set(tenantId);
    }

    public static UUID getCurrentTenant() {
        UUID tenantId = CURRENT_TENANT.get();

        if (tenantId == null) {
            throw new IllegalStateException("No tenant configured for current request");
        }

        return tenantId;
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}