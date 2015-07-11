## Sangria

**Sangria** is a scala [GraphQL](http://facebook.github.io/graphql/) client and server library.

The initial version is WIP. More information about the project will come soon.

[![Build Status](https://travis-ci.org/OlegIlyenko/sangria.svg)](https://travis-ci.org/OlegIlyenko/sangria)

Examples below demonstrate some of the features, that are implemented so far. More features will come very soon!

### Parser and Renderer

Example usage:

```scala
import sangria.parser.QueryParser
import sangria.renderer.QueryRenderer

import scala.util.Success

val query =
  """
    query FetchLukeAndLeiaAliased(
          $someVar: Int = 1.23
          $anotherVar: Int = 123) @include(if: true) {
      luke: human(id: "1000")@include(if: true){
        friends(sort: NAME)
      }

      leia: human(id: "10103\n \u00F6 ö") {
        name
      }

      ... on User {
        birth{day}
      }

      ...Foo
    }

    fragment Foo on User @foo(bar: 1) {
      baz
    }
  """

// Parse GraphQl query
val Success(document: ast.Document) = QueryParser.parse(query)

// Pretty rendering of GraphQl query as a `String`
println(QueryRenderer.render(document))

// Compact rendering of GraphQl query as a `String`
println(QueryRenderer.render(document, QueryRenderer.Compact))
```

## License

**Sangria** is licensed under [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
