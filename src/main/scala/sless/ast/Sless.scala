package sless.ast

import sless.ast.node.selector._
import sless.ast.node._
import sless.ast.visitor.{Compiler, MarginAggregator, PrettyPrinter, PropertyCounter, RemoveEmptyRules}
import sless.dsl.{CommentDSL, Compilable, LintDSL, PropertyDSL, SelectorDSL, ValueDSL}

class Sless extends PropertyDSL with SelectorDSL with ValueDSL with LintDSL with Compilable with CommentDSL {
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

  override def compile(sheet: Css): String = Compiler(sheet)

  override def pretty(sheet: Css, spaces: Int): String = PrettyPrinter(sheet, spaces)

  override def removeEmptyRules(css: SlessSheet): (Boolean, SlessSheet) = RemoveEmptyRules(css)

  override def aggregateMargins(css: SlessSheet): (Boolean, SlessSheet) = MarginAggregator(css)

  override def limitFloats(css: SlessSheet, n: Integer): Boolean = PropertyCounter(css, "float") > n

  override protected def commentRule(rule: RuleNode, str: String): RuleNode =
    new RuleNode(rule.selector, rule.declarations, Some(new CommentNode(str)))

  override protected def commentDeclaration(declaration: DeclarationNode, str: String): DeclarationNode =
    new DeclarationNode(declaration.property, declaration.value, Some(new CommentNode(str)))
}
