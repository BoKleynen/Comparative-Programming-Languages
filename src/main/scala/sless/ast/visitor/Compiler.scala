package sless.ast.visitor
import sless.ast.node.selector.{Adjacent, All, Attribute, Child, Descendant, GeneralSibling, Id, PseudoClass, PseudoElement, Type, List, Class}
import sless.ast.node.{DeclarationNode, PropertyNode, RuleNode, SelectorNode, SlessSheet, ValueNode}

class Compiler() {
  def visitSlessSheet(css: SlessSheet): String = css.rules.map(visitRuleNode).mkString("")

  def visitRuleNode(rule: RuleNode): String =
    s"${visitSelectorNode(rule.selector)}{${rule.declarations.map(visitDeclarationNode).mkString("", ";", ";")}}"


  def visitSelectorNode(selector: SelectorNode): String = selector match {
    case All => "*"
    case Type(name) => name
    case Child(lhs, rhs) => s"${visitSelectorNode(lhs)}>${visitSelectorNode(rhs)}"
    case List(selectors) => selectors.map(visitSelectorNode).mkString(",")
    case Id(selector, id) => s"${visitSelectorNode(selector)}#$id"
    case Adjacent(lhs, rhs) => s"${visitSelectorNode(lhs)}+${visitSelectorNode(rhs)}"
    case Descendant(lhs, rhs) => s"${visitSelectorNode(lhs)} ${visitSelectorNode(rhs)}"
    case GeneralSibling(lhs, rhs) => s"${visitSelectorNode(lhs)}~${visitSelectorNode(rhs)}"
    case Class(selector, className) =>  s"${visitSelectorNode(selector)}.$className"
    case PseudoClass(selector, action) => s"${visitSelectorNode(selector)}:$action"
    case PseudoElement(selector, part) => s"${visitSelectorNode(selector)}::$part"
    case Attribute(selector, attr, value) => s"""${visitSelectorNode(selector)}[$attr="${visitValueNode(value)}"]"""
  }

  def visitDeclarationNode(declaration: DeclarationNode): String =
    s"${visitPropertyNode(declaration.property)}:${visitValueNode(declaration.value)}"

  def visitPropertyNode(property: PropertyNode): String = property.name

  def visitValueNode(value: ValueNode): String = value.value
}
