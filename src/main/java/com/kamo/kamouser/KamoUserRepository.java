package com.kamo.kamouser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface KamoUserRepository extends JpaRepository<KamoUser, Integer> {

    Optional<KamoUser> findByEmail(String email);
    Optional<KamoUser> findByUsername(String email);

}
