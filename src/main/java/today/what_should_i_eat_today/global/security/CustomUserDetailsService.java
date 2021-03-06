package today.what_should_i_eat_today.global.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.admin.dao.AdminRepository;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository userRepository;
    private final AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Optional<Member> optionalMember = userRepository.findByEmail(email);

        // member를 조회하고 없으면 admin을 탐색하는 메소드
        // member 와 admin 테이블에 같은 이메일이 존재하는 경우 문제가 생길 수 있다
        return optionalMember
                .map(UserPrincipal::create)
                .orElse(null);
//                .orElseGet(() -> UserPrincipal.create(
//                        adminRepository
//                                .findByEmail(email)
//                                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND)
//                                )
//                        )
//                );
    }

    @Transactional
    public UserDetails loadUserById(Long id) {

        Optional<Member> optionalMember = userRepository.findById(id);

        // member를 조회하고 없으면 admin을 탐색하는 메소드
        // member 와 admin 테이블에 같은 이메일이 존재하는 경우 문제가 생길 수 있다
        return optionalMember
                .map(UserPrincipal::create)
                .orElseGet(null);
//                .orElseGet(() -> UserPrincipal.create(
//                        adminRepository
//                                .findById(id)
//                                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND)
//                                )
//                        )
//                );
    }

    @Transactional
    public UserDetails loadAdminById(Long id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);

        return optionalAdmin
                .map(UserPrincipal::create)
                .orElseGet(null);
    }

    @Transactional
    public UserDetails loadAdminByUsername(String email)
            throws UsernameNotFoundException {

        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);

        return optionalAdmin
                .map(UserPrincipal::create)
                .orElse(null);
    }
}
