package grails.plugin.smartcase

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class SnakeCodecSpec extends Specification {

    void "test encode"() {
        given:
            String notEncoded = 'this is snake'
        when:
            String encoded = SnakeCodec.encode(notEncoded)
        then:
            encoded == 'this_is_snake'
    }

}
