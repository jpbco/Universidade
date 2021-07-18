package pt.uevora.ServicoDeVacinas;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class ServicoImp extends UnicastRemoteObject implements Servico {

    final static PostgresConnector bd = new PostgresConnector();

    public ServicoImp() throws java.rmi.RemoteException {
        super();
    }

    @Override
    public String consultarCentrosVacinacao() throws RemoteException {
        StringBuilder sbr = new StringBuilder("\n\tCentros\n");
        sbr.append("------------------\n");
        try {
            bd.connect();
            Statement stmt = bd.getStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM centro");

            while (rs.next()) {
                sbr.append(rs.getString("nome"));
                sbr.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bd.disconnect();
        }
        return sbr.toString();
    }

    @Override
    public String consultarFilaEsperaCentro(String centro) throws RemoteException {
        var sbr = new StringBuilder("");

        if (!centroExiste(centro)) {
            return "Centro não encontrado.";
        }

        int filaSize = 0;
        try {
            bd.connect();
            Statement stmt = bd.getStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT id_centro,centro.nome, COUNT(id_centro) as ct FROM fila JOIN centro ON  fila.id_centro = centro.id WHERE centro.nome = '"
                    + centro.trim() + "' GROUP by id_centro,centro.nome;");

            if (rs.next()) {
                filaSize = rs.getInt("ct");
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            bd.disconnect();
        }
        sbr.append("O centro ");
        sbr.append(centro);
        sbr.append(" tem ");
        sbr.append(filaSize);
        sbr.append(" pessoas na fila de espera.");
        return sbr.toString();
    }

    @Override
    public String increverVacinar(String centro, String nome, String cc, int idade, String genero)
            throws RemoteException {
        String result = "Ocorreu um erro durante o processo de inscrição, por favor tente mais tarde.";

        if (!centroExiste(centro)) {
            return "Centro não encontrado.";
        }

        if (isVacinadoOuFila(cc)) {
            return "Oops, parece que você já se encontra na fila de espera ou já foi vacinado!";
        }

        try {
            bd.connect();

            Statement stmt = bd.getStatement();
            result = UUID.randomUUID().toString();
            int rs = stmt.executeUpdate("INSERT into fila (id_centro,codigo,cc,nome,idade,genero) \n"
                    + "SELECT id,'" + result + "','" + cc + "','" + nome + "'," + idade + " ,'" + genero + "'   \n"
                    + "FROM centro\n" + "where centro.nome = '" + centro + "';");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bd.disconnect();
        }
        return result;
    }

    @Override
    public String registarVacina(String codigo, String vacina) throws RemoteException {
        // Verificações de input
        try {
            UUID.fromString(codigo);
        } catch (Exception e) {
            return "Código num formato inválido.";
        }
        if (!isInFila(codigo)) {
            return "Não pode registar a sua vacinação se ainda não se encontra na fila de espera de nenhum centro.";
        }

        if (!isVacina(vacina)) {
            return "Nome de vacina incorreta, por favor tente outra vez";
        }

        int rs = 0;
        try {
            bd.connect();
            Statement stmt = bd.getStatement();
            rs = stmt.executeUpdate("insert into vacinados (id_centro,codigo,cc,nome,idade,genero,tipo_vacina)\n"
                    + "select id_centro,codigo,cc,nome,idade,genero,'" + vacina
                    + "' as tipo_vacina from fila where codigo = '" + codigo + "';");

            stmt.executeUpdate("delete  from fila \n" + "where codigo='" + codigo + "';");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            bd.disconnect();
        }
        if (rs == 1) {
            return "Registo efetuado com successo.";
        } else {
            return "Ocorreu um erro. Por favor tente mais tarde";
        }
    }

    @Override
    public String reportarEfeitos(String codigo) throws RemoteException {

        // Verificações de input
        try {
            UUID.fromString(codigo);
        } catch (Exception e) {
            return "Código num formato inválido.";
        }

        if (isVacinado(codigo, false)) {
            // do nothing and continue
        } else {
            if (isInFila(codigo)) {
                return "Não pode reportar efeitos secundários se ainda se encontra na fila de espera.";
            }

            if (isVacinado(codigo, true)) {
                return "Só é preciso reportar a existência de efeitos secundários uma vez.";
            }
        }

        int rs = 0;
        try {
            bd.connect();
            Statement stmt = bd.getStatement();
            rs = stmt.executeUpdate(
                    "update vacinados \n" + "set efeitos_secundarios=true\n" + "where codigo = '" + codigo + "';");

        } catch (Exception e) {

        } finally {
            bd.disconnect();
        }

        if (rs == 1) {
            return "Operação realizada com successo.";
        } else {
            return "Ocorreu um erro, por favor tente mais tarde.";
        }

    }

    @Override
    public String listarVacinados() throws RemoteException {
        StringBuilder result = new StringBuilder("[Vacina: <Número de vacinados>, <Casos com efeitos secundários]\n");
        result.append("-----------------------------\n");

        try {
            bd.connect();
            Statement stmt = bd.getStatement();
            ResultSet rs = stmt.executeQuery(
                    "select tipo_vacina ,\n" + "count(*) filter (where efeitos_secundarios = true) as ct_efeitos,\n"
                    + "count(*) as ct_total\n" + "from vacinados \n" + "group by tipo_vacina;");

            while (rs.next()) {
                result.append("[ ");
                result.append(rs.getString("tipo_vacina"));
                result.append(": ");
                result.append(Integer.toString(rs.getInt("ct_total")));
                result.append(", ");
                result.append(Integer.toString(rs.getInt("ct_efeitos")));
                result.append(" ]\n");
            }
        } catch (Exception e) {
        } finally {
            bd.disconnect();
        }
        result.append("-----------------------------\n");
        return result.toString();
    }

    @Override
    public String getVacinas() throws RemoteException {
        StringBuilder sbr = new StringBuilder("\n\tVacinas\n");
        sbr.append("------------------\n[ ");
        try {
            bd.connect();
            Statement stmt = bd.getStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM vacinas");

            while (rs.next()) {
                sbr.append(rs.getString("nome"));
                sbr.append(" ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bd.disconnect();
        }
        sbr.append("]");
        return sbr.toString();
    }

    // Métodos Auxiliares
    private boolean centroExiste(String centro) throws RemoteException {
        boolean existe = false;
        try {
            bd.connect();
            Statement stmt = bd.getStatement();
            ResultSet rs = stmt.executeQuery("SELECT nome FROM centro where nome='" + centro + "';");
            existe = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bd.disconnect();
        }
        return existe;
    }

    private boolean isVacinadoOuFila(String cc) throws RemoteException {
        boolean result = false;
        try {
            bd.connect();
            Statement stmt = bd.getStatement();

            ResultSet check_fila = stmt.executeQuery("SELECT cc from fila where cc='" + cc + "';");

            boolean isInfila = check_fila.next();

            ResultSet check_vacinados = stmt.executeQuery("SELECT cc from vacinados where cc='" + cc + "';");

            boolean isVacinado = check_vacinados.next();

            if (isInfila || isVacinado) {
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bd.disconnect();
        }
        return result;
    }

    private boolean isVacinado(String codigo, boolean efeito) {
        boolean result = false;
        try {
            bd.connect();
            Statement stmt = bd.getStatement();
            ResultSet check_vacinados;
            if (efeito) {
                check_vacinados = stmt.executeQuery(
                        "SELECT codigo from vacinados where codigo='" + codigo + "' and efeitos_secundarios = true;");
            } else {
                check_vacinados = stmt.executeQuery(
                        "SELECT codigo from vacinados where codigo='" + codigo + "' and efeitos_secundarios = false;");
            }

            result = check_vacinados.next();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bd.disconnect();
        }
        return result;
    }

    private boolean isInFila(String codigo) {
        boolean result = false;
        try {
            bd.connect();
            Statement stmt = bd.getStatement();
            ResultSet check_vacinados = stmt.executeQuery("SELECT codigo from fila where codigo='" + codigo + "';");
            result = check_vacinados.next();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bd.disconnect();
        }
        return result;
    }

    private boolean isVacina(String vacina) {
        boolean result = false;
        try {
            bd.connect();
            Statement stmt = bd.getStatement();
            ResultSet check_vacinados = stmt.executeQuery("SELECT nome from vacinas where nome='" + vacina + "';");
            result = check_vacinados.next();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bd.disconnect();
        }
        return result;
    }

}
