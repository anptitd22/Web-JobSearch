package com.project.webIT.controllers;

import com.project.webIT.models.District;
import com.project.webIT.models.Province;
import com.project.webIT.response.ResponseObject;
import com.project.webIT.response.locations.ProvinceResponse;
import com.project.webIT.services.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseObject> getProvinceList(){
        List<Province> provinces = locationService.getProvinceList();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Bạn đã lấy danh sách thành phố/tỉnh thành cônng")
                        .data(provinces
                                .stream().map(ProvinceResponse::fromProvince)
                                .collect(Collectors.toList()))
                        .build()
        );
    }

    @GetMapping("provinces/{id}")
    public ResponseEntity<ResponseObject> getProvince(
            @Valid @PathVariable("id") Long id
    ) throws Exception{
        Province province = locationService.getProvinceById(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Bạn đã chọn tỉnh/thành phố: "+id)
                        .data(province).build()
        );
    }

}
