package entidade;

import jakarta.persistence.*;

@Entity
public class Licao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "titulo", nullable = true, length = 145)
    private String titulo;
    @Basic
    @Column(name = "conteudo", nullable = true, length = -1)
    private String conteudo;
    @Basic
    @Column(name = "ordem", nullable = true)
    private Integer ordem;
    @Basic
    @Column(name = "CursoId", nullable = false)
    private int cursoId;

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

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
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

        Licao licao = (Licao) o;

        if (id != licao.id) return false;
        if (cursoId != licao.cursoId) return false;
        if (titulo != null ? !titulo.equals(licao.titulo) : licao.titulo != null) return false;
        if (conteudo != null ? !conteudo.equals(licao.conteudo) : licao.conteudo != null) return false;
        if (ordem != null ? !ordem.equals(licao.ordem) : licao.ordem != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        result = 31 * result + (conteudo != null ? conteudo.hashCode() : 0);
        result = 31 * result + (ordem != null ? ordem.hashCode() : 0);
        result = 31 * result + cursoId;
        return result;
    }

    @Override
    public String toString() {
        return this.titulo;
    }
}
