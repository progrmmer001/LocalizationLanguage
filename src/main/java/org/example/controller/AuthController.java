package org.example.controller;

import org.example.config.beans.SessionUser;
import org.example.model.AuthUser;
import org.example.model.MyFile;
import org.example.repository.FileDao;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SessionUser sessionUser;
    @Autowired
    private FileDao fileDao;

    @GetMapping("/login")
    public String logIn(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("errormessage", error);
        return "auth";
    }

    @GetMapping("/sign")
    public String sign() {
        return "SignIn";
    }

    @PostMapping("/signIn")
    public String signIn(@ModelAttribute AuthUser authUser, @RequestParam(name = "file") MultipartFile file) throws IOException {
        String generatedName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path path = Path.of("C:/Users/hp/Desktop/Dasturlash Darslar Java/FilesAI/" + generatedName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        MyFile myFile = MyFile.builder()
                .originalFileName(file.getOriginalFilename())
                .codeFileName(generatedName)
                .sizeFile(file.getSize())
                .mediaType(file.getContentType())
                .build();
        fileDao.add(myFile);
        authUser.setRole("USER");
        System.out.println(file.getOriginalFilename());
        AuthUser authUser1 = AuthUser.builder()
                .username(authUser.getUsername())
                .password(passwordEncoder.encode(authUser.getPassword()))
                .role("USER")
                .isBlocked(false)
                .pictureGeneratedName(generatedName)
                .build();
        userRepository.save(authUser1);
        return "redirect:/auth/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/blockUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String blockUser(@PathVariable(name = "id") Long user_id) {
        userRepository.blocked(user_id);
        return "redirect:/auth/allUsers";
    }

    @GetMapping("/unblockUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String unblockUser(@PathVariable(name = "id") Long user_id) {
        userRepository.unblocked(user_id);
        return "redirect:/auth/allUsers";
    }

    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView("Admin");
        List<AuthUser> all = userRepository.getAllUsers();
        return modelAndView.addObject("users", all);
    }
}
