package com.macoscope.ketchuplunch.model.lunch

import com.macoscope.ketchuplunch.model.ScriptClient
import java.math.BigDecimal

class MealService(val scriptClient: ScriptClient, val email: String) {


    fun getUserMeals(weekIndex: Long, dayIndex: Int): List<Meal> {

        val parameters: List<Any> = listOf(extractUserName(email), dayIndex, weekIndex)
        val mealRawList = scriptClient.getDataFromApi<List<Map<String, Any>>>("meal", parameters)

        val meals = mealRawList
                .filter { (it["name"] as String).isNotEmpty() }
                .map {
                    it ->
                    Meal(it["name"] as String, (it["count"] as BigDecimal).toInt(),
                            (it["totalCount"] as BigDecimal).toInt(), it["type"] as String)
                }

        return meals
    }

    fun extractUserName(email: String) = email.split("@").first()
}

