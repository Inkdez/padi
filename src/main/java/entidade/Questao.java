package entidade;

import jakarta.persistence.*;

@Entity

public class Questao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "questao", nullable = true, length = -1)
    private String questao;
    @Basic
    @Column(name = "TesteId", nullable = false)
    private int testeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestao() {
        return questao;
    }

    public void setQuestao(String questao) {
        this.questao = questao;
    }

    public int getTesteId() {
        return testeId;
    }

    public void setTesteId(int testeId) {
        this.testeId = testeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Questao questao1 = (Questao) o;

        if (id != questao1.id) return false;
        if (testeId != questao1.testeId) return false;
        if (questao != null ? !questao.equals(questao1.questao) : questao1.questao != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (questao != null ? questao.hashCode() : 0);
        result = 31 * result + testeId;
        return result;
    }

    @Override
    public String toString() {
        return "Questao{" +
                "id=" + id +
                ", questao='" + questao + '\'' +
                ", testeId=" + testeId +
                '}';
    }
}
