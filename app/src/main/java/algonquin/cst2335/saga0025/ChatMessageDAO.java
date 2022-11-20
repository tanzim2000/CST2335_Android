package algonquin.cst2335.saga0025;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatMessageDAO {
    @Insert
    void insertMessage(ChatMessage m); //[C]RUD
    @Query("Select * from ChatMessage")
    List<ChatMessage> getAllMessages(); //C[R]UD
    @Delete
    void deleteMessage(ChatMessage m); //CRU[D]
}
