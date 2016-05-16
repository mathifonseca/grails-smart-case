package grails.plugin.smartcase

import grails.test.mixin.TestFor
import spock.lang.Shared
import spock.lang.Specification
import grails.plugins.SlugCodec

import static grails.plugin.smartcase.Case.*

@TestFor(SmartCaseService)
class SmartCaseServiceSpec extends Specification {

    void setup() {
        mockCodec(SlugCodec)
    }

    @Shared
    def example = [
        human : 'uruguay noma',
        camel : 'uruguayNoma',
        upperCamel : 'UruguayNoma',
        snake : 'uruguay_noma',
        screamingSnake : 'URUGUAY_NOMA',
        slug : 'uruguay-noma'
    ]

    void "without original"() {
        given:
            String original = ''
        when:
            String converted = service.convert(SNAKE, CAMEL, original)
        then:
            !converted
            def ex = thrown(SmartCaseException)
            ex.message == 'No text to convert'
    }

    void "detect human"() {
        when:
            Case detected = service.detectCase(example.human)
        then:
            detected == HUMAN
    }

    void "detect camel"() {
        when:
            Case detected = service.detectCase(example.camel)
        then:
            detected == CAMEL
    }

    void "detect upper camel"() {
        when:
            Case detected = service.detectCase(example.upperCamel)
        then:
            detected == UPPER_CAMEL
    }

    void "detect snake"() {
        when:
            Case detected = service.detectCase(example.snake)
        then:
            detected == SNAKE
    }

    void "detect screaming snake"() {
        when:
            Case detected = service.detectCase(example.screamingSnake)
        then:
            detected == SCREAMING_SNAKE
    }

    void "detect slug"() {
        when:
            Case detected = service.detectCase(example.slug)
        then:
            detected == SLUG
    }

    void "unknown from, detected human"() {
        given:
            String original = example.human
        when:
            String converted = service.convert(UNKNOWN, CAMEL, original)
        then:
            converted == example.camel
    }

    void "from X to Y"() {
        when:
            String converted = service.convert(fromCase, toCase, original)
        then:
            converted == expected
        where:
            fromCase | original | toCase | expected
//            HUMAN | example.human | SCREAMING_SNAKE | example.screamingSnake
//            HUMAN | example.human | SNAKE | example.snake
//            HUMAN | example.human | CAMEL | example.camel
//            HUMAN | example.human | UPPER_CAMEL | example.upperCamel
//            HUMAN | example.human | SLUG | example.slug
//            SCREAMING_SNAKE | example.screamingSnake | HUMAN | example.human
//            SCREAMING_SNAKE | example.screamingSnake | SNAKE | example.snake
//            SCREAMING_SNAKE | example.screamingSnake | CAMEL | example.camel
//            SCREAMING_SNAKE | example.screamingSnake | UPPER_CAMEL | example.upperCamel
//            SCREAMING_SNAKE | example.screamingSnake | SLUG | example.slug
//            SNAKE | example.snake | HUMAN | example.human
//            SNAKE | example.snake | SCREAMING_SNAKE | example.screamingSnake
//            SNAKE | example.snake | CAMEL | example.camel
//            SNAKE | example.snake | UPPER_CAMEL | example.upperCamel
//            SNAKE | example.snake | SLUG | example.slug
//            CAMEL | example.camel | HUMAN | example.human
//            CAMEL | example.camel | SNAKE | example.snake
//            CAMEL | example.camel | SCREAMING_SNAKE | example.screamingSnake
//            CAMEL | example.camel | UPPER_CAMEL | example.upperCamel
            CAMEL | example.camel | SLUG | example.slug
//            UPPER_CAMEL | example.upperCamel | SNAKE | example.snake
//            UPPER_CAMEL | example.upperCamel | HUMAN | example.human
//            UPPER_CAMEL | example.upperCamel | SCREAMING_SNAKE | example.screamingSnake
//            UPPER_CAMEL | example.upperCamel | CAMEL | example.camel
            UPPER_CAMEL | example.upperCamel | SLUG | example.slug
//            SLUG | example.slug | SNAKE | example.snake
//            SLUG | example.slug | HUMAN | example.human
//            SLUG | example.slug | SCREAMING_SNAKE | example.screamingSnake
//            SLUG | example.slug | CAMEL | example.camel
//            SLUG | example.slug | UPPER_CAMEL | example.upperCamel
    }

    void "toFirstLowerCase"() {
        when:
            def res = service.toFirstLowerCase(original)
        then:
            res == expected
        where:
            original | expected
            example.upperCamel | example.camel
            'This' | 'this'
            'A' | 'a'
            '0' | '0'
            '' | ''
    }

}
