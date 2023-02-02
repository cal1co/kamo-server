package com.kamo.friends;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kamo.kamouser.KamoUser;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
@Transactional(readOnly=true)
public interface FriendRepository extends JpaRepository<KamoFriend, Long>{
    List<KamoFriend> findByUser1OrUser2(KamoUser user, KamoUser user2);
}
