package laricaco.model;

import java.io.Serializable;

/**
 * Representa uma tag para categorizar ou descrever produtos.
 */
public class Tag implements Serializable{

    private static final long serialVersionUID = 1L;

     /** Descrição da tag. */
    private String descricaoTag;

    /**
     * Construtor que cria uma nova tag com a descrição informada.
     * 
     * @param descricaoTag descrição da tag
     */
    public Tag(String descricaoTag) {
        this.descricaoTag = descricaoTag;
    }

    /**
     * Retorna a descrição da tag.
     * 
     * @return descrição da tag
     */
    public String getTag() {
        return this.descricaoTag;
    }

    /**
     * Atualiza a descrição da tag.
     * 
     * @param descricaoTagNova nova descrição para a tag
     */
    public void setTag(String descricaoTagNova) {
        this.descricaoTag = descricaoTagNova;
    }
}
