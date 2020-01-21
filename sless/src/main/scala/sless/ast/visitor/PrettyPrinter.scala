package sless.ast.visitor

import sless.ast.node._

object PrettyPrinter {
  def apply(css: SlessSheet, spaces: Int): String = new PrettyPrinter(spaces).visitSlessSheet(css)
}

private class PrettyPrinter(val spaces: Int) {
  private def visitSlessSheet(css: SlessSheet): String = css
    .flatten()
    .rules
    .map(visitRuleNode)
    .mkString("\n\n")

  private def visitRuleNode(rule: FlatRuleNode): String = {
    val indentation = " " * spaces
    val comment = rule.comment match {
      case Some(comment) => s"/* ${comment.str} */\n"
      case None => ""
    }
    val declarations = rule.declarations.map(visitDeclarationNode).mkString(indentation, s"\n$indentation", "")
    val selectorString = visitSelectorNode(rule.selector)

    s"""$comment$selectorString {
$declarations
}"""
  }

  private def visitSelectorNode(selector: SelectorNode): String = selector match {
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

    case Parent => throw new IllegalArgumentException("Can't reach")
  }

  private def visitDeclarationNode(declaration: DeclarationNode): String = {
    val property = visitPropertyNode(declaration.property)
    val value = visitValueNode(declaration.value)
    val comment = declaration.comment match {
      case Some(comment) => s" /* ${comment.str} */"
      case None => ""
    }

    s"$property: $value;$comment"
  }

  private def visitPropertyNode(property: PropertyNode): String = property.name

  private def visitValueNode(value: ValueNode): String = value.value
}
