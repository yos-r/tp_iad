package projectsma;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

/**
 * ReceiverAgent - Agent qui reçoit les messages (version bloquante)
 * 
 * Cet agent utilise blockingReceive() qui arrête tout
 * jusqu'à la réception d'un message.
 * 
 * Avantage: Simple, code clair
 * Inconvénient: L'agent ne peut rien faire d'autre en attendant
 * 
 * Utilisation:
 *   java jade.Boot receiver:projetsma.ReceiverAgent
 */
public class ReceiverAgent extends Agent {
    
    // protected void setup() {
    //     System.out.println("Hello. My name is " + this.getLocalName());
    //     System.out.println("Waiting for message ...");
        
    //     // ---- Réception bloquante ----
    //     // blockingReceive() arrête l'agent jusqu'à réception d'un message
    //     ACLMessage msg = blockingReceive();
        
    //     // ---- Traitement du message ----
    //     // Ce code n'est exécuté que quand un message arrive
    //     System.out.println("Received message: " + msg.toString());
        
    //     // Afficher le contenu du message
    //     String content = msg.getContent();
    //     System.out.println("Content: " + content);
    // }

    protected void setup() {
        System.out.println("Hello. My name is " + this.getLocalName());
        
        // ---- Ajouter le comportement de réception ----
        // addBehaviour() ajoute un comportement à l'agent
        // Le comportement s'exécute en parallèle avec setup()
        addBehaviour(new ReceiverBehaviour(this));
        
        System.out.println(this.getLocalName() + " is ready to receive messages");
    }
}