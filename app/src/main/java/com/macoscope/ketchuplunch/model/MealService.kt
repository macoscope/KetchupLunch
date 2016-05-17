package com.macoscope.ketchuplunch.model

import java.math.BigDecimal

class MealService(val scriptClient: ScriptClient) {


    fun getUserMeals(): List<Meal> {
        return scriptClient.getDataFromApi<List<String>>("mealNames")
                .zip(scriptClient.getDataFromApi<List<BigDecimal>>("mealCount"), { name, count ->
                    Meal(name, count.toInt(), 0)
                })
                .zip(scriptClient.getDataFromApi<List<BigDecimal>>("mealTotalCount"), { meal, totalCount ->
                    meal.totalCount = totalCount.toInt()
                    meal
                })
    }
}
