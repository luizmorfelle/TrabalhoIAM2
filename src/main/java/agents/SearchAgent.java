package agents;

import com.google.gson.Gson;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.Gym;
import model.Request;

import java.util.*;

public class SearchAgent extends Agent {

    int contListsToReceive = 0;
    Set<Gym> itensToSendFront = new HashSet<>();

    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("Agente de Consulta Geral iniciado;");
            }
        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate MT = MessageTemplate.MatchPerformative(ACLMessage.INFORM);

                ACLMessage receive = receive(MT);
                try {
                    if (receive == null || receive.getContentObject() == null) block();
                    else {
                        System.out.println("Recebi uma mensagem do " + receive.getSender().getLocalName());


                        Request request = (Request) receive.getContentObject();


                        if (!request.getPrice().equalsIgnoreCase("")) {
                            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                            msg.setSender(getAID());
                            msg.addReceiver(new AID("Price", AID.ISLOCALNAME));
                            msg.setContent(request.getPrice());
                            send(msg);
                            contListsToReceive++;
                        }

                        if (!request.getScore().equalsIgnoreCase("")) {
                            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                            msg.setSender(getAID());
                            msg.addReceiver(new AID("Score", AID.ISLOCALNAME));
                            msg.setContent(request.getScore());
                            send(msg);
                            contListsToReceive++;
                        }

                        if (!request.getType().equalsIgnoreCase("")) {
                            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                            msg.setSender(getAID());
                            msg.addReceiver(new AID("Type", AID.ISLOCALNAME));
                            msg.setContent(request.getType());
                            send(msg);
                            contListsToReceive++;
                        }

                        if (request.getDistanceFrom() != 0 || request.getDistanceTo() != 0) {
                            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                            msg.setSender(getAID());
                            msg.addReceiver(new AID("Distance", AID.ISLOCALNAME));
                            msg.setContent(request.getDistanceFrom() + ";" + request.getDistanceTo());
                            send(msg);
                            contListsToReceive++;
                        }

                    }
                } catch (UnreadableException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        addBehaviour(new CyclicBehaviour() {
            int contInterno = 0;

            @Override
            public void action() {
                MessageTemplate MT = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

                ACLMessage receive = receive(MT);

                if (receive == null) block();
                else {
                    Gym[] gyms = new Gson().fromJson(receive.getContent(), Gym[].class);

                    System.out.println(receive.getSender().getLocalName() + " enviou " + gyms.length + " itens!");

                    for (Gym gym : gyms) {
                        System.out.println(gym);
                    }

                    System.out.println();

                    if (contInterno == 0)  {
                        contInterno = contListsToReceive - 1;
                        itensToSendFront.addAll(Arrays.asList(gyms));
                    } else {
                        itensToSendFront.removeIf(it -> Arrays.stream(gyms).noneMatch(eb -> eb.getId() == it.getId()));
                        contInterno--;
                    }

                    if (contInterno == 0) {
                        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                        msg.setSender(getAID());
                        msg.addReceiver(new AID("GUI", AID.ISLOCALNAME));
                        msg.setContent(new Gson().toJson(itensToSendFront));
                        System.out.println(itensToSendFront.size() + " enviados");
                        itensToSendFront.clear();
                        send(msg);
                    }
                }

            }
        });
    }
}
