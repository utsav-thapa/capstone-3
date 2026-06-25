package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/profile")
@CrossOrigin(origins = "*")
public class ProfileController {

    private ProfileService profileService;
    private UserService userService;

    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Profile> getProfile(Principal principal) {
        String userName = principal.getName();

        User user = userService.getByUserName(userName);
        int userId = user.getId();

        Profile profile = profileService.findProfileByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(profile);
    }

    @PutMapping
    public ResponseEntity<Profile> updateProfile (Principal principal, @RequestBody Profile profile) {
        String userName = principal.getName();

        User user = userService.getByUserName(userName);
        int userId = user.getId();
        Profile profile1 = profileService.updateProfile(profile,userId);

        return ResponseEntity.status(HttpStatus.OK).body(profile1);

    }


}
