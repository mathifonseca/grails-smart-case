package grails.plugin.smartcase

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class UpperCamelCodecSpec extends Specification {

    void "test encode"() {
        given:
            String notEncoded = 'this_is_upper_camel'
        when:
            String encoded = UpperCamelCodec.encode(notEncoded)
        then:
            encoded == 'ThisIsUpperCamel'
    }

}
