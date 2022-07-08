# Creando errores específicos

Así como no es buena práctica dejar burbujear excepciones sin hacer nada al respecto, tampoco lo es encapsular errores en un Either.Left y pasearlos de punta a punta por nuestra aplicación sin hacer nada.

### Qué podemos hacer entonces?

- Abandonar el mundo de las excepciones y empezar a trabajar con objetos de dominio a izquiera y a derecha
- Transformar dichas excepciones en objetos que nos resulten significativos, identificables y fácilmente diferenciables
- Aprovechar las funcionalidades provistar por el Either para manejar el error, proveer resultados alternativos, etc.
