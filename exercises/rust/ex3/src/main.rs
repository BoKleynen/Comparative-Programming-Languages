use ex3::Expr;

fn main() {
     // Try out some examples by replacing the ? with an expression
     match Expr::Var("joske".to_string()).eval_() {
         Ok(n)  => println!("Ok: {}", n),
         Err(e) => println!("Err: {}", e)
     }
}