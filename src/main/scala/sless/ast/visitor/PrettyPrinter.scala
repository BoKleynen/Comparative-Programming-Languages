package sless.ast.visitor

import sless.ast.node.{DeclarationNode, PropertyNode, RuleNode, SelectorNode, SlessSheet, ValueNode}
import sless.ast.node.selector.{Adjacent, All, Attribute, Child, Class, Descendant, GeneralSibling, Id, List, PseudoClass, PseudoElement, Type}

class PrettyPrinter(val spaces: Int) {
  def visitSlessSheet(css: SlessSheet): String = css.rules.map(visitRuleNode).mkString("\n\n")

  def visitRuleNode(rule: RuleNode): String = {
    val indentation = " " * spaces
    s"""${visitSelectorNode(rule.selector)} {
${rule.declarations.map(visitDeclarationNode).mkString(indentation, s";\n$indentation", ";")}
}"""
  }

  def visitSelectorNode(selector: SelectorNode): String = selector match {
    case All => "*"
    case Type(name) => name
    case Child(lhs, rhs) => s"${visitSelectorNode(lhs)} > ${visitSelectorNode(rhs)}"
    case List(selectors) => selectors.map(visitSelectorNode).mkString(", ")
    case Id(selector, id) => s"${visitSelectorNode(selector)}#$id"
    case Adjacent(lhs, rhs) => s"${visitSelectorNode(lhs)} + ${visitSelectorNode(rhs)}"
    case Descendant(lhs, rhs) => s"${visitSelectorNode(lhs)} ${visitSelectorNode(rhs)}"
    case GeneralSibling(lhs, rhs) => s"${visitSelectorNode(lhs)} ~ ${visitSelectorNode(rhs)}"
    case Class(selector, className) =>  s"${visitSelectorNode(selector)}.$className"
    case PseudoClass(selector, action) => s"${visitSelectorNode(selector)}:$action"
    case PseudoElement(selector, part) => s"${visitSelectorNode(selector)}::$part"
    case Attribute(selector, attr, value) => s"""${visitSelectorNode(selector)}[$attr="${visitValueNode(value)}"]"""
  }

  def visitDeclarationNode(declaration: DeclarationNode): String =
    s"${visitPropertyNode(declaration.property)}: ${visitValueNode(declaration.value)}"

  def visitPropertyNode(property: PropertyNode): String = property.name

  def visitValueNode(value: ValueNode): String = value.value
}
