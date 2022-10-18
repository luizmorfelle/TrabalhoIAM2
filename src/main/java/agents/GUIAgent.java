package agents;

import com.google.gson.Gson;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import model.Gym;
import model.GymTypes;
import model.Request;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class GUIAgent extends Agent {
    private JFrame frame;

    private JButton btnFind;
    private JComboBox<GymTypes> cbType;
    private JComboBox<String> cbScore;
    private JComboBox<String> cbPrice;
    private JLabel lblType;
    private JLabel lblDistance;
    private JLabel lblPrice;
    private JLabel lblScore;
    private JLabel lblTo2;
    private JPanel paneResult;
    private JPanel paneFilter;
    private JScrollPane scroll;
    private JTextField txtDistanceFrom;
    private JTextField txtDistanceTo;

    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                         IllegalAccessException e) {
                }
                frame = new JFrame();
                frame.setSize(new Dimension(355, 500));
                frame.setTitle("GYM TIME");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.getContentPane().setLayout(null);

                createInterface();

                frame.setVisible(true);
            }
        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receive = receive();
                if (receive == null) block();
                else {
                    Gym[] gyms = new Gson().fromJson(receive.getContent(), Gym[].class);
                    for (Gym gym : gyms) {
                        JLabel label = new JLabel(gym.toString());
                        scroll.add(label);
                    }
                }
            }
        });
    }


    private void createInterface() {

        btnFind = new JButton();
        lblType = new JLabel();
        lblDistance = new JLabel();
        lblPrice = new JLabel();
        lblScore = new JLabel();
        lblTo2 = new JLabel();
        cbType = new JComboBox<GymTypes>();
        cbScore = new JComboBox<>();
        cbPrice = new JComboBox<>();
        txtDistanceFrom = new JTextField();
        txtDistanceTo = new JTextField();
        scroll = new JScrollPane();
        paneResult = new JPanel();
        paneFilter = new JPanel();
        Container framePane = frame.getContentPane();

        // pane filtros
        paneFilter.setBorder(new TitledBorder("Busque a academia perfeita para voce!"));
        paneFilter.setLayout(null);
        paneFilter.setBounds(10, 10, 330, 135);

        // comida
        lblType.setText("Estilo de Academia");
        lblType.setBounds(10, 20, 150, 15);
        paneFilter.add(lblType);

        cbType.setModel(new DefaultComboBoxModel<>(GymTypes.values()));

        cbType.setBounds(10, 40, 150, 25);
        paneFilter.add(cbType);

        // cassificação
        lblScore.setText("Classificacao");
        lblScore.setBounds(170, 20, 150, 15);
        paneFilter.add(lblScore);

        cbScore.setModel(new DefaultComboBoxModel<>(new String[]{"+++++", "++++", "+++", "++", "+"}));
        cbScore.setBounds(170, 40, 150, 25);
        paneFilter.add(cbScore);

        // price
        lblPrice.setText("Preco");
        lblPrice.setBounds(10, 70, 150, 15);
        paneFilter.add(lblPrice);

        cbPrice.setModel(new DefaultComboBoxModel<>(new String[]{"$$$", "$$", "$"}));
        cbPrice.setBounds(10, 90, 150, 25);
        paneFilter.add(cbPrice);

        // distance
        lblDistance.setText("Distancia");
        lblDistance.setBounds(170, 70, 150, 15);
        paneFilter.add(lblDistance);

        txtDistanceFrom.setBounds(170, 90, 60, 25);
        txtDistanceFrom.setText("50");
        paneFilter.add(txtDistanceFrom);

        lblTo2.setText("Ate");
        lblTo2.setBounds(235, 90, 30, 25);
        paneFilter.add(lblTo2);

        txtDistanceTo.setText("150");
        txtDistanceTo.setBounds(260, 90, 60, 25);
        paneFilter.add(txtDistanceTo);

        framePane.add(paneFilter);

        // botão procurar
        btnFind.setText("Procurar");
        btnFind.addActionListener(this::btnSendActionPerformed);
        btnFind.setBounds(10, 155, 330, 25);
        framePane.add(btnFind);

        // pane resultado
        paneResult.setLayout(new BoxLayout(paneResult, BoxLayout.Y_AXIS));
        scroll.setViewportView(paneResult);
        scroll.setBounds(10, 190, 330, 270);
        framePane.add(scroll);

    }

    private void btnSendActionPerformed(ActionEvent evt) {

        String type = cbType.getSelectedItem().toString();
        String score = cbScore.getSelectedItem().toString();
        String price = cbPrice.getSelectedItem().toString();

        float distanceFrom;
        try {
            distanceFrom = Float.parseFloat(txtDistanceFrom.getText());
        } catch (NumberFormatException e) {
            distanceFrom = 0;
        }

        float distanceTo;
        try {
            distanceTo = Float.parseFloat(txtDistanceTo.getText());
        } catch (NumberFormatException e) {
            distanceTo = 0;
        }

        try {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setSender(getAID());
            msg.addReceiver(new AID("Search", AID.ISLOCALNAME));
            msg.setContentObject(new Request(type, price, distanceFrom, distanceTo, score));
            send(msg);
        } catch (IOException e) {
            System.out.println("Erro ao enviar mensagem: Usuário -> GuiAgent");
        }
    }
}
