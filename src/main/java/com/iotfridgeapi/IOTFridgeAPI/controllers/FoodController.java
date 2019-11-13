package com.iotfridgeapi.IOTFridgeAPI.controllers;

import com.iotfridgeapi.IOTFridgeAPI.models.Food;
import com.iotfridgeapi.IOTFridgeAPI.repositories.FoodRepository;
import com.iotfridgeapi.IOTFridgeAPI.services.GoogleCloudVision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController    // This means that this class is a Controller
@RequestMapping(path="/iotfridge/api/foods") // This means URL's start with /demo (after Application path)
public class FoodController {

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private FoodRepository foodRepository;

    @GetMapping()
    public @ResponseBody
    Iterable<Food> getAllFoods() {
        // This returns a JSON or XML with the users
        return foodRepository.findAll();
    }

    @PostMapping()
    public @ResponseBody String AddFood(@RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        GoogleCloudVision googleCloudVision = new GoogleCloudVision();
        String name = googleCloudVision.AnalizeImage(imageFile.getBytes());
        Food n = new Food();
        n.setName(name);
        n.setLabels("");
        n.setStock(1);
        foodRepository.save(n);

        return "Saved";
    }
}