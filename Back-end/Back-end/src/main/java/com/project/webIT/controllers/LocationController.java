package com.project.webIT.controllers;

import com.project.webIT.models.Province;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.dtos.response.ProvinceResponse;
import com.project.webIT.services.LocationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/locations")
public class LocationController {
    private final LocationServiceImpl locationServiceImpl;

    @GetMapping("provinces")
    public ResponseEntity<ObjectResponse<List<ProvinceResponse>>> getProvinceList() {
        List<Province> provinces = locationServiceImpl.getProvinceList();

        List<ProvinceResponse> responseList = provinces.stream()
                .map(ProvinceResponse::fromProvince)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                ObjectResponse.<List<ProvinceResponse>>builder()
                        .status(HttpStatus.OK)
                        .message("Successfully retrieved list of provinces/cities")
                        .data(responseList)
                        .build()
        );
    }

    @GetMapping("provinces/{id}")
    public ResponseEntity<ObjectResponse<ProvinceResponse>> getProvince(
            @Valid @PathVariable("id") Long id
    ) throws Exception {
        Province province = locationServiceImpl.getProvinceById(id);

        return ResponseEntity.ok(
                ObjectResponse.<ProvinceResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Successfully retrieved province/city with id " + id)
                        .data(ProvinceResponse.fromProvince(province))
                        .build()
        );
    }
}
