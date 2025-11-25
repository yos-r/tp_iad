package projectsma;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

/**
 * SenderAgent - Agent qui envoie un message simple
 * 
 * Au démarrage, cet agent envoie un message INFORM
 * au ReceiverAgent avec le contenu "Hello! How are you?"
 * 
 * Utilisation:
 *   java jade.Boot sender:projetsma.SenderAgent
 *   (doit y avoir un ReceiverAgent lancé)
 */
public class SenderAgent extends Agent {
    
    protected void setup() {
        System.out.println("Hello. My name is " + this.getLocalName());
        
        // Envoyer un message au démarrage
        sendMessage();
    }
    
    /**
     * sendMessage() - Envoie un message au ReceiverAgent
     * 
     * Processus:
     * 1. Créer un ACLMessage de type INFORM
     * 2. Spécifier le destinataire
     * 3. Ajouter le contenu
     * 4. Envoyer avec send()
     */
    private void sendMessage() {
        // ---- Étape 1: Créer le message ----
        // ACLMessage.INFORM signifie "je t'informe de..."
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        
        // ---- Étape 2: Ajouter le destinataire ----
        // AID.ISLOCALNAME = rechercher l'agent sur la même plateforme
        // "Receiver" = le nom de l'agent ReceiverAgent
        msg.addReceiver(new AID("Receiver", AID.ISLOCALNAME));
        
        // ---- Étape 3: Ajouter le contenu ----
        // C'est le texte qu'on envoie
        msg.setContent("Hello! How are you?");
        
        // ---- Étape 4: Envoyer ----
        // send() envoie le message via l'ACC
        send(msg);
        
        System.out.println(this.getLocalName() + " sent message to Receiver");
    }
}