package ccrm.exception;

public class MaxCreditLimitExceededException extends Exception {
    private final int maxCredits;
    private final int attemptedCredits;
    
    public MaxCreditLimitExceededException(String message, int maxCredits, int attemptedCredits) {
        super(message);
        this.maxCredits = maxCredits;
        this.attemptedCredits = attemptedCredits;
    }
    
    public int getMaxCredits() {
        return maxCredits;
    }
    
    public int getAttemptedCredits() {
        return attemptedCredits;
    }
}
