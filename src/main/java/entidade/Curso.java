package entidade;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Curso {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "titulo", nullable = true, length = 145)
    private String titulo;
    @Basic
    @Column(name = "descricao", nullable = true, length = -1)
    private String descricao;
    @Basic
    @Column(name = "IdiomaId", nullable = false)
    private int idiomaId;
    @Basic
    @Column(name = "UsuarioId", nullable = false)
    private int usuarioId;
    @Basic
    @Column(name = "NivelId", nullable = false)
    private int nivelId;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Teste> testes;

    public List<Teste> getTestes() {
        return testes;
    }

    public void setTestes(List<Teste> testes) {
        this.testes = testes;
    }

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Licao> licoes;

    public List<Licao> getLicoes() {
        return licoes;
    }

    public void setLicoes(List<Licao> licoes) {
        this.licoes = licoes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdiomaId() {
        return idiomaId;
    }

    public void setIdiomaId(int idiomaId) {
        this.idiomaId = idiomaId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getNivelId() {
        return nivelId;
    }

    public void setNivelId(int nivelId) {
        this.nivelId = nivelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Curso curso = (Curso) o;

        if (id != curso.id) return false;
        if (idiomaId != curso.idiomaId) return false;
        if (usuarioId != curso.usuarioId) return false;
        if (nivelId != curso.nivelId) return false;
        if (titulo != null ? !titulo.equals(curso.titulo) : curso.titulo != null) return false;
        if (descricao != null ? !descricao.equals(curso.descricao) : curso.descricao != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        result = 31 * result + idiomaId;
        result = 31 * result + usuarioId;
        result = 31 * result + nivelId;
        return result;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
