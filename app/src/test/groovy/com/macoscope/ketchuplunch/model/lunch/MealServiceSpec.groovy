package com.macoscope.ketchuplunch.model.lunch

import com.macoscope.ketchuplunch.model.ScriptClient
import spock.lang.Specification

class MealServiceSpec extends Specification {

    public static final int ANY = 0
    ScriptClient scriptClientStub = Stub(ScriptClient);
    MealService objectUnderTest = new MealService(scriptClientStub, "darek@macoscope.net");

    def "maps library response to meal object"() {
        given:
            Map<String, Object> mealMap = ["name": "Zurek", "count": 0 as BigDecimal, "totalCount": 1 as BigDecimal,
                                           "type": "Zupa"]
            scriptClientStub.getDataFromApi("meal", _ as List<Object>) >> [mealMap]
        when:
            List<Meal> mealList = objectUnderTest.getUserMeals(ANY, ANY)

        then:
            def mealResult = mealList.first()
            mealResult.name == "Zurek"
            mealResult.count == 0
            mealResult.totalCount == 1
            mealResult.type == "Zupa"
    }


    def "skips meals with empty name"() {
        given:
            Map<String, Object> mealMap = ["name": "", "count": 0 as BigDecimal, "totalCount": 1 as BigDecimal,
                                           "type": "Zupa"]
            scriptClientStub.getDataFromApi("meal", _ as List<Object>) >> [mealMap]
        when:
            List<Meal> mealList = objectUnderTest.getUserMeals(ANY, ANY)

        then:
            mealList.isEmpty()
    }

    def "extracts username from email"() {
        given:
            String email = "darek@macoscope.net"
        when:
            String userName = objectUnderTest.extractUserName(email)
        then:
            userName == "darek"
    }

}
