package controller;

import model.PetAllergenModel;
import view.PetAllergenView;

import java.util.ArrayList;
import java.util.List;

public class PetAllergenController {

    private final PetAllergenModel model;
    private final PetAllergenView view;

    public PetAllergenController(PetAllergenView view, PetAllergenModel model) {
        this.view = view;
        this.model = model;
    }

    public void showPetRecord(int pet_id) {

        List<String[]> data = model.getPetAllergyRecord(pet_id);

        if (data.isEmpty()) {
            view.ifEmptyRecord("No data found for this pet.");
            return;
        }

        // extract basic info from first row
        String[] first = data.get(0);

        String petName = first[0];
        String species = first[1];
        String gender = first[2];
        String age = first[3];
        String weight = first[4];

        // extract allergens + unsafe foods
        List<String> allergens = new ArrayList<>();
        List<String> unsafeFoods = new ArrayList<>();

        for (String[] row : data) {
            if (row[5] != null && !allergens.contains(row[5]))
                allergens.add(row[5]);

            if (row[6] != null && !unsafeFoods.contains(row[6]))
                unsafeFoods.add(row[6]);
        }

        view.showRecord(
                petName,
                species,
                gender,
                age,
                weight,
                allergens,
                unsafeFoods
        );
    }
}
