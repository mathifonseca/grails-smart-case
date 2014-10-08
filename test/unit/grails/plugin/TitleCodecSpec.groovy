package grails.plugin

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class TitleCodecSpec extends Specification {

    void "title case an empty string"() {
        given:
            String str = ""
        when:
            str = TitleCodec.encode(str)
        then:
            str == ""
    }

    void "title case a null string"() {
        given:
            String str
        when:
            str = TitleCodec.encode(str)
        then:
            !str
    }

    void "title case one word in lower case"() {
        given:
            String str = 'one'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'One'
    }

    void "title case one word in upper case"() {
        given:
            String str = 'ONE'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'One'
    }

    void "title case one word in random case"() {
        given:
            String str = 'oNe'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'One'
    }

    void "title case one word with spaces"() {
        given:
            String str = ' one '
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'One'
    }

    void "title case a string in lower case"() {
        given:
            String str = 'one value'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'One Value'
    }

    void "title case a string in upper case"() {
        given:
            String str = 'ONE VALUE'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'One Value'
    }

    void "title case a number"() {
        given:
            long num = 123124
        when:
            String str = TitleCodec.encode(num)
        then:
            str == '123124'
    }

    void "title case a boolean"() {
        given:
            boolean b = true
        when:
            String str = TitleCodec.encode(b)
        then:
            str == 'True'
    }

    void "title case without any force"() {
        given:
            String str = 'Hello, my name is Mathias'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'Hello, My Name Is Mathias'
    }

    void "title case with forced language to ES"() {
        given:
            config.forcedLanguage = 'es'
            config.es.forced.lower = []
            config.es.forced.upper = []
            config.es.forced.unchanged = []

            String str = 'Hello, my name is Mathias'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'Hello, My Name Is Mathias'
    }

    void "title case with forced language to ES and lower forced for EN"() {
        given:
            config.forcedLanguage = 'es'
            config.en.forced.lower = ['my','name','is']
            String str = 'Hello, my name is Mathias'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'Hello, My Name Is Mathias'
    }

    void "title case with forced language to EN and lower forced for EN from upper"() {
        given:
            config.forcedLanguage = 'en'
            config.en.forced.lower = ['my','name','is']
            String str = 'Hello, MY NAME IS MATHIAS'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'Hello, my name is Mathias'
    }

    void "title case with forced language to EN and upper forced for EN from lower"() {
        given:
            config.forcedLanguage = 'en'
            config.en.forced.lower = ['name','is']
            config.en.forced.upper = ['my']
            String str = 'Hello, my name is mathias'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'Hello, MY name is Mathias'
    }

    void "title case with forced language to EN and lower, upper and unchanged forced for EN"() {
        given:
            config.forcedLanguage = 'en'
            config.en.forced.lower = ['my']
            config.en.forced.upper = ['name']
            config.en.forced.unchanged = ['MaThIaS']
            String str = 'Hello, my name is MaThIaS'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'Hello, my NAME Is MaThIaS'
    }

    void "title case with comma messing around"() {
        given:
            config.forcedLanguage = 'en'
            config.en.forced.upper = ['hello']
            config.en.forced.lower = ['my','name','is']
            String str = 'Hello, my name is Mathias'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'HELLO, my name is Mathias'
    }

    private getConfig() {
        grails.util.Holders.config.smartCase
    }
}
