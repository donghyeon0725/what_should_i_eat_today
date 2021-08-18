package today.what_should_i_eat_today.domain.member.entity;

import lombok.*;
import today.what_should_i_eat_today.domain.model.AuthProvider;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profileImg;

    private String email;

    private String name;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;


    public void updateNameAndImage(String name, String imageUrl) {
        this.name = name;
        this.profileImg = imageUrl;
    }
}
