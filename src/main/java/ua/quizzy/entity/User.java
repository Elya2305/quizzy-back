package ua.quizzy.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
    private String pictureUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        var other = (User) o;
        return Objects.equals(getUuid(), other.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
