package sless.ast

import sless.dsl.{Compilable, PropertyDSL, SelectorDSL, ValueDSL}
import selector._

class Sless extends PropertyDSL with SelectorDSL with ValueDSL with Compilable {
  override type Rule = RuleNode
  override type Css = SlessSheet
  override type Selector = SelectorNode
  override type Declaration = DeclarationNode
  override type Property = PropertyNode
  override type Value = ValueNode

  override protected def fromRules(rules: Seq[Rule]): Css = new SlessSheet(rules)

  override def prop(string: String): Property = new PropertyNode(string)

  override protected def assign(p: Property, value: Value): Declaration = new DeclarationNode(p, value)

  override protected def className(s: Selector, string: String): Selector = Class(s, string)

  override protected def id(s: Selector, string: String): Selector = Id(s, string)

  override protected def attribute(s: Selector, attr: String, value: Value): Selector = Attribute(s, attr, value)

  override protected def pseudoClass(s: Selector, string: String): Selector = PseudoClass(s, string)

  override protected def pseudoElement(s: Selector, string: String): Selector = PseudoElement(s, string)

  override protected def adjacent(s: Selector, selector: Selector): Selector = Adjacent(s, selector)

  override protected def general(s: Selector, selector: Selector): Selector = GeneralSibling(s, selector)

  override protected def child(s: Selector, selector: Selector): Selector = Child(s, selector)

  override protected def descendant(s: Selector, selector: Selector): Selector = Descendant(s, selector)

  override protected def group(selectors: Seq[Selector]): Selector = List(selectors)

  override def tipe(string: String): Selector = Type(string)

  override val All: Selector = selector.All

  override protected def bindTo(s: Selector, declarations: Seq[Declaration]): Rule = new RuleNode(s, declarations)

  override def value(string: String): Value = new ValueNode(string)

  override def compile(sheet: Css): String = sheet.compile()

  override def pretty(sheet: Css, spaces: Int): String = sheet.pretty(spaces)
}
