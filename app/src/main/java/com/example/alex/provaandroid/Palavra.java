package com.example.alex.provaandroid;

/**
 * .
 * @author Alex Frederico Ramos Barboza
 * @author Giovanni Di Lucca
 */
public class Palavra {

    private String palavraFinal;
    private String palavraCriptograda;
    private int erros;

    public Palavra(String palavraFinal) {
        this.palavraFinal = palavraFinal;
        this.palavraCriptograda = "";
        this.erros = 7;
        for (int i = 0; i < palavraFinal.length(); i++) {
            this.palavraCriptograda += "-";
        }
    }

    /**
     *
     * @return palavra plana, sem nenhum caracter substituto
     */
    public String getPalavraFinal() {
        return palavraFinal;
    }

    /**
     *
     * @return  a palavra, com caracteres nao desvendados
     *      substituitdos por traco '-'
     */
    public String getPalavraCriptograda() {
        return palavraCriptograda;
    }

    /**
     *
     * @return tamanho da palavra que se quer descobrir
     */
    public int getPalavraSize() {
        return this.palavraFinal.length();
    }

    public int getErros() {
        return this.erros;
    }

    /**
     *
     * @param letra :   letra que se quer tentar descobrir se existe na palavra
     *                  ou nao.
     *
     * Esta funcao percorre a palavra plana checando se o usuario acertou
     * ou errou ao inserir a letra @letra.
     *
     * @return true, se @letra existe na palavra
     *          false, caso contrario.
     */
    public boolean fazerTentativa(char letra) {
        String novaPalavraCriptografada = "";
        String velhaPalavraCriptograda = this.palavraCriptograda;
        boolean acertou = false;

        for (int i = 0; i < this.palavraFinal.length(); i++) {
            if (this.palavraFinal.charAt(i) == letra) {
                novaPalavraCriptografada += letra;
                acertou = true;
            } else {
                novaPalavraCriptografada += this.palavraCriptograda.charAt(i);
            }
        }

        if (acertou == false) {
            this.erros--;
        }
        this.palavraCriptograda = novaPalavraCriptografada;
        return acertou;
    }

    /**
     * Checa o fim de jogo.
     *
     * @return true, caso todas as letras da palavra ja tenham sido acertadas
     *          false, caso contrario.
     */
    public boolean checarFimJogo() {
        return (this.palavraFinal.equals(this.palavraCriptograda) || this.erros <= 0);
    }

    public boolean ganhouJogada() {
        if (this.palavraFinal.equals(this.palavraCriptograda) && this.erros > 0)
            return true;
        else
            return false;
    }
}
