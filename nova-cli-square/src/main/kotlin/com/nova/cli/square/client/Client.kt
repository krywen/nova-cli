//package com.nova.cli.square.client
//
//import com.github.kittinunf.fuel.Fuel
//import com.github.kittinunf.fuel.core.FuelError
//import com.github.kittinunf.fuel.core.FuelManager
//import com.github.kittinunf.fuel.core.Request
//import com.github.kittinunf.result.Result
//import info.blockchain.json.JsonUtil
//import info.blockchain.markets.clients.common.Environment
//import info.blockchain.markets.clients.common.responses.AlgoResponse
//import info.blockchain.markets.clients.common.responses.BalanceResponse
//import info.blockchain.markets.clients.common.responses.BookResponse
//import info.blockchain.markets.clients.common.responses.ConfigResponse
//import info.blockchain.markets.clients.common.responses.CurrencyResponse
//import info.blockchain.markets.clients.common.responses.EnvResponse
//import info.blockchain.markets.clients.common.responses.InstrumentResponse
//import info.blockchain.markets.clients.common.responses.JsonWebTokenResponse
//import info.blockchain.markets.clients.common.responses.PortfolioResponse
//import info.blockchain.markets.clients.common.responses.TradeResponse
//import info.blockchain.markets.clients.common.responses.UserResponse
//import info.blockchain.markets.clients.common.responses.VenueResponse
//import org.beryx.textio.TextTerminal
//import java.net.URI
//import java.time.Instant
//
//class Client(
//    val environment: Environment,
//    private val email: String,
//    private val password: String,
//    private val otp: String,
//    private val terminal: TextTerminal<*>
//) {
//    var token: String? = null
//    var wsClient: WebsocketClient? = null
//
//    init {
//        FuelManager.instance.basePath = environment.url
//    }
//
//    private fun getAuthenticatedHeaders(): Map<String, String> {
//        if (token == null) {
//            throw Exception("Trying to make an authenticated call without token")
//        }
//
//        return mapOf(
//            "Authorization" to "Bearer $token"
//        )
//    }
//
//    fun connect(): String? {
//        val body = mapOf(
//            "email" to email,
//            "password" to password,
//            "otp" to otp
//        )
//
//        val (request, response, result) = Fuel.post("/auth")
//            .body(JsonUtil.toJson(body)).responseString()
//
//        val (data, error) = result
//
//        return if (error != null || data == null) {
//            System.err.println("Bad status code for ${request.url}: ${response.statusCode}, ${String(response.data)}, $error")
//            null
//        } else {
//            token = JsonUtil.fromJson<JsonWebTokenResponse>(data).token
//            wsClient = WebsocketClient(URI.create(environment.wsUrl), token!!)
//            wsClient!!.connectBlocking()
//            token
//        }
//    }
//
//    fun listBalances(currency: String): List<BalanceResponse> {
//        return doListRequest(Fuel.get("/balances", parameters = listOf("currency" to currency)))
//    }
//
//    fun listInstruments(): List<InstrumentResponse> {
//        return doListRequest(Fuel.get("/instruments"))
//    }
//
//    fun listInstruments(status: String): List<InstrumentResponse> {
//        return doListRequest(Fuel.get("/instruments?status=$status"))
//    }
//
//    fun listCurrencies(): List<CurrencyResponse> {
//        return doListRequest(Fuel.get("/currencies"))
//    }
//
//    fun listVenues(): List<VenueResponse> {
//        return doListRequest(Fuel.get("/venues"))
//    }
//
//    fun updateFee(venue: String, newFee: Double, marginUnit: String): Boolean {
//        return doUpdate(
//            Fuel.post("venues/updatefee")
//                .body(JsonUtil.toJson(mapOf(
//                    "venue" to venue,
//                    "fee" to newFee,
//                    "unit" to marginUnit
//                )))
//        )
//    }
//
//    fun listRoutes(): List<AlgoResponse> {
//        return doListRequest(Fuel.get("/routes"))
//    }
//
//    fun listPortfolios(): List<PortfolioResponse> {
//        return doListRequest(Fuel.get("/portfolio"))
//    }
//
//    fun createPortfolio(book: String, portfolio: String, underlying: String, script: String, type: String): Boolean {
//        return doUpdate(
//            Fuel.post("/portfolio")
//                .body(JsonUtil.toJson(mapOf(
//                    "book" to book,
//                    "portfolio" to portfolio,
//                    "underlying" to underlying,
//                    "script" to script,
//                    "type" to type
//                )))
//        )
//    }
//
//    fun updatePortfolio(portfolio: String, script: String, type: String, book: String?): Boolean {
//        val values = mutableMapOf(
//            "portfolio" to portfolio,
//            "script" to script,
//            "type" to type
//        )
//        book?. let {
//            values["book"] = book
//        }
//        return doUpdate(
//            Fuel.put("/portfolio/$portfolio")
//                .body(JsonUtil.toJson(values))
//        )
//    }
//
//    fun listUsers(): List<UserResponse> {
//        return doListRequest(Fuel.get("/users"))
//    }
//
//    fun listActors(): List<String> {
//        return doListRequest(Fuel.get("/control/actors"))
//    }
//
//    fun restartActor(actor: String): Boolean {
//        return doUpdate(
//            Fuel.post("/control/actor/restart")
//                .body(JsonUtil.toJson(mapOf(
//                    "actor" to actor
//                ))))
//    }
//
//    fun listBooks(): List<BookResponse> {
//        return doListRequest(Fuel.get("/books"))
//    }
//
//    fun createToken(email: String, expirationDays: Long?): JsonWebTokenResponse? {
//        return doRequest(
//            Fuel.post("/auth/tokens")
//                .body(JsonUtil.toJson(mapOf(
//                    "email" to email,
//                    "expirationDays" to expirationDays)))
//        )
//    }
//
//    fun deleteToken(tokenId: String): Boolean {
//        return doUpdate(
//            Fuel.delete("/auth/tokens")
//                .body(JsonUtil.toJson(mapOf("tokenId" to tokenId)))
//        )
//    }
//
//    fun createBook(name: String, parent: String, postfix: String): Boolean {
//        return doUpdate(
//            Fuel.post("/books")
//                .body(JsonUtil.toJson(mapOf(
//                    "name" to name,
//                    "parent" to parent,
//                    "postfix" to postfix
//                )))
//        )
//    }
//
//    fun toggleBook(name: String, active: Boolean): Boolean {
//        return doUpdate(
//            Fuel.put("/books/$name")
//                .body(JsonUtil.toJson(mapOf(
//                    "active" to active
//                )))
//        )
//    }
//
//    fun getEnv(): EnvResponse? {
//        return doRequest(Fuel.get("/env"))
//    }
//
//    fun getConfig(): ConfigResponse? {
//        return doRequest(Fuel.get("/config"))
//    }
//
//    fun updateConfig(key: String, value: String): Boolean {
//        return doUpdate(
//            Fuel.post("/config")
//                .body(JsonUtil.toJson(mapOf(
//                    key to value
//                )))
//        )
//    }
//
//    fun getInstrument(instrumentKey: String): InstrumentResponse? {
//        return doRequest(Fuel.get("/instruments/$instrumentKey"))
//    }
//
//    fun createManualInstrument(pair: String): Boolean {
//        return doUpdate(
//            Fuel.post("/instruments/manual")
//                .body(JsonUtil.toJson(mapOf(
//                    "pair" to pair
//                )))
//        )
//    }
//
//    fun createImpliedInstrument(impliedPair: String, targetPair: String, origPair: String): Boolean {
//        return doUpdate(
//            Fuel.post("/instruments/implied")
//                .body(JsonUtil.toJson(mapOf(
//                    "impliedPair" to impliedPair,
//                    "targetPair" to targetPair,
//                    "originatingPair" to origPair
//                )))
//        )
//    }
//
//    fun changeInstrumentsStatus(selector: String, status: String, changeFullStatus: Boolean, force: Boolean): Boolean {
//        return doUpdate(
//            Fuel.post("/instruments/$selector")
//                .body(JsonUtil.toJson(mapOf(
//                    "status" to status,
//                    "updateFullStatus" to if (changeFullStatus) { "true" } else { "false" },
//                    "force" to if (force) { "true" } else { "false" }
//                )))
//        )
//    }
//
//    fun updateBalance(venue: String, currency: String, newBalance: Double): Boolean {
//        return doUpdate(
//            Fuel.post("/balances")
//                .body(JsonUtil.toJson(mapOf(
//                    "venue" to venue,
//                    "currency" to currency,
//                    "balance" to newBalance
//                )))
//        )
//    }
//
//    fun resetUser(email: String): Boolean {
//        return doUpdate(
//            Fuel.post("/users/reset")
//                .body(JsonUtil.toJson(mapOf("email" to email)))
//        )
//    }
//
//    fun reallocPortfolio(refId: String, portfolio: String): Boolean {
//        return doUpdate(
//            Fuel.post("/portfolio/reallocate")
//                .body(JsonUtil.toJson(mapOf(
//                    "refId" to refId,
//                    "newPortfolio" to portfolio
//                )))
//        )
//    }
//
//    fun addYubikey(existingOtp: String, newOtp: String): Boolean {
//        return doUpdate(Fuel.post("/users/yubikey").body(
//            JsonUtil.toJson(mapOf(
//                "otp" to existingOtp,
//                "newOtp" to newOtp
//            ))
//        ))
//    }
//
//    fun cancelOrder(orderID: String): Boolean {
//        return doUpdate(Fuel.put("/orders/cancel/$orderID"))
//    }
//
//    fun cancelAllOrders(venue: String?, instrumentKey: String?): Boolean {
//        val body = mutableMapOf<String, String>()
//        venue ?.let { body["venue"] = venue }
//        instrumentKey ?.let { body["instrumentKey"] = instrumentKey }
//        return doUpdate(Fuel.put("/orders/allcancel").body(
//            JsonUtil.toJson(body)
//        ))
//    }
//
//    fun purgeOrder(orderID: String): Boolean {
//        return doUpdate(Fuel.put("/orders/purge/$orderID"))
//    }
//
//    fun listHistoricalTrades(fromDate: Instant): List<TradeResponse> {
//        return doListRequest(Fuel.get("/orders/historicalTrades?start=$fromDate"))
//    }
//
//    fun disconnect() {
//        if (wsClient != null) {
//            wsClient!!.close()
//        }
//    }
//
//    private fun executeRequest(request: Request): Result<String, FuelError> {
//        val headers = getAuthenticatedHeaders()
//        request.header(headers)
//
//        val (_, response, result) = request.responseString()
//        if (response.statusCode != 200 && response.statusCode != 204) {
//            System.err.println("Bad status code for ${request.url}: ${response.statusCode}, ${String(response.data)}")
//        }
//
//        return result
//    }
//
//    private inline fun <reified T> doRequest(request: Request): T? {
//        val (data, error) = executeRequest(request)
//
//        return if (error != null || data == null) {
//            null
//        } else {
//            JsonUtil.fromJson<T>(data)
//        }
//    }
//
//    private fun doUpdate(request: Request): Boolean {
//        val headers = getAuthenticatedHeaders()
//        request.header(headers)
//
//        val (_, response, _) = request.responseString()
//        return if (response.statusCode != 204) {
//            terminal.println("ERROR for ${request.url}, ${response.statusCode}: ${String(response.data)}")
//            false
//        } else {
//            true
//        }
//    }
//
//    private inline fun <reified T> doListRequest(request: Request): List<T> {
//        val (data, error) = executeRequest(request)
//
//        return if (error != null || data == null) {
//            emptyList()
//        } else {
//            JsonUtil.fromJsonToList(data)
//        }
//    }
//}