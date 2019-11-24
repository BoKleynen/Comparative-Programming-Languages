package sless.dsl

import sless.ast.node.Sless

object CssImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with Compilable
  val dsl: DSL = new Sless
}
