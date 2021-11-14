package today.what_should_i_eat_today.domain.tag.entity;

import lombok.*;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;
import today.what_should_i_eat_today.domain.model.Status;
import today.what_should_i_eat_today.domain.member.entity.Member;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Admin admin;

    private String name;

    @Enumerated(EnumType.STRING)
    private TagStatus status;


}
