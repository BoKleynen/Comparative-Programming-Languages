fn main() {
    // Don't change this line:
    let mut vec: Vec<Box<i32>> = Vec::new();

    // You can change these lines:
    println!("First element: {}", get_first(vec, -1));
    fill(vec);
    print_contents(vec);
    inc_all(vec);
    print_contents(vec);
    swallow(vec);
}