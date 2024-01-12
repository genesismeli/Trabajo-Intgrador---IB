package com.dh.apiClinic.controller;

import com.dh.apiClinic.entity.CodeCie10;
import com.dh.apiClinic.service.ICodeCie10Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Cie10", description = "Operations about cie10")
@RestController
@RequestMapping("/cie10")

public class CodeCie10Controller {

    @Autowired
    private final ICodeCie10Service codeCie10Service;

    @Autowired
    public CodeCie10Controller(ICodeCie10Service codeCie10Service) {
        this.codeCie10Service = codeCie10Service;
    }

    @Operation(summary = "Add cie10")
    @GetMapping("/options")
    public ResponseEntity<List<CodeCie10>> getAllCodeCie10Options() {
        List<CodeCie10> codeCie10Options = codeCie10Service.getAllCodeCie10Options();
        return ResponseEntity.ok(codeCie10Options);
    }
}


