/// The width of the trunk of the Christmas tree.
const TRUNK_WIDTH: usize = 3;

/// A struct used to iterate over the lines used to print a Christmas tree.
struct ChristmasTree {
    /// The character used to print the top of the tree
    top: char,
    height: usize,
    current: usize
}

impl ChristmasTree {
    /// Create a new `ChrismasTree` that uses `top` as the top of the tree and
    /// `height` as the total number of lines to print (excluding the top and
    /// the trunk).
    fn new(top: char, height: usize) -> ChristmasTree {
        Self { top, height, current: 0 }
    }
}


impl Iterator for ChristmasTree {
    /// The iterator generates `String`s
    type Item = String;

    /// Generate the next line of the Christmas tree to print.
    fn next(&mut self) -> Option<String> {
        if self.current == 0 {
            self.current += 1;
            Some(format!("{}", self.top))
        } else if self.current < height {
            self.current += 1;
        } else if self.current == height {
            self.current += 1;
        }

        None
    }
}
