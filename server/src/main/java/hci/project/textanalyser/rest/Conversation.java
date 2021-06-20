package hci.project.textanalyser.rest;

import java.util.Objects;

public class Conversation {
    private final String userA;
    private final String userB;

    public Conversation(String userA, String userB) {
        this.userA = userA;
        this.userB = userB;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + userA.hashCode() + userB.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Conversation other = (Conversation) obj;
        return Objects.equals(userA, other.userA) && Objects.equals(userB, other.userB)
            || Objects.equals(userA, other.userB) && Objects.equals(userB, other.userA);
    }
}