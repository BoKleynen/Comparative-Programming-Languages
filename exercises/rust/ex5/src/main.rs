fn main() {
    match rpn_calc("1 2 + 10 * +") {
        Ok(n) => println!("Ok: {}", n),
        Err(e) => println!("Err: {}", e),
    }
}