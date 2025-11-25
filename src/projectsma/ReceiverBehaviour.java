package projectsma;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.core.behaviours.SimpleBehaviour;

/**
 * ReceiverBehaviour - Comportement de réception non-bloquante
 * 
 * Ce comportement:
 * - S'exécute régulièrement
 * - Vérifie s'il y a un message INFORM
 * - Si oui: traite le message
 * - Si non: se bloque pour ne pas consommer de CPU
 * 
 * Avantage: L'agent peut faire d'autres choses
 * Inconvénient: Code plus complexe
 */
public class ReceiverBehaviour extends SimpleBehaviour {
    
    /**
     * MessageTemplate: Filtre pour recevoir seulement les INFORM
     * 
     * Sans ce filtre, on reçoit tous les messages.
     * Avec ce filtre, on reçoit seulement les INFORM.
     */
    private static final MessageTemplate mt = 
        MessageTemplate.MatchPerformative(ACLMessage.INFORM);
    
    /**
     * Constructeur
     * 
     * @param agent L'agent parent
     */
    public ReceiverBehaviour(Agent agent) {
        super(agent);
    }
    
    /**
     * action() - Appelée régulièrement par JADE
     * 
     * C'est la méthode principale du comportement.
     * Elle s'exécute en boucle jusqu'à ce que done() retourne true.
     */
    public void action() {
        // ---- Étape 1: Essayer de recevoir un message ----
        // receive(mt) ne bloque PAS
        // Retourne le message s'il y en a un, null sinon
        ACLMessage msg = myAgent.receive(mt);
        
        // ---- Étape 2: Si un message a été reçu ----
        if (msg != null) {
            // Afficher le message
            System.out.println(myAgent.getLocalName() + 
                             ":Received message.\n" + 
                             msg.toString());
        } else {
            // ---- Étape 3: Si pas de message ----
            // this.block() met en pause le comportement
            // Évite de consommer du CPU en boucle
            this.block();
        }
    }
    
    /**
     * done() - Indique si le comportement doit continuer
     * 
     * Retourne false = continuer indéfiniment
     * Retourne true = arrêter le comportement
     */
    public boolean done() {
        return false;  // Continuer indéfiniment
    }
}