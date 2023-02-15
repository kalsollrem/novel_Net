package com.project.novelnet.repository;

import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public interface MasterMapper {
    public List<Map<String, Object>> novelShingo();
}
