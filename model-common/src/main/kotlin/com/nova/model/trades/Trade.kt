package com.nova.model.trades

import com.nova.model.tenant.Tenant
import java.time.Instant

data class Trade(
    val ticker: Ticker,
    val executedAt: Instant,
    val buyer: Tenant,
    val seller: Tenant,
)