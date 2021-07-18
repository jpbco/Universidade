package pt.uevora.ServicoDeVacinas;

public interface Servico extends java.rmi.Remote {

    public String consultarCentrosVacinacao() throws java.rmi.RemoteException;

    public String consultarFilaEsperaCentro(String s) throws java.rmi.RemoteException;

    public String increverVacinar(String centro, String cc, String nome, int idade, String genero)
            throws java.rmi.RemoteException;

    public String registarVacina(String codigo, String vacina) throws java.rmi.RemoteException;

    public String reportarEfeitos(String codigo) throws java.rmi.RemoteException;

    public String listarVacinados() throws java.rmi.RemoteException;

    public String getVacinas() throws java.rmi.RemoteException;

}
