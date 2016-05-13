package grails.plugin.smartcase

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
            TitleCodec.config = [
                smartCase : [ 
                    forcedLanguage : 'es',
                    es : [
                        forced : [
                            lower : [],
                            upper : [],
                            unchanged : []
                        ]
                    ]
                ]
            ]
            String str = 'Hello, my name is Mathias'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'Hello, My Name Is Mathias'
    }

    void "title case with forced language to ES and lower forced for EN"() {
        given:
            TitleCodec.config = [
                smartCase : [ 
                    forcedLanguage : 'es',
                    en : [
                        forced : [
                            lower : ['my','name','is'],
                            upper : [],
                            unchanged : []
                        ]
                    ]
                ]
            ]
            String str = 'Hello, my name is Mathias'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'Hello, My Name Is Mathias'
    }

    void "title case with forced language to EN and lower forced for EN from upper"() {
        given:
            TitleCodec.config = [
                smartCase : [ 
                    forcedLanguage : 'en',
                    en : [
                        forced : [
                            lower : ['my','name','is'],
                            upper : [],
                            unchanged : []
                        ]
                    ]
                ]
            ]
            String str = 'Hello, MY NAME IS MATHIAS'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'Hello, my name is Mathias'
    }

    void "title case with forced language to EN and upper forced for EN from lower"() {
        given:
            TitleCodec.config = [
                smartCase : [ 
                    forcedLanguage : 'en',
                    en : [
                        forced : [
                            lower : ['name','is'],
                            upper : ['my'],
                            unchanged : []
                        ]
                    ]
                ]
            ]
            String str = 'Hello, my name is mathias'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'Hello, MY name is Mathias'
    }

    void "title case with forced language to EN and lower, upper and unchanged forced for EN"() {
        given:
            TitleCodec.config = [
                smartCase : [ 
                    forcedLanguage : 'en',
                    en : [
                        forced : [
                            lower : ['my'],
                            upper : ['name'],
                            unchanged : ['MaThIaS']
                        ]
                    ]
                ]
            ]
            String str = 'Hello, my name is MaThIaS'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'Hello, my NAME Is MaThIaS'
    }

    void "title case with comma messing around"() {
        given:
            TitleCodec.config = [
                smartCase : [ 
                    forcedLanguage : 'en',
                    en : [
                        forced : [
                            lower : ['my','name','is'],
                            upper : ['hello'],
                            unchanged : []
                        ]
                    ]
                ]
            ]
            String str = 'Hello, my name is Mathias'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'HELLO, my name is Mathias'
    }

    void "title case with forced lower but first word"() {
        given:
            TitleCodec.config = [
                smartCase : [ 
                    forcedLanguage : 'en',
                    en : [
                        forced : [
                            lower : ['my','name','is'],
                            unchanged : []
                        ]
                    ]
                ]
            ]
            String str = 'my name is Mathias'
        when:
            str = TitleCodec.encode(str)
        then:
            str == 'My name is Mathias'
    }

}
