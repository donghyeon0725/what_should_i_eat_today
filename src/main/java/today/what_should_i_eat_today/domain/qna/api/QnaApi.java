package today.what_should_i_eat_today.domain.qna.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import today.what_should_i_eat_today.domain.qna.application.QnaService;
import today.what_should_i_eat_today.domain.qna.dto.QnaCommand;
import today.what_should_i_eat_today.domain.qna.dto.QnaResponseDto;
import today.what_should_i_eat_today.domain.qna.dto.QnaResponseDtoV1;
import today.what_should_i_eat_today.domain.qna.entity.Qna;
import today.what_should_i_eat_today.domain.qna.entity.QnaStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class QnaApi {
    private final QnaService qnaService;

    /**
     * Q&A 단 건 조회
     *
     * @param qnaId
     * @return
     */
    @GetMapping("/qnas/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getQna(@PathVariable("id") Long qnaId) {

        Qna qna = qnaService.findById(qnaId);

        return ResponseEntity.ok(new QnaResponseDto(qna));
    }

    /**
     * Q&A 리스트 조회
     *
     * @param pageable
     * @param title
     * @param qnaStatus
     * @return
     */
    @GetMapping("/qnas")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getQnas(@PageableDefault Pageable pageable,
                                     @RequestParam(required = false) String title,
                                     @RequestParam(required = false) QnaStatus qnaStatus
    ) {
        QnaCommand command = new QnaCommand();
        command.setTitle(title);
        command.setQnaStatus(qnaStatus);
        Page<Qna> qnas = qnaService.getQnaList(command, pageable);
        return ResponseEntity.ok(qnas.map(QnaResponseDtoV1::new));
    }
}
