package grails.plugin

import grails.util.Holders

import org.codehaus.groovy.grails.web.util.WebUtils
import org.springframework.web.servlet.support.RequestContextUtils as RCU

class TitleCodec {

	static encode = { str ->

		if (str) {

			str = str.toString().trim()

			def words = str.split(/\s+/).collect { encodeWord(it) }

			str = words.join(' ')
		}

		return str
	}

	private static String encodeWord(String word) {

		def encodedWord = word

		def comparableWord = word.replaceAll(/\W+/, '')

		if (!isForcedUnchanged(comparableWord)) {

			if (isForcedLower(comparableWord)) {

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
		(config?."$currentLanguage"?.forced?.lower ?: []).contains(word.toLowerCase())
	}

	private static boolean isForcedUpper(word) {
		(config?."$currentLanguage"?.forced?.upper ?: []).contains(word.toLowerCase())
	}

	private static boolean isForcedUnchanged(word) {
		(config?."$currentLanguage"?.forced?.unchanged ?: []).contains(word)
	}

	private static String getCurrentLanguage() {

		def lang = config.forcedLanguage

		if (!lang) {
			try {
				lang = RCU.getLocale(WebUtils.retrieveGrailsWebRequest().currentRequest)
			}
			catch (Exception ignored) {}
		}

		lang ?: config.defaultLanguage ?: 'es'
	}

	private static getConfig() {
		Holders.config.smartCase
	}
}
