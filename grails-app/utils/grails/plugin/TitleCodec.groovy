package grails.plugin

import org.codehaus.groovy.grails.web.util.WebUtils
import org.springframework.web.servlet.support.RequestContextUtils as RCU
import grails.util.Holders

class TitleCodec {

	static encode = { str ->

		if (str) {

			str = str.toString().trim()

			def words = str.split(/\s+/).collect { encodeWord(it) }

			str = words.join(' ')

		}

		return str

	}

	private static String encodeWord(word) {

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

		def list = Holders.config.smartCase?."${getCurrentLanguage()}"?.forced?.lower

		return list ? list.contains(word.toLowerCase()) : false

	}

	private static boolean isForcedUpper(word) {

		def list = Holders.config.smartCase?."${getCurrentLanguage()}"?.forced?.upper

		return list ? list.contains(word.toLowerCase()) : false

	}

	private static boolean isForcedUnchanged(word) {

		def list = Holders.config.smartCase?."${getCurrentLanguage()}"?.forced?.unchanged

		return list ? list.contains(word) : false

	}

	private static String getCurrentLanguage() {

		def lang = Holders.config.smartCase.forcedLanguage

		if (!lang) {

			try {

				lang = RCU.getLocale(WebUtils.retrieveGrailsWebRequest().getCurrentRequest())

			} catch (Exception ex) {}

		}

		if (!lang) {

			lang = Holders.config.smartCase.defaultLanguage ?: 'es'

		}

		return lang

	}

}