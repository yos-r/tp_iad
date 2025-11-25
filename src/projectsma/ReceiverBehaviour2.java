package projectsma;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.core.behaviours.SimpleBehaviour;

/**
 * ReceiverBehaviour - Version qui envoie une réponse
 * 
 * Quand on reçoit un message, on envoie une réponse.
 */
public class ReceiverBehaviour2 extends SimpleBehaviour {
    
    private static final MessageTemplate mt = 
        MessageTemplate.MatchPerformative(ACLMessage.INFORM);
    
    public ReceiverBehaviour2(Agent agent) {
        super(agent);
    }
    
    public void action() {
        ACLMessage msg = myAgent.receive(mt);
        
        if (msg != null) {
            System.out.println(myAgent.getLocalName() + 
                             ":Received message from " + 
                             msg.getSender().getLocalName());
            System.out.println("Content: " + msg.getContent());
            
            // ---- NOUVEAU: Envoyer une réponse ----
            // createReply() crée un message de réponse
            // Remplit automatiquement:
            //   - receiver = l'émetteur du message original
            //   - inReplyTo = le replyWith du message original
            ACLMessage reply = msg.createReply();
            
            // Marquer comme réponse (c'est automatique avec createReply())
            reply.setPerformative(ACLMessage.INFORM);
            reply.setContent("Hello! I'm fine, thanks for asking!");
            
            // Envoyer la réponse
            myAgent.send(reply);
            
            System.out.println(myAgent.getLocalName() + " sent reply");
        } else {
            this.block();
        }
    }
    
    public boolean done() {
        return false;
    }
}