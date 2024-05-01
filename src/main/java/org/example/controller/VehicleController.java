package org.example.controller;

import org.example.dto.VehicleDto;
import org.example.model.Vehicle;
import org.example.service.UserService;
import org.example.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/all")
    public ResponseEntity<Collection<VehicleDto>> getVehicles() {
        Collection<VehicleDto> vehicles = vehicleService.getVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("{plate}")
    public ResponseEntity<VehicleDto> getVehicle(@PathVariable String plate){
        VehicleDto vehicleDto = vehicleService.getVehicle(plate);
        if(vehicleDto != null) {
            return ResponseEntity.ok(vehicleDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> addVehicle(@RequestBody Vehicle vehicle) {
        boolean success = vehicleService.addVehicle(vehicle);
        if (success) {
            return ResponseEntity.ok("Vehicle added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occured");
        }
    }

    @DeleteMapping("{plate}")
    public ResponseEntity<?> removeVehicle(@PathVariable String plate){
        String result = vehicleService.removeVehicle(plate);
        switch (result){
            case "removed":
                return ResponseEntity.ok().body(result);
            case "user is not null":
                return ResponseEntity.badRequest().body(result);
            case "not found":
                return ResponseEntity.notFound().build();
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occured");
        }
    }

}
