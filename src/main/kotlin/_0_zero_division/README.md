# Either: motivación

Either como tipo de dato es la representación de valores con dos posibilidades: un valor del tipo Either<L, R> es o un Left<L> o un Right<R>.
El Either es frecuentemente usado para representar un valor que es o bien un error o algo correcto.
Por convención, el Left<L> es usado para encapsular el error, mientras que el Right<R> es usado para encapsular el valor correcto (del inglés "right" que también significa "correcto").

El lanzar excepciones se ha convertido en una práctica común a la hora de comunicar errores, sin embargo, las excepciones no tienen un seguimiento de ningún tipo ni forma por el compilador.

Entonces, para ver que tipo de excepciones existen en nuestros programas -si las hubiera-, tenemos que meternos en nuestro código y escarbar.

Luego, para manejar estas excepciones, tenemos que estar seguros de atraparlas en algún punto y operar con ellas.

Toda esta operatoria se vuelve más compleja cuando empezamos a componen llamadas a múltiples métodos que lanzan distintas excepciones.

Además, podrían lanzar excepciones del mismo tipo pero que intentan representar un error diferente, haciendo que sea cada vez más complicado entender de dónde salen estas excepciones.
