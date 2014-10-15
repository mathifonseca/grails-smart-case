Smart Case Grails Plugin
=================

[![Build Status](https://travis-ci.org/mathifonseca/grails-smart-case.svg?branch=master)](https://travis-ci.org/mathifonseca/grails-smart-case)

This is a Grails plugin for converting between cases for Strings and variable names.

The initial version will focus on formatting Strings, but in the future, the idea is to let you convert from and to:

- lowerCamelCase
- UpperCamelCase
- snake_case
- SCREAMING_SNAKE_CASE

##String formatting

We are all used to methods like `toLowerCase()`, `toUpperCase()` and even `capitalize()`. And some combination of these lets you format your Strings the way you usually want it. But sometimes you end up having a capital letter where you don't want it or missing one where there should be one.

This plugin adds a new [custom codec](http://grails.org/doc/latest/ref/Plug-ins/codecs.html) for title casing your strings by calling this magic method:

```groovy
String foo = bar.encodeAsTitle()
```

Here is a comparison with some example phrases:

| Phrase  | toLowerCase()  |  toUpperCase() | capitalize()  | encodeAsTitle()  |
|---|---|---|---|---|
| HELLO, MY NAME IS MATHIAS  |  hello, my name is mathias | HELLO, MY NAME IS MATHIAS  | HELLO, MY NAME IS MATHIAS |  Hello, my name is Mathias |
| united states of america  | united states of america  | UNITED STATES OF AMERICA  | United states of america  | United States of America  |
