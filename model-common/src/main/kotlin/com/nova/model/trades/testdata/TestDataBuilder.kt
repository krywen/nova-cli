package com.nova.model.trades.testdata

import com.nova.model.tenant.Tenant
import com.nova.model.trades.Ticker
import com.nova.model.trades.Trade
import java.time.Instant
import java.util.Calendar
import java.util.Date
import java.util.concurrent.ThreadLocalRandom


class TestDataBuilder {
    fun build(): TestData {
        val tenants = listOf(
            Tenant("BARclay_OTC", "Barclay"),
            Tenant("easyOTC_desk", "EASY"),
            Tenant("Goldman_SDC_OTC", "Goldman"),
        )

        val trades = mutableListOf<Trade>()
        for (i in 1..40) {
            val buyer = tenants.random()
            val possibleSellers = tenants.toMutableSet()
            possibleSellers.remove(buyer)
            trades.add(Trade(
                ticker = Ticker.values().random(),
                executedAt = randomInstant(),
                buyer = buyer,
                seller = possibleSellers.random()
            ))
        }

        return TestData(
            tenants = tenants,
            trades = trades
        )
    }
}
