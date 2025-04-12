package com.project.webIT.controllers;

import com.project.webIT.dtos.response.ObjectResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BaseController<T,ID>{

    ResponseEntity<ObjectResponse<?>> create(@Valid T request, BindingResult result) throws Exception;

    @Transactional
    ResponseEntity<ObjectResponse<?>> update(@Valid @PathVariable("id") ID id, T request, BindingResult result) throws Exception;

    @GetMapping("/get")
    ResponseEntity<ObjectResponse<?>> getAll();

    @GetMapping("/get/{id}")
    ResponseEntity<ObjectResponse<?>> getById(@Valid @PathVariable("id") ID id) throws Exception;

    @DeleteMapping("/delete/{id}")
    @Transactional
    ResponseEntity<ObjectResponse<?>> deleteById(@Valid @PathVariable("id") ID id) throws Exception;

    @DeleteMapping("/delete")
    @Transactional
    ResponseEntity<ObjectResponse<?>> deleteByListId(@Valid @RequestBody List<ID> listId) throws Exception;
}

