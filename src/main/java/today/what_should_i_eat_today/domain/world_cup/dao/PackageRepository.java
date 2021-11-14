package today.what_should_i_eat_today.domain.world_cup.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;

public interface PackageRepository extends JpaRepository<Package, Long>, PackageDslRepository {
}
