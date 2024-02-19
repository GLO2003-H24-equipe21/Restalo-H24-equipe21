package ca.ulaval.glo2003.domain.dtos;

import java.util.Objects;

public class SearchOpenedDto {
    public String from;
    public String to;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SearchOpenedDto) obj;
        return Objects.equals(this.from, that.from) && Objects.equals(this.to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
