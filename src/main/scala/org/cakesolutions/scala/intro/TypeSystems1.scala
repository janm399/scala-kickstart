package org.cakesolutions.scala.intro

/**
 * @author janmachacek
 */

object TypeSystems1 {

  abstract case class Restriction()
  case class Binary(what: String, op: Symbol, value: Any) extends Restriction
  case class Like(what: String, value: String) extends Restriction
  case class Conjunction(lhs: Restriction, rhs: Restriction) extends Restriction
  case class Disjunction(lhs: Restriction, rhs: Restriction) extends Restriction
  case class Tautology() extends Restriction
  case class Contradiction() extends Restriction
  
  def simplifyDisjunction(d: Disjunction) =
    (d.lhs, d.rhs) match {
      case (Tautology(), _) => Tautology()
      case (_, Tautology()) => Tautology()
      case (Binary(what1, '==, value1), Binary(what2, '!=, value2))
        if what1 == what2 && value1 == value2 => Tautology()
      case (Binary(what1, '!=, value1), Binary(what2, '==, value2))
        if what1 == what2 && value1 == value2 => Tautology()
      case (lhs, rhs) => Disjunction(simplify(lhs), simplify(rhs))
    }

  def simplifyConjunction(c: Conjunction) = c
  
  def simplify(r: Restriction): Restriction = r match {
      case d: Disjunction => simplifyDisjunction(d)
      case c: Conjunction => simplifyConjunction(c)
      case x => x
    }
  
  def main(args: Array[String]) {
    val r1 = Disjunction(Binary("id", '>, 1L), Like("username", "Jan%"))
    val r2 = Disjunction(Binary("id", '==, 1L), Binary("id", '!=, 1L))
    
    println(simplify(r1))
    println(simplify(r2))
  }
  
}