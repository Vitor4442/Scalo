package com.vtr.scalo.shared.tenant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

//todo ADD JWT security for validity header
@Component
public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String tenantId = request.getHeader("X-Tenant-ID");

        if (tenantId == null || tenantId.isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Missing tenant identifier"
            );
            return;
        }

        try {
            UUID tenantUuid = UUID.fromString(tenantId);

            TenantContext.setCurrentTenant(tenantUuid);

            filterChain.doFilter(request, response);

        } catch (IllegalArgumentException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid tenant identifier"
            );

        } finally {
            TenantContext.clear();
        }
    }
}
