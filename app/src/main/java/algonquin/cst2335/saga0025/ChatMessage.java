package algonquin.cst2335.saga0025;

/**
 * Class representing one chat message
 * @author Tanzim Ahmed Sagar
 * @version 1.0
 */
public class ChatMessage {
    private String message;
    private String timeSent;
    private boolean isSentButton;

    /**
     * Default Constructors
     * @param m message
     * @param t timeSent
     * @param sent sent button state
     */
    ChatMessage(String m, String t, boolean sent)
    {
        message = m;
        timeSent = t;
        isSentButton = sent;
    }

    /**
     * Accessor for {@code message}
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Mutator for {@code message}
     * @param message message text
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Accessor for {@code timeSent}
     * @return timeSent
     */
    public String getTimeSent() {
        return timeSent;
    }

    /**
     * Mutator for {@code timeSent}
     * @param timeSent message time
     */
    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    /**
     * Accessor for {@code isSentButton}
     * @return isSentButton
     */
    public boolean getSentButton() {
        return isSentButton;
    }

    /**
     * Mutator for {@code sentButton}
     * @param sentButton {@code boolean}
     */
    public void setSentButton(boolean sentButton) {
        isSentButton = sentButton;
    }
}
