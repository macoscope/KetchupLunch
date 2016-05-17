package com.macoscope.ketchuplunch.model

import java.math.BigDecimal

class MealService(val scriptClient: ScriptClient) {


    fun getUserMeals(): List<Meal> {
        val mealList = scriptClient.getDataFromApi<List<Map<String, Any>>>("meal")

        val meals = mealList.map {
            it ->
            Meal(it["name"] as String, (it["count"] as BigDecimal).toInt(), (it["totalCount"] as BigDecimal).toInt())
        }

        return meals
    }
}

