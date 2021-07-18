package pt.uevora.ServicoDeVacinas;

public class Servidor {

    public static void main(String args[]) {

        if (args.length != 2) {
            System.out.println(args.length);
            System.out.println("Usage: java ...YOUR IPv4 registryPort");
            System.exit(1);
        }

        try {
            // binder port
            int regPort = Integer.parseInt(args[1]);
            // "resolve" o problema nos sistemas *nix sem ter de alterar o ficheiro
            // /etc/hosts
            // https://docs.oracle.com/javase/8/docs/technotes/guides/rmi/faq.html#domain
            System.setProperty("java.rmi.server.hostname", args[0]);
            Servico obj = new ServicoImp();

            java.rmi.registry.LocateRegistry.createRegistry(regPort);

            java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry(regPort);

            registry.rebind("servico", obj);

            System.out.println("RMI object bound to service in registry");

            System.out.println("servidor pronto");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
