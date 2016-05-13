package grails.plugin.smartcase

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class ScreamingSnakeCodecSpec extends Specification {

    void "test encode"() {
        given:
            String notEncoded = 'ThisIsScreamingSnake'
        when:
            String encoded = ScreamingSnakeCodec.encode(notEncoded)
        then:
            encoded == 'THIS_IS_SCREAMING_SNAKE'
    }

}
