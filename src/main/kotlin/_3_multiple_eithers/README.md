# Manejando múltiples operaciones que pueden fallar

Muy lindos los Eithers, pero qué pasa cuando -por ejemplo- estamos programando un servicio que tiene como dependencias varios clientes externos y que, por tratarse de operaciones de I/O, todos pueden fallar?

![It's still good](../../../../img/its_still_good.gif)

Para atacar estos problemas existen funciones como `.flatmap`, `.sequence`, `.traverse` y `.zip` que nos permiten operar de distintas formas sobre nuestras estructuras.

## .flatmap

Recordemos que esta función es parte de la interfaz de Mónada y todas las estructuras monádicas deben implementarla, ella nos permite aplanar nuestras estructuras cuando tenemos múltiples llamadas a funciones donde todas ellas generan Eithers.

```
fun <A, B, C> Either<A, B>.flatMap(f: (B) -> Either<A, C>): Either<A, C> 
```

## .sequence (*)(**)

Nos permite, dada una lista de Eithers, devolver un único Either con el primer error encontrado o bien una lista con todos los valores correctos.

```
fun <E, A> Iterable<Either<E, A>>.sequence(): Either<E, List<A>>
```

## .traverse (*)

Nos permite, dada una lista de elementos, ejecutar una operación que puede fallar sobre cada uno de los elementos, para luego devolver el primer error encontrado o una lista con todos los valores correctos.

```
fun <E, A, B> Iterable<A>.traverse(f: (A) -> Either<E, B>): Either<E, List<B>>
```

## .zip
Nos permite componer N Eithers, de forma tal que devuelve el primer error que encuentra o nos permite operar con todos los valores -ya sin estar encapsulados dentro de la Mónada- y transformarlos en algo más en caso de que todos los valores sean correctos.

---

(*) `.sequence` y `.traverse` no son funciones propias de Either sino extension-functions que provee arrow-kt sobre Iterables.

(**) `.sequence` puede implementarse en términos de `.traverse`

```
public fun <E, A> Iterable<Either<E, A>>.sequence(): Either<E, List<A>> =
  traverse(::identity)
```
