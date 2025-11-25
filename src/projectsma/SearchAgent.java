package projectsma;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import java.util.Iterator;

/**
 * SearchAgent - Agent spécialisé dans la recherche de services
 * 
 * Cet agent recherche les services disponibles auprès du Directory Facilitator (DF)
 * et affiche une liste de tous les agents offrant un service spécifique.
 * 
 * Utilisation:
 *   java jade.Boot -gui boss:projetsma.SearchAgent
 * 
 * Cet agent est utile pour:
 * - Découvrir les capacités disponibles dans le système
 * - Localiser les agents offrant un service
 * - Superviser les services enregistrés
 * 
 * @author TP IAD 2025
 * @version 1.0
 */
public class SearchAgent extends Agent {
    
    // ==================== Constantes ====================
    
    /**
     * Le type de service que cet agent cherche.
     * On cherche les agents offrant le service "construction".
     * Cette valeur peut être modifiée pour chercher d'autres services.
     */
    private static final String TARGET_SERVICE = "construction";
    
    
    // ==================== Méthode setup() ====================
    
    /**
     * setup() - Point d'entrée du cycle de vie du SearchAgent
     * 
     * Appelée automatiquement au démarrage de l'agent.
     * Affiche un message d'initialisation, puis lance la recherche.
     */
    protected void setup() {
        // Afficher le message de démarrage
        // getLocalName() retourne le nom de l'agent (ex: "boss")
        System.out.println("Hello. I am " + this.getLocalName() + ".");

        // Attendre que les autres agents se registrent auprès du DF
        // 2000ms = 2 secondes, ce qui laisse le temps aux ProjectAgents de démarrer
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Lancer la recherche des services
        // Cette méthode interroge le DF et affiche les résultats
        this.searchAgents();
    }
    
    
    // ==================== Méthode searchAgents() ====================
    
    /**
     * searchAgents() - Recherche les agents offrant un service spécifique
     * 
     * Processus:
     * 1. Créer une DFAgentDescription pour spécifier les critères de recherche
     * 2. Créer une ServiceDescription avec le type de service recherché
     * 3. Interroger le DF avec DFService.search()
     * 4. Parcourir les résultats et afficher les informations
     * 5. Gérer les erreurs en cas d'échec
     */
    private void searchAgents() {
        try {
            // ---- Étape 1: Préparer les critères de recherche ----
            // DFAgentDescription est utilisée à la fois pour:
            // - Enregistrer un agent (avec ses services)
            // - Chercher des agents (avec les critères)
            DFAgentDescription dfd = new DFAgentDescription();
            
            // ---- Étape 2: Spécifier le service recherché ----
            // Créer une description du service qu'on cherche
            ServiceDescription sd = new ServiceDescription();
            
            // setType() - Le type de service recherché
            // On cherche tous les agents offrant le service "construction"
            sd.setType(TARGET_SERVICE);
            
            // Ajouter le critère à la description
            dfd.addServices(sd);
            
            // ---- Étape 3: Interroger le DF ----
            // DFService.search() retourne un tableau contenant tous les agents
            // qui offrent un service correspondant aux critères
            // Paramètres:
            //   this - l'agent qui effectue la recherche
            //   dfd - la description avec les critères
            DFAgentDescription[] result = DFService.search(this, dfd);
            
            // ---- Étape 4: Traiter les résultats ----
            // Le tableau result contient les agents trouvés
            // Si aucun agent n'offre le service, result.length == 0
            
            // Afficher le nombre d'agents trouvés
            System.out.println("[RECHERCHE] " + this.getLocalName() + 
                             " found " + result.length + 
                             " agents providing '" + TARGET_SERVICE + "' service");
            
            // ---- Étape 5: Parcourir et afficher chaque résultat ----
            // Boucle classique for
            for (int i = 0; i < result.length; i++) {
                // Construire la ligne à afficher
                String out = result[i].getName() + " provides";
                
                // getName() retourne l'AID (Agent IDentifier)
                // Ex: "agent1@ma-plateforme"
                
                // Obtenir un itérateur sur tous les services de cet agent
                // getAllServices() retourne un Iterator sur les ServiceDescription
                Iterator iter = result[i].getAllServices();
                
                // Parcourir tous les services offerts par cet agent
                while (iter.hasNext()) {
                    // Récupérer la description du service
                    ServiceDescription sd_result = (ServiceDescription) iter.next();
                    
                    // Ajouter le nom du service à la chaîne
                    out += " " + sd_result.getName();
                    
                    // Remarque: un agent peut offrir plusieurs services
                    // Cette boucle affichera tous les services
                }
                
                // Afficher le résultat pour cet agent
                System.out.println(this.getLocalName() + ": " + out);
            }
            
            // Message de fin de recherche
            System.out.println("[OK] " + this.getLocalName() + 
                             " search completed successfully");
            
        } catch (Exception fe) {
            // ---- Gestion des erreurs ----
            // Une exception peut être levée si:
            // - Le DF n'est pas accessible
            // - Il y a un problème réseau
            // - La recherche est mal formée
            
            System.err.println("[ERREUR] " + getLocalName() + 
                             " search with DF unsucceeded - " + 
                             fe.getMessage());
            
            // Afficher la stack trace pour le débogage
            fe.printStackTrace();
            
            // Arrêter l'agent en cas d'erreur critique
            doDelete();
        }
    }
    
    
    // ==================== Méthode takeDown() ====================
    
    /**
     * takeDown() - Appelée quand l'agent s'arrête
     * 
     * Le SearchAgent ne s'enregistre pas au DF et n'a pas de ressources spéciales,
     * donc on peut ne pas redéfinir cette méthode.
     * Elle est incluse à titre d'exemple.
     */
    protected void takeDown() {
        System.out.println("[OK] " + getLocalName() + " is shutting down");
    }
    
}
// Fin de la classe SearchAgent