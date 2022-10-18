package agents;

import data.GymData;
import model.Gym;

import java.util.List;
import java.util.stream.Collectors;

public class DistanceAgent extends FilterAgent {
    @Override
    protected List<Gym> filter(String filter) {
        String[] split = filter.split(";");
        return GymData.gymData.stream().filter(it -> Double.parseDouble(it.getDistance()) >= Double.parseDouble(split[0]) &&  Double.parseDouble(it.getDistance()) <= Double.parseDouble(split[1])).collect(Collectors.toList());
    }
}
