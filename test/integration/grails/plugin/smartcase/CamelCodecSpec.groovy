package grails.plugin.smartcase

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.*

@TestMixin(GrailsUnitTestMixin)
class CamelCodecSpec extends Specification {

    void "test encode"() {
        given:
            String notEncoded = 'this is camel'
        when:
            String encoded = notEncoded.encodeAsCamel()
        then:
            encoded == 'thisIsCamel'
    }

}
