import entidade.Nivel;
import util.Armazenamento;

public class Main {
      public static void main(String[] args) {
          final var nivel = new Nivel();
          nivel.setId(2);
          nivel.setNivel("Avançado dinho");
          Armazenamento ar = new Armazenamento();
          ar.actualizarObjeto(nivel);
    }
}