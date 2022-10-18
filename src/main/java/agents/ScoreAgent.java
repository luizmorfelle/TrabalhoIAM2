package agents;

import data.GymData;
import model.Gym;

import java.util.List;
import java.util.stream.Collectors;

public class ScoreAgent extends FilterAgent {
    @Override
    protected List<Gym> filter(String filter) {
        return GymData.gymData.stream().filter(it -> it.getScore().equalsIgnoreCase(filter)).collect(Collectors.toList());
    }
}
