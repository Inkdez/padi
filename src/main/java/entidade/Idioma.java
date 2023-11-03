package entidade;

import jakarta.persistence.*;

@Entity

public class Idioma {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "idioma", nullable = true, length = 145)
    private String idioma;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Idioma idioma1 = (Idioma) o;

        if (id != idioma1.id) return false;
        if (idioma != null ? !idioma.equals(idioma1.idioma) : idioma1.idioma != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (idioma != null ? idioma.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return idioma;
    }
}
