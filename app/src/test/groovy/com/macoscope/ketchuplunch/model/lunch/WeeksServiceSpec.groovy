package com.macoscope.ketchuplunch.model.lunch

import com.macoscope.ketchuplunch.model.ScriptClient
import spock.lang.Specification

class WeeksServiceSpec extends Specification {

    ScriptClient scriptClientStub = Stub(ScriptClient);
    WeeksService objectUnderTest = new WeeksService(scriptClientStub)

    def "maps library response to week list"() {
        given:
            Map<String, Object> dayMap = ["name": "MON", "index": 0 as BigDecimal]
            scriptClientStub.getDataFromApi("weeks", _ as List<Object>) >> [dayMap]
        when:
            List<Week> weekList = objectUnderTest.getWeeks()

        then:
            def dayResult = weekList.first()
            dayResult.name == "MON"
            dayResult.index == 0
    }
}
