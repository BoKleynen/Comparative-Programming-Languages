package sless.ast.node

sealed abstract class SelectorNode extends Node

case object All extends SelectorNode

case object Parent extends SelectorNode

case class Adjacent(lhs: SelectorNode, rhs: SelectorNode) extends SelectorNode

case class Attribute(selector: SelectorNode, attr: String, value: ValueNode) extends SelectorNode

case class Child(lhs: SelectorNode, rhs: SelectorNode) extends SelectorNode

case class Class(selector: SelectorNode, className: String) extends SelectorNode

case class Descendant(lhs: SelectorNode, rhs: SelectorNode) extends SelectorNode

case class GeneralSibling(lhs: SelectorNode, rhs: SelectorNode) extends SelectorNode

case class Id(selector: SelectorNode, id: String) extends SelectorNode

case class List(selectors: Seq[SelectorNode]) extends SelectorNode

case class PseudoClass(selector: SelectorNode, action: String) extends SelectorNode

case class PseudoElement(selector: SelectorNode, part: String) extends SelectorNode

case class Type(name: String) extends SelectorNode
