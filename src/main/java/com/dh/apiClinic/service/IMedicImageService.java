package com.dh.apiClinic.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IMedicImageService {

    void uploadImage(Long medicId, MultipartFile file) throws IOException;
    byte[] getMedicImageBytes(Long medicId);
}
