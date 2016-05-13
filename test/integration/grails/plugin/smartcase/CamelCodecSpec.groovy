package grails.plugin.smartcase

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.*

@TestFor(SmartCaseService)
class CamelCodecSpec extends Specification {

    void setup() {
        def mockService = Mock(SmartCaseService)
        CamelCodec.metaClass.smartCaseService = mockService
    }

    void "test encode"() {
        given:
            String notEncoded = 'this is camel'
        when:
            String encoded = notEncoded.encodeAsCamel()
        then:
            encoded == 'thisIsCamel'
    }

}
