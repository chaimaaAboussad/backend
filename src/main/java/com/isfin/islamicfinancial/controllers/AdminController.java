package com.isfin.islamicfinancial.controllers;

import com.isfin.islamicfinancial.entities.Admin;
import com.isfin.islamicfinancial.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ---------------------------
    // 1️⃣ Get all admins
    // ---------------------------
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    // ---------------------------
    // 2️⃣ Get admin by ID
    // ---------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ---------------------------
    // 3️⃣ Create new admin
    // ---------------------------
    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminService.saveAdmin(admin);
        return ResponseEntity.ok(savedAdmin);
    }

    // ---------------------------
    // 4️⃣ Update existing admin
    // ---------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        return adminService.getAdminById(id)
                .map(existing -> {
                    existing.setUsername(admin.getUsername());
                    existing.setEmail(admin.getEmail());
                    existing.setPassword(admin.getPassword()); // consider encoding
                    Admin updated = adminService.saveAdmin(existing);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ---------------------------
    // 5️⃣ Delete admin by ID
    // ---------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        if (adminService.getAdminById(id).isPresent()) {
            adminService.deleteAdmin(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
