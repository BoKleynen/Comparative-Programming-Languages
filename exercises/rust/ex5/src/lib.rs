/// We can use a `Vec` as a stack because it already has a `push` and a `pop`
/// method.
///
/// You are not allowed to add methods to types that you did not define
/// yourself (they could conflict with other people's methods and/or cause
/// ambiguities), so we define a new struct `Stack` that wraps a `Vec`.
/// Because we defined `Stack` ourselves, we can add methods to it.
///
/// Because this is a struct containing a single field (a /newtype/), Rust
/// optimises this wrapper struct away. When the program is executed, it will
/// be as if there were no extra wrapper around the `Vec`. So no memory is
/// wasted.
///
/// Note the type parameter `<T>` indicating that a `Stack` is generic, just
/// like the `Vec` it wraps.
struct Stack<T> {
    vec: Vec<T>,
}

/// You can use an `impl`-block to add methods to a type. Note the `<T>` after
/// `impl`, this is required because the methods are generic over `T` too.
impl<T> Stack<T> {
    /// A static method that creates a new empty `Stack` struct.
    fn new() -> Stack<T> {
        Self { vec: Vec::new() }
    }

    /// Wraps the `push` method of the underlying `Vec`.
    fn push(&mut self, x: T) {
        self.vec.push(x)
    }

    /// Wraps the `pop` method of the underlying `Vec`. When the `Stack` is
    /// empty, return an `Err` with "no element on the stack" as error
    /// message, otherwise `Ok(x)` where `x` is the element that was last
    /// added.
    fn pop(&mut self) -> Result<T, String> {
        self.vec.pop().ok_or("no element on the stack".to_string())
    }

    /// Pop two elements of the `Stack` using `pop`.
    ///
    /// Return `Ok((before_last, last))` when there are at least two elements
    /// on the `Stack`.
    ///
    /// When there is only one element on the `Stack`, return an `Err` with
    /// "only one element on the stack" as error message.
    ///
    /// When there are no elements on the `Stack`, return an `Err` with "no
    /// element on the stack" as error message.
    fn pop2(&mut self) -> Result<(T, T), String> {
        let last = self.pop()?;
        let before_last = self.vec.pop().ok_or("only one element on the stack")?;

        Ok((before_last, last))
    }

    /// Pop the only element on the `Stack`. Typically used to retrieve the
    /// final result of the calculation.
    ///
    /// When there are no elements on the `Stack`, return an `Err` with "no
    /// element on the stack" as error message.
    ///
    /// When there is more than one element on the `Stack`, return an `Err`
    /// with "more than one element on the stack" as error message.
    fn pop_only(&mut self) -> Result<T, String> {
        let val = self.pop()?;

        if self.vec.is_empty() {
            Ok(val)
        } else {
            Err("more than one element on the stack".to_string())
        }
    }
}

/// Interpret the given string as a RPN calculation.
///
/// Return `Ok(last_element_on_stack)` when the RPN calculation succeeded,
/// otherwise an `Err` containing an error message.
pub fn rpn_calc(s: &str) -> Result<f32, String> {
    let mut stack = Stack::new();

    for x in s.split_whitespace() {
        match x {
            "+" => {
                let (before_last, last) = stack.pop2()?;
                let res = before_last + last;
                stack.push(res);
            },
            "-" => {
                let (before_last, last) = stack.pop2()?;
                let res = before_last - last;
                stack.push(res);
            },
            "*" => {
                let (before_last, last) = stack.pop2()?;
                let res = before_last * last;
                stack.push(res);
            },
            "/" => {
                let (before_last, last) = stack.pop2()?;
                let res = before_last / last;
                stack.push(res);
            },
            num => {
                let num = x.parse().map_err(|_| format!("unknown operator: {}", num))?;
                stack.push(num);
            }
        }
    }

    stack.pop_only()
}
