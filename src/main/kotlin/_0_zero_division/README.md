# Either - Motivation

The Either type represents values with two possibilities: a value of type Either a b is either Left a or Right b.
The Either type is sometimes used to represent a value which is either correct or an error;
by convention, the Left constructor is used to hold an error value and the Right constructor is used to hold a correct value
(mnemonic: "right" also means "correct").

To communicate these errors, it has become common practice to throw exceptions; however, exceptions are not tracked in any way, shape, or form by the compiler.
To see what kind of exceptions (if any) a function may throw, we have to dig through the source code.
Then, to handle these exceptions, we have to make sure we catch them at the call site.
This all becomes even more unwieldy when we try to compose exception-throwing procedures.
Assume we happily throw exceptions in our code. Looking at the types of the functions above, any could throw a number of exceptions -- we do not know.
When we compose, exceptions from any of the constituent functions can be thrown.
Moreover, they may throw the same kind of exception (e.g., IllegalArgumentException) and, thus, it gets tricky tracking exactly where an exception came from.
