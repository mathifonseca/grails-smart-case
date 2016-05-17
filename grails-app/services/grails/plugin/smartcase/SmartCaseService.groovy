package grails.plugin.smartcase

import grails.plugins.SlugCodec

class SmartCaseService {

    String convert(Case fromCase, Case toCase, String original) {

        String converted

        try {

            if (!original) {
                throw new SmartCaseException('No text to convert')
            }

            if (fromCase == Case.UNKNOWN) {
                fromCase = detectCase(original)
            }

            def camelFrom = fromScreamingSnakeToUpperCamel(fromCase.toString())
            def camelTo = fromScreamingSnakeToUpperCamel(toCase.toString())

            converted = "from${camelFrom}To${camelTo}"(original)

        } catch (MissingMethodException ex) {

            if (ex.method.startsWith('from')) {
                throw new SmartCaseException("Still don't know how to convert from ${fromCase} to ${toCase}")
            } else {
                throw new SmartCaseException("Error converting cases", ex)
            }

        } catch (SmartCaseException ex) {

            throw ex

        } catch (Exception ex) {

            throw new SmartCaseException("Error converting cases", ex)

        }

        log.debug "$fromCase -> $toCase | $original -> $converted"

        return converted

    }

    // HUMAN -> SNAKE

    String fromHumanToSnake(String human) {

        return human.split(/\s+/).join('_').toLowerCase()

    }

    // HUMAN -> SCREAMING_SNAKE

    String fromHumanToScreamingSnake(String human) {

        return human.split(/\s+/).join('_').toUpperCase()

    }

    // HUMAN -> CAMEL

    String fromHumanToCamel(String human) {

        return toFirstLowerCase(fromHumanToUpperCamel(human))

    }

    // HUMAN -> UPPER_CAMEL

    String fromHumanToUpperCamel(String human) {

        return human.replaceAll( /([A-Za-z0-9]+)(\s?)/, { all, word, dash -> word.toLowerCase().capitalize() } )

    }

    // HUMAN -> SLUG

    String fromHumanToSlug(String human) {

        return SlugCodec.encode(human, true)

    }

    // SCREAMING_SNAKE -> HUMAN

    String fromScreamingSnakeToHuman(String screamingSnake) {

        return fromSnakeToHuman(screamingSnake)

    }

    // SCREAMING_SNAKE -> CAMEL

    String fromScreamingSnakeToCamel(String screamingSnake) {

        return toFirstLowerCase(fromScreamingSnakeToUpperCamel(screamingSnake))

    }

    // SCREAMING_SNAKE -> UPPER_CAMEL

    String fromScreamingSnakeToUpperCamel(String screamingSnake) {

        return screamingSnake.replaceAll( /([A-Z0-9]+)(_?)/, { all, word, dash -> word.toLowerCase().capitalize() } )

    }

    // SCREAMING_SNAKE -> SNAKE

    String fromScreamingSnakeToSnake(String screamingSnake) {

        return screamingSnake.toLowerCase()

    }

    // SCREAMING_SNAKE -> SLUG

    String fromScreamingSnakeToSlug(String screamingSnake) {

        return SlugCodec.encode(screamingSnake, true)

    }

    // SNAKE -> HUMAN

    String fromSnakeToHuman(String snake) {

        return snake.replace('_', ' ').toLowerCase()

    }

    // SNAKE -> CAMEL

    String fromSnakeToCamel(String snake) {

        return toFirstLowerCase(fromSnakeToUpperCamel(snake))

    }

    // SNAKE -> UPPER_CAMEL

    String fromSnakeToUpperCamel(String snake) {

        return snake.replaceAll( /([A-Za-z0-9]+)(_?)/, { all, word, dash -> word.toLowerCase().capitalize() } )

    }

    // SNAKE -> SCREAMING_SNAKE

    String fromSnakeToScreamingSnake(String snake) {

        return snake.toUpperCase()

    }

