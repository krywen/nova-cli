package com.nova.model.trades.testdata

import com.nova.model.tenant.Tenant
import com.nova.model.trades.Ticker
import com.nova.model.trades.Trade
import java.time.Instant
import java.util.Calendar
import java.util.Date
import java.util.concurrent.ThreadLocalRandom


class TestData(
    val trades: List<Trade>,
    val tenants: List<Tenant>,
) {
    companion object {
        fun builder() = TestDataBuilder()
    }
}

fun randomInstant(): Instant {
    val cal = Calendar.getInstance()
    cal[Calendar.YEAR] = 2020
    cal[Calendar.MONTH] = Calendar.JANUARY
    cal[Calendar.DAY_OF_MONTH] = 1
    val startDate = cal.time
    return between(startDate.toInstant(), Instant.now())
}

fun between(startInclusive: Instant, endExclusive: Instant): Instant {
    val startSeconds = startInclusive.epochSecond
    val endSeconds = endExclusive.epochSecond
    val random = ThreadLocalRandom
        .current()
        .nextLong(startSeconds, endSeconds)
    return Instant.ofEpochSecond(random)
}