package org.yearup.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.service.ProfileService;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/profile")
public class ProfileController {

    private ProfileService profileService;


}
