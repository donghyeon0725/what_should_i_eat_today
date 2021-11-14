package today.what_should_i_eat_today.domain.world_cup.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.world_cup.dao.PackageRepository;
import today.what_should_i_eat_today.domain.world_cup.dao.QuestionPackageRepository;
import today.what_should_i_eat_today.domain.world_cup.dao.QuestionRepository;
import today.what_should_i_eat_today.domain.world_cup.dto.PackageRequest;
import today.what_should_i_eat_today.domain.world_cup.dto.QuestionRequest;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;
import today.what_should_i_eat_today.domain.world_cup.entity.PackageValidator;
import today.what_should_i_eat_today.domain.world_cup.entity.Question;
import today.what_should_i_eat_today.domain.world_cup.entity.QuestionPackage;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PackageService {

    private final PackageRepository packageRepository;

    private final QuestionRepository questionRepository;

    private final QuestionPackageRepository questionPackageRepository;

    private final PackageValidator packageValidator;

    public Package findById(Long id) {
        return packageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    public Page<Package> getPackageList(String title, Pageable pageable) {
        return packageRepository.findBySubject(title, pageable);
    }


    public Page<Package> getPackageListByQuestion(Long questionId, Pageable pageable) {
        return packageRepository.findByQuestionId(questionId, pageable);
    }

    @Transactional
    public Long createPackage(PackageRequest packageRequest) {
        Package packages = Package.builder().subject(packageRequest.getSubject()).build();

        packageRepository.save(packages);

        return packages.getId();
    }

    @Transactional
    public Long updatePackage(PackageRequest packageRequest) {
        Package packages = packageRepository.findById(packageRequest.getPackageId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        packages.changeSubject(packageRequest.getSubject());

        return packages.getId();
    }

    @Transactional
    public void addQuestionToPackage(PackageRequest packageRequest) {
        Question findQuestion = questionRepository.findById(packageRequest.getQuestionId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Package findPackage = packageRepository.findById(packageRequest.getPackageId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        findPackage.addQuestionMapping(QuestionPackage.builder().question(findQuestion).build());
    }

    @Transactional
    public void removeQuestionFromPackage(PackageRequest packageRequest) {
        Question findQuestion = questionRepository.findById(packageRequest.getQuestionId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        Package findPackage = packageRepository.findById(packageRequest.getPackageId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        QuestionPackage questionPackage = questionPackageRepository.findByPackagesAndQuestion(findPackage, findQuestion).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        findPackage.getQuestionPackages().remove(questionPackage);
    }

    @Transactional
    public void deletePackage(Long packageId) {

        Package packages = packageRepository.findById(packageId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        packageValidator.validateForDelete(packages);
        packageRepository.delete(packages);

    }



}
