package grails.plugin.smartcase

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class HumanCodecSpec extends Specification {

    void "test encode"() {
        given:
            String notEncoded = 'thisIsHuman'
        when:
            String encoded = HumanCodec.encode(notEncoded)
        then:
            encoded == 'this is human'
    }

}
