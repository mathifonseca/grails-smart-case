package grails.plugin.smartcase

import grails.util.Holders

class UpperCamelCodec {

    static encode = { String str ->

        if (str) {

            def smartCaseService = Holders.grailsApplication.mainContext.getBean 'smartCaseService'

            str = smartCaseService.convert(Case.UNKNOWN, Case.UPPER_CAMEL, str)

        }

        return str

    }

}
