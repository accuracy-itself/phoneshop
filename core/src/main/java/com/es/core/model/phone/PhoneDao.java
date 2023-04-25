package com.es.core.model.phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findAll(String query, SortField sortField, SortOrder sortOrder, int offset, int limit);
    int countAvailable(String query);
}
