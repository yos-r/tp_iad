package projectsma;

import jade.core.Agent;
import jade.domain.FIPAException;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;

/**
 * ProjectAgent - Agent de base qui offre un service
 * 
 * Cet agent représente une entité dans le système qui offre un service spécifique.
 * Lors de son démarrage, il s'enregistre auprès du Directory Facilitator (DF)
 * pour que les autres agents puissent le découvrir.
 * 
 * Utilisation:
 *   java jade.Boot -gui agent1:projetsma.ProjectAgent(construction)
 * 
 * Où "construction" est le type de service offert par cet agent.
 * 
 * @author TP IAD 2025
 * @version 1.0
 */
public class ProjectAgent extends Agent {
    
    // ==================== Attributs ====================
    
    /**
     * Type de service offert par cet agent.
     * Peut être: construction, blanchissement, découpe, etc.
     */
    private String service;
    
    
    // ==================== Méthode setup() ====================
    
    /**
     * setup() - Point d'entrée du cycle de vie de l'agent
     * 
     * Cette méthode est appelée automatiquement au démarrage de l'agent.
     * Elle effectue les tâches d'initialisation suivantes:
     * 1. Récupère le paramètre du service
     * 2. Affiche un message de bienvenue
     * 3. Enregistre le service auprès du DF
     */
    protected void setup() {
        // ---- Étape 1: Récupérer les paramètres ----
        // getArguments() retourne un tableau contenant tous les paramètres
        // passés lors du lancement de l'agent
        Object[] args = getArguments();
        
        // Vérifier que des paramètres ont été fournis
        if (args != null && args.length > 0) {
            // Récupérer le premier paramètre (type de service)
            service = (String) args[0];
        } else {
            // Si aucun paramètre, utiliser une valeur par défaut
            service = "defaultService";
            System.out.println("[AVERTISSEMENT] Aucun service spécifié, utilisation de: " + service);
        }
        
        // ---- Étape 2: Afficher le message de démarrage ----
        // getLocalName() retourne le nom de l'agent (ex: "agent1")
        // this.getLocalName() est équivalent à getLocalName()
        System.out.println("Hello. My name is " + this.getLocalName() + 
                         " and I provide " + service + " service.");
        
        // ---- Étape 3: Enregistrer le service ----
        registerService();
    }
    
    
    // ==================== Méthode registerService() ====================
    
    /**
     * registerService() - Enregistre le service de cet agent auprès du DF
     * 
     * Le Directory Facilitator (DF) est un agent système qui maintient un annuaire
     * de tous les services offerts par les agents de la plateforme.
     * 
     * Processus:
     * 1. Créer une DFAgentDescription pour décrire cet agent
     * 2. Créer une ServiceDescription pour décrire le service
     * 3. Ajouter la description du service à celle de l'agent
     * 4. Envoyer l'enregistrement au DF
     * 5. Gérer les erreurs en cas d'échec
     */
    private void registerService() {
        try {
            // ---- Création de la description de l'agent ----
            // DFAgentDescription contient les informations sur l'agent
            DFAgentDescription dfd = new DFAgentDescription();
            
            // Définir le nom unique de cet agent
            // getAID() retourne l'Agent Identifier, identifiant unique
            // Ex: "agent1@ma-plateforme"
            dfd.setName(this.getAID());
            
            // ---- Création de la description du service ----
            // ServiceDescription décrit un service spécifique
            ServiceDescription sd = new ServiceDescription();
            
            // setType() - Le type de service (catégorie générale)
            // Tous les agents offrant "construction" auront le même type
            sd.setType(service);
            
            // setName() - Le nom unique du service
            // Peut être identique au type ou différent selon le besoin
            sd.setName(service);
            
            // Note: On pourrait ajouter d'autres propriétés:
            // sd.addProperties(new Property("prix", "50€"));
            
            // ---- Ajouter le service à la description de l'agent ----
            // Un agent peut offrir plusieurs services
            // Dans cet exemple, on n'en offre qu'un
            dfd.addServices(sd);
            
            // ---- Enregistrer auprès du DF ----
            // DFService.register() envoie la description au DF
            // Après cet appel, les autres agents peuvent découvrir ce service
            DFService.register(this, dfd);
            
            // Message de confirmation
            System.out.println("[OK] " + getLocalName() + 
                             " registered with DF. Service type: " + service);
            
        } catch (FIPAException e) {
            // ---- Gestion des erreurs ----
            // Une exception FIPAException peut être levée si:
            // - Le DF n'est pas accessible
            // - Il y a un problème réseau
            // - L'enregistrement est mal formé
            
            System.err.println("[ERREUR] " + getLocalName() + 
                             " registration with DF unsucceeded. Reason: " + 
                             e.getMessage());
            
            // Si l'enregistrement échoue, l'agent n'a pas d'utilité
            // On le supprime donc avec doDelete()
            doDelete();
        }
    }
    
    
    // ==================== Méthode takeDown() (Optionnel) ====================
    
    /**
     * takeDown() - Appelée quand l'agent s'arrête
     * 
     * C'est le meilleur endroit pour:
     * - Désenregistrer le service du DF
     * - Fermer les connexions
     * - Nettoyer les ressources
     * 
     * Cette méthode est optionnelle.
     */
    protected void takeDown() {
        try {
            // Désenregistrer le service auprès du DF
            DFService.deregister(this);
            System.out.println("[OK] " + getLocalName() + " unregistered from DF");
        } catch (FIPAException e) {
            System.err.println("[ERREUR] " + getLocalName() + 
                             " unregistration failed: " + e.getMessage());
        }
    }
    
}
// Fin de la classe ProjectAgent