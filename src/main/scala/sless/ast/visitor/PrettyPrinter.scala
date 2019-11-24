package sless.ast.visitor

import sless.ast.node.{DeclarationNode, PropertyNode, RuleNode, SelectorNode, SlessSheet, ValueNode}
import sless.ast.node.selector.{Adjacent, All, Attribute, Child, Class, Descendant, GeneralSibling, Id, List, PseudoClass, PseudoElement, Type}

class PrettyPrinter(val spaces: Int) extends Visitor[String] {
  override def visitSlessSheet(css: SlessSheet): String = css.rules.map(_.accept(this)).mkString("\n\n")

  override def visitRuleNode(rule: RuleNode): String = {
    val indentation = " " * spaces
    s"""${rule.selector.accept(this)} {
${rule.declarations.map(_.accept(this)).mkString(indentation, s";\n$indentation", ";")}
}"""
  }

  override def visitSelectorNode(selector: SelectorNode): String = selector match {
    case All => "*"
    case Type(name) => name
    case Child(lhs, rhs) => s"${lhs.accept(this)} > ${rhs.accept(this)}"
    case List(selectors) => selectors.map(_.accept(this)).mkString(", ")
    case Id(selector, id) => s"${selector.accept(this)}#$id"
    case Adjacent(lhs, rhs) => s"${lhs.accept(this)} + ${rhs.accept(this)}"
    case Descendant(lhs, rhs) => s"${lhs.accept(this)} ${rhs.accept(this)}"
    case GeneralSibling(lhs, rhs) => s"${lhs.accept(this)} ~ ${rhs.accept(this)}"
    case Class(selector, className) =>  s"${selector.accept(this)}.$className"
    case PseudoClass(selector, action) => s"${selector.accept(this)}:$action"
    case PseudoElement(selector, part) => s"${selector.accept(this)}::$part"
    case Attribute(selector, attr, value) => s"""${selector.accept(this)}[$attr="${value.accept(this)}"]"""
  }

  override def visitDeclarationNode(declaration: DeclarationNode): String =
    s"${declaration.property.accept(this)}: ${declaration.value.accept(this)}"

  override def visitPropertyNode(property: PropertyNode): String = property.name

  override def visitValueNode(value: ValueNode): String = value.value
}
