package agents;

import com.google.gson.Gson;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import model.Gym;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class FilterAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("Agente " + getLocalName() + " iniciado!");
            }
        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receive = receive();
                if (receive == null) block();
                else {
                    System.out.println(getLocalName() + ":Recebi uma mensagem do " + receive.getSender().getLocalName());
                    ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                    msg.setSender(getAID());
                    msg.addReceiver(new AID("Search", AID.ISLOCALNAME));

                    msg.setContent(new Gson().toJson(filter(receive.getContent())));
                    send(msg);

                }
            }
        });
    }

    protected List<Gym> filter(String filter) {
        return null;
    }
}
