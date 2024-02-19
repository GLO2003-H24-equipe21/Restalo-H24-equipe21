package ca.ulaval.glo2003.domain.dto;

import java.util.Objects;

public class CustomerDto {
    public String name;
    public String email;
    public String phoneNumber;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CustomerDto) obj;
        return Objects.equals(this.name, that.name)
                && Objects.equals(this.email, that.email)
                && Objects.equals(this.phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, phoneNumber);
    }
}
