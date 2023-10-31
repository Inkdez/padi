package entidade;

import jakarta.persistence.*;

@Entity

public class Resposta {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "resposta", nullable = true, length = 145)
    private String resposta;
    @Basic
    @Column(name = "correta", nullable = true)
    private Byte correta;
    @Basic
    @Column(name = "QuestaoId", nullable = false)
    private int questaoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Byte getCorreta() {
        return correta;
    }

    public void setCorreta(Byte correta) {
        this.correta = correta;
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

        Resposta resposta1 = (Resposta) o;

        if (id != resposta1.id) return false;
        if (questaoId != resposta1.questaoId) return false;
        if (resposta != null ? !resposta.equals(resposta1.resposta) : resposta1.resposta != null) return false;
        if (correta != null ? !correta.equals(resposta1.correta) : resposta1.correta != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (resposta != null ? resposta.hashCode() : 0);
        result = 31 * result + (correta != null ? correta.hashCode() : 0);
        result = 31 * result + questaoId;
        return result;
    }

    @Override
    public String toString() {
        return "Resposta{" +
                "id=" + id +
                ", resposta='" + resposta + '\'' +
                ", correta=" + correta +
                ", questaoId=" + questaoId +
                '}';
    }
}
