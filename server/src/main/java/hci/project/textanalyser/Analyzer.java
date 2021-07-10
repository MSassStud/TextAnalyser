package hci.project.textanalyser;

public interface Analyzer<S, R> {

    public R analyze(S subject);
}
