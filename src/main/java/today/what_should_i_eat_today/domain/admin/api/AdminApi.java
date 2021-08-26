package today.what_should_i_eat_today.domain.admin.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import today.what_should_i_eat_today.domain.admin.application.AdminService;
import today.what_should_i_eat_today.domain.admin.dto.AdminLoginRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AdminApi {

    private final AdminService adminService;

    @PostMapping("/admin/login")
    public ResponseEntity<String> auth(@RequestBody AdminLoginRequest requestDto) {

        String token = adminService.login(requestDto.getEmail(), requestDto.getPassword());

        return ResponseEntity.ok(token);
    }

    // ROLE_USER
    @GetMapping("/admin/test")
    @Secured("ROLE_ADMIN")
    public String test() {
        return "어드민만 볼 수 있습니다";
    }
}
