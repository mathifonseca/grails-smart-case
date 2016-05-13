package grails.plugin.smartcase

import grails.util.Holders

class CamelCodec {

    static encode = { String str ->

        if (str) {

            def smartCaseService = Holders.grailsApplication.mainContext.getBean 'smartCaseService'

            str = smartCaseService.convert(Case.UNKNOWN, Case.CAMEL, str)

        }

        return str

    }

}
