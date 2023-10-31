package entidade;

import jakarta.persistence.*;

@Entity

public class Nivel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "nivel", nullable = true, length = 145)
    private String nivel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nivel nivel1 = (Nivel) o;

        if (id != nivel1.id) return false;
        if (nivel != null ? !nivel.equals(nivel1.nivel) : nivel1.nivel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nivel != null ? nivel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Nivel{" +
                "id=" + id +
                ", nivel='" + nivel + '\'' +
                '}';
    }
}
