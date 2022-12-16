package com.project.novelnet.service;

import com.google.gson.JsonObject;
import com.project.novelnet.repository.NovelRepository;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class NovelEditService {
    private final NovelRepository novelRepository;

    public NovelEditService(NovelRepository novelRepository) {
        this.novelRepository = novelRepository;
    }

}
