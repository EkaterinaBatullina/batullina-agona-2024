package org.example.api;

import org.example.dto.request.RoleRequest;
import org.example.dto.response.RoleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RequestMapping("/api/v1/roles")
public interface RoleApi {

    @GetMapping("/{role-id}")
    @ResponseStatus(HttpStatus.OK)
    RoleResponse getById(@PathVariable("role-id") UUID uuid);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Set<RoleResponse> getAll();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID create(@RequestBody RoleRequest roleRequest);

    @PutMapping("/{role-id}")
    ResponseEntity<Void> update(@PathVariable("role-id") UUID uuid, @RequestBody RoleRequest roleRequest);

    @DeleteMapping("/{role-id}")
    ResponseEntity<Void> delete(@PathVariable("role-id") UUID uuid);

    @PatchMapping("/{role-id}")
    ResponseEntity<Void> patch(@PathVariable("role-id") UUID uuid, @RequestBody RoleRequest roleRequest);

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    Set<RoleResponse> getByName(@RequestParam("name") String name);
}
