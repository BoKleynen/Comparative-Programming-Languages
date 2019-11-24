package sless.ast.node

import sless.ast.visitor.Visitor

trait Node {
  def accept[T](v: Visitor[T]): T
}
