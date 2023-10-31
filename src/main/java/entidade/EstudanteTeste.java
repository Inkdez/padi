package entidade;

import jakarta.persistence.*;

@Entity
public class EstudanteTeste {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "acertada", nullable = true)
    private Byte acertada;
    @Basic
    @Column(name = "EstudanteId", nullable = false)
    private int estudanteId;
    @Basic
    @Column(name = "QuestaoId", nullable = false)
    private int questaoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Byte getAcertada() {
        return acertada;
    }

    public void setAcertada(Byte acertada) {
        this.acertada = acertada;
    }

    public int getEstudanteId() {
        return estudanteId;
    }

    public void setEstudanteId(int estudanteId) {
        this.estudanteId = estudanteId;
    }

    public int getQuestaoId() {
        return questaoId;
    }

    public void setQuestaoId(int questaoId) {
        this.questaoId = questaoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EstudanteTeste that = (EstudanteTeste) o;

        if (id != that.id) return false;
        if (estudanteId != that.estudanteId) return false;
        if (questaoId != that.questaoId) return false;
        if (acertada != null ? !acertada.equals(that.acertada) : that.acertada != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (acertada != null ? acertada.hashCode() : 0);
        result = 31 * result + estudanteId;
        result = 31 * result + questaoId;
        return result;
    }

    @Override
    public String toString() {
        return "EstudanteTeste{" +
                "id=" + id +
                ", acertada=" + acertada +
                ", estudanteId=" + estudanteId +
                ", questaoId=" + questaoId +
                '}';
    }
}
