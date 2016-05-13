package grails.plugin.smartcase

import grails.util.Holders

class SnakeCodec {

    static encode = { String str ->

        if (str) {

            def smartCaseService = Holders.grailsApplication.mainContext.getBean 'smartCaseService'

            str = smartCaseService.convert(Case.UNKNOWN, Case.SNAKE, str)

        }

        return str

    }

}
