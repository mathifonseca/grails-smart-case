import grails.plugin.smartcase.TitleCodec
import grails.util.Environment

class SmartCaseGrailsPlugin {
    def version = '1.2.1'
    def grailsVersion = "2.3 > *"
    def title = "Smart Case Plugin"
    def author = "Mathias Fonseca"
    def authorEmail = "mathifonseca@gmail.com"
    def description = 'Provides an easy way to convert between cases for Strings and variable names'
    def documentation = "http://grails.org/plugin/smart-case"
    def license = "APACHE"
    def issueManagement = [ system: "GITHUB", url: "https://github.com/mathifonseca/grails-smart-case/issues" ]
    def scm = [ url: "https://github.com/mathifonseca/grails-smart-case" ]

    def doWithSpring = {
        mergeConfig(application)
        TitleCodec.config = application.config
    }

    def onConfigChange = { event ->
        mergeConfig(application)
    }

    private void mergeConfig(app) {
        ConfigSlurper slurper = new ConfigSlurper(Environment.current.name)
        ConfigObject secondaryConfig = slurper.parse(app.classLoader.loadClass("SmartCaseConfig"))
        ConfigObject config = new ConfigObject()
        ConfigObject currentConfig = app.config.smartCase
        config.putAll(secondaryConfig.smartCase.merge(currentConfig))
        app.config.smartCase = config
    }

}