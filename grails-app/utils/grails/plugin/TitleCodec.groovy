package grails.plugin

import org.codehaus.groovy.grails.web.util.WebUtils
import org.springframework.web.servlet.support.RequestContextUtils as RCU

class TitleCodec {

	static config

	static encode = { str ->

		if (str) {

			str = str.toString().trim()

			def words = str.split(/\s+/)

			if (words) {

				if (words.size() == 1) {

					words = [ encodeWord(words[0], true) ]

				} else {

					words = [ encodeWord(words[0], true) ] + words[1..-1].collect { encodeWord(it) }

				}

				str = words.join(' ')

			}

		}

		return str

	}

	private static String encodeWord(String word, boolean first = false) {

		def encodedWord = word

		def comparableWord = word.replaceAll(/\W+/, '')

		if (!isForcedUnchanged(comparableWord)) {

			if (!first && isForcedLower(comparableWord)) {

				encodedWord = word.toLowerCase()

			} else if (isForcedUpper(comparableWord)) {

				encodedWord = word.toUpperCase()

			} else {

				encodedWord = word.toLowerCase().capitalize()
			}

		}

		return encodedWord
	}

	private static boolean isForcedLower(word) {
		(config?.smartCase?."$currentLanguage"?.forced?.lower ?: []).contains(word.toLowerCase())
	}

	private static boolean isForcedUpper(word) {
		(config?.smartCase?."$currentLanguage"?.forced?.upper ?: []).contains(word.toLowerCase())
	}

	private static boolean isForcedUnchanged(word) {
		(config?.smartCase?."$currentLanguage"?.forced?.unchanged ?: []).contains(word)
	}

	private static String getCurrentLanguage() {

		def lang = config?.smartCase?.forcedLanguage

		if (!lang) {
			try {
				lang = RCU.getLocale(WebUtils.retrieveGrailsWebRequest().currentRequest).toString()[0..1]
			}
			catch (Exception ignored) {}
		}

		lang ?: config?.smartCase?.defaultLanguage ?: 'es'

	}

}