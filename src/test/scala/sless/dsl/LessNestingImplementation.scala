package sless.dsl

import sless.ast.Sless

object LessNestingImplementation {
  type DSL = PropertyDSL with NestedSelectorDSL with ValueDSL with Compilable
  val dsl: DSL = new Sless
}
