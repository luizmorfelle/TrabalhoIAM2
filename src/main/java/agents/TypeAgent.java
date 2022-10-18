package agents;

import data.GymData;
import model.Gym;

import java.util.List;
import java.util.stream.Collectors;

public class TypeAgent extends FilterAgent {
    @Override
    protected List<Gym> filter(String filter) {
        return GymData.gymData.stream().filter(it -> it.getType().equalsIgnoreCase(filter)).collect(Collectors.toList());
    }
}
