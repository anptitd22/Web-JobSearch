package com.project.webIT.controllers;

import com.project.webIT.models.District;
import com.project.webIT.models.Province;
import com.project.webIT.response.locations.ProvinceResponse;
import com.project.webIT.services.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/locations")
public class LocationController {
    private final LocationService locationService;

    @GetMapping("provinces")
    public ResponseEntity<?> getProvinceList(){
        try{
            return ResponseEntity.ok().body(locationService.getProvinceList()
                            .stream().map(ProvinceResponse::fromProvince)
                            .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("provinces/{id}")
    public ResponseEntity<?> getProvince(
            @Valid @PathVariable("id") Long id
    ) {
        try{
            return ResponseEntity.ok().body(locationService.getProvinceById(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
