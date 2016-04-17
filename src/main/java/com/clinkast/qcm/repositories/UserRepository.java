package com.clinkast.qcm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clinkast.qcm.entities.Role;
import com.clinkast.qcm.entities.User;
import com.clinkast.qcm.services.dto.UserProfile;

/**
 * @author Oliver
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * find user's roles by his id
     * @param id
     * @return
     */
    @Query("SELECT u.roles FROM User u WHERE u.id=:id")
    List<Role> getRoles(@Param("id") int id);

    /**
     * find user's roles by his login and password
     * @param login
     * @param password
     * @return
     */
    @Query("SELECT u.roles FROM User u WHERE u.login=:login AND u.password=:password")
    List<Role> getRoles(@Param("login") String login, @Param("password") String password);

    /**
     * find user by his login
     * @param login
     * @return user
     */
    @Query("SELECT u FROM User u WHERE u.login=:login")
    User findByLogin(@Param("login") String login);

    /**
     * update user profile's informations by his id
     * @param id
     * @param userProfile
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.firstname = :#{#profile.firstname} ," + " u.lastname = :#{#profile.lastname} WHERE u.id= :id")
    int updateProfile(@Param("id") int id, @Param("profile") UserProfile userProfile);

    /**
     * update user password by his id
     * @param id
     * @param password
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
    int updatePassword(@Param("id") int id, @Param("password") String password);

    /**
     * update the password of an authenticated user
     * @param password
     * @return number of rows updated
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.password = :password WHERE u.login = :#{principal.user.login}")
    int updatePasswordByLogin(@Param("password") String password);

    /**
     * get user's password by his id
     * @param id
     * @return password
     */
    @Query("SELECT u.password FROM User u WHERE u.id = :id")
    String getPassword(@Param("id") int id);
}
