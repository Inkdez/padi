package entidade;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

@NamedQuery(name = "Questao.porTestId",query = "select  u from  Questao  u where  u.testeId =?1")

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

    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Resposta> respostas;

    @ManyToOne
    @JoinColumn(name = "TesteId",insertable = false,updatable = false)
    private Teste teste;


    public Teste getTeste() {
        return teste;
    }

    public void setTeste(Teste teste) {
        this.teste = teste;
    }



    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }




    public List<Resposta> getRespostas() {
        return respostas;
    }

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


//    @Override
//    public String toString() {
//        return this.questao;
//    }


    @Override
    public String toString() {
        return "Questao{" +
                "id=" + id +
                ", questao='" + questao + '\'' +
                ", testeId=" + testeId +
                ", respostas=" + respostas +
                ", teste=" + teste +
                '}';
    }
}
