**Práctica final**

Ampliar el proyecto de ejemplo de la sección "Proyecto de ejemplo" (mini aplicación de parking) añadiendo la siguiente funcionalidad: 

**Abrir barrera, en función de si el ticket está o no pagado.**

NOTA: puedes partir del código fuente que hay disponible en el repositorio.

**Descripción funcional:**

Dado un número de ticket, debemos determinar si está o no está pagado, si está pagado abrimos la barrera y si no, devolvemos un error.

**Alcance del ejercicio:**

● Tests de aceptación (en un pequeño fichero de texto).

● Implementar la funcionalidad lo más completa posible aplicando TDD. Puedes usar el enfoque de testing que prefieras (dentro a fuera con tests unitarios, fuera a dentro con tests de integración, etc.), pero aplicando TDD

**A tener en cuenta:**

● Para no complicarnos, vamos a considerar que un ticket está pagado si tiene algún campo que así lo indique, como una fecha de pago o simplemente un flag "pagado".

    Pista: Tendrás que ampliar el modelo y el esquema de BD con un campo más.

● El acto de abrir la barrera como tal va a ser ficticio. Evidentemente, no vamos a integrar ningún dispositivo o servicio externo real.

   Para la ejecución "real" (la del main) puedes copiarte la siguiente clase en com.sergiotrapiello.cursotesting.infrastructure.device, o hacer una propia tuya.

    package 
    com.sergiotrapiello.cursotesting.infrastructure.device;
    import 
    com.sergiotrapiello.cursotesting.domain.spi.BarrierDevicePort;
    public class XYZBarrierDevicePortAdapter implements BarrierDevicePort {	
    	@Override
    	public void open() {
    		System.out.println("ABRIENDO BARRERA...");
    		System.out.println("BARRERA ABIERTA");
    	}
    }


   Pero ten en cuenta dos cosas:

    (1) Piensa en esta clase como si tuviese dependencias externas reales (i.e. el dispositivo real de la barrera)

    (2) si usas esta clase en los tests, ¿cómo pruebas que se está llamando al método open?

● No hace falta que implementes la parte del main y ConsoleUI, lo importante del ejercicio son los tests.
