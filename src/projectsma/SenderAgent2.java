package projectsma;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

/**
 * SenderAgent - Version avec attente de réponse
 * 
 * Cet agent:
 * 1. Envoie un message au ReceiverAgent
 * 2. Attend la réponse
 * 3. Affiche la réponse
 */
public class SenderAgent2 extends Agent {
    
    protected void setup() {
        System.out.println("Hello. My name is " + this.getLocalName());
        
        // Envoyer un message et attendre la réponse
        sendMessageAndWaitReply();
    }
    
    /**
     * sendMessageAndWaitReply() - Envoie un message et attend la réponse
     * 
     * Processus:
     * 1. Créer le message
     * 2. Ajouter un ID unique (replyWith)
     * 3. Envoyer
     * 4. Attendre la réponse avec blockingReceive()
     * 5. Afficher la réponse
     */
    private void sendMessageAndWaitReply() {
        // ---- Étape 1-3: Créer et envoyer le message ----
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID("Receiver", AID.ISLOCALNAME));
        msg.setContent("Hello! How are you?");
        
        // ---- ID unique pour associer la réponse ----
        // replyWith() marque le message avec un ID
        // Quand on reçoit une réponse, elle aura inReplyTo = cet ID
        String msgId = "msg-" + System.currentTimeMillis();
        msg.setReplyWith(msgId);
        
        send(msg);
        System.out.println(this.getLocalName() + " sent message, waiting for reply...");
        
        // ---- Étape 4: Attendre la réponse ----
        // blockingReceive() attend n'importe quel message
        // On aurait pu utiliser replyReceived() (méthode de Agent)
        // mais blockingReceive() est plus simple pour l'exemple
        ACLMessage reply = blockingReceive();
        
        // ---- Étape 5: Traiter la réponse ----
        if (reply != null) {
            System.out.println(this.getLocalName() + 
                             " received reply: " + reply.getContent());
        }
    }
}