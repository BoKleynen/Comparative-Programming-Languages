/// Use a type-alias to declare that when we write `Id`, we actually mean
/// `String`.
type Id = String;

/// An expression in a simple language with arithmetic and variables. An
/// expression is any of the following:
///
///     Literal, e.g., 3 (u32)
///     Variable, e.g., x (Id)
///     Assignment, e.g., x = y + 2
///     Application of a binary operator, e.g., x + 3
///     A sequence of two expressions, e.g., x = 1; x + 3
pub enum Expr {
    Lit(u32),
    Var(Id),
    Assignment(Id, Box<Expr>),
    BinOp(Op, Box<Expr>, Box<Expr>),
    Seq(Box<Expr>, Box<Expr>)
}

pub enum Op {
    Plus,
    Minus,
    Multiply,
}

// Bring the constructors of `Expr` in scope, see
// http://rustbyexample.com/custom_types/enum/enum_use.html
use self::Expr::*;
use std::collections::HashMap;
use self::Op::*;
// With this we can write:
//
//     match e {
//         Lit(n) => ...
//         ...
//     }
//
// Without this, we would have to write:
//
//     match e {
//         Expr::Lit(n) => ...
//         ...
//     }

/// The environment maps `Id`s to `u32`s.
pub struct Env {
    vars: HashMap<Id, u32>,
}

impl Env {
    /// Create a new empty environment.
    pub fn new() -> Self {
        let vars = HashMap::new();
        Env { vars }
    }
    /// Look up `id` in the environment.
    fn lookup(&self, id: &Id) -> Option<u32> {
        self.vars.get(id).map(|x| *x)
    }
    /// Map `id` to `val` in the environment.
    fn assign(&mut self, id: Id, val: u32) {
        self.vars.insert(id, val);
    }
}

impl Expr {
    /// Evaluate an expression using the given environment to look up and
    /// assign variables in. An `Err` can only occur when a variable is used
    /// before it is assigned.
    pub fn eval(&self, env: &mut Env) -> Result<u32, String> {
        match self {
            Lit(val) => Ok(*val),
            Var(id) => env.lookup(id).ok_or(format!("{} used before it was assigned", id)),
            Assignment(id, expr) => expr.eval(env).map(|val| {
                env.assign(id.clone(), val);
                val
            }),
            BinOp(op, lhs, rhs) => {
                let lhs = lhs.eval(env)?;
                let rhs = rhs.eval(env)?;

                match op {
                    Plus => Ok(lhs + rhs),
                    Minus => Ok(lhs - rhs),
                    Multiply => Ok(lhs * rhs)
                }
            },
            Seq(expr1, expr2) => {
                expr1.eval(env)?;
                expr2.eval(env)
            }
        }
    }

    /// Evaluate an expression using an empty environment.
    pub fn eval_(&self) -> Result<u32, String> {
        match self {
            Lit(val) => Ok(*val),
            BinOp(op, lhs, rhs) => {
                let lhs = lhs.eval_()?;
                let rhs = rhs.eval_()?;

                match op {
                    Plus => Ok(lhs + rhs),
                    Minus => Ok(lhs - rhs),
                    Multiply => Ok(lhs * rhs)
                }
            },
            Seq(expr1, expr2) => {
                expr1.eval_()?;
                expr2.eval_()
            }
            Var(id) => Err(format!("{} used before it was assigned", id)),
            Assignment(_, _) => Err(format!("no environment available."))
        }
    }
}