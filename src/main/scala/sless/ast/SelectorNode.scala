package sless.ast

abstract class SelectorNode extends Node {
  def compile(): String
  def pretty(): String
}

