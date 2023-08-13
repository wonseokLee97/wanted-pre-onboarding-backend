package com.wanted.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

//
@Entity // 테이블 자동 매핑
@AllArgsConstructor // 모든 필드에 대한 생성자 자동생성
@NoArgsConstructor // 파라미터가 없는 기본 생성자 자동 생성
@ToString
@Getter
@Builder // Builder 패턴을 자동으로 생성
@Table(name = "post") // 데이터베이스 테이블의 이름을 지정
// null 이 아닌 필드들을 기준으로 쿼리문 생성
@DynamicInsert
@DynamicUpdate
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
}
