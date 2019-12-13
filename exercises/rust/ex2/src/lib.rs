fn fill(vec: Vec<Box<i32>>) {
    for i in 0..10 {
        vec.push(Box::new(i));
    }
}

fn print_contents(vec: Vec<Box<i32>>) {
    for i in vec.into_iter() {
        print!("{} ", *i)
    }
    println!();
}

fn swallow(_vec: Vec<Box<i32>>) {
    println!("Aaaand it's gone");
}

fn get_first(vec: Vec<Box<i32>>, default: i32) -> i32 {
    // Why do we map here? Why do we need the two dereferences (**)?
    let first: Option<i32> = vec.first().map(|i| **i);
    match first {
        Some(x) => return x,
        None => {
            vec.push(Box::new(default));
            return default;
        }
    }
}

fn inc(i: Box<i32>) {
    // Increment the i32 in the Box, i.e. the heap-allocated i32.
    **i += 1;
}

fn inc_all(vec: Vec<Box<i32>>) {
    for i in vec.into_iter() {
        inc(i);
    }
}
