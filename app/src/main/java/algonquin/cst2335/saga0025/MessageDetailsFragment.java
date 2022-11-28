package algonquin.cst2335.saga0025;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.saga0025.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {
    ChatMessage selected;

    public MessageDetailsFragment(ChatMessage n) {
        selected = n;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.messageText.setText("Content: " + selected.getMessage());
        binding.timeText.setText("Time: " +selected.getTimeSent());
        binding.databaseText.setText("ID: " + selected.id);

        return binding.getRoot();
    }
}
