package entidade;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Teste {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "titulo", nullable = true, length = 145)
    private String titulo;
    @Basic
    @Column(name = "ordem", nullable = true)
    private Integer ordem;
    @Basic
    @Column(name = "CursoId", nullable = false)
    private int cursoId;

    @ManyToOne
    @JoinColumn(name = "CursoId",insertable = false,updatable = false)
    private Curso curso;

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Curso getCurso() {
        return curso;
    }

    @OneToMany(mappedBy = "teste", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Questao> questoes;

    public List<Questao> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(List<Questao> questoes) {
        this.questoes = questoes;
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

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teste teste = (Teste) o;

        if (id != teste.id) return false;
        if (cursoId != teste.cursoId) return false;
        if (titulo != null ? !titulo.equals(teste.titulo) : teste.titulo != null) return false;
        if (ordem != null ? !ordem.equals(teste.ordem) : teste.ordem != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        result = 31 * result + (ordem != null ? ordem.hashCode() : 0);
        result = 31 * result + cursoId;
        return result;
    }

    @Override
    public String toString() {
        return this.titulo;
    }
}
