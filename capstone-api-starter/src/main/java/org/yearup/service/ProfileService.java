package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Profile;
import org.yearup.repository.ProfileRepository;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile create(Profile profile) {
        return profileRepository.save(profile);
    }

    public Profile findProfileByUserId(int userId) {

        return profileRepository.findById(userId).orElseThrow(() -> new RuntimeException("No profile found."));
    }

    // updates an existing user profile
    public Profile updateProfile(Profile profile, int userId) {
        // retrieve existing profile or throw exception if it does not exist
        Profile existing = profileRepository.findById(userId).orElseThrow(() -> new RuntimeException("No profile found"));

        // update profile fields with new values
        existing.setFirstName(profile.getFirstName());
        existing.setLastName(profile.getLastName());
        existing.setAddress(profile.getAddress());
        existing.setCity(profile.getCity());
        existing.setEmail(profile.getEmail());
        existing.setPhone(profile.getPhone());
        existing.setZip(profile.getZip());
        existing.setState(profile.getState());

        return profileRepository.save(existing);
    }
}
