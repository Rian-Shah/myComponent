
import components.map.Map;
import components.map.Map1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;

public final class proofOfConcept {
    public static void main(String[] args) {
        // tracks data from one movement over multiple days
        BenchTracker bench = new BenchTracker();
        // sequence where the value of index = 0 is weight in pounds
        // each index after is a set, the value is the number of reps
        Sequence<Integer> reps1 = new Sequence1L();
        reps1.add(0, 5);
        reps1.add(1, 4);
        reps1.add(2, 3);

        bench.addLift("2/20/2025", 155.0, reps1);

        Sequence<Integer> reps2 = new Sequence1L();

        reps2.add(0, 3);
        reps2.add(1, 2);
        reps2.add(2, 2);

        bench.addLift("2/21/2025", 165.0, reps2);

        bench.displayInfo();

        bench.getORM();
    }
}

class BenchTracker {
    public Map<String, Lift> lifts;

    public BenchTracker() {

        this.lifts = new Map1L<String, Lift>();
    }

    public void addLift(String date, double weight, Sequence<Integer> sets) {
        this.lifts.add(date, new Lift(weight, sets));
    }

    public void displayInfo() {
        System.out.println("Bench Press");
        System.out.println("--------------");

        for (Map.Pair<String, Lift> entry : this.lifts) {
            String date = entry.key();
            Lift lift = entry.value();

            System.out.println("Date: " + date);
            System.out.println("Weight: " + lift.getWeight() + " lbs");
            System.out.println("Sets: " + lift.getSets());
            System.out.println();
        }
    }

    /**
     * Find best set and estimate one rep max
     *
     * Using Matt Brzycki's formula for now weight divided by ( 1.0278 – 0.0278
     * × reps )
     */
    public void getORM() {
        double bestORM = 0.0;

        for (Map.Pair<String, Lift> entry : this.lifts) {
            String date = entry.key();
            Lift lift = entry.value();

            Double weight = lift.getWeight();
            Sequence<Integer> set = lift.getSets();

            for (int i = 0; i < set.length(); i++) {
                double guessORM = weight / (1.0278 - (set.entry(i) * .0278));
                if (guessORM > bestORM) {
                    bestORM = guessORM;
                }
            }
        }

        System.out
                .println("Yout estimated one rep max is: " + bestORM + "lbs.");
    }
}

class Lift {
    private Sequence<Integer> sets;
    private double weight;

    public Lift(double weight, Sequence<Integer> sets) {
        this.sets = sets;
        this.weight = weight;
    }

    public Sequence<Integer> getSets() {
        return this.sets;
    }

    public double getWeight() {
        return this.weight;
    }
}