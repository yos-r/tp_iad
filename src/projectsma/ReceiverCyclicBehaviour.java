package projectsma;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.core.behaviours.CyclicBehaviour;

/**
 * ReceiverCyclicBehaviour - Comportement cyclique
 * 
 * CyclicBehaviour s'exécute indéfiniment en boucle.
 * Parfait pour servir des requêtes de façon continue.
 * 
 * Différence avec SimpleBehaviour:
 * - SimpleBehaviour: done() détermine l'arrêt
 * - CyclicBehaviour: Tourne indéfiniment
 */
public class ReceiverCyclicBehaviour extends CyclicBehaviour {
    
    private static final MessageTemplate mt = 
        MessageTemplate.MatchPerformative(ACLMessage.INFORM);
    
    /**
     * action() - S'exécute EN BOUCLE infiniment
     * 
     * Pas de done() à implémenter (contrairement à SimpleBehaviour)
     */
    public void action() {
        // ---- Recevoir un message ----
        ACLMessage msg = myAgent.receive(mt);
        
        if (msg != null) {
            // Message reçu
            System.out.println(myAgent.getLocalName() + 
                             ": Received message from " + 
                             msg.getSender().getLocalName());
            System.out.println("Content: " + msg.getContent());
            
            // ---- Répondre ----
            ACLMessage reply = msg.createReply();
            reply.setPerformative(ACLMessage.INFORM);
            reply.setContent("Thank you for your message!");
            myAgent.send(reply);
            
            System.out.println(myAgent.getLocalName() + " sent reply\n");
        } else {
            // Pas de message
            this.block();  // Libérer les ressources
        }
    }
}