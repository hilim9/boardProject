package org.koreait.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data @Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BoardData extends BaseMember {

    @Id @GeneratedValue
    private Long seq;

    @Column(length = 100, nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // N : 1
    @JoinColumn(name = "userNo")
    private Member member;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<HashTag> tags = new ArrayList<>();

}
