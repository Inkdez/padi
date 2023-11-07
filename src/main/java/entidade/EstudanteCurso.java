package entidade;

import jakarta.persistence.*;

@Entity
public class EstudanteCurso {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "EstudanteId", nullable = true)
    private Integer estudanteId;
    @Basic
    @Column(name = "CursoId", nullable = true)
    private Integer cursoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getEstudanteId() {
        return estudanteId;
    }

    public void setEstudanteId(Integer estudanteId) {
        this.estudanteId = estudanteId;
    }

    public Integer getCursoId() {
        return cursoId;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EstudanteCurso that = (EstudanteCurso) o;

        if (id != that.id) return false;
        if (estudanteId != null ? !estudanteId.equals(that.estudanteId) : that.estudanteId != null) return false;
        if (cursoId != null ? !cursoId.equals(that.cursoId) : that.cursoId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (estudanteId != null ? estudanteId.hashCode() : 0);
        result = 31 * result + (cursoId != null ? cursoId.hashCode() : 0);
        return result;
    }
}
