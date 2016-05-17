Smart Case Grails Plugin
=================

[![Build Status](https://travis-ci.org/mathifonseca/grails-smart-case.svg?branch=master)](https://travis-ci.org/mathifonseca/grails-smart-case)

This is a Grails plugin for converting between cases for Strings values. This is an overload of one of my favorite Grails plugins, the [slug-generator](https://github.com/ilopmar/grails-slug-generator). 

You can perform convertions **to** and **from** these cases:

- human case
- lowerCamelCase
- UpperCamelCase
- snake_case
- SCREAMING_SNAKE_CASE
- slug-case

##Case convertion

The plugin adds encoding codecs such as:

```groovy
String example = 'some example phrase'
example.encodeAsCamel() == 'someExamplePhrase'
example.encodeAsUpperCamel() == 'SomeExamplePhrase'
example.encodeAsSnake() == 'some_example_phrase'
example.encodeAsScreamingSnake() == 'SOME_EXAMPLE_PHRASE'
example.encodeAsSlug() == 'some-example-phrase' //borrowed from slug-generator plugin
```

Also, you can convert between cases or even going back to human case:

```groovy
String camelExample = 'thisIsCamel'
camelExample.encodeAsSnake() == 'this_is_camel'
camelExample.encodeAsScreamingSnake() == 'THIS_IS_CAMEL'
camelExample.encodeAsUpperCamel() == 'ThisIsCamel'
camelExample.encodeAsSlug() == 'this-is-camel'
camelExample.encodeAsHuman() == 'this is camel'
```

The codecs need to detect the original casing in order to convert. In case you find an error (please report an issue) or want to make sure you get the right convertion, you can inject and use the `smartCaseService` directly like this:

```groovy
smartCaseService.convert(Case.CAMEL, Case.SNAKE, 'thisIsCamel') == 'this_is_camel'
```

##String formatting

We are all used to methods like `toLowerCase()`, `toUpperCase()` and even `capitalize()`. And some combination of these lets you format your Strings the way you usually want it (like capitalizing each word). But sometimes you end up having a capital letter where you don't want it or missing one where there should be one. For example, you don't want works like "of" or "and" to be capitalized (also works for Spanish). So here is how you do the magic:

```groovy
String foo = bar.encodeAsTitle()
```

And here is a comparison with some example phrases:

| Phrase  | toLowerCase()  |  toUpperCase() | capitalize()  | encodeAsTitle()  |
|---|---|---|---|---|
| HELLO, MY NAME IS MATHIAS  |  hello, my name is mathias | HELLO, MY NAME IS MATHIAS  | HELLO, MY NAME IS MATHIAS |  Hello, my name is Mathias |
| united states of america  | united states of america  | UNITED STATES OF AMERICA  | United states of america  | United States of America  |
| Avenida 8 De Octubre | avenida 8 de octubre  | AVENIDA 8 DE OCTUBRE  | Avenida 8 de octubre  | Avenida 8 de Octubre  |
