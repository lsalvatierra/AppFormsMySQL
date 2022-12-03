package model;

public class Curso {
    private String idcurso;
    private String nomcurso;
    private Integer credito;

    public Curso() {
    }

    public Curso(String idcurso, String nomcurso, Integer credito) {
        this.idcurso = idcurso;
        this.nomcurso = nomcurso;
        this.credito = credito;
    }

    public String getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(String idcurso) {
        this.idcurso = idcurso;
    }

    public String getNomcurso() {
        return nomcurso;
    }

    public void setNomcurso(String nomcurso) {
        this.nomcurso = nomcurso;
    }

    public Integer getCredito() {
        return credito;
    }

    public void setCredito(Integer credito) {
        this.credito = credito;
    }
}
