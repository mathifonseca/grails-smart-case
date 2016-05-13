package grails.plugin.smartcase

import grails.util.Holders

class ScreamingSnakeCodec {

    static encode = { String str ->

        if (str) {

            def smartCaseService = Holders.grailsApplication.mainContext.getBean 'smartCaseService'

            str = smartCaseService.convert(Case.UNKNOWN, Case.SCREAMING_SNAKE, str)

        }

        return str

    }

}
