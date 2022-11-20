package algonquin.cst2335.saga0025;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.saga0025.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.saga0025.databinding.RecieveMessageBinding;
import algonquin.cst2335.saga0025.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    private RecyclerView.Adapter adt;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    ChatRoomViewModel chatModel;
    ChatMessageDAO mDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Creating ViewBinding
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Creating a database
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "MessageDatabase").fallbackToDestructiveMigration().build();
        mDAO = db.cmDAO();

        //Running the database query in the separated thread
        if(messages == null)
        {
            chatModel.messages.setValue(messages = new ArrayList<>());
            //Creating a separate thread
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database
                binding.recyclerView.setAdapter( adt ); //You can then load the RecyclerView
            });
        }

        //Data kept in a viewModel so that the data survives screen rotation
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        //verify if the chatModel.messages variable has never been set before
        //The first time you come to the ChatRoom class you will have to initialize the ChatModel class
        if(messages == null)
        {
            chatModel.messages.postValue( messages = new ArrayList<>());
        }

        //OnClickListener for Send
        binding.sendButton.setOnClickListener(click -> {
            //Retrieving Message from the screen and making ChatMessage Obj
            @SuppressLint("SimpleDateFormat") ChatMessage newMessage = new ChatMessage(binding.textInput.getText().toString(),new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a").format(new Date()), true);
            //Adding into the ArrayList
            messages.add(newMessage);
            //notifying the adapter
            adt.notifyItemInserted(messages.size()-1);
            //clears the previous text
            binding.textInput.setText("");
            //Sending the ChatMessage to the database
            //Using a new thread
            Executors.newSingleThreadExecutor().execute(() -> mDAO.insertMessage(newMessage));
        });

        //OnClickListener for Receive
        binding.receiveButton.setOnClickListener(click -> {
            //Retrieving Message from the screen and making ChatMessage Obj
            ChatMessage newMessage = new ChatMessage(binding.textInput.getText().toString(),new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a").format(new Date()), false);
            //Adding into the ArrayList
            messages.add(newMessage);
            //notifying the adapter
            adt.notifyItemInserted(messages.size()-1);
            //clears the previous text
            binding.textInput.setText("");
            //Sending the ChatMessage to the database
            //Using a new thread
            Executors.newSingleThreadExecutor().execute(() -> mDAO.insertMessage(newMessage));
        });

        binding.recyclerView.setAdapter(adt = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType != 1) {
                    return new MyRowHolder(SentMessageBinding.inflate(getLayoutInflater()).getRoot());
                } else {
                    return new MyRowHolder(RecieveMessageBinding.inflate(getLayoutInflater()).getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.messageText.setText(messages.get(position).getMessage());
                holder.timeText.setText(messages.get(position).getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position){
                if (messages.get(position).getSentButton()) {
                    return 0;
                } else {
                    return 1;
                }
            }

        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk ->{
                int position = getAbsoluteAdapterPosition(); //telling which row (position) this row is currently in the adapter object
                //Making an Alert Box
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                builder.setMessage("Do you want to delete the message: " + messageText.getText())
                        .setTitle("Question:")
                        .setNegativeButton("No", ((dialog, which) -> {}))
                        .setPositiveButton("Yes", ((dialog, which) -> {
                            ChatMessage removeMessage = messages.get(position);
                            Executors.newSingleThreadExecutor().execute(() -> mDAO.deleteMessage(removeMessage));
                            messages.remove(position);
                            adt.notifyItemRemoved(position);
                            Snackbar.make(messageText, "You've Deleted the text" + position, Snackbar.LENGTH_LONG).setAction("Undo", click -> {
                                messages.add(position,removeMessage);
                                adt.notifyItemInserted(position);
                            }).show();
                        })).create().show();
            });
            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);
        }
    }
}