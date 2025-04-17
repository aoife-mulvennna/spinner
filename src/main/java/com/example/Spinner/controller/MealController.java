package com.example.spinner.controller;

import com.example.spinner.model.Meal;
import com.example.spinner.repository.MealRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
@CrossOrigin(origins = "http://localhost:5173") // or whatever port React uses
public class MealController {

    private final MealRepository mealRepo;

    public MealController(MealRepository mealRepo) {
        this.mealRepo = mealRepo;
    }

    @GetMapping
    public List<Meal> getAllMeals() {
        return mealRepo.findAll();
    }

    @PostMapping
    public Meal createMeal(@RequestBody Meal meal) {
        return mealRepo.save(meal);
    }

    @PutMapping("/{id}")
    public Meal updateMeal(@PathVariable Long id, @RequestBody Meal updatedMeal) {
        return mealRepo.findById(id)
                .map(meal -> {
                    meal.setName(updatedMeal.getName());
                    meal.setCategory(updatedMeal.getCategory());
                    meal.setFavorite(updatedMeal.isFavorite());
                    meal.setEnabled(updatedMeal.isEnabled());
                    return mealRepo.save(meal);
                })
                .orElseThrow(() -> new RuntimeException("Meal not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteMeal(@PathVariable Long id) {
        mealRepo.deleteById(id);
    }
}

