package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(exclude = "parent")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHILD_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Parent parent;

    public void setParent(Parent parent) {

        // 기존 부모 제거
        if (this.parent != null) {
            this.parent.getChilds().remove(this);
        }

        // 부모 다시 연결
        this.parent = parent;
        // 부모에 child 객체 추가
        parent.getChilds().add(this); // this = Child

    }
}
