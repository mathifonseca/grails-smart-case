import grails.util.Environment
import groovy.util.ConfigObject
import groovy.util.ConfigSlurper

class SmartCaseGrailsPlugin {
    def version = "0.0.1"
    def grailsVersion = "2.3 > *"
    def title = "Smart Case Plugin"
    def author = "Mathias Fonseca"
    def authorEmail = "mathifonseca@gmail.com"
    def description = '''\
This plugin provides an easy way to convert between cases for Strings and variable names
'''
    def documentation = "http://grails.org/plugin/smart-case"
    def license = "APACHE"
    def issueManagement = [ system: "GITHUB", url: "https://github.com/mathifonseca/grails-smart-case/issues" ]
    def scm = [ url: "https://github.com/mathifonseca/grails-smart-case" ]

    def doWithSpring = {
        mergeConfig(application)
    }

    def onConfigChange = { event ->
        mergeConfig(application)
    }

    private void mergeConfig(app) {
        ConfigObject currentConfig = app.config.smartCase
        ConfigSlurper slurper = new ConfigSlurper(Environment.getCurrent().getName())
        ConfigObject secondaryConfig = slurper.parse(app.classLoader.loadClass("SmartCaseConfig"))
        ConfigObject config = new ConfigObject()
        config.putAll(secondaryConfig.smartCase.merge(currentConfig))
        app.config.smartCase = config
    }

}