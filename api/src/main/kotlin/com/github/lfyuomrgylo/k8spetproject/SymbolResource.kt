package com.github.lfyuomrgylo.k8spetproject

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findOne
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.core.query.where
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class SymbolResource(
    private val mongo: MongoTemplate
) {

    @GetMapping(path = ["/symbols/{symbolId}"], produces = [TEXT_PLAIN_VALUE])
    fun getSymbol(@PathVariable("symbolId") symbolId: String): ResponseEntity<String> {
        val symbol = loadSymbol(symbolId)
        return symbol?.symbolValue
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @PutMapping(path = ["/symbols/{symbolId}"], consumes = [TEXT_PLAIN_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun saveSymbol(@PathVariable("symbolId") symbolId: String, @RequestBody symbol: String): ResponseEntity<Any> {
        val symbolEntity = loadSymbol(symbolId)?.also { it.symbolValue = symbol }
            ?: SymbolEntity(symbolId, symbol)
        mongo.save(symbolEntity)
        return mapOf("info" to "Symbol $symbolId is successfully saved")
            .let { ResponseEntity.ok(it) }
    }

    private fun loadSymbol(symbolId: String): SymbolEntity? {
        val criteria = where(SymbolEntity::symbolId).isEqualTo(symbolId)
        return mongo.findOne(Query.query(criteria))
    }
}

data class SymbolEntity(
    @Id val symbolId: String,
    var symbolValue: String
) {
    @Version
    private var concurrencyVersion: Int = 0
}