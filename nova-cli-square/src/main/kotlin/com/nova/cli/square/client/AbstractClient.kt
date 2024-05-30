package com.nova.cli.square.client

import com.nova.model.tenant.Tenant
import com.nova.model.trades.Trade

abstract class AbstractClient {
    abstract fun connect(): String?
    abstract fun listTrades(): List<Trade>
    abstract fun listTenants(): List<Tenant>
    abstract fun listTrades(tenantName: String): List<Trade>
    abstract fun listTradesBySeller(tenantName: String): List<Trade>
    abstract fun disconnect(): Unit
}
