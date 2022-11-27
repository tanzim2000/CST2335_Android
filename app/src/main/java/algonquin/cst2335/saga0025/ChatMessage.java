package algonquin.cst2335.saga0025;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Class representing one chat message
 * @author Tanzim Ahmed Sagar
 * @version 1.0
 */
@Entity
public class ChatMessage {

    /*
     All that you need to do to prepare your object for going into a database:
     1. Add the @Entity annotation above the class to mark this as something that can go into a database
     2. Add @ColumnInfo(name="XXXX") to specify that this variable will go into a database column named XXXX
     3. Add a @PrimaryKey public int id; field
     */

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    public int id;
    @ColumnInfo(name = "Message")
    private String message;
    @ColumnInfo(name = "TimeSent")
    private String timeSent;
    @ColumnInfo(name = "SendOrRecieve")
    private boolean isSentButton;

    /**
     * Default Constructors
     * @param message Body of the message
     * @param timeSent Time of the message
     * @param isSentButton Send button state
     */
    ChatMessage(String message, String timeSent, boolean isSentButton)
    {
        this.message = message;
        this.timeSent = timeSent;
        this.isSentButton = isSentButton;
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
