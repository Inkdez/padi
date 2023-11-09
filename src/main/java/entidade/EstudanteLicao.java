package entidade;

import jakarta.persistence.*;

@Entity

@NamedQuery(name = "EstudanteLicao.progresso",query = "select  u from  EstudanteLicao  u where  u.estudanteId =?1 and u.licaoId =?2")
public class EstudanteLicao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "EstudanteId", nullable = true)
    private Integer estudanteId;
    @Basic
    @Column(name = "LicaoId", nullable = true)
    private Integer licaoId;
    @Basic
    @Column(name = "terminou", nullable = true)
    private Byte terminou;

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

    public Integer getLicaoId() {
        return licaoId;
    }

    public void setLicaoId(Integer licaoId) {
        this.licaoId = licaoId;
    }

    public Byte getTerminou() {
        return terminou;
    }

    public void setTerminou(Byte terminou) {
        this.terminou = terminou;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EstudanteLicao that = (EstudanteLicao) o;

        if (id != that.id) return false;
        if (estudanteId != null ? !estudanteId.equals(that.estudanteId) : that.estudanteId != null) return false;
        if (licaoId != null ? !licaoId.equals(that.licaoId) : that.licaoId != null) return false;
        if (terminou != null ? !terminou.equals(that.terminou) : that.terminou != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (estudanteId != null ? estudanteId.hashCode() : 0);
        result = 31 * result + (licaoId != null ? licaoId.hashCode() : 0);
        result = 31 * result + (terminou != null ? terminou.hashCode() : 0);
        return result;
    }
}
