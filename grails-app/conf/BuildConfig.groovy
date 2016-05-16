grails.project.work.dir = 'target'

grails.project.dependency.resolver = 'maven'
grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    plugins {
        build(':release:3.0.1', ':rest-client-builder:2.0.3') {
            export = false
        }
        provided(':version-update:1.3.2') {
            export = false
        }
        compile "org.grails.plugins:slug-generator:0.5"
    }
    
}