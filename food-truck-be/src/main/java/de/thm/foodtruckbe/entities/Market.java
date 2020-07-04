package de.thm.foodtruckbe.entities;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import de.thm.foodtruckbe.entities.Dish.Ingredient;

public class Market {

    private Market() {

    }

    /**
     * Generates new Coordinates in the range [0 - 9).
     * 
     * @return an array representing the coordinates of the Market. Intended to be
     *         passed to a Location.
     */
    public static double[] getCoordinates() {
        return new double[] { Math.random() * 10, Math.random() * 10 };
    }

    /**
     * Generates new Coordinates in the range [0 - 1).
     * 
     * @param multiplier exclusive maxium for the range
     * @return an array representing the coordinates of the Market. Intended to be
     *         passed to a Location.
     */
    public static double[] getCoordinates(double multiplier) {
        return new double[] { Math.random() * multiplier, Math.random() * multiplier };
    }

    /**
     * Randomly generates the amount of available ingredients. Generation uses the
     * needed amount of each ingredient as the basis.
     * 
     * @param ingredients the shopping-list of an operator.
     * @return a list with the bought ingredients. Always equal or less than the
     *         needed amount.
     */
    public static Map<Ingredient, Integer> buyIngredients(Map<Ingredient, Integer> ingredients) {
        return ingredients.entrySet().stream().map(e -> {
            // generate a random amount of available ingredients
            // TODO check whether *2 produces reasonable results
            int generated = new Random().nextInt(e.getValue() * 2);
            // buy only as much as needed - not more
            e.setValue(generated > e.getValue() ? e.getValue() : generated);
            return e;
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}