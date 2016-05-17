package grails.plugin.smartcase

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class SlugCodecSpec extends Specification {

    void "test encode"() {
        given:
            String notEncoded = 'this is slug'
        when:
            String encoded = SlugCodec.encode(notEncoded)
        then:
            encoded == 'this-is-slug'
    }

    void "test encode strange char"() {
        given:
            String notEncoded = 'this is ´slug'
        when:
            String encoded = SlugCodec.encode(notEncoded)
        then:
            encoded == 'this-is-slug'
    }

}
