package sless.ast.node

sealed abstract class SelectorNode extends Node {
  def flatten(parent: Option[SelectorNode]): SelectorNode = {
    val (flatSelector, parentUsed) = flattenRecursive(parent)
    if (parentUsed) {
      flatSelector
    } else {
      parent match {
        case Some(parent) => Descendant(parent, flatSelector)
        case None => flatSelector
      }
    }
  }

  private def flattenRecursive(parent: Option[SelectorNode]): (SelectorNode, Boolean) = this match {
    case All => (All, false)

    case Parent => parent match {
      case Some(selector) => (selector, true)
      case None => throw new IllegalArgumentException("Invalid use of `Parent`")
    }

    case Adjacent(lhs, rhs) =>
      val (flatLhs, lhsUsedParent) = lhs.flattenRecursive(parent)
      val (flatRhs, rhsUsedParent) = rhs.flattenRecursive(parent)
      (Adjacent(flatLhs, flatRhs), lhsUsedParent || rhsUsedParent)

    case Attribute(selector, attr, value) =>
      val (flatSelector, parentUsed) = selector.flattenRecursive(parent)
      (Attribute(flatSelector, attr, value), parentUsed)

    case Child(lhs, rhs) =>
      val (flatLhs, lhsUsedParent) = lhs.flattenRecursive(parent)
      val (flatRhs, rhsUsedParent) = rhs.flattenRecursive(parent)
      (Child(flatLhs, flatRhs), lhsUsedParent || rhsUsedParent)

    case Class(selector, className) =>
      val (flatSelector, parentUsed) = selector.flattenRecursive(parent)
      (Class(flatSelector, className), parentUsed)

    case Descendant(lhs, rhs) =>
      val (flatLhs, lhsUsedParent) = lhs.flattenRecursive(parent)
      val (flatRhs, rhsUsedParent) = rhs.flattenRecursive(parent)
      (Descendant(flatLhs, flatRhs), lhsUsedParent || rhsUsedParent)

    case GeneralSibling(lhs, rhs) =>
      val (flatLhs, lhsUsedParent) = lhs.flattenRecursive(parent)
      val (flatRhs, rhsUsedParent) = rhs.flattenRecursive(parent)
      (GeneralSibling(flatLhs, flatRhs), lhsUsedParent || rhsUsedParent)

    case Id(selector, id) =>
      val (flatSelector, parentUsed) = selector.flattenRecursive(parent)
      (Id(flatSelector, id), parentUsed)

    case List(selectors) =>
      val (flatSelectors, parentUsed) = selectors.map(_.flattenRecursive(parent)).unzip
      (List(flatSelectors), parentUsed.exists(identity))

    case PseudoClass(selector, action) =>
      val (flatSelector, parentUsed) = selector.flattenRecursive(parent)
      (PseudoClass(flatSelector, action), parentUsed)

    case PseudoElement(selector, part) =>
      val (flatSelector, parentUsed) = selector.flattenRecursive(parent)
      (PseudoElement(flatSelector, part), parentUsed)

    case Type(name) => (Type(name), false)
  }
}

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
