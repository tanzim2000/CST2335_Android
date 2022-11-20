package algonquin.cst2335.saga0025;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.saga0025.data.ChatRoomViewModel;
import algonquin.cst2335.saga0025.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.saga0025.databinding.RecieveMessageBinding;
import algonquin.cst2335.saga0025.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    private RecyclerView.Adapter myAdapter;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    ChatRoomViewModel chatModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        messages = chatModel.messages.getValue();
        //verify if the chatModel.messages variable has never been set before
        //The first time you come to the ChatRoom class you will have to initialize the ChatModel class
        if(messages == null)
        {
            chatModel.messages.postValue( messages = new ArrayList<ChatMessage>());
        }

        binding.sendButton.setOnClickListener(click -> {
            messages.add(new ChatMessage(binding.textInput.getText().toString(),new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a").format(new Date()), true));
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText(""); //clears the previous text
        });

        binding.receiveButton.setOnClickListener(click -> {
            messages.add(new ChatMessage(binding.textInput.getText().toString(),new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a").format(new Date()), false));
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText(""); //clears the previous text
        });

        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
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
}
class MyRowHolder extends RecyclerView.ViewHolder {
    TextView messageText;
    TextView timeText;
    public MyRowHolder(@NonNull View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.messageText);
        timeText = itemView.findViewById(R.id.timeText);
    }
}