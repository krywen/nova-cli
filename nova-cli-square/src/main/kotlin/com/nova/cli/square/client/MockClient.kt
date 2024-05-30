package com.nova.cli.square.client

import com.nova.model.tenant.Tenant
import com.nova.model.trades.Trade
import com.nova.model.trades.testdata.TestData
import org.beryx.textio.TextTerminal

class MockClient(
    val environment: Environment,
    private val email: String,
    private val password: String,
    private val otp: String,
    private val terminal: TextTerminal<*>
): AbstractClient() {
    private val data = TestData.builder().build()

    override fun connect(): String? {
        return "validtoken"
    }

    override fun listTrades(): List<Trade> {
        return data.trades.sortedBy { it.executedAt }
    }

    override fun listTenants(): List<Tenant> {
        return data.tenants
    }

    override fun listTrades(tenantName: String): List<Trade> {
        val tenant = data.tenants.firstOrNull { it.companyName.contains(tenantName, ignoreCase = true)}
        return data.trades.filter { it.buyer == tenant || it.seller == tenant }.sortedBy { it.executedAt }
    }

    override fun listTradesBySeller(tenantName: String): List<Trade> {
        val tenant = data.tenants.firstOrNull { it.companyName.contains(tenantName, ignoreCase = true)}
        return data.trades.filter { it.seller == tenant }.sortedBy { it.executedAt }
    }

    override fun disconnect() {
    }
}