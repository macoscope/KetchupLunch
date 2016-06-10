package com.macoscope.ketchuplunch.model.lunch

import com.macoscope.ketchuplunch.model.ScriptClient
import java.math.BigDecimal

class WeeksService(val scriptClient: ScriptClient) {


    fun getWeeks(): List<Week> {

        val parameters: List<Any> = emptyList()
        val weeksRawList = scriptClient.getDataFromApi<List<Map<String, Any>>>("weeks", parameters)

        val weeks = weeksRawList
                .filter { (it["name"] as String).isNotEmpty() }
                .map {
                    it ->
                    Week((it["index"] as BigDecimal).toLong(), it["name"] as String)
                }

        return weeks
    }
}

