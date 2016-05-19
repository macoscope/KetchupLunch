package com.macoscope.ketchuplunch.model.lunch

import com.macoscope.ketchuplunch.model.ScriptClient
import java.math.BigDecimal

class MealService(val scriptClient: ScriptClient, val userName: String) {


    fun getUserMeals(dayIndex: Int): List<Meal> {

        val parameters: List<Any> = listOf(userName.split("@").first(), dayIndex)
        val mealList = scriptClient.getDataFromApi<List<Map<String, Any>>>("meal", parameters)

        val meals = mealList
//                .filter {
//                    val name = it["name"] as String
//                    name.isNotEmpty()
//                }
                .map {
                    it ->
                    Meal(it["name"] as String, (it["count"] as BigDecimal).toInt(), (it["totalCount"] as BigDecimal).toInt())
                }

        return meals
    }
}

