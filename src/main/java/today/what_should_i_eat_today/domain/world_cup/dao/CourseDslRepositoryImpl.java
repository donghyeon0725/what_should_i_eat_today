package today.what_should_i_eat_today.domain.world_cup.dao;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import today.what_should_i_eat_today.domain.world_cup.entity.Course;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;
import today.what_should_i_eat_today.domain.world_cup.entity.QCourse;
import today.what_should_i_eat_today.domain.world_cup.entity.QPackageCourse;

import static today.what_should_i_eat_today.domain.world_cup.entity.QCourse.course;
import static today.what_should_i_eat_today.domain.world_cup.entity.QPackageCourse.packageCourse;

@RequiredArgsConstructor
public class CourseDslRepositoryImpl implements CourseDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Course> findByPackage(Package packages, Pageable pageable) {

        QueryResults<Course> results = queryFactory
                .select(course)
                .from(packageCourse)
                .innerJoin(packageCourse.course, course)
                .where(packageCourse.packages.id.eq(packages.getId()))
                .fetchResults();


        return new PageImpl(results.getResults(), pageable, results.getTotal());
    }
}
