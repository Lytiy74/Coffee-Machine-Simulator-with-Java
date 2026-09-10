package machine;

import java.util.Arrays;
import java.util.Optional;

public enum MachineAction {
    BUY,
    FILL,
    TAKE,
    CLEAN,
    REMAINING,
    EXIT;

    public String getCommand() {
        return name().toLowerCase();
    }

    public static Optional<MachineAction> from(String input) {
        if (input == null || input.isBlank()) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(action -> action.name().equalsIgnoreCase(input.trim()))
                .findFirst();
    }
}
