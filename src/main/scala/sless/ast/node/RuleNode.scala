package sless.ast.node

class RuleNode(val selector: SelectorNode, val declarations: Seq[DeclarationNode]) extends Node
