package grails.plugin.smartcase

class SmartCaseService {

    String convert(Case fromCase, Case toCase, String original) {

        String converted = ""

        try {

            if (fromCase == Case.SCREAMING_SNAKE && toCase == Case.CAMEL) {

                converted = convertScreamingSnakeCaseToUpperCamelCase(original)

            } else {

                throw new Exception("Still don't know how to convert from ${fromCase} to ${toCase}")

            }

        } catch (Exception ex) {

            log.error "Error converting from screaming snake case to upper camel case -> ${ex}"

        }

        return converted

    }

    String convertScreamingSnakeCaseToUpperCamelCase(String screamingSnake) {

        return screamingSnake.replaceAll( /([A-Z0-9]+)(_?)/, { all, word, dash -> word.toLowerCase().capitalize() } ).capitalize()

    }

}