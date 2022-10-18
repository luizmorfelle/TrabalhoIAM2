import jade.Boot;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(new Locale("pt", "BR"));

        String[] arguments = {
//                "-gui",
//                "-name",
//                "plataforma",
                "-agents",
                "GUI:agents.GUIAgent;"+
//                "-agents",
                "Search:agents.SearchAgent;" +
                "Distance:agents.DistanceAgent;" +
                "Filter:agents.FilterAgent;" +
                "Score:agents.ScoreAgent;" +
                "Type:agents.TypeAgent;" +
                "Price:agents.PriceAgent;",

        };
        Boot.main(arguments);
    }
}