package ca.ulaval.glo2003.domain.entities;

public class Search {
    private final String name;
    private final SearchOpened searchOpened;

    public Search(String name, SearchOpened searchOpened) {
        this.name = name;
        this.searchOpened = searchOpened;
    }

    public String getName() {
        return name;
    }

    public SearchOpened getSearchOpened() {
        return searchOpened;
    }
}
