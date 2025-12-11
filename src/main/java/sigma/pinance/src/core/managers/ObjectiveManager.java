package sigma.pinance.src.core.managers;

import lombok.Getter;
import sigma.pinance.src.core.budget.Objective;
import sigma.pinance.src.core.data.Member;
import sigma.pinance.src.core.exceptions.ObjectiveException;
import sigma.pinance.src.core.exceptions.Severity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * Manages a list of objectives and the current one selected, and can perform operations
 * on the current objective.
 */
public class ObjectiveManager {
    @Getter
    private static final ArrayList<Objective> objectives = new ArrayList<>();
    @Getter
    private static Objective currentObjective;

    public static Objective createNewObjective(String name, double startingBudget, LocalDate startDate, LocalDate endDate) {
        Objective newObjective = new Objective(name, startingBudget, startDate, endDate);
        objectives.add(newObjective);
        currentObjective = newObjective;
        return newObjective;
    }

    public static Objective curr() {
        return currentObjective;
    }

    public static void removeObjective(UUID objectiveID) {
        boolean successful = objectives.removeIf(obj -> obj.getObjectiveID().equals(objectiveID));
        if (!successful) {
            throw new ObjectiveException(Severity.LOW, "Objective with ID " +  objectiveID + " not found");
        }
    }

    public static Objective getObjective(UUID objectiveID) {
        Objective o = objectives.stream().filter(obj -> obj.getObjectiveID().equals(objectiveID))
                .findFirst().orElse(null);
        if (Objects.isNull(o)) {
            throw new ObjectiveException(Severity.LOW, "Objective with ID " + objectiveID + " not found");
        }
        return o;
    }

    public static void selectObjective(UUID objectiveID) {
        currentObjective = getObjective(objectiveID);
    }

    public static void validateCurrentObjective() {
        currentObjective.validate();
    }

    public static void addMember(String name, double expectedContribution) {
        currentObjective.addMember(name, expectedContribution);
    }

    public static ArrayList<Member> getMembers() {
        return currentObjective.getMembers();
    }

    public static Member getMember(UUID memberID) {
        return currentObjective.getMember(memberID);
    }

    public static void addContribution(double amount, LocalDate date, UUID memberID, String desc) {
        currentObjective.addContribution(amount, date, memberID, desc);
    }

    public ObjectiveManager() {}
}
