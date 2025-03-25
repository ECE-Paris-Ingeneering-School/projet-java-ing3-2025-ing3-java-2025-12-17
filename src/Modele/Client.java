package Modele;

public class Client {
        private int clientId;
        private String clientNom;
        private int clientAge;
        private String typeClient;

        // constructeur
        public Client (int clientId, String clientNom, int clientAge, String typeClient) {
                this.clientId = clientId;
                this.clientNom = clientNom;
                this.clientAge = clientAge;
                this.typeClient = typeClient;

        }

        // getters
        public int getClientId() { return clientId; }
        public String getclientNom() { return clientNom; }
        public int getclientAge() { return clientAge; }
        public String gettypeClient() { return typeClient; }

}