    // SNAKE -> SLUG

    String fromSnakeToSlug(String snake) {

        return SlugCodec.encode(snake, true)

    }

    // UPPER_CAMEL -> CAMEL

    String fromUpperCamelToCamel(String upperCamel) {

        return toFirstLowerCase(upperCamel)

    }

    // UPPER_CAMEL -> SNAKE

    String fromUpperCamelToSnake(String upperCamel) {

        return fromCamelToSnake(upperCamel)

    }

    // UPPER_CAMEL -> SCREAMING_SNAKE

    String fromUpperCamelToScreamingSnake(String upperCamel) {

        return fromCamelToSnake(upperCamel).toUpperCase()

    }

    // UPPER_CAMEL -> HUMAN

    String fromUpperCamelToHuman(String upperCamel) {

        return fromCamelToHuman(upperCamel)

    }

    // UPPER_CAMEL -> SLUG

    String fromUpperCamelToSlug(String upperCamel) {

        return SlugCodec.encode(fromCamelToHuman(upperCamel), true)

    }

    // CAMEL -> HUMAN

    String fromCamelToHuman(String camel) {

        return camel.capitalize().replaceAll( /([A-Z][a-z0-9]+)/, { all, word -> "${word.toLowerCase()} " } ).trim()

    }

    // CAMEL -> UPPER_CAMEL

    String fromCamelToUpperCamel(String camel) {

        return camel.capitalize()

    }

    // CAMEL -> SNAKE

    String fromCamelToSnake(String camel) {

        return camel.capitalize().replaceAll( /([A-Z][a-z0-9]+)/, { all, word -> word.toLowerCase() + '_' } )[0..-2]

    }

    // CAMEL -> SCREAMING_SNAKE

    String fromCamelToScreamingSnake(String camel) {

        return fromCamelToSnake(camel).toUpperCase()

    }

    // CAMEL -> SLUG

    String fromCamelToSlug(String camel) {

        return SlugCodec.encode(fromCamelToHuman(camel), true)

    }

    // SLUG -> HUMAN

    String fromSlugToHuman(String slug) {

        return slug.replaceAll('-', ' ')

    }

    // SLUG -> CAMEL

    String fromSlugToCamel(String slug) {

        return toFirstLowerCase(fromSlugToUpperCamel(slug))

    }

    // SLUG -> UPPER_CAMEL

    String fromSlugToUpperCamel(String slug) {

        return slug.replaceAll( /([a-z0-9]+-?)/, { all, word -> word.replace('-','').capitalize() })

    }

    // SLUG -> SNAKE

    String fromSlugToSnake(String slug) {

        return slug.replaceAll('-', '_')

    }

    // SLUG -> SCREAMING_SNAKE

    String fromSlugToScreamingSnake(String slug) {

        return slug.replaceAll('-', '_').toUpperCase()

    }

    private final Case detectCase(String original) {

        Case detected = Case.UNKNOWN

        if (original ==~ /.*\s.*/) {
            detected = Case.HUMAN
        } else if (original ==~ /([A-Z0-9]+_?)+/) {
            detected = Case.SCREAMING_SNAKE
        } else if (original ==~ /([a-z0-9]+_?)+/) {
            detected = Case.SNAKE
        } else if (original ==~ /([a-z0-9]+-?)+/) {
            detected = Case.SLUG
        } else if (original ==~ /([a-z0-9]+)([A-Za-z0-9]*)/) {
            detected = Case.CAMEL
        } else if (original ==~ /([A-Z0-9]+)([A-Za-z0-9]*)/) {
            detected = Case.UPPER_CAMEL
        }

        if (detected == Case.UNKNOWN)
            throw new SmartCaseException("Could not detect original case for string \"${original}\", please call service with specific Case value")

        return detected

    }

    private final String toFirstLowerCase(String str) {
        if (!str) return str
        return str[0].toLowerCase() + (str.size() > 1 ? str[1..-1] : '')
    }

}