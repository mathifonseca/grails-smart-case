package grails.plugin.smartcase

import org.codehaus.groovy.grails.exceptions.GrailsException

class SmartCaseException extends GrailsException {

    public SmartCaseException() {
        super()
    }

    public SmartCaseException(String msg) {
        super(msg)
    }

    public SmartCaseException(String msg, Throwable ex) {
        super(msg, ex)
    }

}
