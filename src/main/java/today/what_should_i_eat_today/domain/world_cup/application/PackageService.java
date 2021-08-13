package today.what_should_i_eat_today.domain.world_cup.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.world_cup.dao.PackageRepository;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PackageService {

    private final PackageRepository packageRepository;

    public Page<Package> getPackageList(String title, Pageable pageable) {
        return packageRepository.findBySubject(title, pageable);
    }


}
