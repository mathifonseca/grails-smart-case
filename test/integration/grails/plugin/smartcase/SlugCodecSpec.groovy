package grails.plugin.smartcase

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification
import grails.plugins.SlugCodec

@TestMixin(GrailsUnitTestMixin)
class SlugCodecSpec extends Specification {

    void setup() {
        mockCodec(SlugCodec)
    }

    void "test encode"() {
        given:
            String notEncoded = 'this is slug'
        when:
            String encoded = notEncoded.encodeAsSlug()
        then:
            encoded == 'this-is-slug'
    }

}
