package today.what_should_i_eat_today.domain.admin.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.admin.dao.AdminRepository;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;
import today.what_should_i_eat_today.global.security.TokenProvider;
import today.what_should_i_eat_today.global.security.UserPrincipal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final TokenProvider tokenProvider;
    private final AdminRepository adminRepository;

    public String login(String email, String password) {

        String dbEmail = adminRepository.findAll().get(0).getEmail();
        System.err.println("requestEmail= " + email);
        System.err.println("dbEmail= " + dbEmail);

        Admin admin = adminRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        boolean matchedPassword = admin.matchPassword(password);

        if (!matchedPassword) {
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);
        }

        return tokenProvider.createToken(UserPrincipal.create(admin));
    }
}
