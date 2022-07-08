# Transformando los valores del Either

A menos que decidamos romper con la estructura de la mónada, lo cuál sólo deberíamos hacer si estamos en condiciones de ofrecer una alternativa en caso de encontrarnos con un Either.Left, normalmente tendremos la necesidad de operar sobre Eithers.

El Either en general, y el de arrow-kt en particular, ofrece algunas funciones para hacer esto:

## .map

Esta función recibe una función (`f`) como parámetro que será utilizada para transformar los valores del tipo encapsulado por los Either.Right, y operará siempre y cuando nos encontremos con un Either.Right.

```
fun <RR> Either<L, R>.map(f: (R) -> RR): Either<L, RR> =
```

## .mapLeft

Esta función también recibe una función (`f`) como parámetro, pero a diferencia del map, esta será utilizada para transformar los valores del tipo encapsulado por los Either.Left, y operará siempre y cuando nos encontremos con un Either.Left.

```
fun <LL> Either<L, R>.mapLeft(f: (L) -> LL): Either<LL, R> =
```
