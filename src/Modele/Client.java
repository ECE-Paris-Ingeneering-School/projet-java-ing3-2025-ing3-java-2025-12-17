package Modele;

public class Client {
        private int clientId;
        private String clientNom;
        private int clientAge;
        private String typeClient;
        private String clientMotDePasse;

        // constructeur
        public Client (int clientId, String clientNom,String clientMotDePasse, int clientAge, String typeClient) {
                this.clientId = clientId;
                this.clientNom = clientNom;
                this.clientAge = clientAge;
                this.typeClient = typeClient;
                this.clientMotDePasse = clientMotDePasse;
        }

        public Client (String clientNom,String clientMotDePasse, int clientAge, String typeClient) {
                this.clientNom = clientNom;
                this.clientAge = clientAge;
                this.typeClient = typeClient;
                this.clientMotDePasse = clientMotDePasse;
        }

        // getters
        public int getClientId() { return clientId; }
        public String getclientNom() { return clientNom; }
        public int getclientAge() { return clientAge; }
        public String gettypeClient() { return typeClient; }
        public String getclientMotDePasse() { return clientMotDePasse; }

}
